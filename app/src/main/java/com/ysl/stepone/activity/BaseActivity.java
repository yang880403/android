package com.ysl.stepone.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stepone.navigator.Navigator;
import com.stepone.navigator.ViewDispatcher;
import com.stepone.navigator.request.Request;
import com.stepone.component.page.STFragmentDispatcherActivity;
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

    @Override
    public void onBackPressed() {
        Navigator.startGoBack().backRequest().back(new Request.Callback() {
            @Override
            public void onFailure() {

            }

            @Override
            public void onSucceed() {
                Toast.makeText(BaseActivity.this, "back", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
