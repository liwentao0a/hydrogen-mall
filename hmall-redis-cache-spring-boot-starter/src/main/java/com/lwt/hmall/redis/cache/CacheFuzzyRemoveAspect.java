package com.lwt.hmall.redis.cache;

import com.lwt.hmall.redis.util.RedisUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lwt
 * @Date 2020/3/22 11:47
 * @Description
 */
@Aspect
public class CacheFuzzyRemoveAspect {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private SpelExpressionParser parser = new SpelExpressionParser();

    private Map<Class,KeyGenerator> classToKeyGeneratorMap=new HashMap<>();


    @AfterReturning(value = "@annotation(CacheFuzzyRemove)",returning = "returning")
    public void cacheFuzzyRemove(JoinPoint point,Object returning){
        //获取注解
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        CacheFuzzyRemove annotation = method.getAnnotation(CacheFuzzyRemove.class);
        //获取注解值
        String[] value = annotation.value();
        boolean isSpel = annotation.isSpel();
        String cacheName = annotation.cacheName();
        //spel解析
        if (isSpel){
            //创建spel上下文
            EvaluationContext context = new StandardEvaluationContext();
            context.setVariable("target", point.getTarget());
            context.setVariable("targetName", point.getTarget().getClass().getName());
            context.setVariable("method", method);
            context.setVariable("methodName", method.getName());
            context.setVariable("params", point.getArgs());
            context.setVariable("returning", returning);
            //解析value
            for (int i = 0; i < value.length; i++) {
                String val = value[i];
                String parserVal = parser.parseExpression(val).getValue(context, String.class);
                value[i]=parserVal;
            }
        }

        if (value.length>0) {
            for (String val : value) {
                String key = (cacheName==""?"":cacheName+"::")+val;
                if (key!=null&&key.length()>0) {
                    Long aLong = RedisUtils.fuzzyDelete(redisTemplate, key);
                    logger.debug("redis模糊删除"+key+"，删除数量"+aLong);
                }
            }
        }
    }

}
