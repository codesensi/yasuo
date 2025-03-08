package cn.codesensi.yasuo.config;

import cn.codesensi.yasuo.constant.Constant;
import cn.codesensi.yasuo.properties.YasuoProperties;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 拦截器配置
 *
 * @author codesensi
 * @since 2024/1/21 15:00
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SaTokenConfig implements WebMvcConfigurer {

    private final YasuoProperties yasuoProperties;

    /**
     * 注册 Sa-Token 路由拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 开发时可跳过鉴权
        if (!yasuoProperties.getSkipAuth()) {
            registry.addInterceptor(new SaInterceptor(handler -> {
                // 登录校验
                SaRouter.match(Constant.ROOT_PATH).notMatch(Constant.SWAGGER_PATH).check(r -> StpUtil.checkLogin());
                // 系统功能：超级管理员角色
                SaRouter.match(Constant.SYS_PATH).check(r -> StpUtil.checkRole(Constant.ROLE_ADMIN_CODE));
            })).addPathPatterns(Constant.ROOT_PATH);
        }
    }

    /**
     * Sa-Token 整合 jwt (Simple 简单模式)
     */
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }

}
