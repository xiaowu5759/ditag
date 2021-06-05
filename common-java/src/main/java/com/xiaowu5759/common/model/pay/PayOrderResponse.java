package com.xiaowu5759.common.model.pay;


import java.net.URI;

/**
 * 二码合一，抽象统一下单响应结果
 *
 * @author xiaowu
 * @date 2021/5/18 2:12 PM
 */
public class PayOrderResponse {
    /**
     * 以下参数只有微信支付会返回 (在微信付款码支付使用)
     * returnCode returnMsg resultCode errCode errCodeDes
     */
    // SUCCESS/FAIL，此字段是通信标识，
    // 主要使用
    private String returnCode;

    // return_code为FAIL时返回信息为错误原因 ，例如签名失败
    // 主要使用
    private String returnMsg;

    // SUCCESS/FAIL，交易标识
    private String resultCode;

    // 当result_code为FAIL时返回错误代码
    private String errCode;

    // 当result_code为FAIL时返回错误描述
    private String errCodeDes;


    private String prePayParams;

    private URI payUri;

    /** 以下字段仅在微信h5支付返回. */
    private String appId;

    private String timeStamp;

    private String nonceStr;

//    @JsonProperty("package")
    private String packAge;

    private String signType;

    private String sign;

    /**
     * 以下字段在微信异步通知下返回.
     * 金额最小单位 分 存储
     */
    private Integer orderAmount;

    private String orderId;

    private String orderName;

    /**
     * 第三方支付的流水号
     */
    private String tradeNo;

    /**
     * 支付宝App支付返回的请求参数信息
     */
    private String tradeInfo;

    /**
     * 以下支付是h5支付返回
     */
    private String mwebUrl;

    /**
     * AliPay  pc网站支付返回的body体，html 可直接嵌入网页使用
     */
    private String body;

    /**
     * 扫码付模式二用来生成二维码
     */
    private String codeUrl;

    /**
     * 附加内容，发起支付时传入
     */
    private String attach;

    /**
     * 支付方式.
     * wechat，alipay
     */
    private String payType;

    private String prepayId;

}
