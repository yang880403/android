package com.stepone.component.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stepone.component.navigator.INavigatorPage;
import com.stepone.component.navigator.NavigatorStack;
import com.stepone.component.navigator.RouterMap;

import java.util.Set;

/**
 * FileName: STFragmentDispatcherActivity
 * Author: y.liang
 * Date: 2019-12-05 14:03
 */

public abstract class STFragmentDispatcherActivity extends STActivity implements INavigatorPage {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String action = intent.getAction();
        Set<String> categorys = intent.getCategories();
        int routerId = intent.getIntExtra(RouterMap.MetaRouter.KEY_ROUTER_ID_INT, 0);
        NavigatorStack.onPageCreate(routerId, this);

        //APP启动分发
        if (Intent.ACTION_MAIN.equals(action) && categorys != null && categorys.contains(Intent.CATEGORY_LAUNCHER)) {
            setContentFragment(onDispatchLauncherFragment());
        } else {//普通分发
            setContentFragment(onDispatchNormalFragment());
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

    abstract protected Fragment onDispatchLauncherFragment();

    abstract protected Fragment onDispatchNormalFragment();
}
