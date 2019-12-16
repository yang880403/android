package com.stepone.uikit.view.tableview;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: RecyclerViewAdapter
 * Author: y.liang
 * Date: 2019-12-13 15:58
 */


/**
 * 将RecyclerView与adapter、LayoutManager打包成整体，统一对外提供服务
 * 统一管理ViewModel
 * 并提供常用的功能及配置，如布局方向、item间距、动画等。
 */

public abstract class RecyclerViewAdapter extends RecyclerView.Adapter {
    private List<ViewModel> mViewModels = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private @RecyclerView.Orientation int mLayoutOrientation;
    private boolean mNeedReverseLayout;

    public RecyclerViewAdapter(@NonNull RecyclerView recyclerView) {
        this(recyclerView, null);
    }

    public RecyclerViewAdapter(@NonNull RecyclerView recyclerView, List<ViewModel> viewModels) {
        mRecyclerView = recyclerView;
        if (viewModels != null) {
            mViewModels.addAll(viewModels);
        }

        //默认布局方向
        mLayoutOrientation = LinearLayout.VERTICAL;
        mNeedReverseLayout = false;

        bindLayoutManager();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @RecyclerView.Orientation
    public int getRecyclerViewLayoutOrientation() {
        return mLayoutOrientation;
    }

    public boolean isRecyclerViewReverseLayout() {
        return mNeedReverseLayout;
    }

    /**
     * 子类需自定义合适的layout manager
     */
    @NonNull
    protected abstract RecyclerView.LayoutManager onCreateLayoutManager(@RecyclerView.Orientation int orientation, boolean reverseLayout);

    private void bindLayoutManager() {
        mRecyclerView.setLayoutManager(onCreateLayoutManager(mLayoutOrientation, mNeedReverseLayout));
    }

    public void setLayoutOrientation(@RecyclerView.Orientation int orientation) {
        mLayoutOrientation = orientation;
        bindLayoutManager();
    }

    public void setNeedReverseLayout(boolean need) {
        mNeedReverseLayout = need;
        bindLayoutManager();
    }

    public void setLayoutOrientationAndNeedReverse(@RecyclerView.Orientation int orientation, boolean reverseLayout) {
        mLayoutOrientation = orientation;
        mNeedReverseLayout = reverseLayout;
        bindLayoutManager();
    }


    /**
     * 修改数据源
     */
    public void add(ViewModel model) {
        if (model != null) {
            mViewModels.add(model);
        }
    }

    public void add(List<ViewModel> models) {
        if (models != null) {
            mViewModels.addAll(models);
        }
    }

    public void remove(ViewModel model) {
        if (model != null) {
            mViewModels.remove(model);
        }
    }

    public void remove(int index) {
        mViewModels.remove(index);
    }

    public void remove(List<ViewModel> models) {
        if (models != null) {
            mViewModels.removeAll(models);
        }
    }

    public void clear() {
        mViewModels.clear();
    }

    @Override
    public int getItemViewType(int position) {
        ViewModel viewModel = mViewModels.get(position);
        return viewModel.getClass().hashCode();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DecorView decorView = new DecorView(parent.getContext());
        return new RecyclerView.ViewHolder(decorView) {};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DecorView decorView = (DecorView) holder.itemView;
        ViewModel viewModel = mViewModels.get(position);

        decorView.onPrepare(viewModel);
        decorView.onDisplay(viewModel);
    }

    @Override
    public int getItemCount() {
        return mViewModels.size();
    }
}
