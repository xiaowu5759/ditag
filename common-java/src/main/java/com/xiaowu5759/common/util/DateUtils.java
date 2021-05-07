package com.xiaowu5759.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 和hutool功能，都是重复的
 * 自己重写一份，对于可视化工具非常有用，基于时间维度的变化
 *
 * @author xiaowu
 * @date 2020/7/27 10:15
 */
public class DateUtils {

    // 获取开始时间和结束时间

    // 获取偏移量

    // 获取时间差

    // 获取周几 0 1 2 3 4 5 6
    public static int getWeekDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
//        cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    // 获取周几
    public static String getWeekDayString(Date date){
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekIndex < 0) {
            weekIndex = 0;
        }
        return weeks[weekIndex];
    }
}
