package com.stepone.uikit.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * FileName: STFragment
 * Author: y.liang
 * Date: 2019-11-27 13:34
 */

/**
 * 帮助开发者更好的基于Fragment进行APP开发
 * 1、提供抽象的onCreateView方法，方便开发者通过传递layoutID来配置页面，同时也允许开发者使用原来的方法
 * 2、提供通用的方法，方便开发者直接调用STActivity的方法
 */
abstract public class STFragment extends Fragment {
    private STActivity mSTActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof STActivity) {
            mSTActivity = ((STActivity) context);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSTActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutResID = onCreateView();
        return inflater.inflate(layoutResID, container, false);
    }

    abstract public int onCreateView();

    public STActivity getSTActivity() {
        return mSTActivity;
    }

    /**
     * 定制App bar
     */

    public void setPageTitle(String pageTitle) {
        if (mSTActivity == null) {
            return;
        }
        mSTActivity.setPageTitle(pageTitle);
    }

    public void setPageTitleColor(int color) {
        if (mSTActivity == null) {
            return;
        }
        mSTActivity.setPageTitleColor(color);
    }

    public void setPageTitleView(int layoutResID) {
        if (mSTActivity == null) {
            return;
        }

    }

    /**
     * 沉浸模式
     */
    public void setAppbarBackgroundColor(int color) {
        if (mSTActivity == null) {
            return;
        }
        mSTActivity.setAppbarBackgroundColor(color);
    }

    /**
     * MD模式
     */
    public void setAppbarBackgroundColor(int appBarColor, int statusBarColor) {
        if (mSTActivity == null) {
            return;
        }
        mSTActivity.setAppbarBackgroundColor(appBarColor, statusBarColor);
    }

    public void setStatusBarBackgroundColor(int color) {
        if (mSTActivity == null) {
            return;
        }
        mSTActivity.setStatusBarBackgroundColor(color);
    }

    /**
     * 布局延伸到状态栏，且状态栏设置为透明
     */
    public void setFullscreen() {
        if (mSTActivity == null) {
            return;
        }
        mSTActivity.setFullscreen();
    }

    /**
     * 设置Dark模式
     */
    public void setDarkModeStatusBar() {
        if (mSTActivity == null) {
            return;
        }
        mSTActivity.setDarkModeStatusBar();
    }

    /**
     * loading view
     */

    public void startLoading() {
        if (mSTActivity == null) {
            return;
        }
        mSTActivity.startLoading();
    }

    public void startLoading(String text) {
        if (mSTActivity == null) {
            return;
        }
        mSTActivity.startLoading(text);
    }

    public void stopLoading() {
        if (mSTActivity == null) {
            return;
        }
        mSTActivity.stopLoading();
    }
}
