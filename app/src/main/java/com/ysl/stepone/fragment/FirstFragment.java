package com.ysl.stepone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stepone.uikit.view.tableview.RecyclerViewAdapter;
import com.stepone.uikit.view.tableview.ResViewModel;
import com.ysl.stepone.R;

/**
 * FileName: FirstFragment
 * Author: y.liang
 * Date: 2019-12-05 11:08
 */

public class FirstFragment extends BaseFragment {
    private RecyclerView mTableView;
    private RecyclerViewAdapter mAdapter = new RecyclerViewAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSTActivity().setPageTitle("FIRST VIEW");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_tableview, container, false);
        mTableView = view.findViewById(R.id.tableview);
        mTableView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mTableView.setAdapter(mAdapter);
        buildDatasource();
        return view;
    }

    @Override
    public int onCreateView() {
        return R.layout.fragment_first_tableview;
    }

    private void buildDatasource() {
        for (int i = 0; i < 22; i++) {
            mAdapter.add(new TestVM(i));
        }
    }


    private static class TestVM extends ResViewModel<Integer, ResViewModel.ViewHolder> {
        TestVM(int i) {
            super(R.layout.cell_test, i);
        }

        @Override
        protected void onBindViewHolder(ViewHolder holder) {
            TextView v = (TextView) holder.get(R.id.title_view);
            v.setText("auto bind text " + getData());
        }
    }
}
