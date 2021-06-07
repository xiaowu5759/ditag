package com.xiaowu5759.ditag.spscala

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
  * @author xiaowu
  * @ 2021/6/7 10:37 AM
  *
  */
// 以下main方法不能启动
//@SpringBootApplication
//class SpScalaApplication {
//  def main(args: Array[String]): Unit = {
//    // 创建一个 clazz 数组
////    val application = Array(classOf[SpScalaApplication])
//    SpringApplication.run(classOf[SpScalaApplication], args: _*)
//  }
//}

// spring的入口类。运行时，该类引导spring应用程序并启动spring上下文
@SpringBootApplication
class SpScalaApplication
object SpScalaApplication extends App {
  SpringApplication.run(classOf[SpScalaApplication], args :_*)
}
