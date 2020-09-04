package com.lunz.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Liuruixia
 * @Description:
 * @date 2019/10/09
 */
@Data
@NoArgsConstructor
public class ColumnDataDTO {
    /**
     * before info
     */
    @JSONField(serialize = false)
    private Map<String, FieldData> before;
    /**
     * after info
     */
    private Map<String, FieldData> after;

    @Data
    @NoArgsConstructor
    public static class FieldData {
        /**
         * 字段名称field name
         */
        @JSONField(serialize = false)
        private String name;
        /**
         * fieldValue字段值
         */
        private String value;
    }
}
