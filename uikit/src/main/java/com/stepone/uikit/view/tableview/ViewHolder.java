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
    private View mView;
    private SparseArray<View> mViewCache = new SparseArray<>();

    interface IViewDisplayer {
        void onViewInitialize(@NonNull View view, @NonNull ViewModel viewModel);
        void onViewDisplay(@NonNull View view, @NonNull ViewModel viewModel);
    }

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

    public ViewHolder setOnClickListener(@IdRes int viewId, final ViewModel.OnClickListener listener, final ViewModel viewModel) {
        if (listener != null) {
            View view = getView(viewId);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, viewModel);
                }
            });
        }

        return this;
    }

    public ViewHolder setItemClickListener(final ViewModel.OnClickListener listener, final ViewModel viewModel) {
        if (listener != null) {
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(mView, viewModel);
                }
            });
        }

        return this;
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
