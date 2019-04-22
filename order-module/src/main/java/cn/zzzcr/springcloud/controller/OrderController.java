package cn.zzzcr.springcloud.controller;

import cn.zzzcr.springcloud.model.OrderInfo;
import cn.zzzcr.springcloud.service.HystrixProClient;
import cn.zzzcr.springcloud.service.ProductClient;
import cn.zzzcr.springcloud.service.RedisMonitorHystrixProClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class OrderController {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private HystrixProClient hystrixProClient;

    @Autowired
    private RedisMonitorHystrixProClient redisMonitorHystrixProClient;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @GetMapping("/hello")
    public String hello(){
        return "order hello";
    }

    /**
     * 模拟普通的远程调用是否成功
     * @param userId
     * @param productId
     * @return
     */
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

    /**
     * 使用 ribbon 进行负载均衡调用
     * @param userId
     * @param productId
     * @return
     */
    @GetMapping("/v2/order")
    public Object save1(@RequestParam("user_id")String userId, @RequestParam("product_id") Integer productId){

        Object forObject = restTemplate.getForObject("http://product-module/v1/find?id=" + productId, Object.class);

        System.out.println("/v2/order => 从商品模块查询到查询到 => "+ forObject);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(UUID.randomUUID().toString());

        orderInfo.setProductInfo(forObject);
        orderInfo.setUserId(userId);
        return orderInfo;

    }

    /**
     * 使用 Feign 进行负载均衡调用并且 指定调用出错后的执行方法
     * @param userId
     * @param productId
     * @return
     */
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

    /**
     * Hystrix 结合 Feign 提供申明式的调用错误后的执行方法
     * @param userId
     * @param productId
     * @return
     */
    @GetMapping("/v4/order")
    public Object save3(@RequestParam("user_id")String userId, @RequestParam("product_id") Integer productId){

        String productInfo = hystrixProClient.findById(productId);

        System.out.println("/v4/order => 从商品模块查询到查询到 => "+ productInfo);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(UUID.randomUUID().toString());

        orderInfo.setProductInfo(productInfo);
        orderInfo.setUserId(userId);
        return orderInfo;

    }

    /**
     * 调用失败后进行报警通知 并且执行兜底操作
     * @param userId
     * @param productId
     * @return
     */
    @GetMapping("/v5/order")
    public Object save4(@RequestParam("user_id")String userId, @RequestParam("product_id") Integer productId){


        String productInfo = redisMonitorHystrixProClient.findById(productId);

        System.out.println("/v5/order => 从商品模块查询到查询到 => "+ productInfo);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(UUID.randomUUID().toString());

        orderInfo.setProductInfo(productInfo);
        orderInfo.setUserId(userId);
        return orderInfo;

    }

    /**
     * 调用失败后进行报警通知 并且执行兜底操作
     * @param userId
     * @param productId
     * @return
     */
    @GetMapping("/v6/order")
    @HystrixCommand(fallbackMethod = "errorOrder1")
    public Object save5(@RequestParam("user_id")String userId, @RequestParam("product_id") Integer productId, HttpServletRequest request){

        String token = request.getHeader("token");

        String cookie = request.getHeader("cookie");

        System.out.println("token = "+ token);
        System.out.println("cookie = "+ cookie);

        String productInfo = productClient.findById(productId);

        System.out.println("/v6/order => 从商品模块查询到查询到 => "+ productInfo);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(UUID.randomUUID().toString());

        orderInfo.setProductInfo(productInfo);
        orderInfo.setUserId(userId);
        return orderInfo;

    }

    private Object errorOrder1(String userId,Integer productId, HttpServletRequest request){

        System.out.println("/v6/order => errorOrder => 接口调用异常 => "+userId+ " " + productId);
        Map<String, Object> msg = new HashMap<>();
        msg.put("code", -1);
        msg.put("msg", "抢购人数太多，您被挤出来了，稍等重试");
        return msg;
    }


    /**
     * Sleuth 日志测试
     * @param userId
     * @param productId
     * @return
     */
    @GetMapping("/v7/order")
    public Object save6(@RequestParam("user_id")String userId, @RequestParam("product_id") Integer productId){

        Object forObject = restTemplate.getForObject("http://product-module/v2/find?id=" + productId, Object.class);

        logger.info("/v7/order => 从商品模块查询到查询到 => {}",forObject);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(UUID.randomUUID().toString());

        orderInfo.setProductInfo(forObject);
        orderInfo.setUserId(userId);
        return orderInfo;

    }

}
