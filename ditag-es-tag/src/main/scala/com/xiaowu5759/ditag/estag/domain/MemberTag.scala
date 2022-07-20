package com.xiaowu5759.ditag.estag.domain

import scala.collection.mutable.ArrayBuffer

/**
  * 用户标签
  *
  * @author xiaowu
  * @ 2021/6/8 9:15 PM
  *
  */
class MemberTag {
  var memberId: String = _
  var phone: String = _
  var sex: String = _
  var channel: String = _
  var subOpenId: String = _
  var address: String = _
  var regTime: String = _
  // i_member.t_member

  // i_order
  var orderCount: Long = _
  // max(create_time) i_order.t_order
  var orderTime: String = _
  var orderMoney: Double = _
  var favGoods: ArrayBuffer[String] = _
  // i_order

  // i_marketing
  var freeCouponTime: String = _
  var couponTimes: ArrayBuffer[String] = _
  var chargeMoney: Double = _
  // i_marketing

  var overTime: Int = _
  var feedBack: Int = _

  def getMemberId: String = {
    this.memberId
  }

  def setMemberId(memberId: String): Unit = {
    this.memberId = memberId
  }

  def getPhone: String = {
    this.phone
  }

  def setPhone(phone: String): Unit = {
    this.phone = phone
  }

  def getSex: String = {
    this.sex
  }

  def setSex(sex: String): Unit = {
    this.sex = sex
  }

  def getChannel: String = {
    this.channel
  }

  def setChannel(channel: String): Unit = {
    this.channel = channel
  }

  def getSubOpenId: String = {
    this.subOpenId
  }

  def setSubOpenId(subOpenId: String): Unit = {
    this.subOpenId = subOpenId
  }

  def getAddress: String = {
    this.address
  }

  def setAddress(address: String): Unit = {
    this.address = address
  }

  def getRegTime: String = {
    this.regTime
  }

  def setRegTime(regTime: String): Unit = {
    this.regTime = regTime
  }

  def getOrderCount: Long = {
    this.orderCount
  }

  def setOrderCount(orderCount: Long): Unit = {
    this.orderCount = orderCount
  }

  def getOrderTime: String = {
    this.orderTime
  }

  def setOrderTime(orderTime: String): Unit = {
    this.orderTime = orderTime
  }

  def getOrderMoney: Double = {
    this.orderMoney
  }

  def setOrderMoney(orderMoney: Double): Unit = {
    this.orderMoney = orderMoney
  }

  def getFavGoods: ArrayBuffer[String] = {
    this.favGoods
  }

  def setFavGoods(favGoods: ArrayBuffer[String]): Unit = {
    this.favGoods = favGoods
  }

  def getFreeCouponTime: String = {
    this.freeCouponTime
  }

  def setFreeCouponTime(freeCouponTime: String): Unit = {
    this.freeCouponTime = freeCouponTime
  }

  def getCouponTimes: ArrayBuffer[String] = {
    this.couponTimes
  }

  def setCouponTimes(couponTimes: ArrayBuffer[String]): Unit = {
    this.couponTimes = couponTimes
  }

  def getChargeMoney: Double = {
    this.chargeMoney
  }

  def setChargeMoney(chargeMoney: Double): Unit = {
    this.chargeMoney = chargeMoney
  }

  def getOverTime: Int = {
    this.overTime
  }

  def setOverTime(overTime: Int): Unit = {
    this.overTime = overTime
  }

  def getFeedBack: Int = {
    this.feedBack
  }

  def setFeedBack(feedBack: Int): Unit = {
    this.feedBack = feedBack
  }

}
