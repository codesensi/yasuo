package cn.codesensi.yasuo.config;

import cn.codesensi.yasuo.properties.YasuoProperties;
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

    private final YasuoProperties yasuoProperties;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(yasuoProperties.getName())
                        .description(yasuoProperties.getName() + "文档")
                        .contact(new Contact().name(yasuoProperties.getAuthor()))
                        .version("v" + yasuoProperties.getVersion())
                );
    }
}
