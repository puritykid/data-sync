package com.lunz.dto.base;

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
public class PayloadDTO {

    /**
     * before
     */
    private Map<String, Object> before;
    /**
     * after
     */
    private Map<String, Object> after;

    /**
     * source
     */
    private SourceDTO source;
    /**
     * op
     */
    private  String op;
    /**
     * ts_ms
     */
    private Long tsMs;
}
