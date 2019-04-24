package cn.zzzcr.springcloud.controller;

import cn.zzzcr.springcloud.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RefreshScope
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Value("${server.port}")
    private int port;


    @Value("${apple.price}")
    private int price;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/hello")
    public String hello(){
        return "product hello";
    }

    @GetMapping("/v1/find")
    public Object find(@RequestParam("id") Integer id) throws InterruptedException {
        System.out.println("我是port:"+ port + " 有人调用我=> id="+id);

        if(id == 3){
            TimeUnit.SECONDS.sleep(3);
        }

        return productService.findById(id);
    }

    /**
     * Sleuth 日志测试
     * @param id
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/v2/find")
    public Object find1(@RequestParam("id") Integer id) throws InterruptedException {

        logger.info("我是port:{} 有人调用我=> id:{}",port,id);


        if(id == 3){
            TimeUnit.SECONDS.sleep(3);
        }

        return productService.findById(id);
    }

    /**
     * Sleuth 消息总线测试
     * @param id
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/v3/find")
    public Object find2(@RequestParam("id") Integer id) throws InterruptedException {

        logger.info("我是port:{} 有人调用我=> id:{} => apple.price:{}",port,id,price);


        if(id == 3){
            TimeUnit.SECONDS.sleep(3);
        }

        return productService.findById(id);
    }
}
