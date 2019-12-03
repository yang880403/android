package com.ysl.stepone;

import android.app.Application;

import com.stepone.uikit.dispatcher.Navigator;
import com.stepone.uikit.dispatcher.RouterMap;
import com.stepone.uikit.dispatcher.interceptor.InterceptorCenter;
import com.ysl.stepone.activity.FirstActivity;
import com.ysl.stepone.activity.SplashAcitivity;
import com.ysl.stepone.fragment.SplashViewContainer;

/**
 * Copyright (C), 2015-2019, Shanghai Keking Technology Group Co., Ltd.
 * FileName: STApplication
 * Author: y.liang
 * Date: 2019-11-22 16:50
 */

public class STApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Navigator.init(this);
        RouterMap.addRouter(new RouterMap.Entry("", "splash", SplashViewContainer.class, SplashAcitivity.class, 0));
        RouterMap.addRouter(new RouterMap.Entry("", "first", FirstActivity.class, null, 0));

        InterceptorCenter.addPushInterceptor(new FirstIntercepter(), null);
    }
}
