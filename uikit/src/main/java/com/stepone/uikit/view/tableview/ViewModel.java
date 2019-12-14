package com.stepone.uikit.view.tableview;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * FileName: ViewModel
 * Author: y.liang
 * Date: 2019-12-13 17:45
 */

abstract class ViewModel<V extends ViewCell, D> {
    private D payload;
    private View.OnClickListener itemClickListener;
    private View.OnLongClickListener itemLongClickListener;
    private Set<Integer> subViewClickListeners = new HashSet<>();
    private Set<Integer> subViewLongClickListeners = new HashSet<>();

    @Nullable
    public D getPayload() {
        return payload;
    }

    public void setPayload(D payload) {
        this.payload = payload;
    }

    public void setItemClickListener(View.OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(View.OnLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public View.OnClickListener getItemClickListener() {
        return itemClickListener;
    }

    public View.OnLongClickListener getItemLongClickListener() {
        return itemLongClickListener;
    }



    abstract Class<V> getViewClazz();

    @LayoutRes
    abstract int getLayoutResource();
}
