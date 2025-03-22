package cn.codesensi.yasuo.api.controller;

import cn.codesensi.yasuo.annotation.ApiResponseBody;
import cn.codesensi.yasuo.annotation.LogOperate;
import cn.codesensi.yasuo.api.service.LoginService;
import cn.codesensi.yasuo.enums.OperateType;
import cn.codesensi.yasuo.pojo.dto.AccountDTO;
import cn.codesensi.yasuo.pojo.dto.LogoutDTO;
import cn.codesensi.yasuo.pojo.dto.TokenRefreshDTO;
import cn.codesensi.yasuo.pojo.vo.LoginVO;
import cn.codesensi.yasuo.pojo.vo.TokenRefreshVO;
import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录 前端控制器
 *
 * @author codesensi
 * @since 2024-07-21 11:09:56
 */
@ApiResponseBody
@RequiredArgsConstructor
@RestController
@Tag(name = "登录接口", description = "登录接口")
@RequestMapping()
public class LoginController {

    private final LoginService loginService;

    /**
     * 账号密码登录
     */
    @LogOperate(description = "账号密码登录")
    @SaIgnore
    @Operation(summary = "账号密码登录")
    @PostMapping("/login/account")
    public LoginVO loginAccount(@Validated @RequestBody AccountDTO accountDTO) {
        return loginService.loginAccount(accountDTO);
    }

    /**
     * 刷新token
     */
    @LogOperate(description = "刷新token")
    @SaIgnore
    @Operation(summary = "刷新token")
    @PostMapping("/token/refresh")
    public TokenRefreshVO tokenRefresh(@Validated @RequestBody TokenRefreshDTO tokenRefreshDTO) {
        return loginService.tokenRefresh(tokenRefreshDTO);
    }

    /**
     * 退出登录
     */
    @LogOperate(description = "退出登录")
    @SaIgnore
    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public void logout(@RequestBody LogoutDTO logoutDTO) {
        loginService.logout(logoutDTO);
    }

}
