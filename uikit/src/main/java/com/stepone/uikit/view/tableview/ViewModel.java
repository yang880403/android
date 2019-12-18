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
    public static final int UNSPECIFIC = -1;
    private D payload;

    /*
    * 跨度值，便于自动填充，目前只在GridLayout中起作用
    * */
    private int spanSize = 1;//最小值为1
    private int minSpanSize = UNSPECIFIC;
    private int maxSpanSize = UNSPECIFIC;
    int layoutSpanSize;//UI布局时，实际采用的spanSize，对用户只读，用户可据此进行UI适配
    int spanIndex = UNSPECIFIC; //缓存spanIndex
    int spanGroupIndex = UNSPECIFIC; //缓存spanGroupIndex

    /*设置事件监听器*/
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

    //最小值为1
    public int getSpanSize() {
        return spanSize > 1 ? spanSize : 1;
    }

    public int getMinSpanSize() {
        if (canZoomOut()) {
            return minSpanSize;
        }

        return getSpanSize();
    }

    public int getMaxSpanSize() {
        if (canZoomIn()) {
            return maxSpanSize;
        }
        return getSpanSize();
    }

    //放大
    public boolean canZoomIn() {
        return maxSpanSize > 0 && maxSpanSize > getSpanSize();
    }

    //缩小
    public boolean canZoomOut() {
        return minSpanSize > 0 && minSpanSize < getSpanSize();
    }

    public int getLayoutSpanSize() {
        return layoutSpanSize;
    }

    public int getSpanIndex() {
        return spanIndex;
    }

    public int getSpanGroupIndex() {
        return spanGroupIndex;
    }

    void clearSpanIndexCache() {
        spanGroupIndex = UNSPECIFIC;
        spanIndex = UNSPECIFIC;
    }

    public void setSpanSize(int size) {
        spanSize = size;
    }

    public void setMinSpanSize(int size) {
        minSpanSize = size;
    }

    public void setMaxSpanSize(int size) {
        maxSpanSize = size;
    }

    /*Click Listener*/
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
