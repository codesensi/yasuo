package cn.codesensi.yasuo.enums;

import lombok.Getter;

/**
 * 菜单类型
 * 1-目录
 * 2-菜单
 * 3-按钮
 */
@Getter
public enum MenuType {
    CATALOG(1, "目录"),
    MENU(2, "菜单"),
    BUTTON(3, "按钮"),
    ;

    /**
     * 菜单类型编码
     */
    private final Integer code;

    /**
     * 菜单类型说明
     */
    private final String message;

    MenuType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
