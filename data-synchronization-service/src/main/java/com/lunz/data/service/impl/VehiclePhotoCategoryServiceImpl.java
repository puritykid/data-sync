package com.lunz.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunz.data.mapper.VehiclePhotoCategoryMapper;
import com.lunz.data.model.VehicleMaintcProjectInfo;
import com.lunz.data.model.VehiclePhotoCategory;
import com.lunz.data.service.VehiclePhotoCategoryService;
import com.lunz.data.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
public class VehiclePhotoCategoryServiceImpl extends ServiceImpl<VehiclePhotoCategoryMapper, VehiclePhotoCategory> implements VehiclePhotoCategoryService {


    @Autowired
    private VehiclePhotoCategoryMapper vehiclePhotoCategoryMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdate(Map<String, String> paramMap) {
        VehiclePhotoCategory vehiclePhotoCategory = CommonUtil.mapToObj(paramMap, VehiclePhotoCategory.class);
        if (Objects.nonNull(vehiclePhotoCategory)) {
            LambdaQueryWrapper<VehiclePhotoCategory> wrapper = Wrappers.<VehiclePhotoCategory>lambdaQuery()
                    .select(VehiclePhotoCategory::getSortOrder)
                    .eq(VehiclePhotoCategory::getId, vehiclePhotoCategory.getId());
            VehiclePhotoCategory node = vehiclePhotoCategoryMapper.selectOne(wrapper);
            if (Objects.isNull(node)) {
                // 新增
                Integer maxSortOrder = this.getMaxSortOrder();
                maxSortOrder++;
                vehiclePhotoCategory.setSortOrder(maxSortOrder);
                vehiclePhotoCategoryMapper.insert(vehiclePhotoCategory);
            } else {
                // 更新
                vehiclePhotoCategory.setSortOrder(node.getSortOrder());
                vehiclePhotoCategoryMapper.updateById(vehiclePhotoCategory);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        vehiclePhotoCategoryMapper.deleteById(id);
    }


    private  Integer getMaxSortOrder() {
        Wrapper<VehiclePhotoCategory> wrapper = Wrappers.<VehiclePhotoCategory>query()
                .select("MAX(SortOrder) SortOrder");
        VehiclePhotoCategory node = vehiclePhotoCategoryMapper.selectOne(wrapper);
        Integer maxSortOrder = 0;
        if (Objects.nonNull(node)) {
            maxSortOrder = node.getSortOrder();
            if (maxSortOrder == null) {
                maxSortOrder = 0;
            }
        }
        return maxSortOrder;
    }
}
