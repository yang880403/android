package com.stepone.uikit.view.tableview;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

/**
 * FileName: ViewModel
 * Author: y.liang
 * Date: 2019-12-13 17:45
 */

public abstract class ViewModel<V extends ViewCell, D> {
    private D payload;
    private OnClickListener itemClickListener;
    private OnLongClickListener itemLongClickListener;
    private SparseArray<OnClickListener> mSubViewClickListeners = new SparseArray<>();
    private SparseArray<OnLongClickListener> mSubViewLongClickListeners = new SparseArray<>();

    public interface OnClickListener {
        void onClick(View view, ViewModel viewModel);
    }

    public interface OnLongClickListener {
        void onLongClick(View view, ViewModel viewModel);
    }

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

    public OnClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemLongClickListener(OnLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public OnLongClickListener getItemLongClickListener() {
        return itemLongClickListener;
    }

    public void setClickListener(@IdRes int viewId, OnClickListener listener) {
        mSubViewClickListeners.put(viewId, listener);
    }

    public OnClickListener getClickListener(@IdRes int viewId) {
        return mSubViewClickListeners.get(viewId);
    }

    public void setLongClickListener(@IdRes int viewId, OnLongClickListener listener) {
        mSubViewLongClickListeners.put(viewId, listener);
    }

    public OnLongClickListener getLongClickListener(@IdRes int viewId) {
        return mSubViewLongClickListeners.get(viewId);
    }

    abstract Class<V> getViewClazz();

    @LayoutRes
    abstract int getLayoutResource();
}
