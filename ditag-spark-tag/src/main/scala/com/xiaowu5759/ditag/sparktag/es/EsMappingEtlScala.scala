package com.xiaowu5759.ditag.sparktag.es

import com.xiaowu5759.ditag.sparktag.support.SparkUtils
import org.apache.spark.sql.SparkSession
import org.elasticsearch.spark.sql.EsSparkSQL
import org.elasticsearch.spark.sql.api.java.JavaEsSparkSQL

import scala.collection.mutable.ArrayBuffer

/**
  * 采用了EsSparkSQL 到es中
  *
  * @author xiaowu
  * @ 2021/6/5 5:13 PM
  *
  */
object EsMappingEtlScala {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkUtils.initSession()
    this.esEtl(sparkSession)
  }

  def esEtl(sparkSession: SparkSession): Unit ={
    val member = sparkSession.sql("select id as memberId,phone,sex,member_channel as channel,mp_open_id as subOpenId, " +
      "address_default_id as address, date_format(create_time,'yyyy-MM-dd') as regTime " +
      "from i_member.t_member")

    // order_commodity collect_list -- group_concat [1,2,3,4,5]
    val order_commodity = sparkSession.sql("select o.member_id as memberId, date_format(max(o.create_time),'yyyy-MM-dd') as orderTime, " +
      "count(o.order_id) as orderCount, collect_list(DISTINCT oc.commodity_id) as favGoods, sum(o.pay_price) as orderMoney " +
      "from i_order.t_order as o left join i_order.t_order_commodity as oc on o.order_id = oc.order_id group by o.member_id")

    val freeCoupon = sparkSession.sql("select member_id as memberId, date_format(create_time,'yyyy-MM-dd') as freeCouponTime " +
      "from i_marketing.t_coupon_member where coupon_id = 1")

    val couponTimes = sparkSession.sql("select member_id as memberId, collect_list(date_format(create_time,'yyyy-MM-dd')) as couponTimes " +
      "from i_marketing.t_coupon_member where coupon_id !=1 group by member_id")

    val chargeMoney = sparkSession.sql("select cm.member_id as memberId, sum(c.coupon_price/2) as chargeMoney " +
      "from i_marketing.t_coupon_member as cm left join i_marketing.t_coupon as c " +
      "on cm.coupon_id = c.id where cm.coupon_channel = 1 group by cm.member_id")

    val overTime = sparkSession.sql("select (to_unix_timestamp(max(arrive_time)) - to_unix_timestamp(max(pick_time))) as overTime, " +
      "member_id as memberId from i_operation.t_delivery group by member_id")

    val feedback = sparkSession.sql("select fb.feedback_type as feedback, fb.member_id as memberId " +
      "from i_operation.t_feedback as fb left join " +
      "(select max(id) as mid,member_id as memberId from i_operation.t_feedback group by member_id) as t " +
      "on fb.id = t.mid")

    member.createOrReplaceTempView("member")
    order_commodity.createOrReplaceTempView("oc")
    freeCoupon.createOrReplaceTempView("freeCoupon")
    couponTimes.createOrReplaceTempView("couponTimes")
    chargeMoney.createOrReplaceTempView("chargeMoney")
    overTime.createOrReplaceTempView("overTime")
    feedback.createOrReplaceTempView("feedback")

    // 将结果整合起来
    val result = sparkSession.sql("select m.*, o.orderCount, o.orderTime, o.orderMoney, o.favGoods, " +
      "fb.freeCouponTime, ct.couponTimes, cm.chargeMoney, ot.overTime, f.feedBack " +
      "from member as m left join oc as o on m.memberId = o.memberId " +
      "left join freeCoupon as fb on m.memberId = fb.memberId " +
      "left join couponTimes as ct on m.memberId = ct.memberId " +
      "left join chargeMoney as cm on m.memberId = cm.memberId " +
      "left join overTime as ot on m.memberId = ot.memberId " +
      "left join feedback as f on m.memberId = f.memberId ")

    EsSparkSQL.saveToEs(result, "/tag/_doc")
    println("====== dataframe上传es成功")
    // 直接将dataframe存入es 结果
    // 展示10个，如果没有的数据就会不存在这个字段
  }

  /**
    * 如何从现有数据中能获取到有效信息
    */
  class MemberTag extends java.io.Serializable {
    // i_member.t_member
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

  }
}
