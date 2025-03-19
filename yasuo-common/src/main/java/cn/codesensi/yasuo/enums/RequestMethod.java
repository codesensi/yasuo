package cn.codesensi.yasuo.enums;

import lombok.Getter;

/**
 * 请求方式
 * GET-GET
 * PUT-PUT
 * POST-POST
 * DELETE-DELETE
 * OPTION-OPTION
 */
@Getter
public enum RequestMethod {
    GET("GET", "GET"),
    PUT("PUT", "PUT"),
    POST("POST", "POST"),
    DELETE("DELETE", "DELETE"),
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
