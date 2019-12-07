package com.ysl.stepone.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.stepone.component.common.ActivityHooker;
import com.stepone.component.navigator.Navigator;
import com.ysl.stepone.R;

/**
 * FileName: FirstActivity
 * Author: shiliang
 * Date: 2019-12-02 21:33
 */
public class FirstActivity extends BaseActivity {
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
                    Navigator.startOpenPath("second").from(FirstActivity.this).pushForResult(new ActivityHooker.OnActivityResultCallback() {
                        @Override
                        public void onActivityResult(int resultCode, Intent data) {
                            if (data != null) {
                                String str = data.getStringExtra("title");
                                if (str != null) {
                                    setPageTitle(str);
                                }
                            }
                        }
                    });
                }
            });
        }

    }
}
