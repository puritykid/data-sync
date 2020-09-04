package com.lunz.data.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 汽车品牌表
 */
@Data
@TableName("Vehicle_Brand")
public class VehicleBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    private String Id;

    private String Code;

    private String Name;

    private String Abbreviation;

    private String EnglishName;

    private String LogoId;

    private String ParentId;

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

    private Integer MySqlId;

}
