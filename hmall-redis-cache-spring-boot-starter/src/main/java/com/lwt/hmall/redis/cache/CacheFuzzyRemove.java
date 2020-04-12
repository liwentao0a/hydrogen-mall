package com.lwt.hmall.redis.cache;

import java.lang.annotation.*;

@Documented@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheFuzzyRemove {

    String[] value() default {};

    boolean isSpel() default true;

    String cacheName() default "";

}
