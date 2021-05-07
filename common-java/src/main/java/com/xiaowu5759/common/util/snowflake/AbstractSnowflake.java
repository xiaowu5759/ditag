package com.xiaowu5759.common.util.snowflake;

/**
 * @author xiaowu
 * @date 2020/4/1 10:56
 * 目标秒级时间戳，添加主题id和业务功能id
 */
public abstract class AbstractSnowflake {
    /**
     * 起始时间戳
     */
    private long startTimestamp = 0L;

    /**
     * 每一部分占用的位数,一共63位
     */
    private long themeBitNumbers = 4L;  // 主题业务，8个主题
    private long businessBitNumbers = 5L;  // 业务功能，每个主题下面16个业务
    private long timestampBitNumbers = 32L;  // 秒级时间戳
    private long centerBitNumbers;  // 数据中心比特数
    private long workerBitNumbers;  // 机器比特数 1024
    private long sequenceBitNumbers = 12L;  // 序列，4096

    /**
     * 每一部分 运算是的右移量
     * 应对多线程访问
     */
    private long workerBitLeft;
    private long centerBitLeft;
    private long timestampBitLeft;
    private long businessBitLeft;
    private long themeBitLeft;

    /**
     * 每一部分的最大值
     */
    private long maxThemeId = -1L ^ (-1L << themeBitNumbers);
    private long maxBusinessId = -1L ^ (-1L << businessBitNumbers);
    private long maxCenterId;
    private long maxWorkerId;
    private long maxSequenceNum = -1L ^ (-1L << sequenceBitNumbers);

    /**
     * 需要输入的常量
     */
    private long themeId;  // 主题业务标识id
    private long businessId;  // 业务功能标识id
    private long centerId;  // 数据中心id
    private long workerId;  // 机器标识id
    private long sequence = 0L;  // 序列号
    private long lastStamp = -1L;  // 上一次时间戳

    protected void init(long themeId, long businessId) {
        init(5, 5, 0, 0, themeId, businessId);
    }

    protected void init(long centerId, long workerId, long themeId, long businessId) {
        init(5, 5, centerId, workerId, themeId, businessId);
    }

    protected void init(long centerBitNumbers, long workerBitNumbers, long centerId, long workerId, long themeId, long businessId) {
        // 设置计算的偏移量
        workerBitLeft = sequenceBitNumbers;
        centerBitLeft = workerBitLeft + workerBitNumbers;
        timestampBitLeft = centerBitLeft + centerBitNumbers;
        businessBitLeft = timestampBitLeft + timestampBitNumbers;
        themeBitLeft = businessBitLeft + businessBitNumbers;
        if (timestampBitLeft > 22) {
            throw new IllegalArgumentException("(dataCenterIdBitNumbers + machineIdBitNumbers + sequenceBitNumbers) can't be greater than 22");
        }
        // 设置每一部分最大值
        maxCenterId = -1L ^ (-1L << centerBitNumbers);
        maxWorkerId = -1L ^ (-1L << workerBitNumbers);
        if (themeId > maxThemeId || themeId < 0) {
            throw new IllegalArgumentException("themeId can't be greater than MAX_THEMEID_NUM or less than 0");
        }
        if (businessId > maxBusinessId || businessId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_BUSINESSID_NUM or less than 0");
        }
        if (centerId > maxCenterId || centerId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_CENTER_NUM or less than 0");
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }

        this.centerBitNumbers = centerBitNumbers;
        this.workerBitNumbers = workerBitNumbers;
        this.themeId = themeId;
        this.businessId = businessId;
        this.centerId = centerId;
        this.workerId = workerId;

//        log.info("Snowflake init param: datacenterBitNumbers={}", this.datacenterBitNumbers);
//        log.info("Snowflake init param: machineBitNumbers={}", this.machineBitNumbers);
//        log.info("Snowflake init param: sequenceBitNumbers={}", this.sequenceBitNumbers);
//        log.info("Snowflake init param: startTimestamp={}", this.startTimestamp);
//        log.info("Snowflake init param: datacenterId={}", this.datacenterId);
//        log.info("Snowflake init param: machineId={}", this.machineId);
    }

    /**
     * 获取下一个atom id
     *
     * @return
     */
    public long nextId() {
        synchronized (this) {
            long currStamp = getNowstamp();
            if (currStamp < this.lastStamp) {
                // 时钟回拨
                throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
            }

            // 相同时间戳内，序列号自增
            if (currStamp == this.lastStamp) {
                this.sequence = (this.sequence + 1) & this.maxSequenceNum;
                // 同一毫秒的序列数已经达到最大
                if (this.sequence == 0L) {
                    currStamp = getNextSecond();
                }
            } else {
                //不同毫秒内，序列号置为0
                this.sequence = 0L;
            }
            this.lastStamp = currStamp;

            return this.themeId << this.themeBitLeft // 核心业务标识
                    | this.businessId << this.businessBitLeft // 业务功能标识
                    | (currStamp - this.startTimestamp) << this.timestampBitLeft //时间戳部分
                    | this.centerId << this.centerBitLeft       //数据中心部分
                    | this.workerId << this.workerBitLeft             //机器标识部分
                    | this.sequence;                             //序列号部分
        }
    }

    /**
     * 获取下一秒unix时间戳
     *
     * @return
     */
    private long getNextSecond() {
        long timestamp = this.getNowstamp();
        while (timestamp <= this.lastStamp) {
            timestamp = this.getNowstamp();
        }
        return timestamp;
    }

    /**
     * 获取当前unix时间戳
     *
     * @return
     */
    private long getNowstamp() {
        return System.currentTimeMillis() / 1000;
    }

}
