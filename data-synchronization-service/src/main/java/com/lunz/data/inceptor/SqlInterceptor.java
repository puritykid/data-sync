package com.lunz.data.inceptor;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @Description: 全局日期处理拦截器
 * @Author: fanxiaoyu
 * @CreateDate: 2020/3/24 14:16
 * @Version: 1.0
 */
@Slf4j
@Intercepts(value = {
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class SqlInterceptor extends AbstractSqlParserHandler implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        List<SqlCommandType> sqlType = ImmutableList.of(SqlCommandType.INSERT, SqlCommandType.UPDATE, SqlCommandType.DELETE);
        if (sqlType.contains(sqlCommandType)) {
            Object parameter = null;
            if (sqlCommandType.equals(SqlCommandType.UPDATE)) {
                Map<String,Object> parameterMap = (Map<String, Object>) invocation.getArgs()[1];
                parameter = parameterMap.get("et");
            } else {
                parameter = invocation.getArgs()[1];
            }
            // 获取新增修改参数

            if (Objects.nonNull(parameter)) {
                Field[] fields = parameter.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    // 如果是静态描述符，则跳过
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }

                    if (field.isAnnotationPresent(JSONField.class)) {
                        JSONField annotation = field.getAnnotation(JSONField.class);
                        String format = annotation.format();
                        // 获取字段值
                        String value = (String) field.get(parameter);
                        if (!StringUtils.isEmpty(value)) {
                            String date = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(value)), ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern(format));
                            field.set(parameter, date);
                        }
                    }
                }
            }
            log.info("时间处理之后的实体：{}", parameter);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}