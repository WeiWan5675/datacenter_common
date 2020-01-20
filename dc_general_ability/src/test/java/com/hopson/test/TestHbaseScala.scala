package com.hopson.test

import com.hopson.dc.ability.scala.hbase
import com.hopson.dc.ability.scala.hbase.{HbaseCore, HbaseFactory, HbaseOper, HbaseOperType}
import com.hopson.dc.common.config.Config
import org.apache.hadoop.hbase.client.{Connection, Table}
import org.junit.{Assert, Test}

import scala.collection.immutable.HashMap
import scala.collection.immutable.HashMap.HashTrieMap

/**
 * @Author: xiaozhennan
 * @Date: 2020/1/13 14:17
 * @Package: com.hopson.test
 * @ClassName: TestHbaseScala
 * @Description:
 **/
object TestHbaseScala {

  val config = new Config()
  config.put("hbase.zookeeper.server", "10.2.39.182,10.2.39.165,10.2.39.166")
  config.put("hbase.zookeeper.port", "2181")
  HbaseFactory.build(config)
  val core = new HbaseCore


  def testQueryRow(): Unit = {
    val oper = new HbaseOper
    oper.operType = HbaseOperType.ROW_QUERY
    oper.family = "info"
    oper.infos = "info:amount,info:bear_fee,info:classification,info:create_time,info:deduction_type,info:delivery_position,info:description,info:get_way,info:img,info:market_id,info:name,info:online_start_dt,info:operation_type,info:point,info:privilege_img,info:receive_limit_quantity,info:receive_limit_type,info:remaining_quantity,info:send_type,info:status,info:total_quantity,info:type,info:update_time,info:use_days,info:use_limit_quantity,info:use_limit_type,info:use_scope,info:use_source_count,info:use_type,info:user_id,info:user_name".split(",").toArray
    oper.rowkeys = Array[String]("1009")
    oper.table = "hopsonone:source_coupons"
    val result: Map[String, Any] = core.execHbaseOperation(oper)
    result.foreach(print)
  }

  def testQueryBatch(): Unit = {
    val oper = new HbaseOper
    oper.operType = HbaseOperType.BATCH_QUERY
    oper.family = "info"
    oper.infos = "info:amount,info:bear_fee,info:classification,info:create_time,info:deduction_type,info:delivery_position,info:description,info:get_way,info:img,info:market_id,info:name,info:online_start_dt,info:operation_type,info:point,info:privilege_img,info:receive_limit_quantity,info:receive_limit_type,info:remaining_quantity,info:send_type,info:status,info:total_quantity,info:type,info:update_time,info:use_days,info:use_limit_quantity,info:use_limit_type,info:use_scope,info:use_source_count,info:use_type,info:user_id,info:user_name".split(",").toArray
    oper.rowkeys = Array[String]("1009", "1098", "1097")
    oper.table = "hopsonone:source_coupons"
    val map = core.execHbaseOperation(oper)


    for ((k, vmap) <- map) {
      println("rowKey:" + k)
      val map = vmap.asInstanceOf[Map[String, String]]
    }
  }

  def testQueryScan(): Unit = {

    val oper = new HbaseOper
    oper.startKey = "1000"
    oper.endKey = "1097"
    oper.table = "hopsonone:source_coupons"
    oper.family = "info"
    oper.infos = "info:amount,info:bear_fee,info:market_id,info:name,info:online_start_dt,info:operation_type,info:point,info:privilege_img,info:receive_limit_quantity,info:receive_limit_type,info:remaining_quantity,info:send_type,info:status,info:total_quantity,info:type,info:update_time,info:use_type,info:user_id,info:user_name".split(",").toArray
    oper.operType = HbaseOperType.SCAN_QUERY
    val map = core.execHbaseOperation(oper)
    map.s
    for ((k, v) <- map) {
      println(k)
      val resMap = v.asInstanceOf[Map[String, String]]
      for ((k, v) <- resMap) {
        println(k)
        println(v)
      }
      println("---------------------------------------------------------")
    }
  }

  def testInsertData(): Unit = {
    val oper = new HbaseOper
    oper.rowkeys = Array("1090")
    oper.family = "info"
    val colls = Map("info:total_quantity" -> "300", "info:point" -> "1")
    oper.colls = colls
    val map = core.execHbaseOperation(oper)
    val status = map.get("status")
    val msg = map.get("msg")


  }

  def main(args: Array[String]): Unit = {
    //    testQueryRow();
    //    testQueryBatch();
    //    testQueryScan()

    testInsertData();
  }


}
