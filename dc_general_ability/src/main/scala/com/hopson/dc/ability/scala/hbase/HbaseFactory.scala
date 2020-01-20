package com.hopson.dc.ability.scala.hbase

import com.hopson.dc.common.config.Config
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{Admin, Connection, ConnectionFactory, Table}

/**
 * @Author: xiaozhennan
 * @Date: 2020/1/13 11:51
 * @Package: com.hopson.dc.ability.scala.hbase
 * @ClassName: HbaseFactory
 * @Description:
 **/
object HbaseFactory {

  private val instance: HbaseFactory = new HbaseFactory;
  private var init_flag = false;

  def getInstance(): HbaseFactory = {
    if (init_flag == false) {
      println("hbase没有初始化!")
      throw new RuntimeException
    }
    instance
  }

  def build(config: Config) = {
    if (!init_flag) {
      val zookeeperStr = config.get("hbase.zookeeper.server")
      val zookeeperPort = config.get("hbase.zookeeper.port")
      instance.hbasecfg.set("hbase.zookeeper.quorum", zookeeperStr)
      instance.hbasecfg.set("hbase.zookeeper.property.clientPort", zookeeperPort)
      instance.conn = ConnectionFactory.createConnection(instance.hbasecfg)
      instance.admin = instance.conn.getAdmin
      init_flag = true
    }
  }


}

class HbaseFactory private {


  private var conn: Connection = null
  private var admin: Admin = null
  private var hbasecfg: Configuration = HBaseConfiguration.create

  def getConnection(): Connection = {
    return this.conn;
  }

  def getAdmin(): Admin = {
    return this.admin;
  }

  def getTable(tableName: String): Table = {
    val table = conn.getTable(TableName.valueOf(tableName));
    return table;
  }

}




