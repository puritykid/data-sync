package com.lunz.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunz.data.model.VehicleBaoyangOthers;

import java.util.Map;

public interface VehicleBaoyangOtherService extends IService<VehicleBaoyangOthers> {

    void addOrUpdate(Map<String, String> paramMap);
}
