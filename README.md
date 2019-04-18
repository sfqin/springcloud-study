# springcloud-study -- v2.1.4.RELEASE

1. Ribbon 基于调用其它服务的客户端,负载均衡工具
2. Feign 一个声明式的WebService，集成了Ribbon的功能，只不过不需要像Ribbon一样使用RestTemplate 去调用，而是事先进行某个方法的声明，使用的时候需要还需要在启动类上添加 @EnableFeignClients 
3. Hystrix 服务治理，当调用第三方服务时出现错误可进行熔断，隔离，等操作，启动类需要添加注解 @EnableCircuitBreaker （在可能出现异常的方法上直接加上注解指定失败后执行哪个方法@HystrixCommand(fallbackMethod = "errorOrder")）
4. Hystrix 结合 Feign 使用；在 Feign 声明的 interface 上 @FeignClient 注解中添加 fallback 属性指定异常操作的实现类 （注意点：1.多个productClient,在配置文件中加入配置 spring.main.allow-bean-definition-overriding=true；2.需要配置开启 feign 配合 hystrix）