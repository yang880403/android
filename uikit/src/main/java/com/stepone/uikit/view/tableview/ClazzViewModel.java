package com.stepone.uikit.view.tableview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * FileName: ClazzViewModel
 * Author: y.liang
 * Date: 2019-12-13 17:46
 */

public class ClazzViewModel<D> extends ViewModel<D> {
    @NonNull
    private Class<? extends ViewCell> viewClazz;

    public ClazzViewModel(@NonNull Class<? extends ViewCell> cls) {
        viewClazz = cls;
    }

    @Override
    @NonNull
    Class getViewClazz() {
        return viewClazz;
    }

    @Override
    @LayoutRes
    int getLayoutResource() {
        return 0;
    }


    /**
     * ViewCell需要承担起ViewHolder的职责，缓存subview
     */
    public static abstract class ViewCell<VM extends ClazzViewModel> extends FrameLayout implements ViewHolder.IViewDisplayer {
        private VM mViewModel;

        public ViewCell(@NonNull Context context) {
            super(context);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(params);
        }

        @Nullable
        protected VM getViewModel() {
            return mViewModel;
        }

        /**
         * UI初始化方法，只会执行一次
         */
        public abstract void onInitialize(@NonNull VM viewModel);

        public abstract void onDisplay(@NonNull VM viewModel);

        @Override
        @SuppressWarnings("unchecked")
        public final void onViewInitialize(@NonNull View view, @NonNull ViewModel viewModel) {
            mViewModel = (VM) viewModel;
            onInitialize(mViewModel);
        }

        @Override
        @SuppressWarnings("unchecked")
        public final void onViewDisplay(@NonNull View view, @NonNull ViewModel viewModel) {
            mViewModel = (VM) viewModel;
            onDisplay(mViewModel);
        }
    }
}
