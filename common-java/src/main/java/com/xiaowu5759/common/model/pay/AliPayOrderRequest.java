package com.xiaowu5759.common.model.pay;


import com.xiaowu5759.common.constant.CharsetConstants;

/**
 * @author xiaowu
 * @date 2021/5/18 2:16 PM
 */
public class AliPayOrderRequest {
    /**
     * app_id
     */
    private String appId;
    /**
     * 接口名称
     */
    private String method = "alipay.trade.create";
    /**
     * 请求使用的编码格式，如utf-8,gbk,gb2312等
     */
    private String charset = CharsetConstants.UTF_8;
    /**
     * 生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     */
    private String signType = "RSA2";
    /**
     * 商户请求参数的签名串，详见签名 https://docs.open.alipay.com/291/105974
     */
    private String sign;
    /**
     * 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
     */
    private String timestamp;
    /**
     * 调用的接口版本，固定为：1.0
     */
    private String version = "1.0";
    /**
     * 支付宝服务器主动通知商户服务器里指定的页面http/https路径。
     */
    private String notifyUrl;

//    private String appAuthToken;

    /**
     * 请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档
     */
    private String bizContent;

    public static class BizContent {
        /**
         * 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。
         * trade_no,out_trade_no如果同时存在优先取trade_no
         */
        private String outTradeNo;

        /**
         * 订单总金额，单位为元，最多两位
         * 不用浮点数操作金额
         */
        private String totalAmount;

        /**
         * 订单标题
         */
        private String subject;

        /**
         * 买家的支付宝唯一用户号（2088开头的16位纯数字）
         * 获取方式见 https://docs.open.alipay.com/api_9/alipay.user.info.auth
         */
        private String buyerId;

        /**
         * PC 二维码，FAST_INSTANT_TRADE_PAY
         * 多种 QUICK_WAP_PAY
         */
        private String productCode;

        /**
         * 用户付款中途退出返回商户网站的地址
         */
        private String quitUrl;

        /**
         * 付款码
         */
        private String authCode;

        /**
         * 是否异步支付，传入true时，表明本次期望走异步支付，会先将支付请求受理下来，再异步推进。商户可以通过交易的异步通知或者轮询交易的状态来确定最终的交易结果
         */
        private Boolean isAsyncPay;
    }
}
