package com.xiaowu5759.ditag.estag

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
  * object 添加
  *
  * @author xiaowu
  * @ 2021/6/7 11:21 AM
  *
  */
@SpringBootApplication
class EsTagApplication
object EsTagApplication {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[EsTagApplication], args :_*)
  }
}
