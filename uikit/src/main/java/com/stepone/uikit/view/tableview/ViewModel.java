package com.stepone.uikit.view.tableview;

import android.util.SparseArray;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * FileName: ViewModel
 * Author: y.liang
 * Date: 2019-12-13 17:45
 */

abstract class ViewModel<D> implements IViewModel {
    private D payload;
    private OnClickListener itemClickListener;
    private OnLongClickListener itemLongClickListener;
    private SparseArray<OnClickListener> mSubViewClickListeners = new SparseArray<>();
    private SparseArray<OnLongClickListener> mSubViewLongClickListeners = new SparseArray<>();

    @Nullable
    public D getPayload() {
        return payload;
    }

    public void setPayload(D payload) {
        this.payload = payload;
    }

    public void setItemClickListener(OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(OnLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public void setClickListener(@IdRes int viewId, OnClickListener listener) {
        mSubViewClickListeners.put(viewId, listener);
    }

    public void setLongClickListener(@IdRes int viewId, OnLongClickListener listener) {
        mSubViewLongClickListeners.put(viewId, listener);
    }

    OnClickListener getItemClickListener() {
        return itemClickListener;
    }

    OnLongClickListener getItemLongClickListener() {
        return itemLongClickListener;
    }

    @NonNull
    SparseArray<OnClickListener> getClickListeners() {
        return mSubViewClickListeners.clone();
    }

    @NonNull
    SparseArray<OnLongClickListener> getLongClickListeners() {
        return mSubViewLongClickListeners.clone();
    }

    abstract Class getViewClazz();

    @LayoutRes
    abstract int getLayoutResource();
}
