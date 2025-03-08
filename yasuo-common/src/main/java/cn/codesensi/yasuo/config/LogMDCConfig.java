package cn.codesensi.yasuo.config;

import cn.codesensi.yasuo.constant.Constant;
import cn.codesensi.yasuo.interceptor.LogMDCInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 日志拦截器配置
 *
 * @author codesensi
 * @since 2024/1/21 15:00
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class LogMDCConfig implements WebMvcConfigurer {

    /**
     * 注册日志拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogMDCInterceptor()).addPathPatterns(Constant.ROOT_PATH);
    }

}
