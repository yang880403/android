package com.stepone.uikit.view.tableview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

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
     * 初始化操作，优先根据res id 装饰content view
     */
    public void onPrepare(ViewModel viewModel) {
        if (isPrepared) {
            return;
        }

        isPrepared = true;
        mViewModel = viewModel;
        if (mViewModel != null) {
            int resId = mViewModel.getViewResourseId();
            if (resId != 0) {
                mContentView = LayoutInflater.from(getContext()).inflate(resId, this);
                return;
            }

            Class clz = mViewModel.getViewClazz();
            if (clz != null && ContentView.class.isAssignableFrom(clz)) {
                try {
                    mContentView = (ContentView) clz.newInstance();
                    ((ContentView) mContentView).onPrepare(mViewModel);
                    addView(mContentView);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }

    public void onDisplay(ViewModel viewModel) {
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
