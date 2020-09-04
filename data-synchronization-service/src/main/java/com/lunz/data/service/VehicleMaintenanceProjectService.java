package com.lunz.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunz.data.model.VehicleMaintenanceProject;

import java.util.Map;

public interface VehicleMaintenanceProjectService extends IService<VehicleMaintenanceProject> {

    void addOrUpdate(Map<String,String> paramMap);

    void delete(String id);
}
