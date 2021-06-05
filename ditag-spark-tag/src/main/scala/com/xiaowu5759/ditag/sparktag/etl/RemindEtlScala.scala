package com.xiaowu5759.ditag.sparktag.etl

import java.util
import java.util.List
import java.util.stream.Collectors

import cn.hutool.core.date.DateUtil
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeConfig
import com.xiaowu5759.common.constant.DateFormatConstants
import com.xiaowu5759.ditag.sparktag.support.SparkSessionUtils
import org.apache.spark.sql.{Dataset, Row, SparkSession}

/**
  * 首单提醒etl
  * 免费券，优惠券
  *
  * @author xiaowu
  * @ 2021/5/27 7:23 PM
  *
  */
object RemindEtlScala {
  def main(args: Array[String]): Unit = {
    // 先定义 领域模型
    val sparkSession = SparkSessionUtils.initSession()
    val freeReminders = this.listFreeReminder(sparkSession)
    val couponReminders = this.listCouponReminder(sparkSession)

    println("======" + JSON.toJSONString(freeReminders, new SerializeConfig(true)))
    println("======" + JSON.toJSONString(couponReminders, new SerializeConfig(true)))

  }

  def listFreeReminder(sparkSession: SparkSession): Array[FreeReminder] ={
    val now = DateUtil.parse("2019-11-30", DateFormatConstants.YYYY_MM_DD)
    // 优惠券 8 天失效的，所以需要加一天
    val tomorrow = DateUtil.offsetDay(now, 1)
    val pickDate = DateUtil.offsetDay(tomorrow, -8)

    var sql: String = "select date_format(create_time,'yyyy-MM-dd') as day,count(member_id) as freeCount " +
      "from i_marketing.t_coupon_member where coupon_id = 1 and coupon_channel = 2 and create_time >= '%s' " +
      "group by date_format(create_time,'yyyy-MM-dd')"
    sql = String.format(sql, DateUtil.format(pickDate, DateFormatConstants.YYYY_MM_DD_HH_MM_SS))
    val dataset: Dataset[Row] = sparkSession.sql(sql)
    val list: Array[String] = dataset.toJSON.collect()
    list.map((str: String) => JSON.parseObject(str, classOf[FreeReminder]))
  }

  def listCouponReminder(sparkSession: SparkSession): Array[CouponReminder] ={
    val now = DateUtil.parse("2019-11-30", DateFormatConstants.YYYY_MM_DD)
    // 优惠券 8 天失效的，所以需要加一天
    val tomorrow = DateUtil.offsetDay(now, 1)
    val pickDate = DateUtil.offsetDay(tomorrow, -8)

    var sql = "select date_format(create_time,'yyyy-MM-dd') as day,count(member_id) as couponCount " +
      "from i_marketing.t_coupon_member where coupon_id != 1 and create_time >= '%s' " +
      "group by date_format(create_time,'yyyy-MM-dd')"
    sql = String.format(sql, DateUtil.format(pickDate, DateFormatConstants.YYYY_MM_DD_HH_MM_SS))
    val dataset: Dataset[Row] = sparkSession.sql(sql)
    val list: Array[String] = dataset.toJSON.collect()
    list.map((str: String) => JSON.parseObject(str, classOf[CouponReminder]))
  }

  class FreeReminder {
    var day: String = _
    var freeCount: Int = _

    def getDay: String = {
      this.day
    }

    def setDay(day: String): Unit = {
      this.day = day
    }

    def getFreeCount: Int = {
      this.freeCount
    }

    def setFreeCount(freeCount: Int): Unit = {
      this.freeCount = freeCount
    }
  }

  class CouponReminder {
    var day: String = _
    var couponCount: Int = _

    def getDay: String = {
      this.day
    }

    def setDay(day: String): Unit = {
      this.day = day
    }

    def getCouponCount: Int = {
      this.couponCount
    }

    def setCouponCount(couponCount: Int): Unit = {
      this.couponCount = couponCount
    }
  }

}
