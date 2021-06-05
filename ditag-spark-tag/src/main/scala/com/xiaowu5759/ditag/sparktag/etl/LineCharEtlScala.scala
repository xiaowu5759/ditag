package com.xiaowu5759.ditag.sparktag.etl


import cn.hutool.core.date.DateUtil
import com.alibaba.fastjson.parser.ParserConfig
import com.alibaba.fastjson.{JSON, JSONObject}
import com.alibaba.fastjson.serializer.SerializeConfig
import com.xiaowu5759.common.constant.DateFormatConstants
import com.xiaowu5759.ditag.sparktag.etl.RemindEtlScala.FreeReminder
import com.xiaowu5759.ditag.sparktag.support.SparkSessionUtils
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Dataset, Row, SparkSession}

import scala.collection.mutable.ArrayBuffer

/**
  * 增长折线图 相关
  *
  * @author xiaowu
  * @ 2021/5/27 10:44 PM
  *
  */
object LineCharEtlScala {
  def main(args: Array[String]): Unit = {
    val LineVOS = this.listLinVO(SparkSessionUtils.initSession())
    println("======" + JSON.toJSONString(LineVOS, new SerializeConfig(true)))
  }

  def listLinVO(sparkSession: SparkSession): Array[LineVO] ={
    // 还是先确定时间
    val now = DateUtil.parse("2019-11-30", DateFormatConstants.YYYY_MM_DD)
    val nowSeven = DateUtil.offsetDay(now, -8)

    // regCount，memberCount
    var memberSql: String = "select date_format(create_time,'yyyy-MM-dd') as day, " +
      "count(id) as regCount, max(id) as memberCount " +
      "from i_member.t_member where create_time >='%s' " +
      "group by date_format(create_time,'yyyy-MM-dd') order by day"
    memberSql = String.format(memberSql, DateUtil.format(nowSeven, DateFormatConstants.YYYY_MM_DD_HH_MM_SS))
    val memberDs: Dataset[Row] = sparkSession.sql(memberSql)

    // orderCount，gmv
    var orderSql: String = "select date_format(create_time,'yyyy-MM-dd') as day, " +
      "max(order_id) as orderCount, sum(origin_price) as gmv " +
      "from i_order.t_order where create_time >='%s' " +
      "group by date_format(create_time,'yyyy-MM-dd') order by day"
    orderSql = String.format(orderSql, DateUtil.format(nowSeven, DateFormatConstants.YYYY_MM_DD_HH_MM_SS))
    val orderDs: Dataset[Row] = sparkSession.sql(orderSql)

    // 数据整合处理，left join on a.id = b.id
    // inner join 表示取交集，数量最小，默认也是inner
    // Dataset[Tuple2[Row, Row]]，两个row构成的
    val tuple2Dataset: Dataset[Tuple2[Row, Row]] = memberDs.joinWith(orderDs, memberDs.col("day").equalTo(orderDs.col("day")), "inner")

    val tuple2s: Array[Tuple2[Row, Row]] = tuple2Dataset.collect()
    // 处理数据，封装结果
    // 也就是如何快速匹配，对应day中的每项数据
    val lineVOS = new ArrayBuffer[LineVO]()
    for (tuple2 <- tuple2s) {
      val obj = new JSONObject()
      val row1 = tuple2._1  // 取出第一个row
      val row2 = tuple2._2  // 取出第二个row
      var schema: StructType = row1.schema
      var fieldNames = schema.fieldNames
      for (str <- fieldNames) {
        // row1数据，取出对应的名字
        // hasdo 强转类型可能出现问题
        val as: Any = row1.getAs(str)
        obj.put(str, as)
      }
      schema = row2.schema
      fieldNames = schema.fieldNames
      for (str <- fieldNames) {
        val as: Any = row2.getAs(str)
        obj.put(str, as)
      }

      // 转化的时候出现问题
//      val lineVO = obj.toJavaObject(classOf[LineVO], new ParserConfig(true), 1)
      val str = JSON.toJSONString(obj, new SerializeConfig(true))
      val lineVO = JSON.parseObject(str, classOf[LineVO])
      // 不能转换成 BigDecimal
      lineVOS.+=(lineVO)
    }

    // 每天的gmv上面，总的gmv
    var gmvSql = "select sum(origin_price) as totalGMV from i_order.t_order where create_time < '%s'"
    gmvSql = String.format(gmvSql, DateUtil.format(nowSeven, DateFormatConstants.YYYY_MM_DD_HH_MM_SS))
    val gmvDs: Dataset[Row] = sparkSession.sql(gmvSql)  // 一行一列的结果
    val gmvAllArr = gmvDs.collect()  // collect开始具体执行spark操作，结果是row-array
    val gmvAll = gmvAllArr(0).getDouble(0)
//    val deciamlGMV = BigDecimal.valueOf(gmvAll)
    val deciamlGMV = gmvAll

    // 将每天的gmv和之前总和相加
    val destList = new ArrayBuffer[Double]()
    for(i <- lineVOS.indices){
      val lineVO = lineVOS(i)
      var temp = deciamlGMV.+(lineVO.gmv)
      // 双重循环，加上之前的
      for(j <- 0 until i){
        val prev = lineVOS(j)
        temp = temp.+(prev.getGMV)
      }

      destList.+=(temp)
    }

    // 将总的GMV赋值给 对象
    for(i <- destList.indices){
      val lineVO = lineVOS(i)
      lineVO.setGMV(destList(i))
    }

    lineVOS.toArray
  }

  class LineVO{
    var day: String = _
    var regCount: Int = _
    var memberCount: Int = _
    var orderCount: Int = _
    var gmv: Double = _

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

    def getMemberCount: Int = {
      this.memberCount
    }

    def setMemberCount(memberCount: Int): Unit = {
      this.memberCount = memberCount
    }

    def getOrderCount: Int = {
      this.orderCount
    }

    def setOrderCount(orderCount: Int): Unit = {
      this.orderCount = orderCount
    }

    def getGMV: Double = {
      this.gmv
    }

    def setGMV(gmv: Double): Unit = {
      this.gmv = gmv
    }
  }
}
