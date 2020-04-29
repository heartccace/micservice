package com.mic.controller;

import com.mic.entity.User;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author liushuang
 * @create 2019-08-30 13:34
 */
@RestController
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    // private String url = "http://localhost:7900/user/";
    @Value("${user.url}")
    private String url;

    @GetMapping("/order/{id}")
    public User getUser(@PathVariable Long id) {
        User user = restTemplate.getForObject(url + id, User.class);
        return user;
    }

    @GetMapping("/serverurl/{id}")
    public User getInfo(@PathVariable Long id) {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("provider-user", false);
        User user = restTemplate.getForObject(instance.getHomePageUrl() + "user/" + id, User.class);
        return user;
    }

    @GetMapping("/ribbon/{id}")
    public User getRibbon(@PathVariable Long id) {
        // InstanceInfo instance = eurekaClient.getNextServerFromEureka("provider-user", false);
        User user = restTemplate.getForObject("http://PROVIDER-USER/user/" + id, User.class);
        return user;
    }

    @GetMapping("/test")
    public String getTest() {
        // ribbon默认的策略是轮询
        ServiceInstance instance = loadBalancerClient.choose("PROVIDER-USER");
        System.out.println(instance.getHost()+ ": " + instance.getPort());
        return instance.getHost()+ ": " + instance.getPort();
    }
}
