package cn.codesensi.yasuo.api.service.impl;

import cn.codesensi.yasuo.api.service.LoginService;
import cn.codesensi.yasuo.constants.CommonConst;
import cn.codesensi.yasuo.exception.LoginException;
import cn.codesensi.yasuo.pojo.dto.AccountDTO;
import cn.codesensi.yasuo.pojo.dto.LogoutDTO;
import cn.codesensi.yasuo.pojo.dto.TokenRefreshDTO;
import cn.codesensi.yasuo.pojo.entity.SysUser;
import cn.codesensi.yasuo.pojo.vo.LoginVO;
import cn.codesensi.yasuo.pojo.vo.TokenRefreshVO;
import cn.codesensi.yasuo.properties.CustomProperties;
import cn.codesensi.yasuo.sys.service.ISysUserService;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.temp.SaTempUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.ZoneOffset;
import java.util.List;

/**
 * 登录接口实现
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final CustomProperties customProperties;
    private final ISysUserService sysUserService;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 账号密码登录
     *
     * @param accountDTO 登录用户信息
     * @return 登录成功后信息
     */
    @Override
    public LoginVO loginAccount(@Validated @RequestBody AccountDTO accountDTO) {
        // 校验验证码
        if (customProperties.getCaptcha().getEnabled()) {
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
        Long refreshTokenTimeout = customProperties.getSecure().getRefreshTokenTimeout();
        String refreshToken = SaTempUtil.createToken(userId, refreshTokenTimeout);
        loginVO.setRefreshToken(refreshToken);
        // accessToken过期时间
        long accessTokenTimeout = StpUtil.getTokenTimeout();
        loginVO.setExpires(LocalDateTimeUtil.now().plusSeconds(accessTokenTimeout).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        // 其他信息
        loginVO.setUsername(sysUser.getUsername());
        loginVO.setNickname(sysUser.getNickname());
        loginVO.setAvatar(sysUser.getAvatar());
        // 角色信息
        List<String> roles = StpUtil.getRoleList();
        loginVO.setRoles(roles);
        // 权限
        List<String> perms = StpUtil.getPermissionList();
        loginVO.setPermissions(perms);

        // 异步记录登录成功日志
//        TaskManager.me().execute(LogRecordFactory.login(StpUtil.getLoginIdAsLong(), LoginType.LOGIN.getCode(), LoginMode.ACCOUNT.getCode(), ServletUtil.getRequest()));
        return loginVO;
    }

    /**
     * 刷新token
     *
     * @return 刷新token结果
     */
    @Override
    public TokenRefreshVO tokenRefresh(TokenRefreshDTO tokenRefreshDTO) {
        String refreshToken = tokenRefreshDTO.getRefreshToken();
        // accessToken过期时间
        long accessTokenTimeout = StpUtil.getTokenTimeout();
        // accessToken过期后才可以获取新值
        Object userId = SaTempUtil.parseToken(refreshToken);
        if (accessTokenTimeout <= CommonConst.ZERO_INT) {
            if (ObjUtil.isNull(userId)) {
                throw new LoginException("登录已失效");
            }
            // 登出
            StpUtil.logout();
            // 重新登录，生成新accessToken
            StpUtil.login(userId);
            // accessToken过期时间重新赋值
            accessTokenTimeout = StpUtil.getTokenTimeout();
        }

        TokenRefreshVO tokenRefreshVO = new TokenRefreshVO();
        tokenRefreshVO.setAccessToken(StpUtil.getTokenValue());
        tokenRefreshVO.setRefreshToken(refreshToken);
        tokenRefreshVO.setExpires(LocalDateTimeUtil.now().plusSeconds(accessTokenTimeout).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        return tokenRefreshVO;
    }

    /**
     * 退出登录
     */
    @Override
    public void logout(LogoutDTO logoutDTO) {
        String accessToken = logoutDTO.getAccessToken();
        if (StrUtil.isNotBlank(accessToken)) {
            Object userId = StpUtil.getLoginIdByToken(accessToken);
            if (ObjUtil.isNotNull(userId)) {
                StpUtil.logout(userId);
                // 异步记录登出成功日志
//                TaskManager.me().execute(LogRecordFactory.login(userId, LoginType.LOGOUT.getCode(), null, ServletUtil.getRequest()));
            }
        }
        // 同步删除refreshToken
        String refreshToken = logoutDTO.getRefreshToken();
        if (StrUtil.isNotBlank(refreshToken)) {
            SaTempUtil.deleteToken(refreshToken);
        }
    }

}
