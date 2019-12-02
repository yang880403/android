package com.stepone.uikit.dispatcher.request;

/**
 * FileName: InvokeRequest
 * Author: shiliang
 * Date: 2019-12-01 23:28
 */
public final class InvokeRequest extends Request {
    @Override
    public int requestType() {
        return Type.INVOKE;
    }

    public void invoke() {
        call();
    }
}
