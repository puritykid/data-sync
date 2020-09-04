package com.lunz.data.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 汽车汽车信息表
 */
@Data
@TableName("Vehicle_Info")
public class VehicleInfo implements Serializable {

    private static final long serialVersionUID = -8951298221091887812L;

    private String Id;

    private String SeriesId;

    private String Name;

    private Integer Year;

    private Float Price;

    private Integer Sales;

    private String Manufacturer;

    private String ModuleId;

    private String Displacement;

    private Integer Horsepower;

    private String DriveId;

    private String GearboxId;

    private String SeatId;

    private String StructureId;

    private String FuelId;

    private String NationId;

    private String AffiliationId;

    private Integer FirstMaintenMiles;

    private Integer FirstMaintenTime;

    private Integer SecondMaintenMiles;

    private Integer SecondMaintenTime;

    private Integer MaintenCycleMiles;

    private Integer MaintenCycleTime;

    private Integer SortOrder;

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

    private String Text;

    private Integer MySqlId;

    private String IntakeType;

    private String DisplacementText;
}
