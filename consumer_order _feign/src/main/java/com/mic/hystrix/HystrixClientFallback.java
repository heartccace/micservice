package com.mic.hystrix;

import com.mic.entity.User;
import com.mic.feign.OrderClient;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 在{@link OrderClient}方法调用失败时，默认回调当前对应的方法 需要在OrderClient中配置@FeignClient 配置fallback对应的类HystrixClientFallback
 * @author liushuang
 * @create 2019-09-05 16:44
 */

public class HystrixClientFallback implements OrderClient {
    @Override
    public User getUser(Long id) {
        return null;
    }

    @Override
    public User getUser1(User user) {
        return null;
    }

    @Override
    public User getUser2(Long id) {
        return null;
    }
}
