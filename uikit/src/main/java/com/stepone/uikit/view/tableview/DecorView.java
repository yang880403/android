package com.stepone.uikit.view.tableview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import java.lang.reflect.Constructor;

/**
 * FileName: CellView
 * Author: y.liang
 * Date: 2019-12-13 14:06
 */

final class DecorView extends FrameLayout {
    private ViewModel mViewModel;
    private View mContentView;
    private boolean isPrepared = false;

    public DecorView(@NonNull Context context) {
        super(context);
        LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
    }

    /**
     * 初始化操作，优先根据resource id 装饰content view
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
            mContentView = LayoutInflater.from(getContext()).inflate(layoutResource, this);
            return;
        }

        Class clz = mViewModel.getViewClazz();
        if (clz != null && ContentView.class.isAssignableFrom(clz)) {
            try {
                Constructor constructor = clz.getDeclaredConstructor(Context.class);
                constructor.setAccessible(true);
                ContentView contentView = (ContentView) constructor.newInstance(getContext());
                contentView.onInitialize(mViewModel);
                addView(contentView);
                mContentView = contentView;
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public void onDisplay(@NonNull ViewModel viewModel) {
        mViewModel = viewModel;

        if (mContentView != null) {
            if (mContentView instanceof ContentView) {
                ((ContentView)mContentView).onDisplay(viewModel);
            } else {
                viewModel.onBindView(mContentView);
            }
        }
    }
}
