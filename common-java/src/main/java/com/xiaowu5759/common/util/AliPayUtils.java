package com.xiaowu5759.common.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import com.xiaowu5759.common.constant.AliPayConstants;
import com.xiaowu5759.common.constant.CharsetConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 通用处理类
 *
 * @author xiaowu
 * @date 2020/7/27 9:35
 */
public class AliPayUtils {

    private static final Logger log = LoggerFactory.getLogger(AliPayUtils.class);

    /**
     * 普通公钥方式实现签名方式
     *
     * @param params  参数map
     * @param privateKey  商户私钥
     * @return
     */
    public static String sign(Map<String, String> params, String privateKey) {
        String signType = params.get("sign_type");
        String signContent = getSignContent(params);
        log.info("sign content: {}",signContent);
        if (AliPayConstants.SIGN_TYPE_RSA2.equals(signType)) {
            return sha256WithRSA(signContent, privateKey);
        }
        throw new RuntimeException("支付宝签名方式有误");
    }

    // 公钥证书方式，实现签名方式


    /**
     * 将map里面的内容转换成str
     *
     * @param params
     * @return
     */
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

    // 默认签名方式 RSA2
    public static String sha256WithRSA(String signContent, String privateKey) {

        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(AliPayConstants.SIGN_TYPE_RSA,
                    new ByteArrayInputStream(privateKey.getBytes()));
            // getSignAlgorithm()
            Signature signature = Signature.getInstance(AliPayConstants.SIGN_SHA256RSA_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(signContent.getBytes(CharsetConstants.UTF_8));

            byte[] signed = signature.sign();
            return Base64.encode(signed);
        } catch (Exception e) {
            throw new RuntimeException("RSAcontent = " + signContent + "; charset = utf-8", e);
        }
    }

    // 来自 alipay rsaSign的标准复刻方法
    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm,
                                                    InputStream ins) throws Exception {
        if (ins == null || StringUtils.isEmpty(algorithm)) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

//         byte[] encodedKey = StreamUtil.readText(ins).getBytes();
        String text = IoUtil.read(ins, CharsetConstants.UTF_8);
        byte[] encodedKey = text.getBytes(CharsetConstants.UTF_8);

        encodedKey = Base64.decode(encodedKey);

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    // 返回结果验签 verify

    // getCertSN，certPath X.509证书文件路径
    // app_cert_sn

    // getRootCertSN，获取根证书序列号
    // alipay_root_cert_sn


}
