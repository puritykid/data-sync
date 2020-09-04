package com.lunz.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunz.data.mapper.ResourceItemMapper;
import com.lunz.data.mapper.VehicleBrandMapper;
import com.lunz.data.model.ResourceItem;
import com.lunz.data.model.VehicleBrand;
import com.lunz.data.service.ResourceItemService;
import com.lunz.data.service.VehicleBrandService;
import com.lunz.data.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class ResourceItemServiceImpl extends ServiceImpl<ResourceItemMapper, ResourceItem> implements ResourceItemService {

    @Autowired
    private ResourceItemMapper resourceItemMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdate(Map<String, String> paramMap) {

        ResourceItem resourceItem = CommonUtil.mapToObj(paramMap, ResourceItem.class);
        if (Objects.nonNull(resourceItem)) {
            resourceItemMapper.insert(resourceItem);
        }
    }
}
