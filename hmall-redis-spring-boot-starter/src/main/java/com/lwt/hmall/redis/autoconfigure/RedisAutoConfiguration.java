package com.lwt.hmall.redis.autoconfigure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lwt.hmall.redis.properties.RedisProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ReflectionUtils;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Field;

/**
 * @Author lwt
 * @Date 2020/4/7 15:51
 * @Description
 */
@Configuration
@ConditionalOnProperty(value = "settings.redis.enable",havingValue = "false",matchIfMissing = true)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //设置键序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 此项必须配置，否则会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);过期
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        //设置值序列化器
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        return redisTemplate;
    }

    @Bean
    public JedisPool jedisPool(JedisConnectionFactory jedisConnectionFactory){
        Field poolField = ReflectionUtils.findField(JedisConnectionFactory.class, "pool");
        ReflectionUtils.makeAccessible(poolField);
        return  (JedisPool) ReflectionUtils.getField(poolField, jedisConnectionFactory);
    }
}
