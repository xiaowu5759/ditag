package com.xiaowu5759.ditag.estag.domain

/**
  * 提交表单的基本信息
  * type -> kind type是关键字
  *
  * @author xiaowu
  * @ 2021/6/8 9:21 PM
  *
  */
class EsTag {

  var name: String = _
  var value: String = _
  var kind: String = _

  def getName: String = {
    this.name
  }

  def setName(name: String): Unit = {
    this.name = name
  }

  def getValue: String = {
    this.value
  }

  def setValue(value: String): Unit = {
    this.value = value
  }

  def getKind: String = {
    this.kind
  }

  def setKind(kind: String): Unit = {
    this.kind = kind
  }

}
