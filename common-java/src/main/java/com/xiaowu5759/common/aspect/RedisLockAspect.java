package com.xiaowu5759.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author xiaowu
 * @date 2020/6/22 17:00
 */
@Aspect
@Component
public class RedisLockAspect {
    // 定义切点
    @Pointcut("execution(* com.bytebeats.spring4.aop.annotation.service.BankServiceImpl.*(..))")
    public void pointcutRedisLock() {
    }

    @Pointcut("execution(* com.bytebeats.spring4.aop.annotation.service.*ServiceImpl.*(..))")
    public void pointcutRedisLimit() {
    }

    // 定义操作
    @Before(value = "pointcut1() || myPointcut()")
    public void before(JoinPoint joinPoint){

        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());

        System.out.println(this.getClass().getSimpleName()+ " before execute:"+methodName+ " begin with "+args);
    }


}
