package com.lunz.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunz.data.mapper.ResourceItemMapper;
import com.lunz.data.mapper.VehicleBrandMapper;
import com.lunz.data.model.ResourceItem;
import com.lunz.data.model.VehicleBrand;
import com.lunz.data.service.VehicleBrandService;
import com.lunz.data.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class VehicleBrandServiceImpl extends ServiceImpl<VehicleBrandMapper, VehicleBrand> implements VehicleBrandService {

    @Autowired
    private VehicleBrandMapper vehicleBrandMapper;

    @Autowired
    private ResourceItemMapper resourceItemMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdate(Map<String, String> paramMap) {

        VehicleBrand vehicleBrand = CommonUtil.mapToObj(paramMap, VehicleBrand.class);
        if (Objects.nonNull(vehicleBrand)) {
            LambdaQueryWrapper<VehicleBrand> wrapper = Wrappers.<VehicleBrand>lambdaQuery()
                    .select(VehicleBrand::getSortOrder)
                    .eq(VehicleBrand::getId, vehicleBrand.getId());
            VehicleBrand node = vehicleBrandMapper.selectOne(wrapper);

            ResourceItem resouceItemEntity = new ResourceItem();
            String uuid = CommonUtil.getUUID();

            if(!CommonUtil.isObjectNull(paramMap.get("LogoUrl")))
            {
                QueryWrapper<ResourceItem> resourceWrapper = Wrappers.<ResourceItem>query()
                        .select("top 1 Id")
                        .eq("Url", paramMap.get("LogoUrl"));
                ResourceItem resouceNode = resourceItemMapper.selectOne(resourceWrapper);
                if(Objects.nonNull(resouceNode))
                {
                    uuid = resouceNode.getId();
                    vehicleBrand.setLogoId(uuid);
                }else
                {
                    vehicleBrand.setLogoId(uuid);
                    resouceItemEntity.setId(uuid);
                    resouceItemEntity.setCreatedAt(CommonUtil.getCurrentDateLong());
                    resouceItemEntity.setCreatedById(vehicleBrand.getCreatedById());
                    resouceItemEntity.setUrl(paramMap.get("LogoUrl"));
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
                vehicleBrand.setSortOrder(maxSortOrder);
                vehicleBrandMapper.insert(vehicleBrand);
            } else {
                // 更新
                vehicleBrand.setSortOrder(node.getSortOrder());
                vehicleBrandMapper.updateById(vehicleBrand);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        vehicleBrandMapper.deleteById(id);
    }


    private Integer getMaxSortOrder() {
        Wrapper<VehicleBrand> wrapper = Wrappers.<VehicleBrand>query()
                .select("MAX(SortOrder) SortOrder");
        VehicleBrand node = vehicleBrandMapper.selectOne(wrapper);
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
