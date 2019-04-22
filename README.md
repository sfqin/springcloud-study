# springcloud-study -- v2.1.4.RELEASE

1. Ribbon 基于调用其它服务的客户端,负载均衡工具
2. Feign 一个声明式的WebService，集成了Ribbon的功能，只不过不需要像Ribbon一样使用RestTemplate 去调用，而是事先进行某个方法的声明，使用的时候需要还需要在启动类上添加 @EnableFeignClients 
3. Hystrix 服务治理，当调用第三方服务时出现错误可进行熔断，隔离，等操作，启动类需要添加注解 @EnableCircuitBreaker （在可能出现异常的方法上直接加上注解指定失败后执行哪个方法@HystrixCommand(fallbackMethod = "errorOrder")）
4. Hystrix 结合 Feign 使用；在 Feign 声明的 interface 上 @FeignClient 注解中添加 fallback 属性指定异常操作的实现类 （注意点：1.多个productClient,在配置文件中加入配置 spring.main.allow-bean-definition-overriding=true；2.需要配置开启 feign 配合 hystrix）
5. Hystrix Feign Ribbon 都有自己的相应的调用超时时间，Hstrix 有最大数量配置，和连接池配置
6. Hystrix dashboard 一般生产不使用，用于监控配合 actuator 使用 启动类加上 @EnableHystrixDashboard
7. Zuul 网关，启动类需要加注解 @EnableZuulProxy 里面默认集成了Hystrix。访问服务的默认地址为 gatewayip:port/{service-key}/接口URI。 自定义路径需要在 Zuul 的配置文件中进行配置 routes （注意路径覆盖问题）
8. Zuul 传递 header 的时候会过滤掉递敏感信息 如需将敏感信息往下游传递 （需要配置 sensitive-headers 为空）
9. Zuul 1) 自定义 Login filter 进行权限校验 2) 谷歌guava框架网关限流使用(可以结合redis用)
10. Sleuth 分布式链路追踪 主要功能可以做日志埋点 全局 traceId 每个服务中traceId 是否要将该信息输出到zipkin服务中来收集和展示
11. Zipkin 结合 Sleuth 做链路追踪的可视化界面，Zipkin server 需要官网下载 jar包（springboot 应用）2.0 已经不推荐使用自建项目搭建 Zipkin server，默认是内存存储，可改用其它存储方式，配置采集日制百分比（zipkin上报显示为false，在相关项目中必须都引用的是Zipkin的集成包，不能有项目引用单独的Sleuth包）
