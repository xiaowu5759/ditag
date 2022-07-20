package com.xiaowu5759.ditag.estag.service

import com.xiaowu5759.ditag.estag.domain.{EsTag, MemberTag}

import scala.collection.mutable.ArrayBuffer

/**
  * @author xiaowu
  * @ 2021/6/8 9:06 PM
  *
  */
trait EsQueryService {

  // 根据tag，查询对应的基本信息
  def buildQuery(tags: ArrayBuffer[EsTag]): ArrayBuffer[MemberTag]
}
