package com.xiaowu5759.common.model.pay;


/**
 * 二码合一，抽象统一下单参数
 *
 * @author xiaowu
 * @date 2021/5/18 2:12 PM
 */
public class PayOrderRequest {
    /**
     * 支付方式.
     * wechat，alipay
     */
    private String payType;

    /**
     * 订单号.
     */
    private String orderId;

    /**
     * 订单金额，采用最小单位，单位分
     */
    private Integer orderAmount;

    /**
     * 订单名字.
     */
    private String orderName;

    /**
     * 微信openid, 仅微信公众号/小程序支付时需要
     */
    private String openid;

    /**
     * 客户端访问Ip  外部H5支付时必传，需要真实Ip
     * 20191015测试，微信h5支付已不需要真实的ip
     */
    private String spbillCreateIp;

    /**
     * 附加内容，发起支付时传入
     */
    private String attach;

    /**
     * 支付后跳转（支付宝PC网站支付）
     * 优先级高于PayConfig.returnUrl
     */
    private String returnUrl;

    /**
     * 买家的支付宝唯一用户号（2088开头的16位纯数字）
     * {@link AliPayTradeCreateRequest.BizContent}
     */
    private String buyerId;

    /**
     * 付款码
     */
    private String authCode;
}
