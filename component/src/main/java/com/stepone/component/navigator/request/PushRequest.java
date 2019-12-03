package com.stepone.component.navigator.request;

import android.net.Uri;

/**
 * FileName: PushRequest
 * Author: shiliang
 * Date: 2019-12-01 11:25
 */
public final class PushRequest extends Request {

    public PushRequest(Uri uri) {
        setUri(uri);
    }

    public PushRequest(String path) {
        setPath(path);
    }

    @Override
    public boolean isValid() {
        return getContext() != null && getPayload() != null;
    }
}
