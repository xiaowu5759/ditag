package com.xiaowu5759.common.model.pay;


import com.xiaowu5759.common.constant.WechatPayConstants;

/**
 * 微信下单接口
 *
 * @author xiaowu
 * @date 2021/5/18 2:15 PM
 */

public class WechatPayOrderRequest {

//    @Element(name = "appid")
    private String appid;

//    @Element(name = "mch_id")
    private String mchId;

//    @Element(name = "nonce_str")
    private String nonceStr;

//    @Element(name = "sign")
    private String sign;

    private String signType = WechatPayConstants.SignType.MD5;

//    @Element(name = "attach", required = false)
    private String attach;

//    @Element(name = "body", required = false)
    private String body;

//    @Element(name = "detail", required = false)
    private String detail;

//    @Element(name = "notify_url")
    private String notifyUrl;

//    @Element(name = "openid", required = false)
    private String openid;

//    @Element(name = "out_trade_no")
    private String outTradeNo;

//    @Element(name = "spbill_create_ip")

    private String spbillCreateIp;

//    @Element(name = "total_fee")
    private Integer totalFee;

//    @Element(name = "trade_type")
    private String tradeType;

//    @Element(name = "auth_code", required = false)
    private String authCode;
}
