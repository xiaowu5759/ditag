package com.xiaowu5759.common.constant;

/**
 * @author xiaowu
 * @date 2021/5/17 5:26 PM
 */
public final class WechatPayConstants {
    private WechatPayConstants(){}

    public static final String DEFAULT_SPBILL_CREATE_IP = "8.8.8.8";

//    public static final String SIGN_TYPE_MD5 = "MD5";

//    public static final String SIGN_TYPE_SHA256 = "HMACSHA256";

    public static class TradeType {
        public static final String JSAPI = "JSAPI";
        public static final String NATIVE = "NATIVE";
        public static final String APP = "APP";
        private TradeType() {
        }
    }

    public static class SignType {
        public static final String MD5 = "MD5";
        public static final String SHA256 = "HMACSHA256";
        private SignType() {
        }
    }

}
