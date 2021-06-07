package com.xiaowu5759.ditag.sparktag.es


import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeConfig
import com.xiaowu5759.ditag.sparktag.support.SparkSessionUtils
import org.apache.commons.beanutils.BeanUtils
import org.apache.spark.api.java.JavaPairRDD
import org.apache.spark.rdd.RDD
import org.elasticsearch.spark.rdd.EsSpark
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark

import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConverters._

/**
  * spark 操作es问题
  *
  * @author xiaowu
  * @ 2021/6/5 4:17 PM
  *
  */
object EsDemoScala {

  def main(args: Array[String]): Unit = {
    this.addEsDemo()
//    this.searchEsDemo()
  }

  // rdd 添加
  def addEsDemo(): Unit ={
    val sc = SparkSessionUtils.getSC2Es(true)

    val helloWorld = sc.parallelize(List("Hello,World!","Hello,Spark!","Hello,BigData!"))
    helloWorld.foreach(line => println(line))

    val arrayBuf: ArrayBuffer[User] = new ArrayBuffer[User]()
    arrayBuf.+=(new User("Jack", 18))
    arrayBuf.+=(new User("Eric", 20))
    val userRDD = sc.parallelize(arrayBuf)

    EsSpark.saveToEs(userRDD, "user/_doc")
    println("====== 上传es成功")
  }

  // es 查询
  def searchEsDemo(): Unit ={
    val sc = SparkSessionUtils.getSC2Es(true)
    // scala collection.map 和 Predef.Map
    val pairRDD: RDD[(String, collection.Map[String, AnyRef])] = EsSpark.esRDD(sc, "/user/_doc")
    val stringMap: collection.Map[String, collection.Map[String, AnyRef]] = pairRDD.collectAsMap()
    println("======" + JSON.toJSONString(stringMap, new SerializeConfig(true)))

    val userRDD = pairRDD.map(v1 => {
      val user = new User()
      BeanUtils.populate(user, v1._2.asJava)
      user
    })
    val collect = userRDD.collect()
    println("======" + JSON.toJSONString(collect, new SerializeConfig(true)))
  }

  // es query

  class User extends java.io.Serializable{
    var name: String = _
    var age: Int = _

    def this(name: String, age: Int) {
      this()
      this.name = name
      this.age = age
    }

    def getName: String = {
      this.name
    }

    def setName(name: String): Unit = {
      this.name = name
    }

    def getAge: Int = {
      this.age
    }

    def setAge(age: Int): Unit = {
      this.age = age
    }
  }

}
