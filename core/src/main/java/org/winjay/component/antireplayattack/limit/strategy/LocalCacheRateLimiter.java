package org.winjay.component.antireplayattack.limit.strategy;

import com.github.benmanes.caffeine.cache.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.NonNegative;
import org.winjay.component.antireplayattack.limit.IRateLimiter;
import org.winjay.component.antireplayattack.limit.model.RateLimitInterfaceDef;
import org.winjay.component.antireplayattack.limit.model.RateLimitRequestEntity;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 本地缓存（JVM级别）限制请求次数，仅限于测试或单个节点
 *
 * Created by Winjay
 *
 * @author <a href="mailto:chenwenjie@do1.com.cn">Winjay</a>
 * @date 2021/3/6 10:14
 */
@Slf4j
public class LocalCacheRateLimiter  implements IRateLimiter {
    private Cache<String, AtomicLong> caches = Caffeine.newBuilder()
            .expireAfter(new Expiry<String, AtomicLong>() {
                @Override
                public long expireAfterCreate(String key, AtomicLong value, long currentTime) {
                    RateLimitInterfaceDef rateLimitInterfaceDef = RateLimitInterfaceDefContext.getRateLimitInterfaceDef();
                    RateLimitInterfaceDefContext.flush();
                    return TimeUnit.SECONDS.toNanos(rateLimitInterfaceDef.getLimitPeriod());
                }

                @Override
                public long expireAfterUpdate(String key, AtomicLong value, long currentTime, @NonNegative long currentDuration) {
                    return currentDuration;
                }

                @Override
                public long expireAfterRead(String key, AtomicLong value, long currentTime, @NonNegative long currentDuration) {
                    return currentDuration;
                }
            })
            .build();

    @Override
    public boolean apply(RateLimitRequestEntity rateLimitRequestEntity, RateLimitInterfaceDef rateLimitInterfaceDef) {

        RateLimitInterfaceDefContext.setRateLimitInterfaceDef(rateLimitInterfaceDef);
        String cacheKey = rateLimitRequestEntity.getCacheKey();

        AtomicLong currentCount = caches.get(cacheKey, key -> new AtomicLong());
        log.info("当前对象hashcode：{}", currentCount.hashCode());
        long current = currentCount.incrementAndGet();
        log.info("当前接口key={}请求次数: {}", cacheKey, current);
        if (current > rateLimitInterfaceDef.getLimit()){
            return false;
        }
        return true;
    }

    private static final class RateLimitInterfaceDefContext{
        private static final ThreadLocal<RateLimitInterfaceDef> CONTEXT = new ThreadLocal<>();

        public static void setRateLimitInterfaceDef(RateLimitInterfaceDef rateLimitInterfaceDef){
            CONTEXT.set(rateLimitInterfaceDef);
        }

        public static RateLimitInterfaceDef getRateLimitInterfaceDef(){
            return CONTEXT.get();
        }

        public static void flush(){
            CONTEXT.remove();
        }
    }
}
