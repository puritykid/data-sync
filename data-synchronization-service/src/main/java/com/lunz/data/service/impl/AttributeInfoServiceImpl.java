package com.lunz.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunz.data.config.DataMapServiceConfig;
import com.lunz.data.mapper.AttributeMapper;
import com.lunz.data.model.AttributeValueInfo;
import com.lunz.data.service.AttributeValueService;
import com.lunz.data.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
public class AttributeInfoServiceImpl extends ServiceImpl<AttributeMapper, AttributeValueInfo> implements AttributeValueService {

    @Autowired
    private AttributeMapper  attributeMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdate(Map<String, String> paramMap) {
        AttributeValueInfo attributeInfo = CommonUtil.mapToObj(paramMap, AttributeValueInfo.class);
            LambdaQueryWrapper<AttributeValueInfo> wrapper = Wrappers.<AttributeValueInfo>lambdaQuery()
                    .select(AttributeValueInfo::getId)
                    .eq(AttributeValueInfo::getId, attributeInfo.getId());
        AttributeValueInfo node = attributeMapper.selectOne(wrapper);

            if (Objects.isNull(node)) {
                // 新增
//                vehicleInfo.setName(paramMap.get("FullName"));
//                vehicleInfo.setModuleId(getMappingId(paramMap.get("LevelId")));
//                vehicleInfo.setDriveId(getMappingId(paramMap.get("DriveId")));
//                vehicleInfo.setGearboxId(getMappingId(paramMap.get("GearboxId")));
//                vehicleInfo.setSeatId(getMappingId(paramMap.get("SeatId")));
//                vehicleInfo.setStructureId(getMappingId(paramMap.get("BodyStructureId")));
//                vehicleInfo.setFuelId(getMappingId(paramMap.get("FuelId")));
//                vehicleInfo.setNationId(getMappingId(paramMap.get("NationId")));
//                vehicleInfo.setIntakeType(getMappingId(paramMap.get("IntakeTypeId")));
//                vehicleInfoMapper.insert(vehicleInfo);
            } else {
                // 更新
//                vehicleInfo.setName(paramMap.get("FullName"));
//                vehicleInfo.setModuleId(getMappingId(paramMap.get("LevelId")));
//                vehicleInfo.setDriveId(getMappingId(paramMap.get("DriveId")));
//                vehicleInfo.setGearboxId(getMappingId(paramMap.get("GearboxId")));
//                vehicleInfo.setSeatId(getMappingId(paramMap.get("SeatId")));
//                vehicleInfo.setStructureId(getMappingId(paramMap.get("BodyStructureId")));
//                vehicleInfo.setFuelId(getMappingId(paramMap.get("FuelId")));
//                vehicleInfo.setNationId(getMappingId(paramMap.get("NationId")));
//                vehicleInfo.setIntakeType(getMappingId(paramMap.get("IntakeTypeId")));
//                vehicleInfoMapper.updateById(vehicleInfo);
            }
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
}
