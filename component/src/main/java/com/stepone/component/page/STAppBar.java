package com.stepone.component.page;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stepone.component.R;
import com.stepone.uikit.view.utils.DisplayUtils;


/**
 * FileName: AppBar
 * Author: y.liang
 * Date: 2019-11-25 16:53
 */

public class STAppBar extends RelativeLayout {
    private ImageView leftView;
    private TextView titleView;


    public STAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.st_component_appbar, this);
        leftView = findViewById(R.id.st_component_appbar_leftview);
        titleView = findViewById(R.id.st_component_appbar_titleview);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.STAppBar);
        titleView.setText(attributes.getString(R.styleable.STAppBar_stTitle));
        titleView.setTextColor(attributes.getColor(R.styleable.STAppBar_stTitleColor, Color.BLACK));
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, attributes.getDimension(R.styleable.STAppBar_stTitleSize, DisplayUtils.sp2px(context, 17)));

        attributes.recycle();
    }


    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void setTitleColor(int color) {
        titleView.setTextColor(color);
    }
}
