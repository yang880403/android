package com.stepone.navigator.interceptor;

import com.stepone.navigator.NavigatorStack;
import com.stepone.navigator.request.BackRequest;
import com.stepone.navigator.request.Request;

import java.util.ArrayList;
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
        List<Interceptor<BackRequest>> list = new ArrayList<>();
        list.add(new FinallyInterceptor());
        return list;
    }

    private final static class FinallyInterceptor implements Interceptor<BackRequest>{

        @Override
        public int priority() {
            return Integer.MAX_VALUE;
        }

        @Override
        public String name() {
            return "FinallyBackInterceptor";
        }

        @Override
        public void intercept(Chain<BackRequest> chain) {
            BackRequest request = chain.getRequest();
            Request.Callback callback = request.getCallback();

            int step = request.getStep();
            NavigatorStack.back(step);

            if (callback != null) {
                callback.onSucceed();
            }
        }
    }
}
