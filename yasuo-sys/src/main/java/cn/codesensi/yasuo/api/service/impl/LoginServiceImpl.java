package cn.codesensi.yasuo.api.service.impl;

import cn.codesensi.yasuo.api.service.LoginService;
import cn.codesensi.yasuo.constants.CommonConst;
import cn.codesensi.yasuo.enums.CommonEnum;
import cn.codesensi.yasuo.enums.LoginMode;
import cn.codesensi.yasuo.enums.LoginType;
import cn.codesensi.yasuo.exception.LoginException;
import cn.codesensi.yasuo.ext.TaskManager;
import cn.codesensi.yasuo.factory.LogRecordFactory;
import cn.codesensi.yasuo.pojo.dto.AccountUserDTO;
import cn.codesensi.yasuo.pojo.vo.LoginSuccessVO;
import cn.codesensi.yasuo.properties.CaptchaProperties;
import cn.codesensi.yasuo.properties.SecureProperties;
import cn.codesensi.yasuo.properties.YasuoProperties;
import cn.codesensi.yasuo.sys.entity.LogLogin;
import cn.codesensi.yasuo.sys.entity.SysUser;
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
     * @param accountUserDTO 登录用户信息
     * @return 登录成功后信息
     */
    @Override
    public LoginSuccessVO loginAccount(@Validated @RequestBody AccountUserDTO accountUserDTO) {
        // 校验验证码
        if (captchaProperties.getEnabled()) {
            if (StrUtil.isBlank(accountUserDTO.getCaptchaKey())) {
                throw new LoginException("验证码唯一标识为空");
            }
            String captcha = accountUserDTO.getCaptcha();
            if (StrUtil.isBlank(captcha)) {
                throw new LoginException("验证码为空");
            }
            // 与缓存中的值对比
            String captchaCache = stringRedisTemplate.opsForValue().get(accountUserDTO.getCaptchaKey());
            if (StrUtil.isBlank(captchaCache)) {
                throw new LoginException("验证码不存在");
            }
            if (!captcha.equals(captchaCache)) {
                throw new LoginException("验证码错误");
            }
        }
        SysUser sysUser = sysUserService.lambdaQuery()
                .eq(SysUser::getUsername, accountUserDTO.getUsername())
                .one();
        if (ObjUtil.isNull(sysUser)) {
            throw new LoginException("账号不存在");
        }
        if (!BCrypt.checkpw(accountUserDTO.getPassword(), sysUser.getPassword())) {
            throw new LoginException("账号密码错误");
        }

        Long userId = sysUser.getId();
        // 校验账户是否封禁
        StpUtil.checkDisable(userId);
        // 登录
        StpUtil.login(userId);

        LoginSuccessVO loginSuccessVO = new LoginSuccessVO();
        loginSuccessVO.setAccessToken(StpUtil.getTokenValue());
        // 获取refreshToken
        Long refreshTokenTimeout = secureProperties.getRefreshTokenTimeout();
        String refreshToken = SaTempUtil.createToken(userId, refreshTokenTimeout);
        loginSuccessVO.setRefreshToken(refreshToken);
        // accessToken过期时间
        long tokenTimeout = StpUtil.getTokenTimeout();
        loginSuccessVO.setExpireTime(LocalDateTimeUtil.now().plusSeconds(tokenTimeout));
        // 其他信息
        loginSuccessVO.setUsername(sysUser.getUsername());
        loginSuccessVO.setNickname(sysUser.getNickname());
        loginSuccessVO.setAvatar(sysUser.getAvatar());
        // 角色信息
        List<String> roleList = StpUtil.getRoleList();
        loginSuccessVO.setRoles(roleList);
        // 权限
        List<String> permissionList = StpUtil.getPermissionList();
        loginSuccessVO.setPermissions(permissionList);

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
        return loginSuccessVO;
    }
}
