# springcloud-study -- v2.1.4.RELEASE

1. Ribbon 基于调用其它服务的客户端,负载均衡工具
2. Feign 一个申明式的WebService，集成了Ribbon的功能，只不过不需要像Ribbon一样使用RestTemplate 去调用，而是事先进行某个方法的声明，使用的时候需要还需要在启动类上添加 @EnableFeignClients 
3. Hystrix 服务治理，当调用第三方服务时出现错误可进行熔断，隔离，等操作，启动类需要添加注解 @EnableCircuitBreaker