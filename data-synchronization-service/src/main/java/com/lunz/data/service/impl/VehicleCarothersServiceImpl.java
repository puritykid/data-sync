package com.lunz.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lunz.data.config.DataMapServiceConfig;
import com.lunz.data.mapper.AttributeMapper;
import com.lunz.data.mapper.VehicleInfoMapper;
import com.lunz.data.model.AttributeValueInfo;
import com.lunz.data.model.VehicleInfo;
import com.lunz.data.service.VehicleCarothersService;
import com.lunz.data.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class VehicleCarothersServiceImpl implements VehicleCarothersService {


    @Autowired
    private VehicleInfoMapper vehicleInfoMapper;
    @Autowired
    private AttributeMapper attributeMapper;

    @Override
    public void addOrUpdate(Map<String, String> paramMap) {
        String SPEC = getMappingId("SPEC");
        String OILSPEND = getMappingId("OILSPEND");
        String DOORS = getMappingId("DOORS");
        String MINHEIGHT = getMappingId("MINHEIGHT");
        // 获取车辆id
        String carinfoId = paramMap.get("CarinfoId");

        AttributeValueInfo specNode = GetData(carinfoId,SPEC);
         handleData(specNode,paramMap,"spec",SPEC);

        AttributeValueInfo oilNode = GetData(carinfoId,OILSPEND);
        handleData(oilNode,paramMap,"oil",OILSPEND);

        AttributeValueInfo doorNode = GetData(carinfoId,DOORS);
        handleData(doorNode,paramMap,"doors",DOORS);

        AttributeValueInfo minNode = GetData(carinfoId,MINHEIGHT);
        handleData(minNode,paramMap,"min",MINHEIGHT);

    }
    private String getMappingId(String mysqlId)
    {
        String sqlServerId = DataMapServiceConfig.staticDataMap.get(mysqlId);
        if(Objects.nonNull(sqlServerId))
        {
            if(sqlServerId=="NOCONTENT")
            {
                sqlServerId = null;
            }
        }
        return sqlServerId;
    }

    private void handleData(AttributeValueInfo node,Map<String, String> paramMap,String type,String attriId)
    {
        AttributeValueInfo entity = new AttributeValueInfo();

        switch (type)
        {
            case "spec":
                entity.setStringValue(paramMap.get("Length * width * height"));
                break;
            case "oil":
                entity.setStringValue(paramMap.get("OilWear"));
                break;
            case "min":
                entity.setStringValue(paramMap.get("MinimumClearance"));
                break;
            case "doors":
                entity.setStringValue(paramMap.get("Doors"));
                break;
        }

        entity.setCreatedById(paramMap.get("CreatedById"));
        entity.setCreatedAt(paramMap.get("CreatedAt"));
        entity.setUpdatedById(paramMap.get("UpdatedById"));
        entity.setUpdatedAt(paramMap.get("UpdatedAt"));
        entity.setDeletedAt(paramMap.get("DeletedAt"));
        entity.setDeletedById(paramMap.get("DeletedById"));
        entity.setDeleted(paramMap.get("Deleted")=="1"?true:false);

        if(Objects.isNull(node))
        {
            entity.setId(CommonUtil.getUUID());
            entity.setVehicleId(paramMap.get("VehicleId"));
            entity.setAttributeId(attriId);
            attributeMapper.insert(entity);

            // 执行插入操作
        }else
        {
            entity.setId(node.getId());
            entity.setVehicleId(node.getVehicleId());
            entity.setAttributeId(attriId);
            // 执行更新操作
            attributeMapper.updateById(entity);
        }
    }

    private AttributeValueInfo GetData(String vehicleId,String attrId)
    {
        QueryWrapper<AttributeValueInfo> minWrapper = Wrappers.<AttributeValueInfo>query()
                .select("top 1 Id")
                .eq("VehicleId", vehicleId).eq("AttributeId",attrId);
        AttributeValueInfo info =  attributeMapper.selectOne(minWrapper);
        // info.setVehicleId(vehicleId);
        return  info;
    }
}
