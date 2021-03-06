package com.mic.controller;

import com.mic.entity.User;
import com.mic.feign.OrderClient;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private OrderClient orderClient;
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

//    @GetMapping("/feign/{id}")
//    public User getUserByFeign(@PathVariable Long id) {
//        return orderClient.getUser(id);
//    }
//
//    @GetMapping("/postuser")
//    public User getUserByFeign1(User user) {
//        System.out.println(user);
//        return orderClient.getUser1(user);
//    }

    @GetMapping("/feign1/{id}")
    public User getUserByFeign1(@PathVariable Long id) {

        return orderClient.getUser2(id);
    }
}
