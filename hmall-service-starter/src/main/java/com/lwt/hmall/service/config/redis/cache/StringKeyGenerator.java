package com.lwt.hmall.service.config.redis.cache;

import org.springframework.cache.interceptor.KeyGenerator;

public interface StringKeyGenerator extends KeyGenerator {

    Object generate(String className, String methodName, Object... params);
}
