package cn.codesensi.yasuo.config;

import cn.codesensi.yasuo.properties.CustomProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j配置
 */
@RequiredArgsConstructor
@Configuration
public class Knife4jConfig {

    private final CustomProperties customProperties;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(customProperties.getProject().getName())
                        .description(customProperties.getProject().getName() + "文档")
                        .contact(new Contact().name(customProperties.getProject().getAuthor()))
                        .version("v" + customProperties.getProject().getVersion())
                );
    }
}
