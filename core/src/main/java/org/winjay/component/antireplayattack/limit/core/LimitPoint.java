package org.winjay.component.antireplayattack.limit.core;

import java.util.List;

/**
 * 限制点
 * Created by Winjay
 *
 * @author <a href="mailto:chenwenjie@do1.com.cn">Winjay</a>
 * @date 2021/3/17 16:30
 */
public interface LimitPoint {
    /**
     * 业务参数的限制点
     * @return
     */
    List<String> getBusinessPoint();
}
