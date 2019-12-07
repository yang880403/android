package com.stepone.component.navigator.request;

/**
 * FileName: BackRequest
 * Author: shiliang
 * Date: 2019-12-01 23:26
 */

/**
 * 通过BackRequest进行回退操作，实质是通过回退栈进行操作，必须在提前构建正确的导航栈
 * 但与单独使用导航栈进行返回操作相比，使用BackRequest可以通过intercept和callback扩展功能
 */
public final class BackRequest extends Request {
    private int step;//回退层级， 默认为1

    public BackRequest() {
        this(1);
    }

    public BackRequest(int backStep) {
        super();
        step = backStep;
    }

    public int getStep() {
        return step;
    }

    public BackRequest withStep(int backStep) {
        step = backStep;
        return this;
    }

    public void back() {
        call();
    }

    public void back(Callback callback) {
        withCallback(callback).call();
    }
}
