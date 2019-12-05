package com.stepone.uikit.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Set;

/**
 * FileName: STFragmentDispatcherActivity
 * Author: y.liang
 * Date: 2019-12-05 14:03
 */

public abstract class STFragmentDispatcherActivity extends STActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String action = intent.getAction();
        Set<String> categorys = intent.getCategories();

        if (Intent.ACTION_MAIN.equals(action) && categorys != null && categorys.contains(Intent.CATEGORY_LAUNCHER)) {//APP启动分发
            setContentFragment(onDispatchLauncherFragment());
        } else {//普通分发
            setContentFragment(onDispatchNormalFragment());
        }
    }

    abstract protected Fragment onDispatchLauncherFragment();

    abstract protected Fragment onDispatchNormalFragment();
}
