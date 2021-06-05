package com.xiaowu5759.common.annotation;

import java.lang.annotation.*;

/**
 * @author xiaowu
 * @date 2020/6/20 15:38
 */
@Target(ElementType.METHOD)  //注解作用于方法上
@Retention(RetentionPolicy.RUNTIME) //保留到运行时
@Inherited  //允许继承
public @interface RedisLock {
}
