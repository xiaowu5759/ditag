package com.xiaowu5759.ditag.spscala.controller

import com.xiaowu5759.common.result.{ResultVO, ResultWrapper}
import com.xiaowu5759.ditag.spscala.service.DemoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RestController}

/**
  * @author xiaowu
  * @ 2021/6/7 10:56 AM
  *
  */
@RestController
@RequestMapping(Array("/demo"))
class DemoController(@Autowired private val demoService: DemoService) {

  @GetMapping
  def get: ResultVO[String] = {
    val str = demoService.demo()
    ResultWrapper.ok(str)
  }

}
