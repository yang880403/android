package com.ysl.stepone.activity;

import android.os.Bundle;
import android.util.Log;

import com.ysl.stepone.R;
import com.ysl.stepone.fragment.SplashFragment;

/**
 * Copyright (C), 2015-2019, Shanghai Keking Technology Group Co., Ltd.
 * FileName: SplashAcitivity
 * Author: y.liang
 * Date: 2019-11-22 17:26
 */

public class SplashAcitivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("SplashAcitivity", "task id = " + getTaskId());

        setPageTitle("标题");

        getSupportFragmentManager().beginTransaction().add(R.id.st_uikit_abs_content, new SplashFragment(), SplashAcitivity.class.toString()).commit();
//        setAppBarBackgroundColor(Color.BLUE);
//        setContentView(R.layout.activity_splash);

//        setStatusBarBackgroundColor(Color.WHITE);
//        setFullscreen();
//        setDarkModeStatusBar();
    }



}
