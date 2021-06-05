package com.xiaowu5759.ditag.sparktag.begin

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author xiaowu
  * @ 2021/5/21 2:54 PM
  *
  */
object HelloSpark {
  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "root")
    val conf = new SparkConf().setMaster("local").setAppName("HelloSpark")

    val sc = new SparkContext(conf)

    val helloWorld = sc.parallelize(List("Hello,World!","Hello,Spark!","Hello,BigData!"))

    helloWorld.foreach(line => println(line))

  }
}
