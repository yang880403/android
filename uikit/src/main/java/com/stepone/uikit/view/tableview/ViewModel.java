package com.stepone.uikit.view.tableview;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * FileName: ViewModel
 * Author: y.liang
 * Date: 2019-12-13 17:45
 */

abstract class ViewModel<E, V extends ContentView> {
    private E data;

    ViewModel(E model) {
        data = model;
    }

    @Nullable
    public E getData() {
        return data;
    }

    abstract Class<V> getViewClazz();

    @LayoutRes
    abstract int getLayoutResource();

    abstract void onBindView(@NonNull View view);
}
