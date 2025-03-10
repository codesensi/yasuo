package cn.codesensi.yasuo.enums;

import cn.codesensi.yasuo.constants.CommonConst;
import lombok.Getter;

/**
 * 通用枚举
 */
public class CommonEnum {

    /**
     * 是/否
     */
    @Getter
    public enum YesOrNo {

        YES(CommonConst.ONE_INT, "是"),
        NO(CommonConst.ZERO_INT, "否"),
        ;

        /**
         * 验证码类型
         */
        private final Integer code;

        /**
         * 枚举说明
         */
        private final String message;

        YesOrNo(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    /**
     * 启用/禁用
     */
    @Getter
    public enum EnableOrDisable {

        ENABLE(CommonConst.ONE_INT, "启用"),
        DISABLE(CommonConst.ZERO_INT, "禁用"),
        ;

        /**
         * 验证码类型
         */
        private final Integer code;

        /**
         * 枚举说明
         */
        private final String message;

        EnableOrDisable(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
