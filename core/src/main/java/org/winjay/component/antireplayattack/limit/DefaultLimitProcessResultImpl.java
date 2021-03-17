package org.winjay.component.antireplayattack.limit;

import org.winjay.component.antireplayattack.limit.core.LimitProcessResult;

/**
 * 默认限制处理结果实现
 * Created by Winjay
 *
 * @author <a href="mailto:chenwenjie@do1.com.cn">Winjay</a>
 * @date 2021/3/17 16:35
 */
public class DefaultLimitProcessResultImpl implements LimitProcessResult {
    private boolean isLimited;
    @Override
    public boolean isLimited() {
        return isLimited;
    }

    /**
     * 设置处理结果
     * @param isLimited
     */
    void setProcessResult(boolean isLimited){
        this.isLimited = isLimited;
    }

}
