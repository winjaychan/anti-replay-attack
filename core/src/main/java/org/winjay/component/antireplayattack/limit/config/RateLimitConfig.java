package org.winjay.component.antireplayattack.limit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.winjay.component.antireplayattack.limit.model.RateLimitInterfaceDef;

import java.util.List;

/**
 * Created by Winjay
 *
 * @author <a href="mailto:chenwenjie@do1.com.cn">Winjay</a>
 * @date 2021/3/5 14:29
 */
@ConfigurationProperties(prefix = "config.rate-limit")
public class RateLimitConfig {
    private List<RateLimitInterfaceDef> rateLimitInterfaces;
    private String prefix;
    private String type;

    public void setRateLimitInterfaces(List<RateLimitInterfaceDef> rateLimitInterfaces) {
        this.rateLimitInterfaces = rateLimitInterfaces;
    }

    public List<RateLimitInterfaceDef> getRateLimitInterfaces() {
        return rateLimitInterfaces;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
