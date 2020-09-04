package com.lunz.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunz.data.model.AttributeValueInfo;

import java.util.Map;

public interface AttributeValueService extends IService<AttributeValueInfo> {

    void addOrUpdate(Map<String, String> paramMap);
}
