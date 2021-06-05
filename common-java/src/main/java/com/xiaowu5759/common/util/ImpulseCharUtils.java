package com.xiaowu5759.common.util;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 名字翻译脉冲字符工具类
 * 转化成短小信息集
 * 26 + 26 + 10 = 62进制
 * 10 位转 62进制 相互转换
 *
 * 2021-05-17，之后我知道其实就是base62/64/32编码
 *
 * @author xiaowu
 * @date 2021/5/14 7:39 PM
 */
public class ImpulseCharUtils {

    private static final Logger log = LoggerFactory.getLogger(ImpulseCharUtils.class);

    public static void main(String[] args) {

        // subString，取头不取尾
//        String hello = "helloworld";
//        String hell = "hell";
//        System.out.println(hello.length());
//        System.out.println(hell.length());
//
//        String helloS = hello.substring(hell.length(), hello.length());
//        System.out.println(helloS);

        String targetSys = "WXPay";
        log.info("【银联商务回调】targetSys={}", targetSys);
        if(StrUtil.isBlank(targetSys) || !("WXPay".equals(targetSys) || targetSys.contains("Alipay"))){
            log.info("【银联商务回调】targetSys进入");
        }

    }
}
