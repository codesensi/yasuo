package cn.codesensi.yasuo.annotation;

import org.springframework.web.bind.annotation.ResponseBody;
import java.lang.annotation.*;

/**
 * 是否构建接口统一响应对象注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ResponseBody
public @interface ApiResponseBody {
}
