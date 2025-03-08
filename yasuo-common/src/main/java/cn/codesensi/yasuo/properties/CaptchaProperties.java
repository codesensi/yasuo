package cn.codesensi.yasuo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 验证码配置实体类
 */
@Data
@Component
@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {

    /**
     * 验证码开关
     */
    private Boolean enabled;

    /**
     * 验证码类型
     */
    private Type type;

    /**
     * 图形验证码类型
     */
    private ImageType imageType;

    /**
     * 验证码类型
     */
    public enum Type {
        /**
         * 短信验证码
         */
        SMS,
        /**
         * 图形验证码
         */
        IMAGE;


        Type() {
        }
    }

    /**
     * 图形验证码类型
     */
    public enum ImageType {
        /**
         * png
         */
        SPEC,
        /**
         * gif
         */
        GIF,
        /**
         * 中文
         */
        CHINESE,
        /**
         * 中文gif
         */
        CHINESE_GIF,
        /**
         * 算术
         */
        ARITHMETIC;

        ImageType() {
        }
    }
}
