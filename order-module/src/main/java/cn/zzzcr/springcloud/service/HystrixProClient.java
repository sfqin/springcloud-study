package cn.zzzcr.springcloud.service;

import cn.zzzcr.springcloud.fallback.HystrixProFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-module",fallback = HystrixProFallback.class)
public interface HystrixProClient {

    /**
     * 拦截路径、拦截方法、方法参数必须一一对应
     * @param id
     * @return
     */
    @GetMapping("/v1/find")
    String findById(@RequestParam("id")int id);
}
