package com.stepone.uikit.view.tableview;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * FileName: ClazzViewModel
 * Author: y.liang
 * Date: 2019-12-13 17:46
 */

public class ClazzViewModel<E, V extends ContentView> extends ViewModel<E, V> {
    private Class<V> viewClazz;

    public ClazzViewModel(Class<V> cls, E model) {
        super(model);
        viewClazz = cls;
    }

    @Override
    Class<V> getViewClazz() {
        return viewClazz;
    }

    @Override
    @LayoutRes
    int getLayoutResource() {
        return 0;
    }

    @Override
    void onBindView(@NonNull View view) {

    }
}
