package cn.zzzcr.springcloud.fallback;

import cn.zzzcr.springcloud.service.HystrixProClient;
import org.springframework.stereotype.Component;

@Component
public class HystrixProFallback implements HystrixProClient {


    @Override
    public String findById(int id) {
        System.out.println("v4/order => 调用异常");

        return "业务异常";
    }
}
