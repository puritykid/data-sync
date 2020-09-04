package com.lunz.data.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 表和service映射关系
 *
 * @author Liuruixia
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "data-mapping")
public class DataMapServiceConfig {
    private Map<String, String> mysqlidmaps;
    public static Map<String, String> staticDataMap = new HashMap<>();

    @PostConstruct
    public void initConfig() {
        if (mysqlidmaps == null || mysqlidmaps.isEmpty()) {
            throw new RuntimeException("配置文件问题");
        }
        staticDataMap.putAll(mysqlidmaps);
        log.info("配置初始化完成！");
    }

    public Map<String, String> getMysqlidmaps() {
        return mysqlidmaps;
    }

    public void setMysqlidmaps(Map<String, String> mysqlidmaps) {
        this.mysqlidmaps = mysqlidmaps;
    }
}