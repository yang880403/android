package com.ysl.stepone.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.stepone.uikit.view.tableview.ClazzViewModel;
import com.stepone.uikit.view.tableview.GridRecyclerViewAdapter;
import com.stepone.uikit.view.tableview.IViewModel;
import com.stepone.uikit.view.tableview.ResViewModel;
import com.stepone.uikit.view.tableview.ViewHolder;
import com.stepone.uikit.view.utils.DisplayUtils;
import com.ysl.stepone.R;

/**
 * FileName: FirstFragment
 * Author: y.liang
 * Date: 2019-12-05 11:08
 */

public class FirstFragment extends BaseFragment {
    private RecyclerView mTableView;
    private GridRecyclerViewAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSTActivity().setPageTitle("FIRST VIEW");
    }

    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_tableview, container, false);
        mTableView = view.findViewById(R.id.tableview);
        mAdapter = new GridRecyclerViewAdapter(mTableView, 4);

//        mAdapter.setNeedReverseLayout(true);
//        mAdapter.setLayoutOrientation(RecyclerView.HORIZONTAL);


        mAdapter.setAverageRowItemSpace(20, GridRecyclerViewAdapter.AVERAGER_SPACE_STRATEGY_ALL, null);

        view.post(new Runnable() {
            @Override
            public void run() {
                buildDatasource();
            }
        });

        return view;
    }

    @Override
    public int onCreateView() {
        return R.layout.fragment_first_tableview;
    }

    private void buildDatasource() {
        for (int i = 0; i < 55; i++) {
            if (i % 3 == 0) {
                GapVM gapVM = new GapVM(i);
                gapVM.setSpanSize(2);
                gapVM.setFullSpan(true);
                gapVM.setUseAutoAverageSpace(false);
                gapVM.bottomDividerHieght = DisplayUtils.dp2px(getSTActivity(), 1);
                gapVM.bottomDivider = new ColorDrawable(Color.RED);

                gapVM.rightDividerWidth = 1;
                gapVM.rightDivider = new ColorDrawable(Color.GREEN);
                mAdapter.append(gapVM);
            } else {
                TestVM vm = new TestVM(i);
                vm.setSpanSize(((i % 3)));
//                vm.setSpanSize(1);
                vm.setItemClickListener(new IViewModel.OnClickListener() {
                    @Override
                    public void onClick(View view, IViewModel viewModel) {
                        TestVM testVM = (TestVM) viewModel;
                        Toast.makeText(getContext(), "TAP item at index "+testVM.getPayload(), Toast.LENGTH_SHORT).show();
                    }
                });
                vm.bottomDividerHieght = 1;
                vm.bottomDividerLeftInset = 1;
                mAdapter.append(vm);
            }
        }
    }


    private static class TestVM extends ResViewModel<Integer, ViewHolder> {
        TestVM(int i) {
            super(R.layout.cell_card);
            setPayload(i);
        }

        @Override
        protected void onInitializeView(@NonNull ViewHolder holder, int position) {

        }

        @Override
        protected void onWillDisplayView(@NonNull ViewHolder holder, int position) {

        }

        @Override
        protected void onDisplayView(@NonNull ViewHolder holder, int position) {
            holder.setText(R.id.title_view, getPayload()+"");
        }
    }

    private static class GapVM extends ClazzViewModel<Integer> {
        private GapVM(int i) {
            super(GapCell.class);
            setPayload(i);
        }

        private static class GapCell extends ClazzViewModel.ViewCell<GapVM> {

            public GapCell(@NonNull Context context) {
                super(context);
                ViewGroup.LayoutParams params = getLayoutParams();
                params.height = DisplayUtils.dp2px(context, 44);
                setBackgroundColor(Color.GRAY);
            }

            @Override
            protected void onInitialize(@NonNull GapVM viewModel, int pisition) {

            }

            @Override
            protected void onWillDisplay(@NonNull GapVM viewModel, int position) {

            }

            @Override
            protected void onDisplay(@NonNull GapVM viewModel, int position) {

            }

        }
    }
}
