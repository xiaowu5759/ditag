package com.xiaowu5759.ditag.sparktag.etl

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeConfig
import com.xiaowu5759.ditag.sparktag.support.SparkSessionUtils
import org.apache.spark.sql.{Dataset, Row, SparkSession}

/**
  * 漏斗图
  *
  * @author xiaowu
  * @ 2021/5/31 10:57 PM
  *
  */
object FunnelEtlScala {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSessionUtils.initSession()
    val funnelVO = this.getFunnelVO(sparkSession)
    println("======" + JSON.toJSONString(funnelVO, new SerializeConfig(true)))
  }

  def getFunnelVO(sparkSession : SparkSession): FunnelVO ={
    val orderMember = sparkSession.sql("select distinct(member_id) from i_order.t_order where order_status=2")

    val orderAgainMember = sparkSession.sql("select t.member_id as member_id " +
      "from (select count(order_id) as orderCount,member_id from i_order.t_order where order_status=2 group by member_id) as t " +
      "where t.orderCount>1")

    val charge = sparkSession.sql("select distinct(member_id) as member_id " +
      "from i_marketing.t_coupon_member where coupon_channel = 1")

    val join = charge.join(orderAgainMember, orderAgainMember.col("member_id").equalTo(charge.col("member_id")), "inner")

    val order = orderMember.count
    val orderAgain = orderAgainMember.count
    val chargeCoupon = join.count

    val vo = new FunnelVO
    vo.setPresent(1000L)
    vo.setClick(800L)
    vo.setAddCart(600L)
    vo.setOrder(order)
    vo.setOrderAgain(orderAgain)
    vo.setChargeCoupon(chargeCoupon)
    vo
  }

  class FunnelVO{
    // 展示，埋点sdk，1000
    var present: Long = _
    // 点击，埋点sdk，800
    var click: Long = _
    // 加购，埋点sdk，600
    var addCart: Long = _
    // 下单
    var order: Long = _
    // 复购
    var orderAgain: Long = _
    // 储值
    var chargeCoupon: Long = _

    def getPresent: Long = {
      this.present
    }

    def setPresent(present: Long): Unit = {
      this.present = present
    }

    def getClick: Long = {
      this.click
    }

    def setClick(click: Long): Unit = {
      this.click = click
    }

    def getAddCart: Long = {
      this.addCart
    }

    def setAddCart(addCart: Long): Unit = {
      this.addCart = addCart
    }

    def getOrder: Long = {
      this.order
    }

    def setOrder(order: Long): Unit = {
      this.order = order
    }

    def getOrderAgain: Long = {
      this.orderAgain
    }

    def setOrderAgain(orderAgain: Long): Unit = {
      this.orderAgain = orderAgain
    }

    def getChargeCoupon: Long = {
      this.chargeCoupon
    }

    def setChargeCoupon(chargeCoupon: Long): Unit = {
      this.chargeCoupon = chargeCoupon
    }
  }
}
