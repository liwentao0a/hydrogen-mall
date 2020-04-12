package com.lwt.hmall.redis.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author lwt
 * @Date 2020/3/17 21:22
 * @Description
 */
public class RedisLock implements Lock {

    private RedisTemplate redisTemplate;
    private String prefix="hmall:lock";
    private String name=RedisLock.class.getName();
    private String lockToken;
    private RedisScript<Boolean> redisScript;

    public RedisLock(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        init();
    }

    public void init(){
        //初始化lua脚本
        redisScript = new RedisScript<Boolean>() {
            private String luaScript = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";

            @Override
            public String getSha1() {
                return DigestUtils.sha1Hex(luaScript);
            }

            @Override
            public Class<Boolean> getResultType() {
                return Boolean.class;
            }

            @Override
            public String getScriptAsString() {
                return luaScript;
            }
        };
    }

    private String getKey(){
        return (prefix==null?"":prefix+":")+name;
    }

    @Override
    public void lock() {
        this.lock(10);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        lockInterruptibly(10);
    }

    @Override
    public boolean tryLock() {
        return tryLock(10);
    }

    @Override
    public Condition newCondition() {
        Condition condition = new Condition() {
            @Override
            public void await() throws InterruptedException {
                //释放锁
                unlock();
                //线程等待
                Thread.currentThread().wait();
            }

            @Override
            public void awaitUninterruptibly() {
                //释放锁
                unlock();
                //线程等待，忽视线程中断
                while (Thread.interrupted()) {
                    try {
                        Thread.currentThread().wait();
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public long awaitNanos(long nanosTimeout) throws InterruptedException {
                long startTime = System.currentTimeMillis();
                //释放锁
                unlock();
                //线程等待
                Thread.currentThread().wait(nanosTimeout);
                long endTime = System.currentTimeMillis();
                //返回剩余时间
                return nanosTimeout-(endTime-startTime);
            }

            @Override
            public boolean await(long time, TimeUnit unit) throws InterruptedException {
                long startTime = System.currentTimeMillis();
                //释放锁
                unlock();
                //线程等待
                long deadline = TimeUnit.MILLISECONDS.convert(time, unit);
                Thread.currentThread().wait(deadline);
                long endTime = System.currentTimeMillis();
                //如果没有到指定时间就被通知，则返回true，否则表示到了指定时间，返回返回false。
                return (endTime-startTime)<deadline;
            }

            @Override
            public boolean awaitUntil(Date deadline) throws InterruptedException {
                long deadlineTime = deadline.getTime();
                //释放锁
                unlock();
                //线程等待
                Thread.currentThread().wait(deadlineTime);
                long endTime = System.currentTimeMillis();
                //如果没有到指定时间就被通知，则返回true，否则表示到了指定时间，返回返回false。
                return endTime>deadlineTime;
            }

            @Override
            public void signal() {
                //上锁
                lock();
                //唤醒线程
                Thread.currentThread().notify();
            }

            @Override
            public void signalAll() {
                //上锁
                lock();
                //唤醒所有线程
                Thread.currentThread().notifyAll();
            }
        };
        return condition;
    }

    @Override
    public void unlock() {
        ArrayList<String> keys = new ArrayList<>();
        keys.add(getKey());
        redisTemplate.execute(redisScript, keys, lockToken);
    }

    /**
     * 上锁
     * @param seconds
     */
    public void lock(long seconds){
        this.lock(seconds,TimeUnit.SECONDS);
    }

    /**
     * 上锁
     * @param l
     * @param timeUnit
     */
    public void lock(long l,TimeUnit timeUnit){
        lockToken = UUID.randomUUID().toString();
        while (!tryLock(l,timeUnit,lockToken)){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//                Thread.yield();
        }
    }

    /**
     * 上锁
     * @param seconds
     */
    public void lockInterruptibly(long seconds) throws InterruptedException {
        this.lockInterruptibly(seconds,TimeUnit.SECONDS);
    }

    /**
     * 上锁
     * @param l
     * @param timeUnit
     */
    public void lockInterruptibly(long l,TimeUnit timeUnit) throws InterruptedException {
        lockToken = UUID.randomUUID().toString();
        while (!tryLock(l,timeUnit,lockToken)){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//                Thread.yield();
            if (Thread.interrupted())
                throw new InterruptedException();
        }
    }

    /**
     * 尝试上锁
     * @param seconds
     */
    public boolean tryLock(long seconds){
        return this.tryLock(seconds,TimeUnit.SECONDS);
    }

    /**
     * 尝试上锁
     * @param l
     * @param timeUnit
     * @return
     */
    public boolean tryLock(long l,TimeUnit timeUnit){
        lockToken = UUID.randomUUID().toString();
        return this.tryLock(l,timeUnit,lockToken);
    }

    /**
     * 尝试上锁
     * @param l
     * @param timeUnit
     * @return
     */
    private boolean tryLock(long l,TimeUnit timeUnit,String lockToken){
        this.lockToken=lockToken;
        return l==-1?redisTemplate.opsForValue().setIfAbsent(getKey(), lockToken):redisTemplate.opsForValue().setIfAbsent(getKey(), lockToken, l, timeUnit);
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLockToken() {
        return lockToken;
    }

    public void setLockToken(String lockToken) {
        this.lockToken = lockToken;
    }

    public RedisScript<Boolean> getRedisScript() {
        return redisScript;
    }

    public void setRedisScript(RedisScript<Boolean> redisScript) {
        this.redisScript = redisScript;
    }
}
