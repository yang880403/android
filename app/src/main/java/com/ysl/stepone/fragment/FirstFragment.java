package com.ysl.stepone.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stepone.uikit.view.tableview.ClazzViewModel;
import com.stepone.uikit.view.tableview.IViewModel;
import com.stepone.uikit.view.tableview.RecyclerViewAdapter;
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
    private RecyclerViewAdapter mAdapter;

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
        mAdapter = new RecyclerViewAdapter(mTableView);
//        mAdapter.setNeedReverseLayout(true);
//        mAdapter.setLayoutOrientation(LinearLayout.HORIZONTAL);

//        mTableView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                outRect.set(10, 0, 5, 0);
//            }
//        });

        mAdapter.setItemSpace(10, 80, 47);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 5);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int t = position % 7;
                switch (t) {
                    case 0:
                    case 3:
                        return 1;
                    case 4:
                    case 5:
                        return 3;
                }

                return 2;
            }
        });
//        mTableView.setLayoutManager(layoutManager);
//        mTableView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                outRect.set(0, 5, 0, 10);
//            }
//        });
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
            TestVM vm = new TestVM(i);
            vm.setItemClickListener(new IViewModel.OnClickListener() {
                @Override
                public void onClick(View view, IViewModel viewModel) {
                    TestVM testVM = (TestVM) viewModel;
                    Toast.makeText(getContext(), "TAP item at index "+testVM.getPayload(), Toast.LENGTH_SHORT).show();
                }
            });
            mAdapter.add(vm);
        }
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
                setBackgroundColor(Color.BLUE);
            }

            @Override
            public void onInitialize(@NonNull GapVM viewModel) {

            }

            @Override
            public void onDisplay(@NonNull GapVM viewModel) {

            }

        }
    }
}
