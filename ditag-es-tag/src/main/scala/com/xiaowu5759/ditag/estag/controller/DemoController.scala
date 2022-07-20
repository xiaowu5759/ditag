package com.xiaowu5759.ditag.estag.controller

import java.util

import com.xiaowu5759.common.result.{ResultVO, ResultWrapper}
import com.xiaowu5759.ditag.estag.service.DemoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RestController}

import scala.collection.mutable
import scala.collection.JavaConverters._

/**
  * @author xiaowu
  * @ 2021/6/7 10:56 AM
  *
  */
@RestController
@RequestMapping(Array("/demo"))
class DemoController(@Autowired private val demoService: DemoService) {

  @GetMapping
  def get(): ResultVO[util.Map[String, AnyRef]] = {
    val str = demoService.demo()
    // 封装返回对象
    val returnMap = new mutable.HashMap[String, AnyRef]
    returnMap.put("str", str)
    ResultWrapper.ok(returnMap.asJava)
  }

}
