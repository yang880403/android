package com.stepone.uikit.dispatcher.request;

import android.content.Context;
import android.net.Uri;
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

    private String group;//根据分组查询路由，默认分组为空字符串
    private String path;
    private RouterMap.Entry payload;

    private Uri uri;

    public interface Callback {
        void onLost(Request request);
    }

    protected Request() {
        this.bundle = new Bundle();
    }

    public void call() {
        Navigator.call(this);
    }

    public boolean isValid() {
        return true;
    }

    /**
     * getter && setter
     */
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        if (bundle != null) {
            this.bundle = bundle;
        }
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

    public Request fillEntry(RouterMap.Entry entry) {
        this.payload = entry;
        return this;
    }

    public Request withGroup(String group) {
        this.group = group;
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
