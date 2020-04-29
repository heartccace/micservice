package com.mic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableTurbine
public class HystrixTurbineApplicationBootstrap
{
    public static void main( String[] args )
    {
        SpringApplication.run(HystrixTurbineApplicationBootstrap.class);
    }
}
