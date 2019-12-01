package com.stepone.uikit.dispatcher.request;

/**
 * FileName: CustomRequest
 * Author: shiliang
 * Date: 2019-12-01 23:30
 */
public class CustomRequest extends Request {
    @Override
    public int requestType() {
        return Type.CUSTOM;
    }
}
