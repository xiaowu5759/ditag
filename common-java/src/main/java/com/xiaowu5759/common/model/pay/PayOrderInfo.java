package com.xiaowu5759.common.model.pay;


import java.util.Date;

/**
 * 订单支付关键信息
 * 交付给前端关键信息
 *
 * @author xiaowu
 * @date 2021/5/19 9:37 AM
 */
public class PayOrderInfo {

    // accountId
    private String accountId;

    // orderId
    private String orderId;

    // 订单创建时间
    // "yyyy-MM-dd HH:mm:ss"
    private Date createTime;

    // 微信流水号
    private String wechatPayTradeNo;

    // 支付宝流水号，交易编号
    private String aliPayTradeNo;

    // 支付宝，h5，body
    private String aliPayBody;

}
