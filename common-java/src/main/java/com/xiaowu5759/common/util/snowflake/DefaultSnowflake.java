package com.xiaowu5759.common.util.snowflake;

/**
 * @author xiaowu
 * @date 2020/4/1 10:50
 * <p>
 * 目标秒级时间戳，添加主题id和业务功能id
 */
public class DefaultSnowflake extends AbstractSnowflake{
    /**
     * 涉及到可以配置的部分
     */
    private long centerBitNumbers;
    private long workerBitNumbers;
    private long centerId;
    private long workerId;
    private long themeId;
    private long businessId;

    /**
     * 双重校验锁生成单个生成器
     */
    private volatile static DefaultSnowflake defaultSnowflake;

    private DefaultSnowflake() {
    }

    public static DefaultSnowflake getSingleton() {
        if (defaultSnowflake == null) {
            synchronized (DefaultSnowflake.class) {
                if (defaultSnowflake == null) {
                    defaultSnowflake = new DefaultSnowflake();
                }
            }
        }
        return defaultSnowflake;
    }

    /**
     * 第一
     * 添加主题业务id 业务功能id
     */
    public void setThemeAndBusiness(long themeId, long businessId) {
        this.themeId = themeId;
        this.businessId = businessId;
        this.init(themeId, businessId);
    }

    /**
     * 第二
     * 添加设置，中心标识id，机器标识id
     */
    public void setCenterAndWorkerId(long centerId, long workerId, long themeId, long businessId) {
        this.themeId = themeId;
        this.businessId = businessId;
        this.centerId = centerId;
        this.workerId = workerId;
        this.init(centerId, workerId, themeId, businessId);
    }

    /**
     * 第三
     * 可以动态配置中心标识和机器标识的占位大小
     */
    public void setCenterAndWorkerBitNunbers(long centerBitNumbers, long workerBitNumbers, long centerId, long workerId, long themeId, long businessId) {
        this.centerBitNumbers = centerBitNumbers;
        this.workerBitNumbers = workerBitNumbers;
        this.centerId = centerId;
        this.workerId = workerId;
        this.themeId = themeId;
        this.businessId = businessId;
        this.init(centerBitNumbers, workerBitNumbers, centerId, workerId, themeId, businessId);
    }
}
