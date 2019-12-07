package com.stepone.component.navigator.request;

import android.net.Uri;

import com.stepone.component.page.ActivityHooker;

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
