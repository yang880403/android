package com.stepone.component.navigator;

/**
 * FileName: Navigator
 * Author: y.liang
 * Date: 2019-11-29 16:50
 */


import android.app.Application;

import com.stepone.component.navigator.request.Request;

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

    public static Request startOpenPath(String path) {
        return navigator().startOpenPath(path);
    }

    public static Request startGoBack() {
        return navigator().startGoBack();
    }

    public static Request startGoBack(int step) {
        return navigator().startGoBack(step);
    }

    public static void call(Request request) {
        navigator().call(request);
    }
}
