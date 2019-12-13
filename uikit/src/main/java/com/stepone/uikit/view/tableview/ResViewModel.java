package com.stepone.uikit.view.tableview;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * FileName: ResViewModel
 * Author: y.liang
 * Date: 2019-12-13 17:46
 */

public abstract class ResViewModel<E,  VH extends ResViewModel.ViewHolder> extends ViewModel<ContentView, E> {

    private @LayoutRes int layoutId;
    private VH viewHolder;

    public ResViewModel(@LayoutRes int resId, E model) {
        super(model);
        layoutId = resId;
    }

    @Override
    Class<ContentView> getViewClazz() {
        return null;
    }

    @Override
    @LayoutRes
    int getLayoutResource() {
        return layoutId;
    }

    @Override
    void onBindView(@NonNull View view) {
        if (viewHolder == null) {
            viewHolder = onCreateViewHolder(view);
        }

        onBindViewHolder(viewHolder);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    protected VH onCreateViewHolder(View view) {
        return (VH) new ViewHolder(view);
    }

    protected abstract void onBindViewHolder(VH holder);

    public static class ViewHolder {
        private View mView;
        private SparseArray<View> mViewCache = new SparseArray<>();

        protected ViewHolder(@NonNull View view) {
            mView = view;
        }

        public View itemView() {
            return mView;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(@IdRes int viewId) {
            View view = mViewCache.get(viewId);
            if (view == null) {
                view = mView.findViewById(viewId);
                mViewCache.put(viewId, view);
            }

            return (T) view;
        }

        public ViewHolder setText(@IdRes int viewId, CharSequence text) {
            TextView textView = getView(viewId);
            textView.setText(text);

            return this;
        }

        public ViewHolder setText(@IdRes int viewId, @StringRes int strId) {
            TextView textView = getView(viewId);
            textView.setText(strId);

            return this;
        }
    }
}
