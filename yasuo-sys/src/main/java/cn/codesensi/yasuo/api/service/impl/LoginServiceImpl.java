package cn.codesensi.yasuo.api.service.impl;

import cn.codesensi.yasuo.api.service.LoginService;
import cn.codesensi.yasuo.enums.CommonEnum;
import cn.codesensi.yasuo.enums.LoginMode;
import cn.codesensi.yasuo.enums.LoginType;
import cn.codesensi.yasuo.exception.LoginException;
import cn.codesensi.yasuo.ext.TaskManager;
import cn.codesensi.yasuo.factory.LogRecordFactory;
import cn.codesensi.yasuo.pojo.dto.AccountDTO;
import cn.codesensi.yasuo.pojo.vo.LoginVO;
import cn.codesensi.yasuo.properties.CaptchaProperties;
import cn.codesensi.yasuo.properties.SecureProperties;
import cn.codesensi.yasuo.pojo.entity.LogLogin;
import cn.codesensi.yasuo.pojo.entity.SysUser;
import cn.codesensi.yasuo.sys.service.ISysUserService;
import cn.codesensi.yasuo.util.Ip2regionUtil;
import cn.codesensi.yasuo.util.IpUtil;
import cn.codesensi.yasuo.util.ServletUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.temp.SaTempUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 登录接口实现
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final CaptchaProperties captchaProperties;
    private final ISysUserService sysUserService;
    private final StringRedisTemplate stringRedisTemplate;
    private final SecureProperties secureProperties;

    /**
     * 账号密码登录
     *
     * @param accountDTO 登录用户信息
     * @return 登录成功后信息
     */
    @Override
    public LoginVO loginAccount(@Validated @RequestBody AccountDTO accountDTO) {
        // 校验验证码
        if (captchaProperties.getEnabled()) {
            if (StrUtil.isBlank(accountDTO.getCaptchaKey())) {
                throw new LoginException("验证码唯一标识为空");
            }
            String captcha = accountDTO.getCaptcha();
            if (StrUtil.isBlank(captcha)) {
                throw new LoginException("验证码为空");
            }
            // 与缓存中的值对比
            String captchaCache = stringRedisTemplate.opsForValue().get(accountDTO.getCaptchaKey());
            if (StrUtil.isBlank(captchaCache)) {
                throw new LoginException("验证码不存在");
            }
            if (!captcha.equals(captchaCache)) {
                throw new LoginException("验证码错误");
            }
        }
        SysUser sysUser = sysUserService.lambdaQuery()
                .eq(SysUser::getUsername, accountDTO.getUsername())
                .one();
        if (ObjUtil.isNull(sysUser)) {
            throw new LoginException("账号不存在");
        }
        if (!BCrypt.checkpw(accountDTO.getPassword(), sysUser.getPassword())) {
            throw new LoginException("账号密码错误");
        }

        Long userId = sysUser.getId();
        // 校验账户是否封禁
        StpUtil.checkDisable(userId);
        // 登录
        StpUtil.login(userId);

        LoginVO loginVO = new LoginVO();
        loginVO.setAccessToken(StpUtil.getTokenValue());
        // 获取refreshToken
        Long refreshTokenTimeout = secureProperties.getRefreshTokenTimeout();
        String refreshToken = SaTempUtil.createToken(userId, refreshTokenTimeout);
        loginVO.setRefreshToken(refreshToken);
        // accessToken过期时间
        long tokenTimeout = StpUtil.getTokenTimeout();
        loginVO.setExpireTime(LocalDateTimeUtil.now().plusSeconds(tokenTimeout));
        // 其他信息
        loginVO.setUsername(sysUser.getUsername());
        loginVO.setNickname(sysUser.getNickname());
        loginVO.setAvatar(sysUser.getAvatar());
        // 角色信息
        List<String> roles = StpUtil.getRoleList();
        loginVO.setRoles(roles);
        // 权限
        List<String> perms = StpUtil.getPermissionList();
        loginVO.setPerms(perms);

        // 异步记录登录成功日志
        LogLogin logLogin = new LogLogin();
        logLogin.setType(LoginType.LOGIN.getCode());
        logLogin.setMode(LoginMode.ACCOUNT.getCode());
        logLogin.setLoginTime(LocalDateTime.now());
        String ipAddr = IpUtil.getIpAddr();
        logLogin.setIp(ipAddr);
        logLogin.setArea(Ip2regionUtil.search(ipAddr));
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtil.getUserAgent());
        logLogin.setOs(userAgent.getOperatingSystem().getName());
        logLogin.setDevice(userAgent.getOperatingSystem().getDeviceType().getName());
        logLogin.setBrowser(userAgent.getBrowser().getName());
        logLogin.setStatus(CommonEnum.OkOrFail.OK.getCode());
        logLogin.setCreator(StpUtil.getLoginIdAsLong());
        TaskManager.me().execute(LogRecordFactory.login(logLogin));
        return loginVO;
    }
}
