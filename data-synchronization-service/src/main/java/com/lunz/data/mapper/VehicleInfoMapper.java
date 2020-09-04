package com.lunz.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lunz.data.model.VehicleInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleInfoMapper extends BaseMapper<VehicleInfo> {
    void updateVlues(VehicleInfo vehicleInfo);
}
