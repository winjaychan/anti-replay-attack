package org.winjay.component.antireplayattack.spring.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.winjay.component.antireplayattack.limit.RateLimiterManager;
import org.winjay.component.antireplayattack.limit.config.RateLimitConfig;
import org.winjay.component.antireplayattack.limit.strategy.LocalCacheRateLimiter;
import org.winjay.component.antireplayattack.limit.strategy.RedisRateLimiter;

/**
 * Created by Winjay
 *
 * @author <a href="mailto:chenwenjie@do1.com.cn">Winjay</a>
 * @date 2021/3/6 20:27
 */
@Configuration
@EnableConfigurationProperties({RateLimitConfig.class})
public class RateLimitAutoConfigure {
    @Bean
    @ConditionalOnProperty(value = "config.rate-limit.type", havingValue = "local")
    public LocalCacheRateLimiter localCacheRateLimiter(){
        return new LocalCacheRateLimiter();
    }

    @Bean
    @ConditionalOnBean(type = {"org.springframework.data.redis.core.RedisTemplate"})
    @ConditionalOnProperty(value = "config.rate-limit.type", havingValue = "redis")
    public RedisRateLimiter redisRateLimiter(){
        return new RedisRateLimiter();
    }

    @Bean
    public RateLimiterManager rateLimiterManager(){
        return new RateLimiterManager();
    }
}
