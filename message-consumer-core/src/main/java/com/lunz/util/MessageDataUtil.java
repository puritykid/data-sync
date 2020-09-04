package com.lunz.util;

import com.lunz.dto.ColumnDataDTO;
import com.lunz.dto.RowDataDTO;
import com.lunz.dto.base.PayloadDTO;
import com.lunz.dto.base.SourceDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Liuruixia
 * @Description:
 * @date 2019/10/09
 */
public class MessageDataUtil {
    /**
     * 获取主键ID,Id
     * @param before
     * @param after
     * @return
     */
    private static String getId(Map<String, Object> before, Map<String, Object> after) {
        if (before == null || before.isEmpty()) {
            if (after.containsKey(Constants.ID_LOWERCASE)) {
                return after.get(Constants.ID_LOWERCASE).toString();
            }
            return after.get(Constants.ID).toString();
        }

        if (before.containsKey(Constants.ID_LOWERCASE)) {
            return before.get(Constants.ID_LOWERCASE).toString();
        }
        return before.get(Constants.ID).toString();
    }

    /**
     * 获取最新after的columnData的集合
     * @param after
     * @return
     */
    private static ColumnDataDTO getColumnData(Map<String, Object> after) {
        if (after == null || after.isEmpty()) {
            return null;
        }

        ColumnDataDTO rowData = new ColumnDataDTO();
        Map<String, ColumnDataDTO.FieldData> columnEntryMap = new HashMap<String,ColumnDataDTO.FieldData>();
        for (String key : after.keySet()) {
            ColumnDataDTO.FieldData fieldBean = new ColumnDataDTO.FieldData();
            fieldBean.setName(key);
            fieldBean.setValue(after.get(key) + "");
            columnEntryMap.put(key, fieldBean);
        }
        rowData.setAfter(columnEntryMap);
        return rowData;
    }

    /**
     * 初始化rowData
     * @param payloadDTO
     * @return
     */
    public static RowDataDTO getRowData(PayloadDTO payloadDTO) {
        if (payloadDTO == null || payloadDTO.getSource() == null || payloadDTO.getOp() == null) {
            return null;
        }
        SourceDTO sourceVO = payloadDTO.getSource();
        RowDataDTO tableRowVO = new RowDataDTO();
        tableRowVO.setId(getId(payloadDTO.getBefore(), payloadDTO.getAfter()));
        tableRowVO.setDb(sourceVO.getDb());
        tableRowVO.setName(sourceVO.getName());
        tableRowVO.setTable(sourceVO.getTable());
        tableRowVO.setTsMs(payloadDTO.getTsMs());
        tableRowVO.setColumnData(getColumnData(payloadDTO.getAfter()));
        tableRowVO.setOperationType(Operate.OperationType.forCode(payloadDTO.getOp()));
        return tableRowVO;
    }

}
