package com.mic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableZuulProxy  //自带熔断   自动注册到eureka
public class SpringZuulApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(SpringZuulApplication.class);
    }
}
