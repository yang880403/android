package com.stepone.uikit;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * FileName: AppBar
 * Author: y.liang
 * Date: 2019-11-25 16:53
 */

public class AppBar extends RelativeLayout {
    private ImageView leftView;
    private TextView titleView;


    public AppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.st_appbar, this);
        leftView = findViewById(R.id.st_appbar_leftview);
        titleView = findViewById(R.id.st_appbar_titleview);

//        TypedArray attributes = context.obtainStyledAttributes(attrs)
    }
}
