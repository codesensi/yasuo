package cn.codesensi.yasuo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 安全配置实体类
 */
@Data
@Component
@ConfigurationProperties(prefix = "secure")
public class SecureProperties {

    /**
     * 演示模式
     */
    private Boolean demoMode;

    /**
     * 跳过鉴权
     */
    private Boolean skipAuth;

    /**
     * refresh-token过期时间（单位：秒）
     */
    private Long refreshTokenTimeout;
}
