package com.mic.configuration;

import com.mic.annotation.ExcludeConponentScan;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liushuang
 * @create 2019-08-31 16:42
 */
//
//        策略类	                    命 名	        描 述
//        RandomRule	            随即策略	        随机选择server
//        RoundRobinRule	        轮询策略	        按顺序循环选择server
//        RetryRule	                重试策略	        在一个配置内当选择server不成功，则一直重试选择到一个可用server
//        BestAvailableRule	        最低并发策略	    server检查，如果server断路器打开，则忽略，并再去选择一个并发连接最低的server
//        AvailabilityFilteringRule	可用过滤策略	    过滤掉一直连接失败并标记为circuit tripped的server，过滤掉那些高并发连接的server（active connections超过配置的阀值）
//        ResponseTimeWeightedRule	响应时间加权策略	根据server响应时间分配权重。响应时间越长，权重越低，被选择的概率就越低；响应时间越短，权重越高，被选择到的概率就越高。影响响应时常的因素有：网络、磁盘、IO等
//        ZoneAvoidanceRule	        区域权衡策略	    综合判断server所在区域的性能和server的可用性，轮询选择server，并且判断一个AWS Zone的运行性能是否可用，剔除不可用的Zone中的所有server
@Configuration
@ExcludeConponentScan
public class RibbonConfiguration {
    /**
     * 自定义策略
     * @return
     */

    @Bean
    public IRule ribbonRule(/*IClientConfig config*/) {
        return new RandomRule();
    }
}
