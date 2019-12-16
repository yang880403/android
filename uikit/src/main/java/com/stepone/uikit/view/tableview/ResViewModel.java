package com.stepone.uikit.view.tableview;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * FileName: ResViewModel
 * Author: y.liang
 * Date: 2019-12-13 17:46
 */

public abstract class ResViewModel<D, VH extends ViewHolder> extends ViewModel<D> implements ViewHolder.IViewDisplayer {

    @LayoutRes
    private  int layoutId;

    public ResViewModel(@LayoutRes int resId) {
        layoutId = resId;
    }

    @Override
    Class getViewClazz() {
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

    protected abstract void onInitializeView(@NonNull VH holder);
    protected abstract void onDisplayView(@NonNull VH holder);

    @Override
    public final void onViewInitialize(@NonNull View view, @NonNull ViewModel viewModel) {
        VH viewHolder = onCreateViewHolder(view);
        ViewHolder.Factory.put(view, viewHolder);
        onInitializeView(viewHolder);
    }

    @Override
    public final void onViewDisplay(@NonNull View view, @NonNull ViewModel viewModel) {
        @SuppressWarnings("unchecked")
        VH viewHolder = (VH) ViewHolder.Factory.get(view);
        if (viewHolder == null) {
            throw new NullPointerException("view holder is null");
        }

        onDisplayView(viewHolder);
    }
}
