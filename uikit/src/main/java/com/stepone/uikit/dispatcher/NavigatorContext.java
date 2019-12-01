package com.stepone.uikit.dispatcher;

/**
 * FileName: NavigatorContext
 * Author: shiliang
 * Date: 2019-11-30 18:23
 */


import android.app.Application;
import android.net.Uri;

import com.stepone.uikit.dispatcher.interceptor.InterceptorCenter;
import com.stepone.uikit.dispatcher.request.BackRequest;
import com.stepone.uikit.dispatcher.request.PushRequest;
import com.stepone.uikit.dispatcher.request.Request;

/**
 * 搜集参数，根据RouterMap等构建完整Request，根据requestType，选择选择不同的拦截器进行拦截
 */
final class NavigatorContext {
    private static volatile boolean hasInit = false;
    private static Application mContext;


    private NavigatorContext() { }
    private final static class Holder {
        private static NavigatorContext INSTANCE = new NavigatorContext();
    }

    static synchronized boolean init(Application application) {
        mContext = application;
        hasInit = true;
        return hasInit;
    }

    static NavigatorContext getInstance() {
        if (!hasInit) {
            throw new RuntimeException("please invoke init() func first!");
        }
        return Holder.INSTANCE;
    }

    /**
     * 构造请求
     */
    Request startOpenURL(String url) {
        return new PushRequest(Uri.parse(url));
    }

    Request startOpenID(String targetId) {
        return new PushRequest(targetId);
    }

    Request startGoback() {
        return new BackRequest();
    }

    void call(Request request) {
        if (request != null) {
            request.from(mContext);

            //开启拦截器
            InterceptorCenter.callRequest(request);
        }
    }
}
