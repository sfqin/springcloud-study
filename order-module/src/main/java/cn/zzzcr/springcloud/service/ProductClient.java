package cn.zzzcr.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-module")
public interface ProductClient {

    @GetMapping("/v1/find")
    String findById(@RequestParam("id")int id);
}
