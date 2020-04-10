package com.lwt.hmall.order.constant;

import com.lwt.hmall.redis.cache.autoconfigure.RedisCacheAutoConfiguration;

/**
 * @Author lwt
 * @Date 2020/3/22 16:31
 * @Description
 */
public class CacheName {

    public static final String CACHE_NAME= RedisCacheAutoConfiguration.CACHE_NAME_PREFIX+":order";
}
