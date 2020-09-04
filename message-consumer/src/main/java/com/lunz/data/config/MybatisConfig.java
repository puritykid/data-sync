package com.lunz.data.config;

import com.lunz.data.inceptor.SqlInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.lunz.data.mapper")
public class MybatisConfig {

    // 时间转换拦截器
    @Bean
    public SqlInterceptor sqlInterceptor() {
        return new SqlInterceptor();
    }

}
