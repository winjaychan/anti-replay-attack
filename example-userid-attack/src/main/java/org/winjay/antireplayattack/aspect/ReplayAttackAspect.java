package org.winjay.antireplayattack.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.winjay.component.antireplayattack.limit.RateLimiterManager;

/**
 * Created by Winjay
 *
 * @author <a href="mailto:chenwenjie@do1.com.cn">Winjay</a>
 * @date 2021/3/5 12:40
 */
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
