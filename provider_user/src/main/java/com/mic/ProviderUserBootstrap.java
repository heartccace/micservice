package com.mic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class ProviderUserBootstrap
{
//    @Bean   //增加版本号可以使用该方法
//    public PatternServiceRouteMapper serviceRouteMapper() {
//        return new PatternServiceRouteMapper(
//                "(?<name>^.+)-(?<version>v.+$)",
//                "${version}/${name}");
//    }
    public static void main( String[] args )
    {
        SpringApplication.run(ProviderUserBootstrap.class);
    }
}
