package cn.codesensi.yasuo.enums;

import lombok.Getter;

/**
 * 请求方式
 */
@Getter
public enum RequestMethod {

    /**
     * GET请求
     */
    GET("GET", "GET"),

    /**
     * PUT请求
     */
    PUT("PUT", "PUT"),

    /**
     * POST请求
     */
    POST("POST", "POST"),

    /**
     * DELETE请求
     */
    DELETE("DELETE", "DELETE"),

    /**
     * OPTION请求
     */
    OPTION("OPTION", "OPTION"),
    ;

    /**
     * 请求方式编码
     */
    private final String code;

    /**
     * 请求方式说明
     */
    private final String message;

    RequestMethod(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
