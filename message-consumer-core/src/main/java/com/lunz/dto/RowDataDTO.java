package com.lunz.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.lunz.util.Operate;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Liuruixia
 * @Description:
 * @date 2019/10/09
 */
@Data
@NoArgsConstructor
public class RowDataDTO {
    public RowDataDTO(String name, String db, String table, Long tsMs, Operate.OperationType operationType, ColumnDataDTO rowData) {
        this.name = name;
        this.db = db;
        this.table = table;
        this.tsMs = tsMs;
        this.operationType = operationType;
        this.columnData = rowData;
    }

    /**
     * 记录主键id
     */
    private String id;

    /**
     * 数据源 name eg:UC-SqlServer
     */
    @JSONField(serialize = false)
    private String name;

    /**
     * 数据库 eg:UserCenter-2015
     */
    @JSONField(serialize = false)
    private String db;

    /**
     * table名称 eg:Sys_User
     */
    private String table;

    /**
     * 执行时间戳 eg:1570523322529
     */
    private Long tsMs;
    /**
     * 操作类型 增删改
     */
    private Operate.OperationType operationType;
    /**
     * rowData 包含了所有列字段对应关系的行记录
     */
    private ColumnDataDTO columnData;
}
