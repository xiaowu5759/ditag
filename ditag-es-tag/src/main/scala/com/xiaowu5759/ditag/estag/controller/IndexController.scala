package com.xiaowu5759.ditag.estag.controller

import com.xiaowu5759.common.result.{ResultVO, ResultWrapper}
import com.xiaowu5759.common.util.AliPayUtils
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RestController}
import org.springframework.web.servlet.ModelAndView

/**
  * 首页专用
  *
  * @author xiaowu
  * @ 2021/6/8 9:36 AM
  *
  */
@RestController
class IndexController {
  private val log = LoggerFactory.getLogger(classOf[IndexController])

  @GetMapping(Array("/"))
  def get(): ModelAndView = {
    val mav = new ModelAndView("/index.ftl")
    log.info("进入接口")
    mav
  }
}
