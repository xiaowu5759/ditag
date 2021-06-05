package com.xiaowu5759.common.util.snowflake;

import cn.hutool.core.net.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 重新封装，方法utils
 *
 * @author xiaowu
 * @date 2021/3/20 9:40 AM
 */
public class SnowflakeGenerator {

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

    public static long genAtomId(long workerId, DefaultSnowflakeEnum snowflakeEnum) {
        DefaultSnowflake defaultSnowflake = DefaultSnowflake.getSingleton();
        defaultSnowflake.setCenterAndWorkerId(0, workerId, snowflakeEnum.getThemeId(), snowflakeEnum.getBusinessId());
        return defaultSnowflake.nextId();
    }

//    public static long genAtomId(DefaultSnowflakeEnum snowflakeEnum) {
//        DefaultSnowflake defaultSnowflake = DefaultSnowflake.getSingleton();
//        String redisWorkerKey = snowflakeEnum.name() + ":worker";
//        String redisNumKey = snowflakeEnum.name() + ":num";
//        String redisMapKey = snowflakeEnum.name() + ":map";
//        String localMac = NetUtil.getLocalMacAddress();
//        log.info("localMac={}", localMac);
//        long workerId;
//        generator.redisTemplate.opsForHash().get(redisMapKey, localMac);
//        if (generator.redisTemplate.opsForSet().isMember(redisWorkerKey, localMac)) {
//            try {
//                workerId = Long.valueOf(String.valueOf(generator.redisTemplate.opsForValue().get(redisNumKey)));
//            } catch (Exception e) {
//                log.info("【雪花算法】，{}获取workerId失败", snowflakeEnum.name());
//                workerId = 0L;
//            }
//        } else {
//            generator.redisTemplate.opsForSet().add(redisWorkerKey, localMac);
//            workerId = generator.redisTemplate.opsForValue().increment(redisNumKey, 1);
//            if (workerId > 15L) {
//                log.info("【雪花算法】，{}workerId超过15", snowflakeEnum.name());
//                workerId = 15;
//            }
//        }
//        defaultSnowflake.setCenterAndWorkerId(0, workerId, snowflakeEnum.getThemeId(), snowflakeEnum.getBusinessId());
//        return defaultSnowflake.nextId();
//    }


    // 通过redisTemplate 获取 workId


}
