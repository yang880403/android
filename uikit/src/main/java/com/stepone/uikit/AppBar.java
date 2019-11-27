package com.stepone.uikit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.STAppBar);
        titleView.setText(attributes.getString(R.styleable.STAppBar_stTitle));
        titleView.setTextColor(attributes.getColor(R.styleable.STAppBar_stTitleColor, Color.BLACK));
//        titleView.setTextSize(attributes.getDimension(R.styleable.STAppBar_stTitleSize, 17));

        attributes.recycle();
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void setTitleColor(int color) {
        titleView.setTextColor(color);
    }
}
