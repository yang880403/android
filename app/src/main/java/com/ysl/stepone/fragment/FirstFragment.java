package com.ysl.stepone.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.stepone.component.image.MediaFetcher;
import com.stepone.uikit.view.tableview.ClazzViewModel;
import com.stepone.uikit.view.tableview.GridRecyclerViewAdapter;
import com.stepone.uikit.view.tableview.ResViewModel;
import com.stepone.uikit.view.tableview.ViewHolder;
import com.stepone.uikit.view.tableview.ViewModel;
import com.stepone.uikit.view.utils.DisplayUtils;
import com.ysl.stepone.R;

import java.util.ArrayList;
import java.util.List;

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
        Log.i("TT", getActivity().toString());
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


        mAdapter.setItemSpaceInRow(20, GridRecyclerViewAdapter.SPACE_STRATEGY_ALL, null);

        view.post(new Runnable() {
            @Override
            public void run() {
                buildData();
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
                vm.setItemClickListener(new ViewModel.OnClickListener() {
                    @Override
                    public void onClick(View view, ViewModel viewModel) {
                        TestVM testVM = (TestVM) viewModel;
                        Toast.makeText(getContext(), "TAP item at index "+testVM.getPayload(), Toast.LENGTH_SHORT).show();
                        buildData();
                    }
                });
                vm.bottomDividerHieght = 1;
                vm.bottomDividerLeftInset = 1;
                mAdapter.append(vm);
            }
        }
    }

    private void buildData() {
        List<TestVM> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            TestVM vm = new TestVM(i);
            vm.setSpanSize((1));
            vm.setItemClickListener(new ViewModel.OnClickListener() {
                @Override
                public void onClick(View view, ViewModel viewModel) {
                    TestVM testVM = (TestVM) viewModel;
                    Toast.makeText(getContext(), "TAP item at index "+testVM.getPayload(), Toast.LENGTH_SHORT).show();
//                    buildDatasource();
//                    mAdapter.remove(viewModel);
                    new MediaFetcher().fetch(getSTActivity());
                }

            });

            vm.bottomDividerHieght = 1;
            vm.bottomDividerLeftInset = 1;
            list.add(vm);
        }

        mAdapter.setData(list);
    }


    private static class TestVM extends ResViewModel<Integer, ViewHolder> {
        TestVM(int i) {
            super(R.layout.cell_card);
            setPayload(i);
        }

        @Override
        protected void onInitializeView(@NonNull ViewHolder holder) {

        }

        @Override
        protected void onWillDisplayView(@NonNull ViewHolder holder) {

        }

        @Override
        protected void onDisplayView(@NonNull ViewHolder holder) {
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
            protected void onInitialize(@NonNull GapVM viewModel) {

            }

            @Override
            protected void onWillDisplay(@NonNull GapVM viewModel) {

            }

            @Override
            protected void onDisplay(@NonNull GapVM viewModel) {

            }

        }
    }
}
