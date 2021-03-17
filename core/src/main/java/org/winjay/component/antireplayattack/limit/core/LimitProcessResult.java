package org.winjay.component.antireplayattack.limit.core;

/**
 * 限制处理结果
 * Created by Winjay
 *
 * @author <a href="mailto:chenwenjie@do1.com.cn">Winjay</a>
 * @date 2021/3/17 16:29
 */
public interface LimitProcessResult {
    /**
     * 获取是否被限制的处理结果
     * @return  true：未达到限制上限制，false: 达到限制上限值
     */
    boolean isLimited();
}
