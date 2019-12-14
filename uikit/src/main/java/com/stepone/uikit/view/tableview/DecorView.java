package com.stepone.uikit.view.tableview;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stepone.uikit.view.utils.DisplayUtils;

import java.lang.reflect.Constructor;

/**
 * FileName: CellView
 * Author: y.liang
 * Date: 2019-12-13 14:06
 */

final class DecorView extends FrameLayout {
    private ViewModel mViewModel;

    @Nullable
    private View mContentView;
    private boolean isPrepared = false;

    public DecorView(@NonNull Context context) {
        super(context);
        LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);

        /*Warning: is Decor Cell View*/
        TextView textView = new TextView(context);
        textView.setText("Warning: is Decor Cell View");
        textView.setTextSize(14);
        textView.setTextColor(Color.RED);
        textView.setWidth(-1);
        int padding = DisplayUtils.dp2px(context, 10);
        textView.setPadding(0, padding, 0, padding);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.WHITE);
        addView(textView);
    }

    /**
     * 初始化操作，只会执行一次，优先根据resource id 装饰content view
     * 如果没有resource id ,则根据view model中的ViewClass来装饰content view
     */
    @SuppressWarnings("unchecked")
    public void onPrepare(@NonNull ViewModel viewModel) {
        if (isPrepared) {
            return;
        }

        isPrepared = true;
        mViewModel = viewModel;

        int layoutResource = mViewModel.getLayoutResource();
        if (layoutResource > 0) {
            removeAllViews();
            mContentView = LayoutInflater.from(getContext()).inflate(layoutResource, this);

            if (mViewModel instanceof ViewHolder.IViewDisplayer) {
                ViewHolder.IViewDisplayer displayer = (ViewHolder.IViewDisplayer) mViewModel;
                displayer.onViewInitialize(mContentView, mViewModel);
            }
            return;
        }

        Class clz = mViewModel.getViewClazz();
        if (clz != null && ViewCell.class.isAssignableFrom(clz)) {
            try {
                Constructor constructor = clz.getDeclaredConstructor(Context.class);
                constructor.setAccessible(true);
                mContentView = (ViewCell) constructor.newInstance(getContext());
                removeAllViews();
                addView(mContentView);

                if (mContentView instanceof ViewHolder.IViewDisplayer) {
                    ViewHolder.IViewDisplayer displayer = (ViewHolder.IViewDisplayer) mContentView;
                    displayer.onViewInitialize(mContentView, mViewModel);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void onDisplay(@NonNull ViewModel viewModel) {
        mViewModel = viewModel;

        if (mContentView != null) {
            ViewHolder.IViewDisplayer displayer = null;
            if (mContentView instanceof ViewCell) {
                displayer = (ViewHolder.IViewDisplayer) mContentView;
            } else if (mViewModel instanceof ViewHolder.IViewDisplayer){
                displayer = (ViewHolder.IViewDisplayer) mViewModel;
            }

            if (displayer != null) {
                displayer.onViewDisplay(mContentView, mViewModel);
            }
        }
    }
}
