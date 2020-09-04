package com.lunz.dto.base;

import lombok.Data;

/**
 * @author Liuruixia
 * @Description:
 * @date 2019/10/09
 */
@Data
public class SourceDTO {
    /**
     * version
     */
    private String version;
    /**
     * connector
     */
    private String connector;
    /**
     * name
     */
    private String name;
    /**
     * ts_ms
     */
    private Long tsMs;
    /**
     * snapshot
     */
    private Boolean snapshot;
    /**
     * db
     */
    private String db;
    /**
     * schema
     */
    private String schema;
    /**
     * table
     */
    private String table;

}
