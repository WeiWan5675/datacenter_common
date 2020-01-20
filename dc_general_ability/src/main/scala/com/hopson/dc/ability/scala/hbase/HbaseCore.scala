package com.hopson.dc.ability.scala.hbase

import org.apache.hadoop.hbase.{Cell, CellUtil, TableName}
import org.apache.hadoop.hbase.client.{Admin, Connection, Get, HTable, Table}

/**
 * @Author: xiaozhennan
 * @Date: 2020/1/13 15:31
 * @Package: com.hopson.dc.ability.scala.hbase
 * @ClassName: HbaseCore
 * @Description:
 **/
class HbaseCore {


  private val hbaseFactory: HbaseFactory = HbaseFactory.getInstance()
  private val conn: Connection = hbaseFactory.getConnection()
  private val admin: Admin = hbaseFactory.getAdmin();


  def addQualifierInfo(infos: List[String], get: Get): Unit = {
    for (info <- infos) {
      val tmp = info.split(":")
      val family = tmp(0)
      val qualifier = tmp(1)
      get.addColumn(family.getBytes, qualifier.getBytes)
    }
  }

  def transRowformCells(cells: Array[Cell]): Map[String, Any] = {
    var res: Map[String, Any] = Map()
    for (cell <- cells) {
      val key = new String(CellUtil.cloneFamily(cell), "utf-8") + ":" + new String(CellUtil.cloneQualifier(cell), "utf-8");
      val value = new String(CellUtil.cloneValue(cell), "utf-8")
      res += (key -> value)
    }
    res
  }

  def singleQuery(table: Table, infos: List[String], rowKye: String): Map[String, Any] = {
    val get = new Get(rowKye.getBytes())
    addQualifierInfo(infos, get)
    val result = table.get(get)
    val cells = result.rawCells()
    transRowformCells(cells)
  }


  def batchQuery(table: Table, infos: List[String], rowkeys: List[String]): Map[String, Any] = {
//    val get = new Get(rowKye.getBytes())
//    addQualifierInfo(infos, get)
//    val result = table.get(get)
//    val cells = result.rawCells()
//    transRowformCells(cells)
    null
  }


  def execHbaseOperation(oper: HbaseOper): Map[String, Any] = {
    val table: Table = conn.getTable(TableName.valueOf(oper.table))
    var res: Map[String, Any] = null
    oper.hbaseOperType match {
      case HbaseOperType.ROW_QUERY =>
        this.singleQuery(table, oper.infos, oper.rowkeys.head)
      case HbaseOperType.BATCH_QUERY =>
        this.batchQuery(table, oper.infos, oper.rowkeys)
      case HbaseOperType.SCAN_QUERY =>
        val res: Map[String, Any] = Map()
        res
      case HbaseOperType.INSERT_ROW =>
        val res: Map[String, Any] = Map()
        res
      case HbaseOperType.DELETE_ROW =>
        val res: Map[String, Any] = Map()
        res
      case _ =>
        null
    }
  }


}
