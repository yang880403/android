package com.stepone.uikit.view.tableview;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * FileName: ClazzViewModel
 * Author: y.liang
 * Date: 2019-12-13 17:46
 */

public class ClazzViewModel<V extends ViewCell, D> extends ViewModel<V, D> {
    @NonNull
    private Class<V> viewClazz;

    public ClazzViewModel(@NonNull Class<V> cls) {
        viewClazz = cls;
    }

    @Override
    @NonNull
    Class<V> getViewClazz() {
        return viewClazz;
    }

    @Override
    @LayoutRes
    int getLayoutResource() {
        return 0;
    }
}
