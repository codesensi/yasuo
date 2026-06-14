package cn.codesensi.yasuo.enums;

import lombok.Getter;

/**
 * 操作类型
 * 0-未知
 * 1-新增
 * 2-更新
 * 3-查询
 * 4-删除
 */
@Getter
public enum OperateType {
    OTHER(0, "未知"),
    LOGIN(1, "登录"),
    LOGOUT(2, "登出"),
    INSERT(3, "新增"),
    UPDATE(4, "更新"),
    QUERY(5, "查询"),
    DELETE(6, "删除"),
    ;

    /**
     * 操作类型编码
     */
    private final Integer code;

    /**
     * 操作类型说明
     */
    private final String message;

    OperateType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
