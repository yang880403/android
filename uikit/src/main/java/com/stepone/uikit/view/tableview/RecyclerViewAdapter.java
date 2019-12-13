package com.stepone.uikit.view.tableview;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: RecyclerViewAdapter
 * Author: y.liang
 * Date: 2019-12-13 15:58
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private List<ViewModel> mViewModels = new ArrayList<>();

    public RecyclerViewAdapter() { }

    public RecyclerViewAdapter(List<ViewModel> viewModels) {
        if (viewModels != null) {
            mViewModels.addAll(viewModels);
        }
    }

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
