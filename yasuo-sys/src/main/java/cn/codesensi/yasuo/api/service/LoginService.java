package cn.codesensi.yasuo.api.service;

import cn.codesensi.yasuo.pojo.dto.AccountDTO;
import cn.codesensi.yasuo.pojo.vo.LoginVO;

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
}
