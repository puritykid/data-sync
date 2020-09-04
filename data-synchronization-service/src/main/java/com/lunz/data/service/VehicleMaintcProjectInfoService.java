package com.lunz.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunz.data.model.VehicleMaintcProjectInfo;

import java.util.Map;

public interface VehicleMaintcProjectInfoService extends IService<VehicleMaintcProjectInfo> {

    void addOrUpdate(Map<String,String> paramMap);

    void delete(String id);
}
