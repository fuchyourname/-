package com.and.music.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component
public class RedisExpirationAspect {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final long DEFAULT_EXPIRE_TIME = 1800; // 1 hour in seconds

    @Around("execution(* org.springframework.data.redis.core.RedisTemplate.opsForValue(..)) || " +
            "execution(* org.springframework.data.redis.core.RedisTemplate.opsForHash(..)) || " +
            "execution(* org.springframework.data.redis.core.RedisTemplate.opsForList(..)) || " +
            "execution(* org.springframework.data.redis.core.RedisTemplate.opsForSet(..)) || " +
            "execution(* org.springframework.data.redis.core.RedisTemplate.opsForZSet(..))")
    public Object setExpiration(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof String) {
            String key = (String) args[0];
            // 检查键是否已经设置了过期时间
            Long ttl = redisTemplate.getExpire(key);
            if (ttl == -1) { // -1 表示没有设置过期时间
                // 设置默认过期时间
                redisTemplate.expire(key, DEFAULT_EXPIRE_TIME, java.util.concurrent.TimeUnit.SECONDS);
            }
        }

        return result;
    }
}
