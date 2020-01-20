package com.hopson.dc.ability.scala.hbase

import java.util

import org.apache.hadoop.hbase.{Cell, CellUtil, TableName}
import org.apache.hadoop.hbase.client.{Admin, Connection, Get, HTable, Result, Scan, Table}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConverters._


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


  def execHbaseOperation(oper: HbaseOper): Map[String, Any] = {
    val table: Table = conn.getTable(TableName.valueOf(oper.table))
    oper.operType match {
      case HbaseOperType.ROW_QUERY =>
        this.singleQuery(table, oper.infos, oper.rowKey)
      case HbaseOperType.BATCH_QUERY =>
        this.batchQuery(table, oper.infos, oper.rowkeys)
      case HbaseOperType.SCAN_QUERY =>
        this.scanQuery(table, oper.startKey, oper.endKey, oper.infos)
      case HbaseOperType.INSERT_ROW =>
        this.insertRow(table,oper.rowkeys,oper.colls)
        null
      case HbaseOperType.DELETE_ROW =>
        null
      case _ =>
        null
    }
  }


  def scanQuery(table: Table, startKey: String, endKey: String, infos: Array[String]): Map[String, Map[String, String]] = {
    var res: Map[String, Map[String, String]] = Map()
    val scan = new Scan
    scan.setStartRow(startKey.getBytes())
    scan.setStopRow(endKey.getBytes())
    scan.setMaxVersions(1)
    for (t <- infos) {
      val infos = t.split(":")
      val f = infos(0)
      val c = infos(1)
      scan.addColumn(f.getBytes(), c.getBytes())
    }
    val resScaner = table.getScanner(scan)
    val items = resScaner.iterator()
    while (items.hasNext) {
      val result = items.next()
      val resMap = transRowformCells(result.rawCells())
      res += (new String(result.getRow) -> resMap)
    }
    res
  }


  def addQualifierInfo(infos: Array[String], get: Get): Unit = {
    for (info <- infos) {
      val tmp = info.split(":")
      val family = tmp(0)
      val qualifier = tmp(1)
      get.addColumn(family.getBytes, qualifier.getBytes)
    }
  }

  def transRowformCells(cells: Array[Cell]): Map[String, String] = {
    var res: Map[String, String] = Map()
    for (cell <- cells) {
      val key = new String(CellUtil.cloneFamily(cell), "utf-8") + ":" + new String(CellUtil.cloneQualifier(cell), "utf-8");
      val value = new String(CellUtil.cloneValue(cell), "utf-8")
      res += (key -> value)
    }
    res
  }

  def singleQuery(table: Table, infos: Array[String], rowKye: String): Map[String, String] = {
    val get = new Get(rowKye.getBytes())
    addQualifierInfo(infos, get)
    val result = table.get(get)
    val cells = result.rawCells()
    transRowformCells(cells)
  }


  def batchQuery(table: Table, infos: Array[String], rowkeys: Array[String]): Map[String, Map[String, String]] = {
    var res: Map[String, Map[String, String]] = Map()
    val gets: ArrayBuffer[Get] = ArrayBuffer()

    for (key <- rowkeys) {
      val get: Get = new Get(key.getBytes())
      addQualifierInfo(infos, get)
      gets.+=(get)
    }

    val results = table.get(gets.asJava)
    for (result <- results) {
      val cells = result.rawCells()
      val map = transRowformCells(cells)
      res += (new String(result.getRow) -> map)
    }
    res
  }

}
