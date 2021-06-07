package com.xiaowu5759.ditag.spscala.service.impl

import com.xiaowu5759.ditag.spscala.service.DemoService
import org.springframework.stereotype.Service

/**
  * 如何实现继承
  *
  * @author xiaowu
  * @ 2021/6/7 11:03 AM
  *
  */
@Service
class DemoServiceImpl extends DemoService {
  override def demo: String = {
    val string = "demoService"
    string
  }
}
