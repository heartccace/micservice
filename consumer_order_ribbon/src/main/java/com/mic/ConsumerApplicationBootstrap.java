package com.mic;

import com.mic.annotation.ExcludeConponentScan;
import com.mic.configuration.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
// @RibbonClient(name = "PROVIDER-USER", configuration = {}) configuration用于配置算法等
// @RibbonClient(name = "PROVIDER-USER", configuration = RibbonConfiguration.class)  // 启用ribbon，对PROVIDER-USER做负载均衡，并指定算法
// @RibbonClients(value = {
//		@RibbonClient(name = "application-Client-a", configuration = TestConfiguration.class),
//		@RibbonClient(name = "application-Client-b", configuration = TestConfiguration.class)
//})  多个应用可采用不同算法配置

@RibbonClient(name = "PROVIDER-USER") //采用配置文件方式实现自定义算法
@ComponentScan(excludeFilters = {@ComponentScan.Filter( type = FilterType.ANNOTATION, value = ExcludeConponentScan.class ) })
public class ConsumerApplicationBootstrap
{
    public static void main( String[] args )
    {
        SpringApplication.run(ConsumerApplicationBootstrap.class);
    }
}
