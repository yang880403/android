package com.stepone.component.navigator.interceptor;

import com.stepone.component.navigator.request.CustomRequest;

import java.util.List;

/**
 * FileName: CustomInterceptorCenter
 * Author: y.liang
 * Date: 2019-12-02 13:05
 */

final class CustomInterceptorCenter extends InterceptorCenter<CustomRequest> {
    private static CustomInterceptorCenter instance = null;
    static CustomInterceptorCenter getInstance() {
        if (instance == null) {
            synchronized (CustomInterceptorCenter.class) {
                if (instance == null) {
                    instance = new CustomInterceptorCenter();
                }
            }
        }

        return instance;
    }

    @Override
    protected List<Interceptor<CustomRequest>> constructDefaultInterceptors() {
        return null;
    }
}
