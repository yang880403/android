package com.stepone.uikit.dispatcher.request;

/**
 * FileName: BackRequest
 * Author: shiliang
 * Date: 2019-12-01 23:26
 */
public final class BackRequest extends Request {
    @Override
    public int requestType() {
        return Type.BACK;
    }
}
