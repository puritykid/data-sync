package com.lunz.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunz.data.mapper.VehicleMaintcProjectInfoMapper;
import com.lunz.data.model.VehicleInfo;
import com.lunz.data.model.VehicleMaintcProjectInfo;
import com.lunz.data.service.VehicleMaintcProjectInfoService;
import com.lunz.data.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
public class VehicleMaintcProjectInfoServiceImpl extends ServiceImpl<VehicleMaintcProjectInfoMapper, VehicleMaintcProjectInfo> implements VehicleMaintcProjectInfoService {


    @Autowired
    private VehicleMaintcProjectInfoMapper vehicleMaintcProjectInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdate(Map<String, String> paramMap) {

        VehicleMaintcProjectInfo vehicleMaintcProjectInfo = CommonUtil.mapToObj(paramMap, VehicleMaintcProjectInfo.class);
        if (Objects.nonNull(vehicleMaintcProjectInfo)) {
            VehicleMaintcProjectInfo node = null;
            if(Objects.isNull(vehicleMaintcProjectInfo.getProjectNo()))
            {
                LambdaQueryWrapper<VehicleMaintcProjectInfo> wrapper = Wrappers.<VehicleMaintcProjectInfo>lambdaQuery()
                    .select(VehicleMaintcProjectInfo::getId)
                    .eq(VehicleMaintcProjectInfo::getProjectName, vehicleMaintcProjectInfo.getProjectName())
                    .eq(VehicleMaintcProjectInfo::getDeleted,false);
                node = vehicleMaintcProjectInfoMapper.selectOne(wrapper);
            }else
            {
                LambdaQueryWrapper<VehicleMaintcProjectInfo> wrapper = Wrappers.<VehicleMaintcProjectInfo>lambdaQuery()
                        .select(VehicleMaintcProjectInfo::getId)
                        .eq(VehicleMaintcProjectInfo::getProjectNo, vehicleMaintcProjectInfo.getProjectNo())
                        .eq(VehicleMaintcProjectInfo::getDeleted,false);
                node = vehicleMaintcProjectInfoMapper.selectOne(wrapper);
            }

            if (Objects.isNull(node)) {
                // 新增
                vehicleMaintcProjectInfo.setId(CommonUtil.getUUID());
                vehicleMaintcProjectInfo.setProjectNo(CommonUtil.getUUID());
                vehicleMaintcProjectInfoMapper.insert(vehicleMaintcProjectInfo);
            } else {
                // 更新
                vehicleMaintcProjectInfo.setId(node.getId());
                vehicleMaintcProjectInfoMapper.updateById(vehicleMaintcProjectInfo);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        vehicleMaintcProjectInfoMapper.deleteById(id);
    }

}
