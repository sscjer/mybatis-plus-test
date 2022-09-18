package com.cj.modular.config;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author Caoj
 * @version 2022-08-29 14:38
 */
@Configuration
public class MyBatisConfig {

    @Bean
    public Interceptor[] interceptors() {
        return new Interceptor[]{new MyBatisInterceptor()};
    }
}
