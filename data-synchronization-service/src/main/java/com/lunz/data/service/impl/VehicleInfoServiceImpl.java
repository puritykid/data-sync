package com.lunz.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunz.data.config.DataMapServiceConfig;
import com.lunz.data.mapper.AttributeMapper;
import com.lunz.data.mapper.VehicleInfoMapper;
import com.lunz.data.model.AttributeValueInfo;
import com.lunz.data.model.ResourceItem;
import com.lunz.data.model.VehicleBrand;
import com.lunz.data.model.VehicleInfo;
import com.lunz.data.service.VehicleInfoService;
import com.lunz.data.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
public class VehicleInfoServiceImpl extends ServiceImpl<VehicleInfoMapper, VehicleInfo> implements VehicleInfoService {


    @Autowired
    private VehicleInfoMapper vehicleInfoMapper;

    @Autowired
    private AttributeMapper attributeMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdate(Map<String, String> paramMap) {

        String inTakeTypeId = getMappingId("INTAKETYPE");
        VehicleInfo vehicleInfo = CommonUtil.mapToObj(paramMap, VehicleInfo.class);
        LambdaQueryWrapper<VehicleInfo> wrapper = Wrappers.<VehicleInfo>lambdaQuery()
                .select(VehicleInfo::getId)
                .eq(VehicleInfo::getId, vehicleInfo.getId());
        VehicleInfo node = vehicleInfoMapper.selectOne(wrapper);

        QueryWrapper<AttributeValueInfo> attrWrapper = Wrappers.<AttributeValueInfo>query()
                .select("top 1 Id")
                .eq("VehicleId", paramMap.get("Id")).eq("AttributeId", inTakeTypeId);
        AttributeValueInfo attrNode = attributeMapper.selectOne(attrWrapper);
        String FullName = "";

        StringBuffer sb = new StringBuffer();
        if ("0".equals(paramMap.get("CarFlag"))) {

            sb.append(paramMap.get("DisplacementText")).append(" ")
                    .append(getMappingName(paramMap.get("GearboxId")))
                    .append(" ")
                    .append(getMappingName(paramMap.get("DriveId")))
                    .append(" ")
                    .append(paramMap.get("Name"));
            FullName = sb.toString();

        } else {
            sb.append(paramMap.get("NoticeNum")).append(" ")
                    .append(paramMap.get("Horsepower")).append(" ")
                    .append(paramMap.get("Name")).append(" ")
                    .append(getMappingName(paramMap.get("FuleId"))).append(" ")
                    .append(getMappingName(paramMap.get("EmissionId"))).append(" ")
                    .append(paramMap.get("Capacity").toString());
            FullName = sb.toString();
            vehicleInfo.setYear(2000);
        }


        if (Objects.isNull(node)) {
            // 新增
            if (Objects.isNull(paramMap.get("Displacement"))) {
                vehicleInfo.setDisplacement("0.0");
            }else
            {
                vehicleInfo.setDisplacement(paramMap.get("Displacement"));
            }
            vehicleInfo.setName(FullName);
            vehicleInfo.setModuleId(getMappingId(paramMap.get("LevelId")));
            vehicleInfo.setDriveId(getMappingId(paramMap.get("DriveId")));
            vehicleInfo.setGearboxId(getMappingId(paramMap.get("GearboxId")));
            vehicleInfo.setSeatId(getMappingId(paramMap.get("SeatId")));
            vehicleInfo.setStructureId(getMappingId(paramMap.get("BodyStructureId")));
            vehicleInfo.setFuelId(getMappingId(paramMap.get("FuelId")));
            vehicleInfo.setNationId(getMappingId(paramMap.get("CountryId")));
            vehicleInfo.setSales(0);
            vehicleInfo.setIntakeType(getMappingId(paramMap.get("IntakeTypeId")));
            vehicleInfoMapper.insert(vehicleInfo);

            // 将进气形式写入 AttributeValue 表
            AttributeValueInfo attributeValueInfo = new AttributeValueInfo();
            attributeValueInfo.setId(CommonUtil.getUUID());
            attributeValueInfo.setAttributeId(inTakeTypeId);
            attributeValueInfo.setVehicleId(vehicleInfo.getId());
            attributeValueInfo.setStringValue(getMappingId(paramMap.get("IntakeTypeId")));
            attributeMapper.insert(attributeValueInfo);

        } else {
            // 更新
            if (Objects.isNull(paramMap.get("Displacement"))) {
                vehicleInfo.setDisplacement("0.0");
            }else
            {
                vehicleInfo.setDisplacement(paramMap.get("Displacement"));
            }
            vehicleInfo.setName(FullName);
            vehicleInfo.setModuleId(getMappingId(paramMap.get("LevelId")));
            vehicleInfo.setDriveId(getMappingId(paramMap.get("DriveId")));
            vehicleInfo.setGearboxId(getMappingId(paramMap.get("GearboxId")));
            vehicleInfo.setSeatId(getMappingId(paramMap.get("SeatId")));
            vehicleInfo.setStructureId(getMappingId(paramMap.get("BodyStructureId")));
            vehicleInfo.setFuelId(getMappingId(paramMap.get("FuelId")));
            vehicleInfo.setNationId(getMappingId(paramMap.get("CountryId")));
            vehicleInfo.setIntakeType(getMappingId(paramMap.get("IntakeTypeId")));
            vehicleInfo.setSales(0);
            vehicleInfoMapper.updateById(vehicleInfo);

            // 将进气形式写入 AttributeValue 表
            AttributeValueInfo attributeValueInfo = new AttributeValueInfo();
            attributeValueInfo.setId(attrNode.getId());
            attributeValueInfo.setStringValue(getMappingId(paramMap.get("IntakeTypeId")));
            attributeValueInfo.setVehicleId(vehicleInfo.getId());
            attributeValueInfo.setAttributeId(inTakeTypeId);
            attributeValueInfo.setDeleted(false);

            attributeMapper.updateById(attributeValueInfo);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        vehicleInfoMapper.deleteById(id);
    }


    private String getMappingId(String mysqlId) {
        String sqlServerId = DataMapServiceConfig.staticDataMap.get(mysqlId);
        if (Objects.nonNull(sqlServerId)) {
            if (sqlServerId == "NOCONTENT") {
                sqlServerId = null;
            }
        }
        return sqlServerId;
    }

    private String getMappingName(String mysqlId) {
        if (Objects.isNull(mysqlId)) {
            return "";
        }
        String mappingName = DataMapServiceConfig.staticDataMap.get("IDNAME" + mysqlId);
        if (Objects.isNull(mappingName)) {
            mappingName = "";
        }
        return mappingName;
    }
}
