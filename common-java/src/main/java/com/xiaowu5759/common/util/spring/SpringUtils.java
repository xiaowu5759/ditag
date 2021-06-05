package com.xiaowu5759.common.util.spring;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ArrayUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Map;

/**
 * 在使用的地方 @Import(SpringUtil.class)
 * 且需要初始化 applicationContext 上下文
 *
 * @author xiaowu
 * @date 2021/4/20 1:40 PM
 */
@Component
public class SpringUtils implements ApplicationContextAware {

//    private static final Logger logger = LoggerFactory.getLogger(SpringUtils.class);

    private static ApplicationContext applicationContext;

    public SpringUtils() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicationContext注入,请在applicationContext.xml中定义SpringContextHolder");
        }
    }

//    public static <T> T getBean(String name) {
//        return applicationContext.getBean(name);
//    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

//    public static <T> T getBean(TypeReference<T> reference) {
//        ParameterizedType parameterizedType = (ParameterizedType)reference.getType();
//        Class<T> rawType = (Class)parameterizedType.getRawType();
//        Class<?>[] genericTypes = (Class[])Arrays.stream(parameterizedType.getActualTypeArguments()).map((type) -> {
//            return (Class)type;
//        }).toArray((x$0) -> {
//            return new Class[x$0];
//        });
//        String[] beanNames = applicationContext.getBeanNamesForType(ResolvableType.forClassWithGenerics(rawType, genericTypes));
//        return getBean(beanNames[0], rawType);
//    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return applicationContext.getBeansOfType(type);
    }

    public static String[] getBeanNamesForType(Class<?> type) {
        return applicationContext.getBeanNamesForType(type);
    }

    public static String getProperty(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

    public static String[] getActiveProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    public static String getActiveProfile() {
        String[] activeProfiles = getActiveProfiles();
        return ArrayUtil.isNotEmpty(activeProfiles) ? activeProfiles[0] : null;
    }

    public static <T> void registerBean(String beanName, T bean) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext)applicationContext;
        context.getBeanFactory().registerSingleton(beanName, bean);
    }
}
