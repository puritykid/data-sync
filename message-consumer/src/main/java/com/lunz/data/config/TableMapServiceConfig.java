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
@ConfigurationProperties(prefix = "table-mapping")
public class TableMapServiceConfig {
    private Map<String, String> maps;
    public static Map<String, String> staticMap = new HashMap<>();

    @PostConstruct
    public void initConfig() {
        if (maps == null || maps.isEmpty()) {
            throw new RuntimeException("配置文件问题");
        }
        staticMap.putAll(maps);
        log.info("配置初始化完成！");
    }

    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }
}