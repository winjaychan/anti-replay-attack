package org.winjay.component.antireplayattack.limit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Winjay
 *
 * @author <a href="mailto:chenwenjie@do1.com.cn">Winjay</a>
 * @date 2021/3/5 14:20
 */
@Data
@AllArgsConstructor
@ToString
@Builder
public class RateLimitRequestEntity {
    private String method;
    private String uri;
    private String cacheKey;
}
