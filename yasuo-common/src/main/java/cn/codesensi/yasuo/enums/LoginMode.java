package cn.codesensi.yasuo.enums;

import lombok.Getter;

/**
 * 登录方式
 * 0-未知
 * 1-账号密码
 * 2-手机验证码
 */
@Getter
public enum LoginMode {
    OTHER(0, "未知"),
    ACCOUNT(1, "账号密码"),
    MOBILE(2, "手机验证码"),
    ;

    /**
     * 操作类型编码
     */
    private final Integer code;

    /**
     * 操作类型说明
     */
    private final String message;

    LoginMode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
