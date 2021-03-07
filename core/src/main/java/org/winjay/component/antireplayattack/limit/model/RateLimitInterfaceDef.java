package org.winjay.component.antireplayattack.limit.model;

import lombok.Data;

/**
 * Created by Winjay
 *
 * @author <a href="mailto:chenwenjie@do1.com.cn">Winjay</a>
 * @date 2021/3/5 14:44
 */
@Data
public class RateLimitInterfaceDef {
    private String method;
    private String uri;
    /**
     * 限流次数
     */
    private Integer limit;
    /**
     * 限制周期，以秒为单位
     */
    private Integer limitPeriod;
}
