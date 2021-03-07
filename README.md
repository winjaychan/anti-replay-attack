# anti-replay-attack

#### 介绍
配置化处理统一重放攻击，可支持restful风格中的PathVariable变量



#### 使用说明

1. 在pom.xml增加starter依赖

   ```xml
   <dependency>
       <groupId>org.winjay</groupId>
       <artifactId>anti-replay-attack-spring-boot-starter</artifactId>
       <version>0.0.1-SNAPSHOT</version>
    </dependency>
   ```

2. 定义接口拦截（以下实例使用AOP）

   ```java
   @Aspect
   @Component
   @Slf4j
   public class ReplayAttackAspect {
   
   //    @Pointcut("@annotation(org.springframework.web.bind.annotation.RestController)")
       @Pointcut("execution(* org.winjay.antireplayattack.controller.*.*(..))")
       public void pointCut(){}
   
       @Before("pointCut()")
       public void handle(JoinPoint joinPoint){
           boolean result = RateLimiterManager.getInstance().applyRateLimit("userid", "123");
           log.info("result is {}", result);
       }
   }
   ```

   

3. 调用RateLimiterManager.getInstance().applyRateLimit()说明

   参数：可传入业务 属性值，如按用户对拉口进行限制请求次数，可传入当前用户标识

   返回值：true：未达到限制上限制，false: 达到限制上限值
