package com.lunz.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunz.data.model.VehicleInfo;

import java.util.Map;

public interface VehicleInfoService extends IService<VehicleInfo> {

    void addOrUpdate(Map<String,String> paramMap);

    void delete(String id);
}
