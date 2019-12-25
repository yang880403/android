package com.stepone.component.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stepone.navigator.INavigatorPage;
import com.stepone.navigator.NavigatorStack;
import com.stepone.navigator.RouterMap;

import java.util.Set;

/**
 * FileName: STFragmentDispatcherActivity
 * Author: y.liang
 * Date: 2019-12-05 14:03
 */

public abstract class STFragmentDispatcherActivity extends STActivity implements INavigatorPage {
    private final static String K_CONTENT_FRAGMENT = "K_CONTENT_FRAGMENT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String action = intent.getAction();
        Set<String> categorys = intent.getCategories();
        int routerId = intent.getIntExtra(RouterMap.MetaRouter.KEY_ROUTER_ID_INT, 0);
        NavigatorStack.onPageCreate(routerId, this);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(K_CONTENT_FRAGMENT);
        if (fragment == null) {
            //APP启动分发
            if (Intent.ACTION_MAIN.equals(action) && categorys != null && categorys.contains(Intent.CATEGORY_LAUNCHER)) {
                setContentFragment(onDispatchLauncherFragment(), K_CONTENT_FRAGMENT);
            } else {//普通分发
                setContentFragment(onDispatchNormalFragment(), K_CONTENT_FRAGMENT);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NavigatorStack.onPageDestroy(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean isPageDestroyed() {
        return isFinishing() || isDestroyed();
    }

    //分发启动页面
    abstract protected Fragment onDispatchLauncherFragment();

    //分发普通页面
    abstract protected Fragment onDispatchNormalFragment();
}
