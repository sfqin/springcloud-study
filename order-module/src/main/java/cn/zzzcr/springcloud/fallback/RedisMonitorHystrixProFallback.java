package cn.zzzcr.springcloud.fallback;

import cn.zzzcr.springcloud.service.RedisMonitorHystrixProClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Component
public class RedisMonitorHystrixProFallback implements RedisMonitorHystrixProClient {


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String findById(int id) {
        System.out.println("v5/order => 调用异常");

        String monitorKey = "save-order";


        Object sendStatus = redisTemplate.opsForValue().get(monitorKey);

        new Thread(() -> {
            if(StringUtils.isEmpty(sendStatus)){
                System.out.println("短信紧急通知，下单失败");
                // 调用短信，邮件接口发送报警信息 TODO
                redisTemplate.opsForValue().set(monitorKey,"fail",20,TimeUnit.SECONDS);

            }else {
                System.out.println("已经发送短信报警，20s内不重复");
            }
        }).start();

        return "业务异常";
    }
}
