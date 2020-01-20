package com.hopson.dc.ability.java.hbase;

import com.hopson.dc.common.config.Config;
import com.hopson.dc.common.config.ConfigHolder;
import com.hopson.dc.common.config.ConfigStore;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;


/**
 * @Date: 2018/8/23 11:00
 * @Author: xiaozhennan
 * @Package: com.ipaynow.dc.srv.factory
 * @ClassName: HbaseFactory
 * @Description: 访问Hbase单例工程, 保存Hbase链接
 **/
public class HbaseFactory {


    private static volatile HbaseFactory instance = null;

    private HbaseFactory() {
    }

    private Configuration hbaseCfg = null;
    private Config config = null;
    private Connection conn;

    private Admin hbaseAdmin;
    private boolean init_flag = false;

    public static HbaseFactory getInstance() {
        if (instance != null) {
            return instance;
        }
        return null;
    }

    public static void build(Config cfg) {
        if (null == instance) {
            synchronized (HbaseFactory.class) {
                instance = new HbaseFactory();
                instance.config = cfg;
                try {
                    instance.init();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void init() throws IOException {
        hbaseCfg = HBaseConfiguration.create();
        ConfigStore cache = ConfigHolder.getCache();
        String zookeeperStr = config.get("hbase.zookeeper.server");
        String zookeeperPort = config.get("hbase.zookeeper.port");
        hbaseCfg.set("hbase.zookeeper.quorum", zookeeperStr);
        hbaseCfg.set("hbase.zookeeper.property.clientPort", zookeeperPort);
        conn = ConnectionFactory.createConnection(hbaseCfg);
        hbaseAdmin = conn.getAdmin();
        this.init_flag = true;
    }

    public Connection getConnection() {
        return conn;
    }

    public Admin getHbaseAdmin() {
        return hbaseAdmin;
    }

}
