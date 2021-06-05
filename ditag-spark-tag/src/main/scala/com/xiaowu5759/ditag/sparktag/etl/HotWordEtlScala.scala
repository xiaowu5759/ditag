package com.xiaowu5759.ditag.sparktag.etl

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author xiaowu
  * @ 2021/5/21 10:16 PM
  *
  */
object HotWordEtlScala {
  def main(args: Array[String]): Unit = {
    val config = new SparkConf().setMaster("local").setAppName("hotwordscala")
    val sc = new SparkContext(config)
    val lines = sc.textFile("hdfs://gown:8020/data/SogouQ.sample")

    // 搜狗和用户认为的url，是统一的，占多大比例
    // 不需要设置具体类型
    val total = lines.count()
    // scala 的函数式编程
    // 入的参数 x
    // 操作x的方法体
    // 通过空格 切分，是否相等
    val hitCount = lines.map(x => x.split("\t")(3))
      // 上一步map 传出的操作
      .map(word => word.split(" ")(0).equals(word.split(" ")(1)))
      .filter(f => f).count()

    println("hit rate:" + (hitCount.toFloat / total.toFloat))

  }
}
