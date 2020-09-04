package com.lunz.data.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 汽车汽车图片表
 */
@Data
@TableName("Vehicle_Photo")
public class VehiclePhoto implements Serializable {
    private static final long serialVersionUID = -6438354723343504353L;

    private String Id;

    private String VehicleId;

    private String CategoryId;

    private String OrientationId;

    private String ImageId;

    private String Description;

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
}
