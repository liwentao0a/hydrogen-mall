package com.lwt.hmall.redis.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Author lwt
 * @Date 2020/3/11 20:08
 * @Description
 */
public class RedisUtils {

    /**
     * 主数据系统标识
     */
    public static final String KEY_PREFIX = "hmall";
    /**
     * 分割字符，默认[:]，使用:可用于rdm分组查看
     */
    private static final String KEY_SPLIT_CHAR = ":";

    public static String keyBuilder(String module, String func, String... args) {
        return keyBuilder(null,module,func,args);
    }

    public static String keyBuilder(String prefix,String module, String func, String... args) {
        if (prefix==null){
            prefix=KEY_PREFIX;
        }
        StringBuffer key = new StringBuffer(prefix);
        key.append(KEY_SPLIT_CHAR).append(module);
        key.append(KEY_SPLIT_CHAR).append(func);
        for (String arg : args) {
            key.append(KEY_SPLIT_CHAR).append(arg);
        }
        return key.toString();
    }

    public static String keyBuilder(RedisEnum redisEnum, String... args) {
        return keyBuilder(redisEnum.getPrefix(),redisEnum.getModule(),redisEnum.getFunc(),args);
    }

    public static void scan(RedisTemplate redisTemplate, String pattern, Consumer<byte[]> consumer){
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Cursor<byte[]> scan = redisConnection.scan(
                        ScanOptions.scanOptions()
                                .count(1000)
                                .match(pattern)
                                .build());

                scan.forEachRemaining(consumer);
                return null;
            }
        });
    }

    public static List<String> keys(RedisTemplate redisTemplate, String pattern){
        List<String> keys = new ArrayList<>();
        scan(redisTemplate,pattern,new Consumer<byte[]>() {
            @Override
            public void accept(byte[] bytes) {
                String key = new String(bytes, StandardCharsets.UTF_8);
                keys.add(key);
            }
        });
        return keys;
    }

    public static Long fuzzyDelete(RedisTemplate redisTemplate, String pattern){
        List<String> keys = keys(redisTemplate, pattern);
        return redisTemplate.delete(keys);
    }
}
