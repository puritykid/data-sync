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
public class MessageDTO {
    private Map<String, Object> schema;
    private PayloadDTO payload;
}
