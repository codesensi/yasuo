package cn.codesensi.yasuo.enums;

import cn.codesensi.yasuo.constants.CommonConst;
import lombok.Getter;

/**
 * 通用枚举
 */
public class CommonEnum {

    /**
     * 是/否
     * 1-是
     * 0-否
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
     * 状态枚举
     * 0-启用
     * 1-禁用
     */
    @Getter
    public enum StatusEnum {
        ENABLE(CommonConst.ZERO_INT, "启用"),
        DISABLE(CommonConst.ONE_INT, "禁用"),
        ;

        /**
         * 码值
         */
        private final Integer code;

        /**
         * 说明
         */
        private final String message;

        StatusEnum(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    /**
     * 成功(正常)/失败(异常)
     * 1-成功(正常)
     * 0-失败(异常)
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
