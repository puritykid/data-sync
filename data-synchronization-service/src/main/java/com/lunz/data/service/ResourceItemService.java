package com.lunz.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunz.data.model.ResourceItem;

import java.util.Map;

public interface ResourceItemService extends IService<ResourceItem> {

    void addOrUpdate(Map<String, String> paramMap);
}
