package com.xiaowu5759.common.enums;

/**
 * 如果存在名称之外的延伸属性应使用 enum 类型，下面正例中的数字就是延伸信息，表示一年中的第几个季节
 *
 * @author xiaowu
 * @date 2021/1/18 3:00 PM
 */
public enum SeasonEnum {
    SPRING(1),
    SUMMER(2),
    AUTUMN(3),
    WINTER(4);

    private int seq;

    SeasonEnum(int seq) {
        this.seq = seq;
    }

    public int getSeq() {
        return seq;
    }

}
