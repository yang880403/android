package com.stepone.component.navigator.interceptor;

import com.stepone.component.navigator.request.InvokeRequest;

import java.util.List;

/**
 * FileName: InvokeInterceptorCenter
 * Author: y.liang
 * Date: 2019-12-02 13:04
 */

final class InvokeInterceptorCenter extends InterceptorCenter<InvokeRequest> {
    private static InvokeInterceptorCenter instance = null;
    static InvokeInterceptorCenter getInstance() {
        if (instance == null) {
            synchronized (InvokeInterceptorCenter.class) {
                if (instance == null) {
                    instance = new InvokeInterceptorCenter();
                }
            }
        }

        return instance;
    }

    @Override
    protected List<Interceptor<InvokeRequest>> constructDefaultInterceptors() {
        return null;
    }
}
