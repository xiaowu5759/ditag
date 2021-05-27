package com.xiaowu5759.ditag.sparktag.spark

import org.apache.spark.sql.SparkSession

/**
  * Spark 利用 hive的元数据
  *
  * @author xiaowu
  * @ 2021/5/22 1:53 PM
  *
  */
object SparkSqlOnHive {

  def main(args: Array[String]): Unit = {
    // 利用hive元数据创建
    // SparkSession
    val ss = SparkSession.builder()
      .appName("SparkOnHive")
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "hdfs://gown:8020/user/hive/warehouse")
      .config("hive.metastore.uris", "thrift://emilia:9083")
      .enableHiveSupport()
      .getOrCreate()

    // 设置日志级别
    ss.sparkContext.setLogLevel("DEBUG")

    // 查看有哪些数据库
    ss.sql("show databases").show()

    // 也可以创建表
//    CREATE TABLE person (id int, name string, age int) row format delimited fields terminated by '\t'

    // 也可以加载数据
//    LOAD DATA LOCAL INPATH 'in/person.txt' INTO TABLE person

    ss.stop()
  }

}
