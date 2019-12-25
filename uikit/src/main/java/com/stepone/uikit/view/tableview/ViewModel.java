package com.stepone.uikit.view.tableview;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
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

public abstract class ViewModel<D> {
    public static final int UNSPECIFIC = -1;
    private D payload;

    //divider相关
    public int bottomDividerLeftInset;
    public int bottomDividerRightInset;
    public int bottomDividerHieght;
    public int rightDividerTopInset;
    public int rightDividerBottomInset;
    public int rightDividerWidth;

    public Drawable bottomDivider;
    public Drawable rightDivider;

    /*
    * 跨度值，便于自动填充，目前只在GridLayout中起作用
    * */
    private int spanSize = 1;//最小值为1
    private int minSpanSize = UNSPECIFIC;
    private int maxSpanSize = UNSPECIFIC;
    private boolean isFullSpan = false;//占据整行，如果剩余span小于整行，则换行
    private boolean useAutoAverageSpace = true;//是否使用自动均分行间距功能

    //UI的位置发生变化后，需要重新计算，特别是网格类布局
    int layoutSpanSize;//UI布局时，实际采用的spanSize，对用户只读，用户可据此进行UI适配
    int spanIndex = UNSPECIFIC; //缓存spanIndex
    int spanGroupIndex = UNSPECIFIC; //缓存spanGroupIndex
    private boolean isPositionChanged = false;

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

    /**
     * span 相关
     */
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

    public boolean isFullSpan() {
        return isFullSpan;
    }

    public boolean useAutoAverageSpace() {
        return useAutoAverageSpace;
    }

    private void clearSpanIndexCache() {
        spanGroupIndex = UNSPECIFIC;
        spanIndex = UNSPECIFIC;
    }

    void notifyPositionChanged() {
        isPositionChanged = true;
        clearSpanIndexCache();
    }

    void fixPosition() {
        isPositionChanged = false;
    }

    boolean isPositionChanged() {
        return isPositionChanged;
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

    public void setFullSpan(boolean fullSpan) {
        isFullSpan = fullSpan;
    }

    public void setUseAutoAverageSpace(boolean useAutoAverageSpace) {
        this.useAutoAverageSpace = useAutoAverageSpace;
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

    public boolean isSame(ViewModel viewModel) {
        return equals(viewModel);
    }

    public boolean isContentSame(ViewModel viewModel) {
        return hashCode() == viewModel.hashCode();
    }

    public interface OnClickListener {
        void onClick(View view, ViewModel viewModel);
    }

    public interface OnLongClickListener {
        boolean onLongClick(View view, ViewModel viewModel);
    }
}
