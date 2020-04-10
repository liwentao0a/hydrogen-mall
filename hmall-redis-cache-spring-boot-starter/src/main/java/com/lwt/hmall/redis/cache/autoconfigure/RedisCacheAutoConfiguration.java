package com.lwt.hmall.redis.cache.autoconfigure;

import com.lwt.hmall.redis.cache.properties.RedisCacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @Author lwt
 * @Date 2020/4/8 21:54
 * @Description
 */
@Configuration
@ConditionalOnBean({RedisTemplate.class})
@ConditionalOnProperty(value = "settings.redis.cache.enable",havingValue = "false",matchIfMissing = true)
@EnableConfigurationProperties(RedisCacheProperties.class)
@EnableCaching
@EnableAspectJAutoProxy
public class RedisCacheAutoConfiguration extends CachingConfigurerSupport {

    public static final String CACHE_NAME_PREFIX="hmall:cache";

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
                .entryTtl(Duration.ofMinutes(10));
        return RedisCacheManager.builder(redisCacheWriter).cacheDefaults(redisCacheConfiguration).build();
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new CMPKeyGenerator();
    }

}
