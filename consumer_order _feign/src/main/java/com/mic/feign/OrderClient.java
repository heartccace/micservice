package com.mic.feign;

import com.mic.entity.User;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author liushuang
 * @create 2019-09-02 11:20
 */
@FeignClient(name = "PROVIDER-USER", configuration = OrderClientConfiguration.class)  // configuration请参照FeignClientsConfiguration
public interface OrderClient {
    @GetMapping("/user/{id}")
    User getUser(@PathVariable("id")Long id);

    @PostMapping(value = "/postuser", consumes = "application/json") // 提供者那边必须使用post方式才能使用
    User getUser1(User user); //feign默认会将复杂参数的请求方式设置为post

    @RequestLine("GET /feign/{id}")
    User getUser2(@Param("id") Long id);
}
