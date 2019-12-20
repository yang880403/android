package com.stepone.uikit.view.tableview;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stepone.uikit.view.utils.DisplayUtils;

import java.lang.reflect.Constructor;

/**
 * FileName: CellView
 * Author: y.liang
 * Date: 2019-12-13 14:06
 */

/**
 * 装饰模式，使用者通过填充contentView来定制UI
 */
final class DecorView extends FrameLayout {
    private ViewModel mViewModel;

    @Nullable
    private View mContentView;
    private boolean isInitialized = false;

    public DecorView(@NonNull Context context) {
        super(context);
        LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);

        /*Warning: is Decor Cell View*/
        TextView textView = new TextView(context);
        textView.setText("Warning: is Decor Cell View");
        textView.setTextSize(14);
        textView.setTextColor(Color.RED);
        textView.setWidth(-1);
        int padding = DisplayUtils.dp2px(context, 10);
        textView.setPadding(0, padding, 0, padding);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.WHITE);
        addView(textView);
    }

    /**
     * UI初始化操作，只会执行一次，当出现UI复用的时候，不再执行此方法
     * 优先根据layout id 装饰 content view
     * 如果layoutID无效,则根据view model中的ViewClass来装饰content view
     * 如果没有view class，则直接向用户显示 DecorView
     */
    public void onInitialize(@NonNull ViewModel viewModel) {
        if (isInitialized) {
            return;
        }

        isInitialized = true;
        mViewModel = viewModel;

        int layoutResource = mViewModel.getLayoutResource();
        if (layoutResource > 0) {
            removeAllViews();
            mContentView = LayoutInflater.from(getContext()).inflate(layoutResource, this);

            if (mViewModel instanceof ViewHolder.IViewDisplayer) {
                ViewHolder.IViewDisplayer displayer = (ViewHolder.IViewDisplayer) mViewModel;
                displayer.onViewInitialize(mContentView, mViewModel);

                //事件绑定
                autoBindViewListener();
            }
            return;
        }

        Class clz = mViewModel.getViewClazz();
        if (clz != null && ClazzViewModel.ViewCell.class.isAssignableFrom(clz)) {
            try {
                @SuppressWarnings("unchecked")
                Constructor constructor = clz.getDeclaredConstructor(Context.class);
                constructor.setAccessible(true);
                mContentView = (ClazzViewModel.ViewCell) constructor.newInstance(getContext());
                removeAllViews();
                addView(mContentView);

                if (mContentView instanceof ViewHolder.IViewDisplayer) {
                    ViewHolder.IViewDisplayer displayer = (ViewHolder.IViewDisplayer) mContentView;
                    displayer.onViewInitialize(mContentView, mViewModel);

                    //事件绑定
                    autoBindViewListener();
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }


    public void onDisplay(@NonNull ViewModel viewModel) {
        mViewModel = viewModel;

        if (mContentView != null) {
            ViewHolder.IViewDisplayer displayer = null;
            if (mContentView instanceof ClazzViewModel.ViewCell) {
                displayer = (ViewHolder.IViewDisplayer) mContentView;
            } else if (mViewModel instanceof ViewHolder.IViewDisplayer){
                displayer = (ViewHolder.IViewDisplayer) mViewModel;
            }

            if (displayer != null) {
                displayer.onViewWillDisplay(mContentView, mViewModel);
                displayer.onViewDisplay(mContentView, mViewModel);
            }
        }
    }

    /**
     * content view 初始化完成之后，自动将viewmodel中记录的listener绑定到对应的view上
     * 为确保让复用后的view也能正常的相应事件，需要将事件监听器设置为全局对象，而不能设置为匿名内部类对象
     */
    private final OnClickListener itemClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewModel.OnClickListener click = mViewModel.getItemClickListener();
            if (click != null) {
                click.onClick(v, mViewModel);
            }
        }
    };

    private final OnLongClickListener itemLongClick = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ViewModel.OnLongClickListener longClick = mViewModel.getItemLongClickListener();
            if (longClick == null) {
                return false;
            }
            return longClick.onLongClick(v, mViewModel);
        }
    };

    private final OnClickListener viewClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            @SuppressWarnings("unchecked")
            SparseArray<ViewModel.OnClickListener> clicks = mViewModel.getClickListeners();
            ViewModel.OnClickListener click = clicks.get(v.getId());
            if (click != null) {
                click.onClick(v, mViewModel);
            }
        }
    };

    private final OnLongClickListener viewLongClick = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            @SuppressWarnings("unchecked")
            SparseArray<ViewModel.OnLongClickListener> longClicks = mViewModel.getLongClickListeners();
            ViewModel.OnLongClickListener longClick = longClicks.get(v.getId());
            if (longClick != null) {
                return longClick.onLongClick(v, mViewModel);
            }
            return false;
        }
    };

    private void autoBindViewListener() {
        if (mContentView == null || mViewModel == null) {
            return;
        }

        mContentView.setOnClickListener(itemClick);
        mContentView.setOnLongClickListener(itemLongClick);

        @SuppressWarnings("unchecked")
        SparseArray<ViewModel.OnClickListener> clicks = mViewModel.getClickListeners();
        for (int i = 0; i < clicks.size(); i++) {
            int viewId = clicks.keyAt(i);
            View view = mContentView.findViewById(viewId);
            if (view != null) {
                view.setOnClickListener(viewClick);
            }
        }

        @SuppressWarnings("unchecked")
        SparseArray<ViewModel.OnLongClickListener> longClicks = mViewModel.getLongClickListeners();
        for (int i = 0; i < longClicks.size(); i++) {
            int viewId = longClicks.keyAt(i);
            View view = mContentView.findViewById(viewId);
            if (view != null) {
                view.setOnLongClickListener(viewLongClick);
            }
        }
    }
}
