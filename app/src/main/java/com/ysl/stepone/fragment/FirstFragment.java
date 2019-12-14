package com.ysl.stepone.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private RecyclerViewAdapter mAdapter = new RecyclerViewAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSTActivity().setPageTitle("FIRST VIEW");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_tableview, container, false);
        mTableView = view.findViewById(R.id.tableview);

        mTableView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }
        });
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
        protected void onBindView(@NonNull ViewHolder holder) {

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
