package com.stepone.uikit;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * FileName: AbsSTActivity
 * Author: shiliang
 * Date: 2019-11-24 18:00
 */
public class AbsSTActivity extends AppCompatActivity {
    private ViewGroup mContentParent;
    private AppBar mAppBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.STAppTheme);
        super.setContentView(R.layout.abs_st_activity);

        mAppBar = findViewById(R.id.abs_st_appbar);
        mContentParent = findViewById(R.id.abs_st_content);
        setAppbarBackgroundColor(Color.WHITE, Color.BLACK);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (mContentParent != null) {
            getLayoutInflater().inflate(layoutResID, mContentParent);
        }
    }

    @Override
    public void setContentView(View view) {

    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {

    }

    /**
     * 定制App bar
     */

    public void setPageTitle(String pageTitle) {
        mAppBar.setTitle(pageTitle);
    }

    public void setPageTitleColor(int color) {
        mAppBar.setTitleColor(color);
    }

    public void setPageTitleView(int layoutResID) {

    }

    /**
     * 沉浸模式
     */
    public void setAppBarBackgroundColor(int color) {
        mAppBar.setBackgroundColor(color);
        setStatusBarBackgroundColor(color);
    }

    /**
     * MD模式
     */
    public void setAppbarBackgroundColor(int appBarColor, int statusBarColor) {
        mAppBar.setBackgroundColor(appBarColor);
        setStatusBarBackgroundColor(statusBarColor);
    }

    public void setStatusBarBackgroundColor(int color) {
        getWindow().setStatusBarColor(color);
    }

    /**
     * 布局延伸到状态栏，且状态栏设置为透明
     */
    public void setFullscreen() {
        int op = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        getWindow().getDecorView().setSystemUiVisibility(op);
        setStatusBarBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * 设置Dark模式
     */
    public void setDarkModeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            int op = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(op | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
