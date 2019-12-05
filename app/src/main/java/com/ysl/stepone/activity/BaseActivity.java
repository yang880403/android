package com.ysl.stepone.activity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stepone.component.navigator.ViewDispatcher;
import com.stepone.uikit.view.STFragmentDispatcherActivity;
import com.ysl.stepone.fragment.SplashFragment;

/**
 * FileName: BaseActivity
 * Author: y.liang
 * Date: 2019-12-05 10:57
 */

public class BaseActivity extends STFragmentDispatcherActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setAppbarBackgroundColor(Color.WHITE);
        setDarkModeStatusBar();
    }

    @Override
    protected Fragment onDispatchLauncherFragment() {
        return new SplashFragment();
    }

    @Override
    protected Fragment onDispatchNormalFragment() {
        return ViewDispatcher.resolveFragment(getIntent());
    }


}
