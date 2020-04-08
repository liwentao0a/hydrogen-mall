package com.lwt.hmall.service.config.redis.cache;

import com.lwt.hmall.redis.util.RedisUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lwt
 * @Date 2020/3/22 11:47
 * @Description
 */
@Aspect
@Component
public class CacheFuzzyRemoveAspect {

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
        Class<KeyGenerator> keyGeneratorClass = annotation.keyGenerator();
        String params1 = annotation.params1();
        String params2 = annotation.params2();
        String[] params3 = annotation.params3();
        boolean notStringKeyGenerator = annotation.notStringKeyGenerator();
        //spel解析
        Object paramsObj1 = params1;
        Object paramsObj2 = params2;
        Object[] paramsObj3= params3;
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
            //解析params
            paramsObj1 = "".equals(params1)?"":parser.parseExpression(params1).getValue(context, Object.class);
            paramsObj2 = "".equals(params2)?"":parser.parseExpression(params2).getValue(context, Object.class);
            for (int i = 0; i < params3.length; i++) {
                String p = params3[i];
                paramsObj3[i]="".equals(p)?"":parser.parseExpression(p).getValue(context, Object.class);
            }
        }

        if (value.length>0) {
            for (String val : value) {
                String key = cacheName==""?"":cacheName+"::";;
                KeyGenerator keyGenerator = getKeyGenerator(keyGeneratorClass, paramsObj1, paramsObj2, paramsObj3);
                if (keyGenerator==null){
                    key+=val;
                }else {
                    if (keyGenerator instanceof StringKeyGenerator){
                        key+=((StringKeyGenerator) keyGenerator).generate((String) paramsObj1, (String) paramsObj2, paramsObj3);
                    }else {
                        key+=keyGenerator.generate(paramsObj1, (Method) paramsObj2,paramsObj3);
                    }
                }

                Long aLong = RedisUtils.fuzzyDelete(redisTemplate, key);
                System.out.println(key+"==="+aLong);
            }
        }
    }

    private KeyGenerator getKeyGenerator(Class<KeyGenerator> keyGeneratorClass, Object paramsObj1, Object paramsObj2, Object[] paramsObj3) {
        KeyGenerator keyGenerator = null;
        if (keyGeneratorClass==null){
            return null;
        }
        try {
            keyGenerator = classToKeyGeneratorMap.get(keyGeneratorClass);
            if (keyGenerator==null){
                keyGenerator = keyGeneratorClass.getDeclaredConstructor().newInstance();
                classToKeyGeneratorMap.put(keyGeneratorClass,keyGenerator);
            }
            if (keyGenerator instanceof StringKeyGenerator){
                ((StringKeyGenerator) keyGenerator).generate((String)paramsObj1,(String) paramsObj2,paramsObj3);
            }else {
                keyGenerator.generate(paramsObj1, (Method) paramsObj2,paramsObj3);
            }
        } catch (InstantiationException e) {
//            e.printStackTrace();
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
        } catch (InvocationTargetException e) {
//            e.printStackTrace();
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return keyGenerator;
    }

}
