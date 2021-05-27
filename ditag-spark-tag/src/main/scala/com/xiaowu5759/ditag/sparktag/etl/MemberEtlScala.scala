package com.xiaowu5759.ditag.sparktag.etl

import java.util.stream.Collectors

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.{SerializeConfig, SerializerFeature}
import org.apache.spark.sql.SparkSession

/**
  * @author xiaowu
  * @ 2021/5/22 10:43 PM
  *
  */
object MemberEtlScala {

  def getSparkSession: SparkSession = {
    // 创建上下文
    SparkSession.builder()
      .appName("MemberEtl")
      .master("local[2]")
      .config("spark.sql.warehouse.dir", "hdfs://gown:8020/user/hive/warehouse")
      .config("hive.metastore.uris", "thrift://emilia:9083")
      .enableHiveSupport()
      .getOrCreate()

  }

  /**
    * memberSex etl
    *
    * @param sparkSession
    * @return
    */
  def listMemberSex(sparkSession: SparkSession): Array[MemberSex] = {
    val dataframe = sparkSession.sql("select sex as memberSex, count(id) as sexCount " +
      "from i_member.t_member group by sex")

    // 将查到的mysql的数据 转化为 pojo
    val arr = dataframe.toJSON.collect()

    // 这里list 不能直接Lambda操作，所以先操作成流
    // 为了实现 类型的自动断言
    arr.map(str => JSON.parseObject(str, classOf[MemberSex]))
  }

  /**
    * memberRegChannel 用户注册渠道
    *
    * @param sparkSession
    * @return
    */
  def listMemberRegChannel(sparkSession: SparkSession): Array[MemberRegChannel] = {
    val dataframe = sparkSession.sql("select member_channel as memberChannel, count(id) as channelCount " +
      "from i_member.t_member group by member_channel")

    // 将查到的mysql的数据 转化为 pojo
    val arr = dataframe.toJSON.collect()

    arr.map(str => JSON.parseObject(str, classOf[MemberRegChannel]))
  }

  /**
    * 用户有没有关注我们的 微信公众号
    *
    * @param sparkSession
    * @return
    */
  def listMemberMpSub(sparkSession: SparkSession): Array[MemberMpSub] = {
    // sqoop 导入数据库表时，如果没有特别设置，null -> 'null'  mp_open_id is not null;
    // 可以采用 if，一个sql全部查询
    // 这里需要转变思路，在业务开发中，默认都是dao层复用，jpa，而数据开发中尽量一个sql查出所有
    val dataframe = sparkSession.sql("select count(if(mp_open_id is not null, id, null)) as subCount," +
      "count(if(mp_open_id is null, id, null)) as unSubCount " +
      "from i_member.t_member")

    // 将查到的mysql的数据 转化为 pojo
    val arr = dataframe.toJSON.collect()

    arr.map(str => JSON.parseObject(str, classOf[MemberMpSub]))
  }

  /**
    * 获取用户分群，复购，没有复购人数
    *
    * @param sparkSession
    * @return
    */
  def getMemberHeat(sparkSession: SparkSession): MemberHeat = {
    // reg， complete， order， orderAgain， coupon
    // reg, complete ==> i_member.t_member
    // order, orderAgain ==> i_order.t_order
    // coupon ==> i_marketing.t_coupon_member

    // 只有一行记录
    val regComplete = sparkSession.sql("select count(if(phone is null, id, null)) as reg," +
      "count(if(phone is not null, id, null)) as complete " +
      "from i_member.t_member")

    // order, orderAgain
    // 采用子查询
    val orderAgain = sparkSession.sql("select count(if(t1.orderCount = 1, t1.member_id, null)) as order," +
      "count(if(t1.orderCount >= 2, t1.member_id, null)) as orderAgain " +
      "from (select count(order_id) as orderCount, member_id from i_order.t_order group by member_id) as t1")

    // coupon 多少用户领了券
    val coupon = sparkSession.sql("select count(distinct member_id) as coupon from i_marketing.t_coupon_member")

    val result = coupon.crossJoin(regComplete).crossJoin(orderAgain)
    val arr = result.toJSON.collect()
    // 这里parse一个
    val collect = arr.map(str => JSON.parseObject(str, classOf[MemberHeat]))
    collect(0)
  }


  def main(args: Array[String]): Unit = {
    val ss = this.getSparkSession

    val memberSexes = this.listMemberSex(ss)
    val memberRegChannels = this.listMemberRegChannel(ss)
    val memberMpSubs = this.listMemberMpSub(ss)
    val memberHeat = this.getMemberHeat(ss)

    val memberVO = new MemberVO()
    memberVO.memberSexes = memberSexes
    memberVO.memberRegChannels = memberRegChannels
    memberVO.memberMpSubs = memberMpSubs
    memberVO.memberHeat = memberHeat
    println("=====" + JSON.toJSONString(memberVO, new SerializeConfig(true)) + "======")

  }

  class MemberSex() {
    var sexType: Int = _
    var sexCount: Int = _

    def getSexType: Int = {
      this.sexType
    }

    def getSexCount: Int = {
      this.sexCount
    }

    def setSexType(sexType: Int): Unit = {
      this.sexType = sexType
    }

    def setSexCount(sexCount: Int): Unit = {
      this.sexCount = sexCount
    }
  }

  class MemberRegChannel() {
    var memberChannel: Int = _
    var channelCount: Int = _

    def getMemberChannel: Int = {
      this.memberChannel
    }

    def getChannelCount: Int = {
      this.channelCount
    }

    def setSexType(memberChannel: Int): Unit = {
      this.memberChannel = memberChannel
    }

    def setChannelCount(channelCount: Int): Unit = {
      this.channelCount = channelCount
    }

  }

  class MemberMpSub() {
    var subCount: Int = _
    var unSubCount: Int = _

    def getSubCount: Int = {
      this.subCount
    }

    def getUnSubCount: Int = {
      this.unSubCount
    }

    def setSubCount(subCount: Int): Unit = {
      this.subCount = subCount
    }

    def setUnSubCount(unSubCount: Int): Unit = {
      this.unSubCount = unSubCount
    }
  }

  class MemberVO() {
    var memberSexes: Array[MemberSex] = _
    var memberRegChannels: Array[MemberRegChannel] = _
    var memberMpSubs: Array[MemberMpSub] = _
    var memberHeat: MemberHeat = _

  }

  // 用户标签
  class MemberHeat(){
    var reg: Int = _
    var complete: Int = _
    var order: Int = _
    var orderAgain: Int = _
    var coupon: Int = _

    def getReg: Int = {
      this.reg
    }

    def setReg(reg: Int): Unit = {
      this.reg = reg
    }

    def getComplete: Int = {
      this.complete
    }

    def setComplete(complete: Int): Unit = {
      this.complete = complete
    }

    def getOrder: Int = {
      this.order
    }

    def setOrder(order: Int): Unit = {
      this.order = order
    }

    def getOrderAgain: Int = {
      this.orderAgain
    }

    def setOrderAgain(orderAgain: Int): Unit = {
      this.orderAgain = orderAgain
    }

    def getCoupon: Int = {
      this.coupon
    }

    def setCoupon(coupon: Int): Unit = {
      this.coupon = coupon
    }

  }

}
