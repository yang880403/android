package com.ysl.stepone.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.stepone.uikit.AbsSTActivity;
import com.ysl.stepone.R;

/**
 * Copyright (C), 2015-2019, Shanghai Keking Technology Group Co., Ltd.
 * FileName: SplashAcitivity
 * Author: y.liang
 * Date: 2019-11-22 17:26
 */

public class SplashAcitivity extends AbsSTActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPageTitle("标题");
//        setAppBarBackgroundColor(Color.BLUE);
        setContentView(R.layout.activity_splash);

//        setStatusBarBackgroundColor(Color.WHITE);
//        setFullscreen();
//        setDarkModeStatusBar();
    }



}
