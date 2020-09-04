package com.lunz.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunz.data.model.VehicleBrand;

import java.util.Map;

public interface VehicleBrandService extends IService<VehicleBrand> {

    void addOrUpdate(Map<String,String> paramMap);

    void delete(String id);
}
