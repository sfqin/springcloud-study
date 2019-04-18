package cn.zzzcr.springcloud.controller;

import cn.zzzcr.springcloud.model.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
public class OrderController {


    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello(){
        return "order hello";
    }

    @GetMapping("/order")
    public Object save(@RequestParam("user_id")String userId, @RequestParam("product_id") String productId){

        Object forObject = restTemplate.getForObject("http://product-module/find?id=" + productId, Object.class);

        System.out.println("从商品模块查询到查询到 => "+ forObject);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(UUID.randomUUID().toString());

        orderInfo.setProductInfo(forObject);
        orderInfo.setUserId(userId);
        return orderInfo;

    }

}
