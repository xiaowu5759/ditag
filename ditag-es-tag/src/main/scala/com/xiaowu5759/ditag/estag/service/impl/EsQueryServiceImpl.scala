package com.xiaowu5759.ditag.estag.service.impl

import com.xiaowu5759.ditag.estag.domain.{EsTag, MemberTag}
import com.xiaowu5759.ditag.estag.service.{DemoService, EsQueryService}
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.mutable.ArrayBuffer

/**
  *
  * 关于es查询的基本信息
  *
  * @author xiaowu
  * @ 2021/6/8 9:09 PM
  *
  */
@Service
class EsQueryServiceImpl(@Autowired private val restHighLevelClient: RestHighLevelClient) extends EsQueryService{
  override def buildQuery(tags: ArrayBuffer[EsTag]): ArrayBuffer[MemberTag] = {

    null
  }
}
