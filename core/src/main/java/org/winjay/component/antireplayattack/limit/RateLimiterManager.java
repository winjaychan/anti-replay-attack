package org.winjay.component.antireplayattack.limit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import org.winjay.component.antireplayattack.limit.config.RateLimitConfig;
import org.winjay.component.antireplayattack.limit.model.RateLimitInterfaceDef;
import org.winjay.component.antireplayattack.limit.model.RateLimitRequestEntity;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Winjay
 *
 * @author <a href="mailto:chenwenjie@do1.com.cn">Winjay</a>
 * @date 2021/3/5 14:13
 */
@Slf4j
public class RateLimiterManager {
    private static RateLimiterManager rateLimiterManager;

    @Autowired
    private IRateLimiter rateLimiter;
    @Autowired
    private RateLimitConfig rateLimitConfig;



    public static RateLimiterManager getInstance(){
        return rateLimiterManager;
    }

    @PostConstruct
    public void init(){
        rateLimiterManager = this;
    }

    /**
     * 应用限流次数规则
     * @param bizCacheKey 业务的Key，可以使用多个组成，按业务需要进行组装
     * @return
     */
    public boolean applyRateLimit(String... bizCacheKey){
        RateLimitRequestEntity rateLimitRequestEntity = fromHttpRequest();
        RateLimitInterfaceDef rateLimitInterfaceDef = findRateLimitInterfaceDef(rateLimitRequestEntity);
        log.info("限流请求对象信息rateLimitRequestEntity={}, rateLimitInterfaceDef={}", rateLimitRequestEntity, rateLimitInterfaceDef);
        if (rateLimitInterfaceDef == null){
            return true;
        }

        String cacheKey = getCacheKey(rateLimitRequestEntity, bizCacheKey);
        rateLimitRequestEntity.setCacheKey(cacheKey);

        return rateLimiter.apply(rateLimitRequestEntity, rateLimitInterfaceDef);
    }

    /**
     * 组装缓存Key prefix:bizCacheKey:method:uri
     * eg: do1: userid: put:/{tentanid}/role/{roleid}
     * @param bizCacheKey
     * @return
     */
    private String getCacheKey(RateLimitRequestEntity rateLimitRequestEntity, String... bizCacheKey){

        List<String> list = new ArrayList<>(Arrays.asList(bizCacheKey));
        if (rateLimitConfig.getPrefix() != null){
            list.add(0, rateLimitConfig.getPrefix());
        }
        list.add(rateLimitRequestEntity.getMethod().toLowerCase());
        list.add(rateLimitRequestEntity.getUri());
        return list.stream().collect(Collectors.joining(":", "",""));
    }

    private RateLimitInterfaceDef findRateLimitInterfaceDef(RateLimitRequestEntity rateLimitRequestEntity) {
        List<RateLimitInterfaceDef> rateLimitInterfaces = rateLimitConfig.getRateLimitInterfaces();
        if (rateLimitInterfaces == null || rateLimitInterfaces.size() == 0) {
            return null;
        }

        Optional<RateLimitInterfaceDef> rateLimitInterfaceDef = rateLimitInterfaces.stream().filter(rateLimitInterface ->
                rateLimitRequestEntity.getMethod().equalsIgnoreCase(rateLimitInterface.getMethod().toLowerCase())
                        && rateLimitRequestEntity.getUri().equals(rateLimitInterface.getUri())
        ).findFirst();
        if (rateLimitInterfaceDef.isPresent()){
            return rateLimitInterfaceDef.get();
        }
        return null;
    }

    private RateLimitRequestEntity fromHttpRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String method = request.getMethod();
        String uri = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return RateLimitRequestEntity.builder().method(method).uri(uri).build();
    }

}
