package com.xiaowu5759.ditag.sparktag.etl

import java.util
import java.util.{Date, List}

import cn.hutool.core.date.DateUtil
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeConfig
import com.xiaowu5759.common.constant.DateFormatConstants
import com.xiaowu5759.ditag.sparktag.support.SparkSessionUtils
import org.apache.spark.sql.SparkSession

/**
  * 周注册量 和 周下单量
  *
  * @author xiaowu
  * @ 2021/5/27 7:23 PM
  *
  */
object WowEtlScala {

  def main(args: Array[String]): Unit = {
    val ss: SparkSession = SparkSessionUtils.initSession()
    val regs: Array[Reg] = listRegCount(ss)
    val orders: Array[Order] = listOrderCount(ss)
    println("======" + JSON.toJSONString(regs, new SerializeConfig(true)))
    println("======" + JSON.toJSONString(orders, new SerializeConfig(true)))
  }


  def listRegCount(sparkSession: SparkSession): Array[Reg] = {
    // 计算当天时间
    val now = DateUtil.parse("2019-11-30", DateFormatConstants.YYYY_MM_DD)
    val nowSeven = DateUtil.offsetDay(now, -7)
    val lastSeven = DateUtil.offsetDay(nowSeven, -7)

    // 查询结果
    var sql: String = "select date_format(create_time,'yyyy-MM-dd') as day, " +
      "count(id) as regCount from i_member.t_member where create_time >='%s' " +
      "and create_time < '%s' group by date_format(create_time,'yyyy-MM-dd')"
    sql = String.format(sql, DateUtil.format(lastSeven, DateFormatConstants.YYYY_MM_DD_HH_MM_SS),
      DateUtil.format(nowSeven, DateFormatConstants.YYYY_MM_DD_HH_MM_SS))
    val dataframe = sparkSession.sql(sql)

    val arr = dataframe.toJSON.collect()
    arr.map(str => JSON.parseObject(str, classOf[Reg]))
  }

  def listOrderCount(sparkSession: SparkSession): Array[Order] ={
    val now = DateUtil.parse("2019-11-30", DateFormatConstants.YYYY_MM_DD)
    val nowSeven = DateUtil.offsetDay(now, -7)
    val lastSeven = DateUtil.offsetDay(nowSeven, -7)

    // 查询结果
    var sql: String = "select date_format(create_time,'yyyy-MM-dd') as day, " +
      "count(order_id) as orderCount from i_order.t_order where create_time >='%s' " +
      "and create_time < '%s' group by date_format(create_time,'yyyy-MM-dd')"
    sql = String.format(sql, DateUtil.format(lastSeven, DateFormatConstants.YYYY_MM_DD_HH_MM_SS),
      DateUtil.format(nowSeven, DateFormatConstants.YYYY_MM_DD_HH_MM_SS))
    val dataframe = sparkSession.sql(sql)

    val arr = dataframe.toJSON.collect()
    arr.map(str => JSON.parseObject(str, classOf[Order]))
  }

  class Reg {
    var day: String = _
    var regCount: Int = _

    def getDay: String = {
      this.day
    }

    def setDay(day: String): Unit = {
      this.day = day
    }

    def getRegCount: Int = {
      this.regCount
    }

    def setRegCount(regCount: Int): Unit = {
      this.regCount = regCount
    }
  }

  class Order {
    var day: String = _
    var orderCount: Int = _

    def getDay: String = {
      this.day
    }

    def setDay(day: String): Unit = {
      this.day = day
    }

    def getOrderCount: Int = {
      this.orderCount
    }

    def setOrderCount(orderCount: Int): Unit = {
      this.orderCount = orderCount
    }

  }




}
