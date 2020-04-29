package com.mic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "PROVIDER-USER")
public class ConsumerApplicationBootstrap
{
    public static void main( String[] args )
    {
        SpringApplication.run(ConsumerApplicationBootstrap.class);
    }
}
