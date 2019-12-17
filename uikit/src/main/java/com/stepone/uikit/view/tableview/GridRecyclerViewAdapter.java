package com.stepone.uikit.view.tableview;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stepone.uikit.view.utils.DisplayUtils;

import java.util.List;

/**
 * FileName: GridRecyclerViewAdapter
 * Author: y.liang
 * Date: 2019-12-17 10:56
 */

public class GridRecyclerViewAdapter extends LinearRecyclerViewAdapter {
    private int mSpanCount;
    private GridLayoutManager mLayoutManager;
    private AverageRowItemSpaceDecoration mItemSpaceDecoration;

    public GridRecyclerViewAdapter(@NonNull RecyclerView recyclerView, int spanCount) {
        super(recyclerView);
        setSpanCount(spanCount);
    }

    public GridRecyclerViewAdapter(@NonNull RecyclerView recyclerView, int spanCount, List<ViewModel> viewModels) {
        super(recyclerView, viewModels);
        setSpanCount(spanCount);
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager onBindLayoutManager(int orientation, boolean reverseLayout) {
        if (mLayoutManager == null) {
            mLayoutManager = new GridLayoutManager(getRecyclerView().getContext(), mSpanCount, orientation, reverseLayout);
            /**
             * 智能填充
             * 根据VM设定的spanSize minSpanSize maxSpanSize,智能计算其layoutSpanSize
             * 优先保证当前视图正常显示（即不优先做缩小处理，后续可将此逻辑封装成策略，让用户选择）
             */
            GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    ViewModel viewModel = getViewModel(position);
                    int size = viewModel.getSpanSize();
                    int minSize = viewModel.getMinSpanSize();
                    int maxSize = viewModel.getMaxSpanSize();
                    if (size <= 0) {
                        size = 1;
                    }

                    boolean canZoomIn = maxSize > size;//放大
                    boolean canZoomOut = minSize < size;//缩小

                    ViewModel previousVM = getViewModel(position-1);
                    ViewModel nextVM = getViewModel(position+1);
                    int remaining = mSpanCount;
                    if (previousVM != null) {
                        remaining -= previousVM.spanIndex + previousVM.layoutSpanSize;
                    }

                    if (size > remaining && canZoomOut) {
                        size = Math.max(minSize, remaining);
                    } else if (nextVM != null) {
                        int nextSize = nextVM.getSpanSize();
                        int nextMinSize = nextVM.getMinSpanSize();

                        if (size + minSize > remaining)
                    }

                    //step1 计算前一个view产生的影响
                    if (position == 0) {
                        viewModel.spanIndex = 0;
                        viewModel.spanGroupIndex = 0;

                    } else {



                    }

                    viewModel.layoutSpanSize = size;
                    return viewModel.layoutSpanSize;
                }
            };

            mLayoutManager.setSpanSizeLookup(spanSizeLookup);
            return mLayoutManager;
        }

        mLayoutManager.setOrientation(orientation);
        mLayoutManager.setReverseLayout(reverseLayout);
        return mLayoutManager;
    }

    public void setSpanCount(int count) {
        mSpanCount = count;
        rebindLayoutManager();
    }

    public static final int AVERAGER_SPACE_STRATEGY_ALL = 0;
    public static final int AVERAGER_SPACE_STRATEGY_CENTER = 1;//两边间距为0，中间间距均分

    /**
     * 根据spanCount及targetSpace，自动计算每个item的左右缩进，确保各个item之间的间距相等
     *
     * 每个spanIndex处，item的左右缩进是确定值
     * AVERAGER_ALL模式下的计算公式
     * Li = averageSpace*(spanCount+1-i)/spanCount
     * Ri = averageSpace*(i/spanCount)
     *
     * AVERAGER_CENTER模式下的计算公式
     * Li = averageSpace*(i-1)/spanCount
     * Ri = averageSpace*(spanCount-i)/spanCount
     */
    public class AverageRowItemSpaceDecoration extends RecyclerView.ItemDecoration {
        //间距单位均为px
        private int averageSpace;
        private int averageStrategy;
        private Drawable spaceDrawable = new ColorDrawable(Color.TRANSPARENT);

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int layoutOrientation = getRecyclerViewLayoutOrientation();
            int iPos = parent.getChildLayoutPosition(view);
            int spanIndex = mLayoutManager.getSpanSizeLookup().getSpanIndex(iPos, mSpanCount)+1;//从1开始，便于计算
            int spanSize = mLayoutManager.getSpanSizeLookup().getSpanSize(iPos);

            int Li = 0;
            int Ri = 0;

            //默认为AVERAGER_SPACE_STRATEGY_CENTER
            if (averageStrategy == AVERAGER_SPACE_STRATEGY_ALL) {
                Li = (int) (averageSpace * (mSpanCount+1-spanIndex) * 1.0f / mSpanCount);
                Ri = (int) (averageSpace * ((spanIndex+spanSize-1)*1.0f/mSpanCount));
            } else {
                Li = (int) (averageSpace * (spanIndex-1) * 1.0f / mSpanCount);
                Ri = (int) (averageSpace * (mSpanCount - spanIndex) * 1.0f / mSpanCount);
            }

            if (layoutOrientation == RecyclerView.VERTICAL) {
                outRect.left = Li;
                outRect.right = Ri;
            } else {
                outRect.top = Li;
                outRect.bottom = Ri;
            }

            if (isRecyclerViewReverseLayout()) {
                int tmp = outRect.top;
                outRect.top = outRect.bottom;
                outRect.bottom = tmp;

                tmp = outRect.left;
                outRect.left = outRect.right;
                outRect.right = tmp;
            }
        }
    }

    /**
     * 设置item的间距，单位为dp
     *
     * @param space item间距
     * @param strategy 均分策略 AVERAGER_SPACE_STRATEGY_ALL、AVERAGER_SPACE_STRATEGY_CENTER
     * @param drawable 间距绘制的内容
     */
    public void setAverageRowItemSpace(float space, int strategy, Drawable drawable) {
        if (mItemSpaceDecoration == null) {
            mItemSpaceDecoration = new AverageRowItemSpaceDecoration();
            getRecyclerView().addItemDecoration(mItemSpaceDecoration);
        }

        mItemSpaceDecoration.averageSpace = DisplayUtils.dp2px(getRecyclerView().getContext(), space);
        mItemSpaceDecoration.averageStrategy = strategy;
    }
}
