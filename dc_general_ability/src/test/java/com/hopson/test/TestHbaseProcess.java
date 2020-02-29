package com.hopson.test;

import com.hopson.dc.ability.java.hbase.HbaseFactory;
import com.hopson.dc.common.config.Config;
import com.hopson.dc.common.config.ConfigHolder;

import java.io.IOException;

/**
 * @Author: xiaozhennan
 * @Date: 2020/1/13 0:01
 * @Package: com.hopson.test
 * @ClassName: TestHbaseProcess
 * @Description:
 **/
public class TestHbaseProcess {
    public static void main(String[] args) {
        Config cfg = ConfigHolder.loadConf("dev.properties");
        HbaseFactory.build(cfg);
        HbaseFactory instance = HbaseFactory.getInstance();
//        val hc : HbaseCore = HbaseCore();
//        Connection conn = hf.getConnection();
    }
}
