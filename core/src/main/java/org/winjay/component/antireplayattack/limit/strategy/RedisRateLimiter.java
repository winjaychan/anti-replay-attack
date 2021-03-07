package org.winjay.component.antireplayattack.limit.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.winjay.component.antireplayattack.limit.IRateLimiter;
import org.winjay.component.antireplayattack.limit.model.RateLimitInterfaceDef;
import org.winjay.component.antireplayattack.limit.model.RateLimitRequestEntity;

import java.util.concurrent.TimeUnit;

/**
 * Redis限制请求次数
 *
 * Created by Winjay
 *
 * @author <a href="mailto:chenwenjie@do1.com.cn">Winjay</a>
 * @date 2021/3/5 16:30
 */
@Slf4j
public class RedisRateLimiter implements IRateLimiter {
    private static final String DEFAULT_VALUE = "0";
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean apply(RateLimitRequestEntity rateLimitRequestEntity, RateLimitInterfaceDef rateLimitInterfaceDef) {
        String cacheKey = rateLimitRequestEntity.getCacheKey();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        Boolean ret = operations.setIfAbsent(cacheKey, DEFAULT_VALUE, rateLimitInterfaceDef.getLimitPeriod(), TimeUnit.SECONDS);
        log.info("redis set expire {}", ret);
        Long current = operations.increment(cacheKey);

        log.info("当前接口key={}请求次数: {}", cacheKey, current);
        if (current > rateLimitInterfaceDef.getLimit()){
            return false;
        }
        return true;
    }
}
