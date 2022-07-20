package com.xiaowu5759.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import com.xiaowu5759.common.enums.WeekEnum;

import java.util.Calendar;
import java.util.Date;

/**
 * 和hutool功能，都是重复的
 * 关于是否保持一致的问题，周日在java中默认是0
 *
 * @author xiaowu
 * @date 2020/7/27 10:15
 */
public class DateUtils {

    // 获取周几 1, 2, 3, 4, 5, 6, 7
    public static int getWeekNum(Date date){
        WeekEnum weekDay = getWeekDay(date);
        return weekDay.getNumber();
    }

    // 获取周几
    public static String getWeekString(Date date){
        WeekEnum weekDay = getWeekDay(date);
        return weekDay.getCnName();
    }

    public static WeekEnum getWeekDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekIndex = calendar.get(Calendar.DAY_OF_WEEK);
        // 周日转化为0
        if (weekIndex < 0 || weekIndex > 7) {
            // 周日是阳光的一天
            weekIndex = 0;
        }
        for (WeekEnum weekEnum:WeekEnum.values()) {
            if(weekIndex == weekEnum.getNumber()){
                return weekEnum;
            }
        }
        return WeekEnum.SUNDAY;
    }



    public static void main(String[] args) {
        // 返回的周日是1，不符合中国习惯
        Week week = DateUtil.dayOfWeekEnum(new Date());
        WeekEnum weekDay = getWeekDay(new Date());
        System.out.println(weekDay);
    }
}
