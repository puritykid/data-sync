package com.lunz.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunz.data.mapper.ResourceItemMapper;
import com.lunz.data.mapper.VehicleSeriesMapper;
import com.lunz.data.model.ResourceItem;
import com.lunz.data.model.VehicleSeries;
import com.lunz.data.service.VehicleSeriesService;
import com.lunz.data.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class VehicleSeriesServiceImpl extends ServiceImpl<VehicleSeriesMapper, VehicleSeries> implements VehicleSeriesService {


    @Autowired
    private VehicleSeriesMapper vehicleSeriesMapper;
    @Autowired
    private ResourceItemMapper resourceItemMapper;
    @Override
    public void addOrUpdate(Map<String, String> paramMap) {

        VehicleSeries vehicleSeries = CommonUtil.mapToObj(paramMap, VehicleSeries.class);
        if (Objects.nonNull(vehicleSeries)) {
            LambdaQueryWrapper<VehicleSeries> wrapper = Wrappers.<VehicleSeries>lambdaQuery()
                    .select(VehicleSeries::getSortOrder)
                    .eq(VehicleSeries::getId, vehicleSeries.getId());
            VehicleSeries node = vehicleSeriesMapper.selectOne(wrapper);

            ResourceItem resouceItemEntity = new ResourceItem();
            String uuid = CommonUtil.getUUID();

            if(!CommonUtil.isObjectNull(paramMap.get("ImageUrl")))
            {
                QueryWrapper<ResourceItem> resourceWrapper = Wrappers.<ResourceItem>query()
                        .select("top 1 Id")
                        .eq("Url", paramMap.get("ImageUrl"));
                ResourceItem resouceNode = resourceItemMapper.selectOne(resourceWrapper);
                if(Objects.nonNull(resouceNode))
                {
                    uuid = resouceNode.getId();
                    vehicleSeries.setImageId(uuid);
                }else
                {
                    vehicleSeries.setImageId(uuid);
                    resouceItemEntity.setId(uuid);
                    resouceItemEntity.setCreatedAt(CommonUtil.getCurrentDateLong());
                    resouceItemEntity.setCreatedById(vehicleSeries.getCreatedById());
                    resouceItemEntity.setUrl(paramMap.get("ImageUrl"));
                    resouceItemEntity.setFileName("");
                    resouceItemEntity.setFileType("");
                    resouceItemEntity.setFileLength("0");
                    resouceItemEntity.setDeleted(false);
                    resourceItemMapper.insert(resouceItemEntity);
                }
            }

            if (Objects.isNull(node)) {
                // 新增
                Integer maxSortOrder = this.getMaxSortOrder();
                maxSortOrder++;
                vehicleSeries.setSortOrder(maxSortOrder);
                vehicleSeriesMapper.insert(vehicleSeries);
            } else {
                // 更新
                vehicleSeries.setSortOrder(node.getSortOrder());
                vehicleSeriesMapper.updateById(vehicleSeries);
            }
        }

    }

    @Override
    public void delete(String id) {
        vehicleSeriesMapper.deleteById(id);
    }

    private  Integer getMaxSortOrder() {
        Wrapper<VehicleSeries> wrapper = Wrappers.<VehicleSeries>query()
                .select("MAX(SortOrder) SortOrder");
        VehicleSeries node = vehicleSeriesMapper.selectOne(wrapper);
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
