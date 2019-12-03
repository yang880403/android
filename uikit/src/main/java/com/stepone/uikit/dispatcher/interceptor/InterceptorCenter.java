package com.stepone.uikit.dispatcher.interceptor;

import androidx.annotation.NonNull;

import com.stepone.uikit.dispatcher.request.BackRequest;
import com.stepone.uikit.dispatcher.request.CustomRequest;
import com.stepone.uikit.dispatcher.request.InvokeRequest;
import com.stepone.uikit.dispatcher.request.PushRequest;
import com.stepone.uikit.dispatcher.request.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName: InterceptorCenter
 * Author: shiliang
 * Date: 2019-12-01 19:26
 */
public abstract class InterceptorCenter<T extends Request> {
    /**
     * 根据groupID分组存储拦截器，用户可以自定义拦截逻辑
     */
    private final Map<String, List<Interceptor<T>>> mInterceptorMap = new HashMap<>();

    /**
     * 默认拦截器，用来实现基本业务逻辑，用户不可控制
     */
    private final List<Interceptor<T>> mDefaultInterceptors = new ArrayList<>(10);

    InterceptorCenter() {
        List<Interceptor<T>> list = constructDefaultInterceptors();
        if (list != null) {
            mDefaultInterceptors.addAll(list);
        }
    }

    void addInterceptor(Interceptor<T> interceptor, String groupId) {
        if (interceptor != null) {
            String key = groupId == null ? "" : groupId;
            List<Interceptor<T>> interceptorList = mInterceptorMap.get(key);
            if (interceptorList == null) {
                interceptorList = new ArrayList<>();

                mInterceptorMap.put(key, interceptorList);
            }

            interceptorList.add(interceptor);
        }
    }

    private List<Interceptor<T>> getInterceptors(String groupId) {
        List<Interceptor<T>> interceptors = new ArrayList<>(mDefaultInterceptors);
        List<Interceptor<T>> list = mInterceptorMap.get(groupId == null ? "" : groupId);
        if (list != null && !list.isEmpty()) {
            interceptors.addAll(list);
        }

        return interceptors;
    }

    void call(@NonNull T request) {
        List<Interceptor<T>> interceptors = getInterceptors(request.getGroup());
        if (interceptors != null) {
            new Interceptor.Chain<>(interceptors, request).proceed();
        }
    }

    abstract protected List<Interceptor<T>> constructDefaultInterceptors();


    /**
     * 工厂方法
     */

    public static void addPushInterceptor(Interceptor<PushRequest> intercepter) {
        addPushInterceptor(intercepter, null);
    }
    public static void addPushInterceptor(Interceptor<PushRequest> interceptor, String groupId) {
        PushInterceptorCenter.getInstance().addInterceptor(interceptor, groupId);
    }

    public static void addBackInterceptor(Interceptor<BackRequest> interceptor, String groupId) {
        BackInterceptorCenter.getInstance().addInterceptor(interceptor, groupId);
    }

    public static void addInvokeInterceptor(Interceptor<InvokeRequest> interceptor, String groupId) {
        InvokeInterceptorCenter.getInstance().addInterceptor(interceptor, groupId);
    }

    public static void addCustomInterceptor(Interceptor<CustomRequest> interceptor, String groupId) {
        CustomInterceptorCenter.getInstance().addInterceptor(interceptor, groupId);
    }

    public static void callRequest(@NonNull Request request) {
        if (request instanceof PushRequest) {
            PushInterceptorCenter.getInstance().call((PushRequest) request);
        } else if (request instanceof BackRequest) {
            BackInterceptorCenter.getInstance().call((BackRequest) request);
        } else if (request instanceof InvokeRequest) {
            InvokeInterceptorCenter.getInstance().call((InvokeRequest) request);
        } else if (request instanceof CustomRequest) {
            CustomInterceptorCenter.getInstance().call((CustomRequest) request);
        }
    }
}
