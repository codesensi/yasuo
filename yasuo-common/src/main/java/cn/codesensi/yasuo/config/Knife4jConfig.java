package cn.codesensi.yasuo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j配置
 */
@Configuration
public class Knife4jConfig {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Yasuo后台管理系统")
                        .description("Yasuo后台管理系统API文档")
                        .contact(new Contact().name("codesensi"))
                        .version("1.0.0")
                );
    }
}
