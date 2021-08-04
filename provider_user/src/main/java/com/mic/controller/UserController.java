package com.mic.controller;


import com.mic.entity.User;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author liushuang
 * @create 2019-08-30 13:15
 */
@RestController
public class UserController {

    @Autowired
    private EurekaClient eurekaClient;

   @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        User user = new User();
        user.setAge(6900);
        user.setId(id);
        user.setData(new Date());
        user.setName("Hello world");
        return user;
    }

    @GetMapping("/serviceurl")
    public String serviceUrl() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("provider-user", false);
        return instance.getHomePageUrl();
    }

    @PostMapping(value = "/postuser")
    public User update(@RequestBody User user) {
        System.out.println(user);
       return user;
    }

    @GetMapping("/feign/{id}")
    public User getFeignUser(@PathVariable Long id) {
        User user = new User();
        user.setAge(6900);
        user.setId(id);
        user.setData(new Date());
        user.setName("Hello world");
        return user;
    }
}
