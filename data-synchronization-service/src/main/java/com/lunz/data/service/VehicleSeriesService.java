package com.lunz.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunz.data.model.VehicleSeries;

import java.util.Map;

public interface VehicleSeriesService extends IService<VehicleSeries> {

    void addOrUpdate(Map<String,String> paramMap);

    void delete(String id);
}
