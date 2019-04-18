package cn.zzzcr.springcloud.controller;

import cn.zzzcr.springcloud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Value("${server.port}")
    private int port;

    @GetMapping("/hello")
    public String hello(){
        return "product hello";
    }

    @GetMapping("/find")
    public Object find(@RequestParam("id") Integer id){
        System.out.println("我是port:"+ port + " 有人调用我");
        return productService.findById(id);
    }
}
