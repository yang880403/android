package com.stepone.uikit.dispatcher.request;

import android.content.Context;
import android.os.Bundle;

import com.stepone.uikit.dispatcher.Navigator;
import com.stepone.uikit.dispatcher.RouterMap;

/**
 * FileName: Request
 * Author: shiliang
 * Date: 2019-11-30 19:59
 */


/**
 * 路由请求
 *
 * 采用链式方式构造路由请求对象
 */

public abstract class Request {
    private Context context;
    private Bundle bundle;
    private Callback callback;

    private String groupId;//根据分组查询路由，默认分组为空字符串
    private String payloadId;
    private RouterMap.Entry payload;

    public interface Callback {
        void onLost(Request request);
    }

    public final static class Type {
        public final static int PUSH = 0;
        public final static int BACK = 1;
        public final static int INVOKE = 2;
        public final static int CUSTOM = 3;
    }

    protected Request(String targetId) {
        this.payloadId = targetId;
    }

    protected Request() {}

    protected void call() {
        Navigator.call(this);
    }

    abstract public int requestType();

    /**
     * getter && setter
     */
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public RouterMap.Entry getPayload() {
        return payload;
    }

    public void setPayload(RouterMap.Entry payload) {
        this.payload = payload;
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    /**
     * 链式调用方式设置参数
     */

    public Request from(Context context) {
        this.context = context;
        return this;
    }

    public Request withGroup(String group) {
        this.groupId = group;
        return this;
    }

    public Request withBundle(Bundle bundle) {
        if (bundle != null) {
            this.bundle.putAll(bundle);
        }
        return this;
    }

    public Request withInt(String key, int i) {
        bundle.putInt(key, i);
        return this;
    }

    public Request withLong(String key, long l) {
        bundle.putLong(key, l);
        return this;
    }

    public Request withFloat(String key, float f) {
        bundle.putFloat(key, f);
        return this;
    }

    public Request withDouble(String key, double d) {
        bundle.putDouble(key, d);
        return this;
    }

    public Request withString(String key, String str) {
        bundle.putString(key, str);
        return this;
    }

    public Request withChar(String key, char ch) {
        bundle.putChar(key, ch);
        return this;
    }

    public Request withCallback(Callback callback) {
        this.callback = callback;
        return this;
    }
}
