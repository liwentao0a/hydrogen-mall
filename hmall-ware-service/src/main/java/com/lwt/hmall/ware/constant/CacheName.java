package com.lwt.hmall.ware.constant;


import com.lwt.hmall.redis.cache.autoconfigure.RedisCacheAutoConfiguration;

/**
 * @Author lwt
 * @Date 2020/3/22 16:17
 * @Description
 */
public class CacheName {

    public static final String CACHE_NAME = RedisCacheAutoConfiguration.CACHE_NAME_PREFIX+":ware";
}
