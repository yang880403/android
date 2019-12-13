package com.stepone.uikit.view.tableview;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

/**
 * FileName: CellView
 * Author: y.liang
 * Date: 2019-12-13 17:50
 */

public abstract class ContentView extends FrameLayout {
    private ViewModel mViewModel;

    public ContentView(@NonNull Context context) {
        super(context);
    }

    public void onInitialize(@NonNull ViewModel viewModel) {
        mViewModel = viewModel;
    }

    public abstract void onDisplay(ViewModel viewModel);
}
