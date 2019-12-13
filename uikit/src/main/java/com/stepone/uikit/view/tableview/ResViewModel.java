package com.stepone.uikit.view.tableview;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * FileName: ResViewModel
 * Author: y.liang
 * Date: 2019-12-13 17:46
 */

public abstract class ResViewModel<E,  VH extends ResViewModel.ViewHolder> extends ViewModel<E, ContentView> {
    private int viewResourceId;
    private ViewHolder viewHolder;

    public ResViewModel(int resId, E model) {
        super(model);
        viewResourceId = resId;
    }

    @Override
    Class<ContentView> getViewClazz() {
        return null;
    }

    @Override
    int getViewResourseId() {
        return viewResourceId;
    }

    @Override
    void onBindView(@NonNull View view) {
        if (viewHolder == null) {
            viewHolder = new ViewHolder(view);
        }

        onBindViewHolder((VH)viewHolder);
    }

    protected abstract void onBindViewHolder(VH holder);

    public static class ViewHolder {
        private View mView;

        protected ViewHolder(@NonNull View view) {
            mView = view;
        }

        public View get(int resId) {
            return mView.findViewById(resId);
        }
    }
}
