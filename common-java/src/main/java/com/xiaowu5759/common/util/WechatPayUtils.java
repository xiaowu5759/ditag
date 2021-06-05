package com.xiaowu5759.common.util;

import com.xiaowu5759.common.constant.AliPayConstants;
import com.xiaowu5759.common.constant.WechatPayConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author xiaowu
 * @date 2021/5/17 5:27 PM
 */
public class WechatPayUtils {

    private static final Logger log = LoggerFactory.getLogger(WechatPayUtils.class);


    // 随机字符串，随机数工具类

    // 签名算法

    /**
     * 微信支付，md5签名
     *
     * @param params
     * @param privateKey
     * @return
     */
    public static String sign(Map<String, String> params, String privateKey) {
        String signType = params.get("sign_type");
        String signContent = getSignContent(params);
        signContent = signContent + "&key=" + privateKey;
        log.info("sign content: {}",signContent);
        if (WechatPayConstants.SignType.MD5.equals(signType)) {
            return md5(signContent);
        }
        throw new RuntimeException("支付宝签名方式有误");
    }


    public static String getSignContent(Map<String, String> params) {
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        int index = 0;
        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
                index++;
            }
        }
        return content.toString();
    }

    // md5

    /**
     * 借鉴微信官方的签名算法
     *
     * @param signContent
     * @return
     */
    public static String md5(String signContent) {
        try {
            MessageDigest md = MessageDigest.getInstance(WechatPayConstants.SignType.MD5);
            byte[] array = md.digest(signContent.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e){
            throw new RuntimeException("sign content = " + signContent + "; charset = utf-8", e);
        }
    }

    // hash_hmac
}
