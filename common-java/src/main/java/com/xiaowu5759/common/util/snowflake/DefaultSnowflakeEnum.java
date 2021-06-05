package com.xiaowu5759.common.util.snowflake;

/**
 * 从0开始的
 * themeId 0-7
 * businessId 0-16
 *
 * @author xiaowu
 * @date 2021/3/20 9:41 AM
 */
public enum DefaultSnowflakeEnum {
    ACCOUNT_ID(0L, 0L),
    ORDER_ID(1L, 0L),
    PAY_ID(2L, 0L),
    ;


    private Long themeId;

    private Long businessId;

    DefaultSnowflakeEnum(Long themeId, Long businessId){
        this.themeId = themeId;
        this.businessId = businessId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public Long getBusinessId() {
        return businessId;
    }
}
