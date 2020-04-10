package com.lwt.hmall.redis.cache.autoconfigure;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.annotation.*;

@Documented@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheFuzzyRemove {

    String[] value() default {};

    boolean isSpel() default true;

    String cacheName() default "";

    Class<KeyGenerator> keyGenerator() default KeyGenerator.class;

    String params1() default "";

    String params2() default "";

    String[] params3() default "";

    boolean notStringKeyGenerator() default false;
}
