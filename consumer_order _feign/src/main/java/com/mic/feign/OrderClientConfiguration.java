package com.mic.feign;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

/**
 * @author liushuang
 * @create 2019-09-03 11:08
 */
@Configuration
public class OrderClientConfiguration {
    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }
}
