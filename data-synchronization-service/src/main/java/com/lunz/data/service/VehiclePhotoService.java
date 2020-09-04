package com.lunz.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunz.data.model.VehiclePhoto;

import java.util.Map;

public interface VehiclePhotoService extends IService<VehiclePhoto> {

    void addOrUpdate(Map<String,String> paramMap);

    void delete(String id);
}
