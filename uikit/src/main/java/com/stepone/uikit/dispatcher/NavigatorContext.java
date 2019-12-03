package com.stepone.uikit.dispatcher;

/**
 * FileName: NavigatorContext
 * Author: shiliang
 * Date: 2019-11-30 18:23
 */


import android.app.Application;
import android.content.Context;
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

    Request startOpenPath(String path) {
        return new PushRequest(path);
    }

    Request startGoBack() {
        return new BackRequest();
    }

    Request startGoBack(int backStep) {
        return new BackRequest(backStep);
    }

    /**
     * 开始路由
     */
    void call(Request request) {
        if (request != null) {
            RouterMap.Entry entry = RouterMap.getRouter(request.getPath(), request.getGroup());

            if (entry == null) {
                entry = RouterMap.parseUri(request.getUri());
            }

            Context currentContext = request.getContext() == null ? mContext : request.getContext();
            //开启拦截器
            if (request.from(currentContext).fillEntry(entry).isValid()) {
                InterceptorCenter.callRequest(request);
            }
        }
    }
}
