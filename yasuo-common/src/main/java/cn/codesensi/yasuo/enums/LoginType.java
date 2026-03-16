package cn.codesensi.yasuo.enums;

import lombok.Getter;

/**
 * 登录类型
 * 0-未知
 * 1-登录
 * 2-登出
 * 3-封禁
 */
@Getter
public enum LoginType {
    OTHER(0, "未知"),
    LOGIN(1, "登录"),
    LOGOUT(2, "登出"),
    DISABLE(3, "封禁"),
    ;

    /**
     * 操作类型编码
     */
    private final Integer code;

    /**
     * 操作类型说明
     */
    private final String message;

    LoginType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
