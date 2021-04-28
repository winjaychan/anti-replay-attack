package org.winjay.component.antireplayattack.limit.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.winjay.component.antireplayattack.limit.model.RateLimitInterfaceDef;

import java.util.List;

/**
 * Created by Winjay
 *
 * @author <a href="mailto:chenwenjie@do1.com.cn">Winjay</a>
 * @date 2021/3/5 14:29
 */
@ConfigurationProperties(prefix = "config.rate-limit")
@Slf4j
public class RateLimitConfig {
    private List<RateLimitInterfaceDef> rateLimitInterfaces;
    private String prefix;
    private String type;
    private String rateLimitInterfaceString;
    private boolean arrayConfigEnabled = true;

    public void setRateLimitInterfaces(List<RateLimitInterfaceDef> rateLimitInterfaces) {
        if (arrayConfigEnabled){
            this.rateLimitInterfaces = rateLimitInterfaces;
        }
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

    public String getRateLimitInterfaceString() {
        return rateLimitInterfaceString;
    }

    public void setRateLimitInterfaceString(String rateLimitInterfaceString) {
        this.rateLimitInterfaceString = rateLimitInterfaceString;
        if (arrayConfigEnabled){
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        if (ObjectUtils.isEmpty(rateLimitInterfaceString)){
            return ;
        }
        try {
            rateLimitInterfaces = mapper.readValue(rateLimitInterfaceString,  new TypeReference<List<RateLimitInterfaceDef>>() {
            });
        } catch (JsonProcessingException e) {
            log.warn("限流配置解析异常，rateLimitInterfaceString={}", rateLimitInterfaceString);
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        String json = "[{\"method\": \"put\", \"uri\": \"/relayattack/{tenantid}/role/{roleid}\", \"limit\": 6, \"limitPeriod\": 60},{\"method\": \"put\", \"uri\": \"/relayattackxx/{tenantid}/role/{roleid}\", \"limit\": 6, \"limitPeriod\": 60}]";
        ObjectMapper mapper = new ObjectMapper();
        List<RateLimitInterfaceDef> rateLimitInterfaceDefs = mapper.readValue(json, new TypeReference<List<RateLimitInterfaceDef>>() {
        });
        System.out.println(rateLimitInterfaceDefs);
    }

    public boolean isArrayConfigEnabled() {
        return arrayConfigEnabled;
    }

    public void setArrayConfigEnabled(boolean arrayConfigEnabled) {
        this.arrayConfigEnabled = arrayConfigEnabled;
    }
}
