package com.xiaowu5759.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 致敬hutool，对两个List的比较操作
 * 差集：求不同的部分
 *
 * @author xiaowu
 * @date 2020/7/25 17:13
 */
public class ListMathUtils {

    /**
     * 求交集
     *
     * @param origin
     * @param target
     * @param <T>
     * @return
     */
    public static <T> List<T> inner(List<T> origin, List<T> target) {
        // 求两个公共部分
        return origin.stream().filter(target::contains).collect(Collectors.toList());
    }

    /**
     * 求去重并集
     *
     * @param origin
     * @param target
     * @param <T>
     * @return
     */
    public static <T> List<T> union(List<T> origin, List<T> target) {
        // do 这里涉及了，数据结构，对象的引用关系
//        List<T> temp = origin;  // 这里origin始终会收到影响
        List<T> temp = new ArrayList<>();
        temp.addAll(origin);
        temp.addAll(target);
        // 排序功能
        return temp.stream().sorted().distinct().collect(Collectors.toList());
    }

    /**
     * 求去重
     *
     * @param origin
     * @param <T>
     * @return
     */
    public static <T> List<T> distinct(List<T> origin){
        return origin.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 求差集，求差异
     * origin 中与target的差异部分
     *
     * @param origin
     * @param target
     * @param <T>
     * @return
     */
    public static <T> List<T> diff(List<T> origin, List<T> target) {
        if(target == null || target.isEmpty()){
            return origin;
        }
        return origin.stream().filter(item -> !target.contains(item)).collect(Collectors.toList());
    }
}
