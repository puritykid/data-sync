package com.lunz.data.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author haha
 */
@Data
@TableName("Vehicle_MaintenanceProject")
public class VehicleMaintenanceProject implements Serializable {
    private static final long serialVersionUID = -8994147469890905556L;

    private String Id;

    private String CarinfoId;

    private Integer FirstMaintenMiles;

    private Integer SecondMaintenMiles;

    private Integer MaintenCycleMiles;

    private Integer FirstMaintenTime;

    private Integer SecondMaintenTime;

    private Integer MaintenCycleTime;

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
