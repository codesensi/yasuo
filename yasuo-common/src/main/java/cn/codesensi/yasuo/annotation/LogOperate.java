package cn.codesensi.yasuo.annotation;

import cn.codesensi.yasuo.enums.OperateType;
import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Documented
public @interface LogOperate {

    /**
     * 日志类型
     */
    OperateType operateType() default OperateType.OTHER;

    /**
     * 日志描述
     */
    String description() default "";

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestParam() default true;

    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponseData() default true;

}
