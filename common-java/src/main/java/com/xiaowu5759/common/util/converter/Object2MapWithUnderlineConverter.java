package com.xiaowu5759.common.util.converter;

import cn.hutool.core.convert.Converter;
import com.xiaowu5759.common.util.CamelCaseUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *  implements Converter<Map<String, String>>
 *
 * @author xiaowu
 * @date 2021/5/19 2:23 PM
 */
public class Object2MapWithUnderlineConverter{

    /**
     *
     * @param value  初始值
     * @param defaultValue  默认值
     * @return
     */
    public static Map<String, String> convert(Object value, Map<String, String> defaultValue) {
        try {
            Map<String, String> map = new HashMap<>();
            Class<?> clazz = value.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                fieldName = CamelCaseUtils.toUnderlineName(fieldName);
//                String val = field.get(value) == null ? "" : String.valueOf(field.get(value));
                if(field.get(value) != null){
                    map.put(fieldName, String.valueOf(field.get(value)));
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}

