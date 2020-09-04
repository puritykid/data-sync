package com.lunz.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunz.data.mapper.VehicleInfoMapper;
import com.lunz.data.mapper.VehicleMaintenanceProjectMapper;
import com.lunz.data.model.VehicleInfo;
import com.lunz.data.model.VehicleMaintenanceProject;
import com.lunz.data.service.VehicleMaintenanceProjectService;
import com.lunz.data.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
public class VehicleMaintenanceProjectServiceImpl extends ServiceImpl<VehicleMaintenanceProjectMapper, VehicleMaintenanceProject> implements VehicleMaintenanceProjectService {


    @Autowired
    private VehicleInfoMapper vehicleInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdate(Map<String, String> paramMap) {

        VehicleMaintenanceProject vehicleMaintenanceProject = CommonUtil.mapToObj(paramMap, VehicleMaintenanceProject.class);

        LambdaUpdateWrapper<VehicleInfo> wrapper = Wrappers.<VehicleInfo>lambdaUpdate()
                .set(VehicleInfo::getFirstMaintenMiles, vehicleMaintenanceProject.getFirstMaintenMiles())
                .set(VehicleInfo::getSecondMaintenMiles, vehicleMaintenanceProject.getSecondMaintenMiles())
                .set(VehicleInfo::getMaintenCycleMiles, vehicleMaintenanceProject.getMaintenCycleMiles())
                .set(VehicleInfo::getFirstMaintenTime, vehicleMaintenanceProject.getFirstMaintenTime())
                .set(VehicleInfo::getSecondMaintenTime, vehicleMaintenanceProject.getSecondMaintenTime())
                .set(VehicleInfo::getMaintenCycleTime, vehicleMaintenanceProject.getMaintenCycleTime())
                .eq(VehicleInfo::getId, vehicleMaintenanceProject.getCarinfoId());

        vehicleInfoMapper.update(null, wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        vehicleInfoMapper.deleteById(id);
    }
}
