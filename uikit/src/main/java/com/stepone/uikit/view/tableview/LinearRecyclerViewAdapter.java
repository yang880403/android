package com.stepone.uikit.view.tableview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * FileName: LinearRecyclerViewAdapter
 * Author: shiliang
 * Date: 2019-12-16 22:47
 */
public class LinearRecyclerViewAdapter extends RecyclerViewAdapter {
    private LinearLayoutManager layoutManager;

    public LinearRecyclerViewAdapter(@NonNull RecyclerView recyclerView) {
        super(recyclerView);
    }

    public LinearRecyclerViewAdapter(@NonNull RecyclerView recyclerView, List<ViewModel> viewModels) {
        super(recyclerView, viewModels);
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager onBindLayoutManager(int orientation, boolean reverseLayout) {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getRecyclerView().getContext(), orientation, reverseLayout);
            return layoutManager;
        }

        layoutManager.setOrientation(orientation);
        layoutManager.setReverseLayout(reverseLayout);
        return layoutManager;
    }
}
