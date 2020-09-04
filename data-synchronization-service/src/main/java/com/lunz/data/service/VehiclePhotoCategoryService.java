package com.lunz.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunz.data.model.VehiclePhotoCategory;

import java.util.Map;

public interface VehiclePhotoCategoryService extends IService<VehiclePhotoCategory> {

    void addOrUpdate(Map<String,String> paramMap);

    void delete(String id);
}
