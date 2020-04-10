package com.xvls.alexander.aspect;

import com.xvls.alexander.utils.JacksonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Aspect
@Order(5)
@Component
public class RequestCountRecordAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("execution(public * com.xvls.alexander.Controller..*.*(..))")
    public void controllerRecord(){}

    /**
     * 记录访问次数，放入到redis中
     * @param joinPoint
     */
    @Before("controllerRecord()")
    public void doBefore(JoinPoint joinPoint){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        String key = "requireRecord"+date;
        if(stringRedisTemplate.hasKey(key)){//如果键存在
            Integer count = Integer.valueOf(stringRedisTemplate.opsForValue().get(key));
            count = count + 1;
            //System.out.println(key+" count:"+count);
            stringRedisTemplate.opsForValue().set(key,count.toString(),365,TimeUnit.DAYS);
        }else {
            Integer count = 1;
            //System.out.println("new count:"+count);
            stringRedisTemplate.opsForValue().set(key,count.toString(),365,TimeUnit.DAYS);
        }
    }
}
