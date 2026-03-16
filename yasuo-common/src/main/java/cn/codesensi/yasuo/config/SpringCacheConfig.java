package cn.codesensi.yasuo.config;

import cn.codesensi.yasuo.properties.CustomProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@RequiredArgsConstructor
@Configuration
public class SpringCacheConfig {

    private final CustomProperties customProperties;

    /**
     * 解决@Cacheable缓存key存在双冒号::的问题
     */
    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(lettuceConnectionFactory)
                .cacheDefaults(RedisCacheConfiguration
                        .defaultCacheConfig()
                        .entryTtl(Duration.ofSeconds(customProperties.getCache().getRedisDefaultExpiresTime()))
                        .computePrefixWith(cacheName -> cacheName + ":"))
                .build();
    }
}
