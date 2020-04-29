package com.mic.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author liushuang
 * @create 2019-08-30 13:50
 */
@Configuration
public class ProviderConfiguration {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
