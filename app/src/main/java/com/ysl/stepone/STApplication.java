package com.ysl.stepone;

import android.app.Application;

import com.stepone.navigator.Navigator;
import com.stepone.navigator.RouterMap;
import com.stepone.navigator.interceptor.InterceptorCenter;
import com.ysl.stepone.activity.BaseActivity;
import com.ysl.stepone.fragment.FirstFragment;
import com.ysl.stepone.fragment.SecondFragment;
import com.ysl.stepone.fragment.SplashFragment;

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
        RouterMap.addRouter(new RouterMap.MetaRouter("", "splash", SplashFragment.class, BaseActivity.class, 0));
        RouterMap.addRouter(new RouterMap.MetaRouter("", "first", FirstFragment.class, BaseActivity.class, 0));
        RouterMap.addRouter(new RouterMap.MetaRouter("", "second", SecondFragment.class, BaseActivity.class, 0));

        InterceptorCenter.addPushInterceptor(new FirstIntercepter(), null);
    }
}
