package com.ysl.stepone.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.stepone.uikit.view.AbsSTActivity;
import com.stepone.component.navigator.Navigator;
import com.ysl.stepone.R;

/**
 * FileName: FirstActivity
 * Author: shiliang
 * Date: 2019-12-02 21:33
 */
public class FirstActivity extends AbsSTActivity {
    View bottomView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPageTitle("first page");
        setPageTitleColor(Color.YELLOW);

        setAppbarBackgroundColor(Color.BLUE, Color.RED);

        setContentView(R.layout.activity_splash);

        bottomView = findViewById(R.id.textview);
        if (bottomView != null) {
            bottomView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigator.startOpenPath("splash").from(FirstActivity.this).call();
                }
            });
        }

    }
}
