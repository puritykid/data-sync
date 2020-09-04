package com.lunz.data.listener;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.lunz.data.config.TableMapServiceConfig;
import com.lunz.data.constance.Constance;
import com.lunz.data.util.SpringContextUtil;
import com.lunz.data.util.Tools;
import com.lunz.dto.ColumnDataDTO;
import com.lunz.dto.RowDataDTO;
import com.lunz.dto.base.MessageDTO;
import com.lunz.util.MessageDataUtil;
import com.lunz.util.Operate;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @author fanxiaoyu
 * @Description:
 * @date 2019/10/09
 */
@Component
public class ConsumerListener {

    private final static Logger log = LoggerFactory.getLogger(ConsumerListener.class);

    @Value("${kafka.topics.dc.mysql.name}")
    private String dbMysql;

    /**
     * @Description: 初步思路：
     * 1. 消息转换
     * 2. 表过滤：按照表名过滤掉不需要同步的表数据
     * 3. 操作过滤：如果是read直接返回，新增、修改、删除进行下步操作
     * 4. 业务处理：调用对应的service
     */
    @KafkaListener(topics = "${kafka.topics.dc.mysql.name}")
    public void newUcListen(ConsumerRecord<?, ?> record) {
        doConvert(record);
    }

    /**
     * @Descripion: 消息转换
     */
    private void doConvert(ConsumerRecord<?, ?> record) {
        try {
            // null消息过滤
            if (Objects.nonNull(record) && Objects.nonNull(record.value())) {
                MessageDTO messageDTO = JSON.parseObject(record.value().toString(), MessageDTO.class);
                RowDataDTO rowDataDTO = MessageDataUtil.getRowData(messageDTO.getPayload());
                if (Objects.nonNull(rowDataDTO)) {
                    filterTable(rowDataDTO);
                }
            }
        } catch (Exception e) {
            log.info("消费出错：{}", e.getMessage());
        }
    }

    /**
     * @Description: 表过滤
     */
    private void filterTable(RowDataDTO rowDataDTO) {
        String tableName = "";
         if (rowDataDTO.getName().equals(dbMysql)) {
            tableName = rowDataDTO.getTable().toLowerCase();
        } else {
            return;
        }
        log.info("[tableName]:{}", tableName);
        String classNames = TableMapServiceConfig.staticMap.get(tableName);
        if (StringUtils.isEmpty(classNames)) {
            return;
        }
        filterOperate(rowDataDTO);
    }

    /**
     * @Description: 操作过滤
     */
    private void filterOperate(RowDataDTO rowDataDTO) {
        if (Operate.OperationType.CREATE.equals(rowDataDTO.getOperationType())) {
            doService(rowDataDTO);
        } else if (Operate.OperationType.UPDATE.equals(rowDataDTO.getOperationType())) {
            doService(rowDataDTO);
        } else if (Operate.OperationType.READ.equals(rowDataDTO.getOperationType())) {
            doService(rowDataDTO);
        } else if (Operate.OperationType.DELETE.equals(rowDataDTO.getOperationType())) {
            doService(rowDataDTO);
        } else {
            return;
        }
    }


    /**
     * @Description: 服务调用
     */
    private void doService(RowDataDTO rowDataDTO){
        String tableName = rowDataDTO.getTable().toLowerCase();
        String serviceName = TableMapServiceConfig.staticMap.get(tableName);
        Map<String, String> messageMap = doWithMessage(rowDataDTO);
        if (serviceName == null ||messageMap == null) {
            return;
        }
        log.info("调用方法前：[{}]表，消费消息：{},", tableName, messageMap);
        if (messageMap.size() == 1) {
            doInvoke(serviceName, messageMap.get(Constance.DELETE_MARK));
        } else {
            doInvoke(serviceName, messageMap);
        }
        log.info("调用方法结束：[{}]表", tableName);
    }

    /**
     * @Description: 消息处理
     */
    private Map<String, String> doWithMessage(RowDataDTO rowDataDTO) {
        // 字段重新封装map
        Map<String, String> paramMap = Maps.newHashMap();
        ColumnDataDTO columnData = rowDataDTO.getColumnData();
        if (Objects.nonNull(columnData)) {
            rowDataDTO.getColumnData().getAfter().forEach((key, val) -> {
//                key = Tools.lineToBigHump(key);
                paramMap.put(key, val.getValue().equals("null") ? null : val.getValue());
            });
        } else {
            paramMap.put(Constance.DELETE_MARK, rowDataDTO.getId());
        }
        return paramMap;
    }

    private void doInvoke(String serviceName,Object parameter) {
        try {
            Object serviceImpl = SpringContextUtil.getBean(Joiner.on("").join(serviceName, "Impl"));
            Method method = null;
            // 通过反射指定了对应的方法名，对应实现类必须有对应的方法
            if (parameter instanceof String) {
                method = serviceImpl.getClass().getDeclaredMethod(Constance.DELETE, String.class);
            } else if(parameter instanceof Map){
                method = serviceImpl.getClass().getDeclaredMethod(Constance.ADD_OR_UPDATE, Map.class);
            }

            method.invoke(serviceImpl, parameter);
        } catch (Exception e) {
            log.error("反射调用方法异常：{}",e);
        }
    }
}
