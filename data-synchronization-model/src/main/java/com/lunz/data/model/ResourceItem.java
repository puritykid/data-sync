package com.lunz.data.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 汽车图片表
 */
@Data
@TableName("Basic_ResourceItem")
public class ResourceItem implements Serializable {

    private static final long serialVersionUID = -3619270853216199682L;

    private String Id;

    private String FileName;

    private String Url;

    private String FileType;

    private String FileLength;

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
