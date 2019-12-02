package com.stepone.uikit.dispatcher.request;

/**
 * FileName: BackRequest
 * Author: shiliang
 * Date: 2019-12-01 23:26
 */
public final class BackRequest extends Request {
    private int backStep = 1;//回退层级， 默认为1，小于1或者大于当前回退栈层级时，会返回到栈底

    public BackRequest() {
        super();
    }
    @Override
    public int requestType() {
        return Type.BACK;
    }

    public void back() {
        call();
    }

    public int getBackStep() {
        return backStep;
    }

    public void setBackStep(int backStep) {
        this.backStep = backStep;
    }

    public BackRequest withBackStep(int step)  {
        if (step <= 0) {
            step = 1;
        }

        this.backStep = step;
        return this;
    }
}
