package com.lunz.data.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 汽车保养项目表
 */
@Data
@TableName("Vehicle_MaintcProjectInfo")
public class VehicleMaintcProjectInfo implements Serializable {
    private static final long serialVersionUID = 2452643992265930508L;

    private String Id;

    private String ProjectNo;

    private String ProjectName;

    private String CreatedById;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private String CreatedAt;

    private String UpdatedById;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private String UpdatedAt;

    private Boolean Deleted;

    private String DeletedById;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private String DeletedAt;

    private Integer SortOrder;
}
