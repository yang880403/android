package com.stepone.navigator.request;

import android.net.Uri;

import com.stepone.navigator.ActivityHooker;


/**
 * FileName: PushRequest
 * Author: shiliang
 * Date: 2019-12-01 11:25
 */
public final class PushRequest extends Request {
    private ActivityHooker.OnActivityResultCallback resultCallback;

    public PushRequest(Uri uri) {
        setUri(uri);
    }

    public PushRequest(String path) {
        setPath(path);
    }

    public PushRequest pushRequest() {
        return this;
    }

    @Override
    public boolean isValid() {
        return getContext() != null && getMetaRouter() != null;
    }

    public ActivityHooker.OnActivityResultCallback getResultCallback() {
        return resultCallback;
    }

    public void push() {
        call();
    }

    public void pushForResult(ActivityHooker.OnActivityResultCallback callback) {
        this.resultCallback = callback;
        call();
    }
}
