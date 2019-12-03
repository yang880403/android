package com.stepone.component.navigator.interceptor;

import com.stepone.component.navigator.request.BackRequest;

import java.util.List;

/**
 * FileName: BackInterceptorCenter
 * Author: y.liang
 * Date: 2019-12-02 13:03
 */

final class BackInterceptorCenter extends InterceptorCenter<BackRequest> {
    private static BackInterceptorCenter instance = null;
    static BackInterceptorCenter getInstance() {
        if (instance == null) {
            synchronized (BackInterceptorCenter.class) {
                if (instance == null) {
                    instance = new BackInterceptorCenter();
                }
            }
        }

        return instance;
    }

    @Override
    protected List<Interceptor<BackRequest>> constructDefaultInterceptors() {
        return null;
    }
}
