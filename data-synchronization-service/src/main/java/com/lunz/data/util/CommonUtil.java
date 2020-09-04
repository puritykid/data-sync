package com.lunz.data.util;

import com.alibaba.fastjson.JSON;
import com.lunz.data.constance.Constance;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

//import org.apache.commons.codec.digest.DigestUtils;


/**
 * @author chenxiaojun
 * @Title:CommonUtil
 * @Package com.lunz.uc.users.util
 * @Description: 公共工具类
 * @date 2019/04/24
 */
public class CommonUtil {
    private static  final String DATE_FORMAT_19 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 判断对象是否为null和空
     *
     * @param object
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isObjectNull(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof Collection) {
            return ((Collection) object).isEmpty() ? true : false;
        } else if (object instanceof Map) {
            return ((Map) object).isEmpty() ? true : false;
        } else if (object instanceof String) {
            return ((String) object).trim().length() == 0 ? true : false;
        }
        return false;
    }

    /**
     * 生成UUID
     *
     * @Description: TODO
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * @Description: 获取当前日期
     */
    public static String getCurrentDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_19));
    }

    /**
     * @Description: 获取当前日期
     */
    public static String getCurrentDateLong() {
        return Instant.now().toEpochMilli() + "";
    }

    public static <T> T setDefaultVal(T t1, T t2) {
        return t1 == null ? t2 : t1;
    }

    public static String formatDate(Object longTime) {

        if (Objects.isNull(longTime)) {
            return Constance.SYNC_CREATED_AT;
        } else {
            String time = longTime.toString();
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(time)), ZoneId.of("UTC"))
                    .format(DateTimeFormatter.ofPattern(DATE_FORMAT_19));
        }
    }

    public static String formatDateToSqlserver(Object longTime) {
        if (Objects.isNull(longTime)) {
            return getCurrentDate();
        } else {
            String time = longTime.toString();
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(time)), ZoneId.of("Asia/Shanghai"))
                    .format(DateTimeFormatter.ofPattern(DATE_FORMAT_19));
        }
    }

    /**
     * map转实体
     *
     * @param map
     * @param clazz
     * @return
     */
    public static <T> T mapToObj(Map<?, ?> map, Class<T> clazz) {
        String json = JSON.toJSONString(map);
        return JSON.parseObject(json, clazz);
    }
}
