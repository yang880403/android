package com.stepone.component.navigator.interceptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.stepone.component.common.ActivityHooker;
import com.stepone.component.navigator.RouterMap;
import com.stepone.component.navigator.request.PushRequest;
import com.stepone.component.navigator.request.Request;

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
            return "FinallyPushInterceptor";
        }

        @Override
        public void intercept(Chain<PushRequest> chain) {
            final PushRequest request = chain.getRequest();

            final Context context = request.getContext();
            RouterMap.Entry entry = request.getPayload();
            Request.Observer observer = request.getObserver();

            if (context != null && entry != null) {
                Class pageClazz = entry.getTargetClazz();
                if (pageClazz != null) {
                    Class targetActivityClazz = pageClazz;
                    if (Fragment.class.isAssignableFrom(pageClazz)) {
                        targetActivityClazz = entry.getParentClazz();
                    }

                    if (targetActivityClazz != null && Activity.class.isAssignableFrom(targetActivityClazz)) {
                        final Intent intent = new Intent(context, targetActivityClazz);

                        //传递routerID
                        intent.putExtra(RouterMap.Entry.KEY_ENTRY_ID, entry.hashCode());

                        if (!(context instanceof Activity)) {
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }

                        Bundle params = request.getBundle();
                        if (params != null) {
                            intent.putExtras(params);
                        }

                        if (observer != null) {
                            observer.onFound(request);
                        }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                ActivityHooker.OnActivityResultCallback resultCallback = request.getResultCallback();

                                /**
                                 * 只能自动hook FragmentActivity的返回结果
                                 */
                                if ((context instanceof FragmentActivity) && resultCallback != null) {
                                    ActivityHooker.startActivityForResult((FragmentActivity) context, intent, resultCallback);
                                } else {
                                    ActivityCompat.startActivity(context, intent, null);
                                }
                            }
                        });

                        if (observer != null) {
                            observer.onArrival(request);
                        }

                        return;
                    }
                }
            }

            if (observer != null) {
                observer.onLost(request);
            }
        }
    }
}
