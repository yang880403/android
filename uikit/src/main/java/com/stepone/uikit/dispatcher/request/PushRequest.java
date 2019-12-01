package com.stepone.uikit.dispatcher.request;

import android.net.Uri;

/**
 * FileName: PushRequest
 * Author: shiliang
 * Date: 2019-12-01 11:25
 */
public final class PushRequest extends Request {
    private Uri targetUri;

    public PushRequest(Uri uri) {
        this.targetUri = uri;
    }

    public PushRequest(String targetId) {
        super(targetId);
    }

    @Override
    public int requestType() {
        return Type.PUSH;
    }

    public void push() {
        call();
    }

    /**
     * getter and setter
     */
    public Uri getTargetUri() {
        return targetUri;
    }

    public void setTargetUri(Uri targetUri) {
        this.targetUri = targetUri;
    }
}
