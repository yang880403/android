package com.stepone.uikit.view.tableview;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

/**
 * FileName: CellView
 * Author: y.liang
 * Date: 2019-12-13 17:50
 */

public abstract class ContentView<T extends ViewModel> extends FrameLayout {
    private T mViewModel;

    public ContentView(@NonNull Context context) {
        super(context);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
    }

    public T getViewModel() {
        return mViewModel;
    }

    public void onInitialize(@NonNull T viewModel) {
        mViewModel = viewModel;
    }

    public abstract void onDisplay(T viewModel);
}
