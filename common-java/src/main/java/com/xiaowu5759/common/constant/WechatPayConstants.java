package com.xiaowu5759.common.constant;

/**
 * @author xiaowu
 * @date 2021/5/7 10:37 AM
 */
public final class WechatPayConstants {
    private WechatPayConstants(){}

    public static final String GIFT_UNDELIVERED_STATUS = "未发货";

    public static final String GIFT_SHIPPED_STATUS = "已发货";

    public static class Wechat {
        public static final String MP_APP_ID = "wx1ab340019b4962c4";
        public static final String MP_APP_SECRET = "9f2a0e515e07c8b328f47df0ff8ea23b";
        private Wechat() {
        }
    }

    public static class ThirdAuthLoginType {
        public static final String WECHAT = "wechat";
        private ThirdAuthLoginType() {
        }
    }

    public static class LocalLoginType {
        public static final String VALIDATE_CODE = "validate";
        private LocalLoginType() {
        }
    }

    public static class PackageStrategy {
        public static final String PACKAGE_TIME = "packageTime";
        public static final String PACKAGE_SIZE = "packageSize";
        private PackageStrategy() {
        }
    }
}
