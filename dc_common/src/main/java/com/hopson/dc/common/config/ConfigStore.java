package com.hopson.dc.common.config;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: xiaozhennan
 * @Date: 2020/1/12 21:36
 * @Package: com.hopson.dc.common.config
 * @ClassName: ConfigStore
 * @Description: 有几种加载模式,具体加载模式取决于创建的ConfigStore
 **/
public class ConfigStore extends ConcurrentHashMap<String, Config> {

    public Config getModule(String key) {
        return this.get(key);
    }

    public Config putModule(String key, Config module) {
        return this.put(key, module);
    }

    public void init() {

    }
}
