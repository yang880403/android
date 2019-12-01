package com.stepone.uikit.dispatcher;

/**
 * FileName: Navigator
 * Author: y.liang
 * Date: 2019-11-29 16:50
 */


import android.app.Application;

import com.stepone.uikit.dispatcher.request.Request;

/**
 * API provider
 */
final public class Navigator {
    private static NavigatorContext navigator() {
        return NavigatorContext.getInstance();
    }

    public static boolean init(Application application) {
        return NavigatorContext.init(application);
    }


    public static Request startOpenURL(String url) {
        return navigator().startOpenURL(url);
    }

    public static Request startOpenID(String targetId) {
        return navigator().startOpenID(targetId);
    }

    public static void call(Request request) {
        navigator().call(request);
    }
}
