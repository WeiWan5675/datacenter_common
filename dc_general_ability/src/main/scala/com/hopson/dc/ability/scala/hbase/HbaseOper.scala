package com.hopson.dc.ability.scala.hbase

import com.hopson.dc.ability.scala.hbase.HbaseOperType.HbaseOperType

import scala.collection.mutable.ListBuffer

/**
 * @Author: xiaozhennan
 * @Date: 2020/1/13 15:52
 * @Package: com.hopson.dc.ability.scala.hbase
 * @ClassName: HbaseOper
 * @Description:
 **/
class HbaseOper {

  var datas : ListBuffer[HbaseData]= null
  var operType: HbaseOperType = null;
  var table: String = null;
  var family: String = null;
  var infos: Array[String] = null;
  var rowkeys: Array[String] = null;
  var rowKey: String  = null;
  var startKey : String = null;
  var endKey : String = null;
}

object HbaseOperType extends Enumeration {
  type HbaseOperType = Value //声明枚举对外暴露的变量类型
  val ROW_QUERY = Value(1, "行查询")
  val BATCH_QUERY = Value(2, "多行查询")
  val SCAN_QUERY = Value(3, "扫描查询")
  val INSERT_ROW = Value(4, "插入数据")
  val DELETE_ROW = Value(5, "删除数据")
  val MAKE_TABLE = Value(6, "表操作")

  def checkExists(day: String) = this.values.exists(_.toString == day) //检测是否存在此枚举值
  def showAll = this.values.foreach(println) // 打印所有的枚举值
}


class HbaseData{
  var tableName = null
  var rowKey = null
  var data = Map[String,String] = null
}
