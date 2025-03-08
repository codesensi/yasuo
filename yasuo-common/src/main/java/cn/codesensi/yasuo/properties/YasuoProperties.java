package cn.codesensi.yasuo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目配置实体类
 */
@Data
@Component
@ConfigurationProperties(prefix = "yasuo")
public class YasuoProperties {

    /**
     * 名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 版权
     */
    private String copyright;

    /**
     * 演示模式
     */
    private Boolean demoMode;

    /**
     * 跳过鉴权
     */
    private Boolean skipAuth;
}
