package com.lunz.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunz.data.config.DataMapServiceConfig;
import com.lunz.data.mapper.ResourceItemMapper;
import com.lunz.data.mapper.VehicleBaoyangOtherMapper;
import com.lunz.data.mapper.VehicleBrandMapper;
import com.lunz.data.mapper.VehicleInfoMapper;
import com.lunz.data.model.ResourceItem;
import com.lunz.data.model.VehicleBaoyangOthers;
import com.lunz.data.model.VehicleBrand;
import com.lunz.data.model.VehicleInfo;
import com.lunz.data.service.VehicleBaoyangOtherService;
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
public class VehicleBaoyangOtherServiceImpl extends ServiceImpl<VehicleBaoyangOtherMapper, VehicleBaoyangOthers> implements VehicleBaoyangOtherService {

    @Autowired
    private VehicleBaoyangOtherMapper baoyangMapper;

    @Autowired
    private VehicleBrandMapper brandMapper;

    @Autowired
    private VehicleInfoMapper vehicleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdate(Map<String, String> paramMap) {

        VehicleBaoyangOthers baoyangOthers = CommonUtil.mapToObj(paramMap, VehicleBaoyangOthers.class);
        // 根据品牌Id 获取品牌名称
        QueryWrapper<VehicleBrand> brand = Wrappers.<VehicleBrand>query()
                .select("top 1 Name")
                .eq("Id", baoyangOthers.getBrandId());
        VehicleBrand brandNode = brandMapper.selectOne(brand);
        String brandName = brandNode.getName();
        // 根据车型Id 获取车型名称
        QueryWrapper<VehicleInfo> model = Wrappers.<VehicleInfo>query()
                .select("top 1 Name")
                .eq("Id", paramMap.get("SeriesOrModelId"));
        VehicleInfo vehicleNode = vehicleMapper.selectOne(model);
        String modelName = vehicleNode.getName();
        //根据Id 获取保养项目名称
        String projectName = getMappingId(paramMap.get("MaintenProjectId"));
        //根据车型Id和保养项目名称获取一条数据
        LambdaQueryWrapper<VehicleBaoyangOthers> wrapper = Wrappers.<VehicleBaoyangOthers>lambdaQuery()
                .select(VehicleBaoyangOthers::getId)
                .eq(VehicleBaoyangOthers::getSeriesID, paramMap.get("SeriesOrModelId"))
                .eq(VehicleBaoyangOthers::getMaintenProjectName,projectName);
        VehicleBaoyangOthers node = baoyangMapper.selectOne(wrapper);

        if (Objects.isNull(node)) {
            // 新增
            baoyangOthers.setId(CommonUtil.getUUID());
            baoyangOthers.setBrandName(brandName);
            baoyangOthers.setSeriesName(modelName);
            baoyangOthers.setMaintenProjectName(projectName);
            baoyangOthers.setSeriesID(paramMap.get("SeriesOrModelId"));
            baoyangMapper.insert(baoyangOthers);
        } else {
            // 更新
            baoyangOthers.setId(node.getId());
            baoyangOthers.setBrandName(brandName);
            baoyangOthers.setSeriesName(modelName);
            baoyangOthers.setMaintenProjectName(projectName);
            baoyangOthers.setSeriesID(paramMap.get("SeriesOrModelId"));
            baoyangMapper.updateById(baoyangOthers);
        }
    }


    private String getMappingId(String mysqlId) {
        String sqlServerId = DataMapServiceConfig.staticDataMap.get(mysqlId);
        if (Objects.nonNull(sqlServerId)) {
            if (sqlServerId == "NOCONTENT") {
                sqlServerId = null;
            }
        }
        return sqlServerId;
    }
}
