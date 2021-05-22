package com.xiaowu5759.ditag.sparktag.etl

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author xiaowu
  * @ 2021/5/22 12:33 AM
  *
  */
object HotWordSql {
  def main(args: Array[String]): Unit = {
    val config = new SparkConf().setMaster("local").setAppName("hotwordscala")
    val sc = new SparkContext(config)
    // txt，csv，json，jdbc
    val lines = sc.textFile("hdfs://gown:8020/data/SogouQ.sample")

    val row = lines.map(line => {
      // 推断类型
      val arr = line.split("\t")
      val str = arr(3)
      val rankClick = str.split(" ")
      // 转换成结构化数据
      // 类型也最好转换
      Row(rankClick(0).toInt, rankClick(1).toInt)
    })

    // 构造函数
    val structType = StructType(
      // 名字，数据类型，是否可以为空
      // 将元素加起来
      StructField("rank", IntegerType, false)::
      StructField("click", IntegerType, false):: Nil
    )

    val ss = SparkSession.builder().getOrCreate()
    val df = ss.createDataFrame(row, structType)
    // 定义表名字
    df.createOrReplaceTempView("tb")
    // dataframe, 是一个二维数据表的抽象
    val re = df.sqlContext.sql("select count(if(t.rank=t.click, 1, null)) as hit" +
      ", count(*) as total from tb as t")
    re.show()
    val next = re.toLocalIterator().next()
    val hit = next.getAs[Long]("hit")
    val total = next.getAs[Long]("total")
    println("=====" + hit + "=====" + total)
    println("hit rate:" + (hit.toFloat / total.toFloat))
  }
}
