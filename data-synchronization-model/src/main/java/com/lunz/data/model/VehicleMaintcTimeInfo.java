package com.lunz.data.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author haha
 */
@Data
@TableName("Vehicle_MaintcTimeInfo")
public class VehicleMaintcTimeInfo implements Serializable {
    private static final long serialVersionUID = 4591147173414957109L;

    private String ID;

    private Integer TimeValue;

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
}
