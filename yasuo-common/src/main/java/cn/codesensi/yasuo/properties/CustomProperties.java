package cn.codesensi.yasuo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {

    /**
     * 项目配置
     */
    private Project project;

    /**
     * 安全配置
     */
    private Secure secure;

    /**
     * 缓存配置
     */
    private Cache cache;

    /**
     * 验证码配置
     */
    private Captcha captcha;

    /**
     * 项目配置
     */
    @Data
    public static class Project {
        /**
         * 名称
         */
        private String name;

        /**
         * 版本
         */
        private String version;

        /**
         * 负责人
         */
        private String author;

        /**
         * 版权
         */
        private String copyright;
    }

    /**
     * 安全配置
     */
    @Data
    public static class Secure {
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

    /**
     * 缓存配置
     */
    @Data
    public static class Cache {
        /**
         * redis默认过期时间（单位：秒）
         */
        private Long redisDefaultExpiresTime;
    }

    /**
     * 验证码配置
     */
    @Data
    public static class Captcha {
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

}
