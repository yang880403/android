package com.ysl.stepone.activity;

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

        setContentView(R.layout.activity_splash);

//        View decorView = getWindow().getDecorView();
//        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//        decorView.setSystemUiVisibility(option);
//
//        setContentView(R.layout.abs_st_activity);
//        getWindow().getDecorView().setFitsSystemWindows(true);
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏
////        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(Color.RED);//将状态栏设置成透明色
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);//将导航栏设置为透明色
//        }
    }



}
