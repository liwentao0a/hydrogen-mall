package com.lwt.hmall.service.config.redis.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @Author lwt
 * @Date 2020/3/22 9:23
 * @Description
 */
@Configuration
@ConditionalOnClass({RedisTemplate.class,CacheManager.class})
@ConditionalOnProperty(value = "settings.service.redis.cache.enable",havingValue = "false",matchIfMissing = true)
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

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
