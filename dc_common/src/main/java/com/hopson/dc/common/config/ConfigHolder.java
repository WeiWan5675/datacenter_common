package com.hopson.dc.common.config;


import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/1/12 21:07
 * @Package: com.hopson.dc.common
 * @ClassName: ConfigHolder
 * @Description: 处理配置相关的操作, 如读取配置文件, 获取相关配置等
 **/
public class ConfigHolder {

    private static final ConfigStore cache = new ConfigStore();
    private static ConfigHolder instance;
    private Map<String, String> configuration;

    public ConfigHolder(Map<String, String> configuration) {
        this.configuration = configuration;
    }


    public static ConfigStore getCache() {
        return cache;
    }


    public static Config getConfig(String key) {
        return cache.get(key);
    }

    /**
     * 单例方法
     *
     * @param configuration
     * @return ConfigHolder
     */
    public static ConfigHolder build(Map<String, String> configuration) {
        if (null == instance) {
            synchronized (ConfigHolder.class) {
                instance = new ConfigHolder(configuration);
                instance.init();
            }
        }
        return instance;
    }

    public static Config loadConf(String conf_file) {

        return new Config();
    }

    private void init() {

    }

    public static ConfigHolder build() {
        if (null == instance) {
            synchronized (ConfigHolder.class) {
                instance = new ConfigHolder(null);
                instance.init();
            }
        }
        return instance;
    }


}
