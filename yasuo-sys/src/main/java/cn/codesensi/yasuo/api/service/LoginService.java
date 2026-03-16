package cn.codesensi.yasuo.api.service;

import cn.codesensi.yasuo.pojo.dto.AccountDTO;
import cn.codesensi.yasuo.pojo.dto.LogoutDTO;
import cn.codesensi.yasuo.pojo.dto.TokenRefreshDTO;
import cn.codesensi.yasuo.pojo.vo.LoginVO;
import cn.codesensi.yasuo.pojo.vo.TokenRefreshVO;

/**
 * 登录接口
 */
public interface LoginService {

    /**
     * 账号密码登录
     *
     * @param accountDTO 登录用户信息
     * @return 登录成功后信息
     */
    LoginVO loginAccount(AccountDTO accountDTO);

    /**
     * 刷新token
     *
     * @return 刷新token结果
     */
    TokenRefreshVO tokenRefresh(TokenRefreshDTO tokenRefreshDTO);

    /**
     * 退出登录
     */
    void logout(LogoutDTO logoutDTO);
}
