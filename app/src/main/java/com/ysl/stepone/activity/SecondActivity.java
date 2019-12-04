package com.ysl.stepone.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.stepone.component.common.ActivityHooker;
import com.stepone.component.navigator.Navigator;
import com.stepone.uikit.view.AbsSTActivity;
import com.ysl.stepone.R;

/**
 * FileName: SecondActivity
 * Author: y.liang
 * Date: 2019-12-04 18:21
 */

public class SecondActivity extends AbsSTActivity {

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
                    Intent intent = new Intent();
                    intent.putExtra("title", "123");
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
            });
        }

    }
}
