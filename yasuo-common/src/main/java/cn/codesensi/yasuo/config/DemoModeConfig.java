package cn.codesensi.yasuo.config;

import cn.codesensi.yasuo.interceptor.DemoModeInterceptor;
import cn.codesensi.yasuo.properties.CustomProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 演示模式拦截器配置
 *
 * @author codesensi
 */
@Configuration
@RequiredArgsConstructor
public class DemoModeConfig implements WebMvcConfigurer {

    private final CustomProperties customProperties;

    /**
     * 注册演示模式拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DemoModeInterceptor(customProperties))
                .addPathPatterns("/**");
    }
}
