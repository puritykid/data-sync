package com.lunz.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunz.data.config.DataMapServiceConfig;
import com.lunz.data.mapper.ResourceItemMapper;
import com.lunz.data.mapper.VehiclePhotoMapper;
import com.lunz.data.model.ResourceItem;
import com.lunz.data.model.VehiclePhoto;
import com.lunz.data.service.VehiclePhotoService;
import com.lunz.data.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
public class VehiclePhotoServiceImpl extends ServiceImpl<VehiclePhotoMapper, VehiclePhoto> implements VehiclePhotoService {

    @Autowired
    private VehiclePhotoMapper vehiclePhotoMapper;


    @Autowired
    private ResourceItemMapper resourceItemMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdate(Map<String, String> paramMap) {

        VehiclePhoto vehiclePhoto = CommonUtil.mapToObj(paramMap, VehiclePhoto.class);
        String ImageUrl = paramMap.get("ImageUrl");
        if(ImageUrl.startsWith("http"))
        {
            ImageUrl =ImageUrl.substring(5);
        }else if(ImageUrl.startsWith("https"))
        {
            ImageUrl =ImageUrl.substring(6);
        }
        String photoCategoryId = DataMapServiceConfig.staticDataMap.get(vehiclePhoto.getCategoryId());
        String resourceId = CommonUtil.getUUID();
        String imageId = null;
        ResourceItem resouceItemEntity = new ResourceItem();

        if (Objects.nonNull(vehiclePhoto)) {

            QueryWrapper<ResourceItem> resourceWrapper = Wrappers.<ResourceItem>query()
                    .select("top 1 Id")
                    .eq("Url", ImageUrl);
            ResourceItem resouceNode = resourceItemMapper.selectOne(resourceWrapper);

            if(Objects.nonNull(resouceNode))
            {
                // 如果imageUrl存在  则取出ID 给ImageId 赋值
                imageId = resouceNode.getId();
            }else
            {
                // 如果不存在，往resource表中插入一条数据 Id为 resourceId
                resouceItemEntity.setId(resourceId);
                resouceItemEntity.setCreatedAt(CommonUtil.getCurrentDateLong());
                resouceItemEntity.setCreatedById(vehiclePhoto.getCreatedById());
                resouceItemEntity.setUrl(ImageUrl);
                resouceItemEntity.setFileName("");
                resouceItemEntity.setFileType("");
                resouceItemEntity.setFileLength("0");
                resouceItemEntity.setDeleted(false);
                resourceItemMapper.insert(resouceItemEntity);
                imageId= resourceId;
            }
            LambdaQueryWrapper<VehiclePhoto> wrapper = Wrappers.<VehiclePhoto>lambdaQuery()
                    .select(VehiclePhoto::getId)
                    .eq(VehiclePhoto::getVehicleId, vehiclePhoto.getVehicleId())
                    .eq(VehiclePhoto::getCategoryId, photoCategoryId)
                    .eq(VehiclePhoto::getImageId, imageId);
            // 根据分类Id  车型Id 以及imageid确认kafka推送的数据是否存在
            VehiclePhoto node = vehiclePhotoMapper.selectOne(wrapper);

            if (Objects.isNull(node)) {
                // 新增
                vehiclePhoto.setCategoryId(photoCategoryId);
                vehiclePhoto.setId(CommonUtil.getUUID());
                vehiclePhoto.setImageId(imageId);
                vehiclePhotoMapper.insert(vehiclePhoto);
            } else {
                // 更新
                vehiclePhoto.setCategoryId(photoCategoryId);
                vehiclePhoto.setImageId(imageId);
                vehiclePhoto.setUpdatedAt(CommonUtil.getCurrentDateLong());
                vehiclePhoto.setUpdatedById(vehiclePhoto.getCreatedById());
                vehiclePhoto.setId(node.getId());
                vehiclePhotoMapper.updateById(vehiclePhoto);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        vehiclePhotoMapper.deleteById(id);
    }
}
