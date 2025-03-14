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
         * 码值
         */
        private final Integer code;

        /**
         * 说明
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
         * 码值
         */
        private final Integer code;

        /**
         * 说明
         */
        private final String message;

        EnableOrDisable(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    /**
     * 成功(正常)/失败(异常)
     */
    @Getter
    public enum OkOrFail {

        OK(CommonConst.ONE_INT, "成功(正常)"),
        FAIL(CommonConst.ZERO_INT, "失败(异常)"),
        ;

        /**
         * 码值
         */
        private final Integer code;

        /**
         * 说明
         */
        private final String message;

        OkOrFail(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
