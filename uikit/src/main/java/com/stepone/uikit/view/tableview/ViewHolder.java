package com.stepone.uikit.view.tableview;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * FileName: ViewHolder
 * Author: shiliang
 * Date: 2019-12-14 08:59
 */
public class ViewHolder {

    interface IViewDisplayer<VM extends ViewModel> {
        void onViewInitialize(@NonNull View view, @NonNull VM viewModel);
        void onViewDisplay(@NonNull View view, @NonNull VM viewModel);
    }

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
