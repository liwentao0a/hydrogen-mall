package com.lwt.hmall.redis.cache;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * @Author lwt
 * @Date 2020/3/22 12:57
 * @Description
 */
public class CMPKeyGenerator implements KeyGenerator {

    private static String keySplitChar = ":";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        String className = target.getClass().getName();
        String methodName = method.getName();
        StringBuffer key = new StringBuffer();
        key.append(className)
                .append(keySplitChar)
                .append(methodName);

        if (params!=null&&params.length>0) {
            key.append(keySplitChar)
                    .append(DigestUtils.md5Hex(JSON.toJSONBytes(params)));
        }
        System.out.println(key.toString());
        return key.toString();
    }

}
