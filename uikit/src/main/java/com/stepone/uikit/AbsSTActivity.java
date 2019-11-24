package com.stepone.uikit;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * FileName: AbsSTActivity
 * Author: shiliang
 * Date: 2019-11-24 18:00
 */
public class AbsSTActivity extends AppCompatActivity {
    ViewGroup mContentParent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.STAppTheme);
        super.setContentView(R.layout.abs_st_activity);

        Toolbar toolbar = findViewById(R.id.abs_st_toolbar);
        toolbar.setBackgroundColor(Color.GREEN);
        setActionBar(toolbar);
        mContentParent = findViewById(R.id.abs_st_content);
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

    

}
