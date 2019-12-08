package com.ysl.stepone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.stepone.navigator.interceptor.Interceptor;
import com.stepone.navigator.request.PushRequest;

/**
 * FileName: FirstIntercepter
 * Author: y.liang
 * Date: 2019-12-03 09:53
 */

public class FirstIntercepter implements Interceptor<PushRequest> {
    @Override
    public int priority() {
        return 0;
    }

    @Override
    public String name() {
        return "FirstIntercepter";
    }

    @Override
    public void intercept(final Chain<PushRequest> chain) {
        PushRequest request = chain.getRequest();
        Context context = request.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("title")
                .setMessage("message")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chain.cancel();
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chain.proceed();
                    }
                });
        builder.show();
    }
}
