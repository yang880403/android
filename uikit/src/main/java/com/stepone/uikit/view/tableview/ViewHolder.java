package com.stepone.uikit.view.tableview;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.util.WeakHashMap;

/**
 * FileName: ViewHolder
 * Author: shiliang
 * Date: 2019-12-14 08:59
 */
public class ViewHolder {
    private View mView;
    private SparseArray<View> mViewCache = new SparseArray<>();

    protected ViewHolder(@NonNull View view) {
        mView = view;
    }

    interface IViewDisplayer {
        /**
         * 可在此处进行UI初始化及事件绑定等操作，每个view只会执行一次，复用时将不再执行
         */
        void onViewInitialize(@NonNull View view, @NonNull ViewModel viewModel);

        /**
         * UI展示
         */
        void onViewDisplay(@NonNull View view, @NonNull ViewModel viewModel);
    }

    static class Factory {
        private static final WeakHashMap<View, ViewHolder> mViewHolderMap = new WeakHashMap<>(50);

        static ViewHolder get(View view) {
            return mViewHolderMap.get(view);
        }

        static void put(View view, ViewHolder viewHolder) {
            mViewHolderMap.put(view, viewHolder);
        }
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
