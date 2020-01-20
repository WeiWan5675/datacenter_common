package com.hopson.test

import com.hopson.dc.ability.scala.hbase
import com.hopson.dc.ability.scala.hbase.{HbaseCore, HbaseFactory, HbaseOper, HbaseOperType}
import com.hopson.dc.common.config.Config
import org.apache.hadoop.hbase.client.{Connection, Table}

/**
 * @Author: xiaozhennan
 * @Date: 2020/1/13 14:17
 * @Package: com.hopson.test
 * @ClassName: TestHbaseScala
 * @Description:
 **/
object TestHbaseScala {


  def main(args: Array[String]): Unit = {
    val config = new Config()
    config.put("hbase.zookeeper.server","10.2.39.182,10.2.39.165,10.2.39.166")
    config.put("hbase.zookeeper.port","2181")
    HbaseFactory.build(config)
    val instance : HbaseFactory  = HbaseFactory.getInstance();
    val core = new HbaseCore
    val oper = new HbaseOper
    oper.hbaseOperType = HbaseOperType.ROW_QUERY
    oper.family = "info"
    oper.infos = List[String]("info:business_id,info:business_name,info:consume_market_id,info:consume_market_name,info:create_time,info:mid,info:only_flag,info:order_id,info:order_time,info:source,info:total_fee,info:update_time")
    oper.rowkeys = List[String]("100085")
    oper.table = "ods_bill"

    val result : Map[String,Any]  = core.execHbaseOperation(oper)

  }

}
