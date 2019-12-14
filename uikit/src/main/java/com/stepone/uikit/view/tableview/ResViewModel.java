package com.stepone.uikit.view.tableview;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * FileName: ResViewModel
 * Author: y.liang
 * Date: 2019-12-13 17:46
 */

public abstract class ResViewModel<D, VH extends ViewHolder> extends ViewModel<ViewCell, D> implements ViewHolder.IViewDisplayer {

    @LayoutRes
    private  int layoutId;
    private VH viewHolder;

    public ResViewModel(@LayoutRes int resId) {
        layoutId = resId;
    }

    @Override
    Class<ViewCell> getViewClazz() {
        return null;
    }

    @Override
    @LayoutRes
    int getLayoutResource() {
        return layoutId;
    }

    /**
     * 子类可通过重写此方法定制VH
     */
    @NonNull
    @SuppressWarnings("unchecked")
    protected VH onCreateViewHolder(View view) {
        return (VH) new ViewHolder(view);
    }

    protected abstract void onBindView(@NonNull VH holder);
    protected abstract void onDisplayView(@NonNull VH holder);

    @Override
    public final void onViewInitialize(@NonNull View view, @NonNull ViewModel viewModel) {
        if (viewHolder == null) {
            viewHolder = onCreateViewHolder(view);
        }

        onBindView(viewHolder);
    }

    @Override
    public final void onViewDisplay(@NonNull View view, @NonNull ViewModel viewModel) {
        if (viewHolder != null) {
            onDisplayView(viewHolder);
        }
    }
}
