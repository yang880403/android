package com.stepone.uikit.dispatcher.interceptor;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.stepone.uikit.dispatcher.RouterMap;
import com.stepone.uikit.dispatcher.request.PushRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: PushInterceptorCenter
 * Author: y.liang
 * Date: 2019-12-02 12:35
 */

final class PushInterceptorCenter extends InterceptorCenter<PushRequest> {
    private static PushInterceptorCenter instance = null;
    static PushInterceptorCenter getInstance() {
        if (instance == null) {
            synchronized (PushInterceptorCenter.class) {
                if (instance == null) {
                    instance = new PushInterceptorCenter();
                }
            }
        }

        return instance;
    }

    @Override
    protected List<Interceptor<PushRequest>> constructDefaultInterceptors() {
        List<Interceptor<PushRequest>> list = new ArrayList<>();
        list.add(new FinallyInterceptor());

        return list;
    }

    private final static class FinallyInterceptor implements Interceptor<PushRequest> {

        @Override
        public int priority() {
            return Integer.MAX_VALUE;
        }

        @Override
        public String name() {
            return "finallyPushInterceptor";
        }

        @Override
        public void interceptor(Chain<PushRequest> chain) {
            PushRequest request = chain.getRequest();

            Context context = request.getContext();
            RouterMap.Entry entry = request.getPayload();
            if (context != null && entry != null) {
                Class pageClazz = entry.getPageClazz();
                if (pageClazz != null) {
                    Class activityClazz = null;
                    if (Fragment.class.isAssignableFrom(pageClazz)) {
                        activityClazz = entry.getPageContainerClazz();
                    } else if (Activity.class.isAssignableFrom(pageClazz)) {
                        activityClazz = pageClazz;
                    }

                    Intent intent = new Intent(context, activityClazz);
                    if (context instanceof Application) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }

                    Bundle params = request.getBundle();
                    if (params != null) {
                        intent.putExtras(params);
                    }

                    ActivityCompat.startActivity(context, intent, params);
                }
            }
        }
    }
}
