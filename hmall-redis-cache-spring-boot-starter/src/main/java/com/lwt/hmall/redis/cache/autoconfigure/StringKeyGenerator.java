package com.lwt.hmall.redis.cache.autoconfigure;

import org.springframework.cache.interceptor.KeyGenerator;

public interface StringKeyGenerator extends KeyGenerator {

    Object generate(String className, String methodName, Object... params);
}
