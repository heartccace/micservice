package com.mic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
public class HystrixDashboardApplicationBootstrap
{
    public static void main( String[] args )
    {
        SpringApplication.run(HystrixDashboardApplicationBootstrap.class);
    }
}
