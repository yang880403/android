package com.stepone.uikit.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.stepone.uikit.R;

/**
 * FileName: STActivity
 * Author: shiliang
 * Date: 2019-11-24 18:00
 */

/**
 * 1、开发者不用再花精力去处理状态条和导航条的问题，只需要实现导航条之下的UI填充及交互处理即可
 * 2、重写setContentView实现，开发者遵照原来的使用方式和习惯，给activity填充布局
 * 3、如果是基于Fragment进行APP开发，那么只需要通过setContentFragent进行fragment绑定
 * 4、提供接口，方便使用者控制statusBar、navigationBar、loadingAnimation等基础操作
 */
public class STActivity extends AppCompatActivity {
    private ViewGroup mContentParent;
    private ViewGroup mLoadingContentParent;
    private TextView mLoadingTextView;
    private AppBar mAppBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.STAppTheme);
        super.setContentView(R.layout.st_uikit_abs_activity);

        mAppBar = findViewById(R.id.st_uikit_abs_appbar);
        mContentParent = findViewById(R.id.st_uikit_abs_content);
        mLoadingContentParent = findViewById(R.id.st_uikit_abs_loading_content);

        setLoadingView(R.layout.st_uikit_default_loading_view);
        setAppbarBackgroundColor(Color.WHITE, Color.GRAY);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (mContentParent != null) {
            mContentParent.removeAllViews();
            getLayoutInflater().inflate(layoutResID, mContentParent);
        }
    }

    @Override
    public void setContentView(View view) {
        if (mContentParent != null) {
            mContentParent.removeAllViews();
            mContentParent.addView(view);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (mContentParent != null) {
            mContentParent.removeAllViews();
            mContentParent.addView(view, params);
        }
    }

    void setContentFragment(Fragment fragment) {
        if (fragment != null && mContentParent != null) {
            mContentParent.removeAllViews();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.st_uikit_abs_content, fragment)
                    .commit();
        }
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
    public void setAppbarBackgroundColor(int color) {
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

    /**
     * loading view
     */

    protected void setLoadingView(int layoutResID) {
        if (mLoadingContentParent != null) {
            mLoadingContentParent.removeAllViews();
            getLayoutInflater().inflate(layoutResID, mLoadingContentParent);

            mLoadingTextView = findViewById(R.id.st_uikit_loading_view_textview);
        }
    }

    public void startLoading() {
        if (mLoadingContentParent != null) {
            mLoadingContentParent.setVisibility(View.VISIBLE);
        }

        if (mLoadingTextView != null) {
            mLoadingTextView.setText("");
            mLoadingTextView.setVisibility(View.GONE);
        }
    }

    public void startLoading(String text) {
        if (mLoadingContentParent != null) {
            mLoadingContentParent.setVisibility(View.VISIBLE);
        }

        if (mLoadingTextView != null && text != null && !text.isEmpty()) {
            mLoadingTextView.setText(text);
            mLoadingTextView.setVisibility(View.VISIBLE);
        }
    }

    public void stopLoading() {
        if (mLoadingContentParent != null) {
            mLoadingContentParent.setVisibility(View.GONE);
        }
    }
}
