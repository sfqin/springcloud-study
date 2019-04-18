package cn.zzzcr.springcloud.controller;

import cn.zzzcr.springcloud.model.OrderInfo;
import cn.zzzcr.springcloud.service.ProductClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class OrderController {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @GetMapping("/hello")
    public String hello(){
        return "order hello";
    }

    @GetMapping("/v1/order")
    public Object save(@RequestParam("user_id")String userId, @RequestParam("product_id") String productId){

        Object forObject = restTemplate.getForObject("http://product-module/v1/find?id=" + productId, Object.class);

        System.out.println("/v1/order => 从商品模块查询到查询到 => "+ forObject);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(UUID.randomUUID().toString());

        orderInfo.setProductInfo(forObject);
        orderInfo.setUserId(userId);
        return orderInfo;

    }


    @GetMapping("/v2/order")
    public Object save1(@RequestParam("user_id")String userId, @RequestParam("product_id") Integer productId){

        String byId = productClient.findById(productId);

        System.out.println("/v2/order => 从商品模块查询到查询到 => "+ byId);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(UUID.randomUUID().toString());

        orderInfo.setProductInfo(byId);
        orderInfo.setUserId(userId);
        return orderInfo;

    }

    @GetMapping("/v3/order")
    @HystrixCommand(fallbackMethod = "errorOrder")
    public Object save2(@RequestParam("user_id")String userId, @RequestParam("product_id") Integer productId){

        String byId = productClient.findById(productId);

        System.out.println("/v3/order => 从商品模块查询到查询到 => "+ byId);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(UUID.randomUUID().toString());

        orderInfo.setProductInfo(byId);
        orderInfo.setUserId(userId);
        return orderInfo;

    }

    /**
     * 调用超时或者失败时候则调用此方法
     * 通过 @HystrixCommand 注解配置兜底方法
     * 方法签名必须和 api 方法保持一致
     * @param userId
     * @param productId
     * @return
     */
    private Object errorOrder(String userId,Integer productId){

        System.out.println("/v3/order => errorOrder => 接口调用异常 => "+userId+ " " + productId);
        Map<String, Object> msg = new HashMap<>();
        msg.put("code", -1);
        msg.put("msg", "抢购人数太多，您被挤出来了，稍等重试");
        return msg;
    }

}
