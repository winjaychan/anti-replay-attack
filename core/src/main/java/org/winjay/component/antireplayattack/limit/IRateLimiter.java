package org.winjay.component.antireplayattack.limit;

import org.winjay.component.antireplayattack.limit.model.RateLimitInterfaceDef;
import org.winjay.component.antireplayattack.limit.model.RateLimitRequestEntity;

/**
 * Created by Winjay
 *
 * @author <a href="mailto:chenwenjie@do1.com.cn">Winjay</a>
 * @date 2021/3/5 12:40
 */
public interface IRateLimiter {
    /**
     * 应用限流规则
     * @param rateLimitRequestEntity
     */
    boolean apply(RateLimitRequestEntity rateLimitRequestEntity, RateLimitInterfaceDef rateLimitInterfaceDef);
}
