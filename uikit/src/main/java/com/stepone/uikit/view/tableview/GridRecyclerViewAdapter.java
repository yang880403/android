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
        int count = mSpanCount > 0 ? mSpanCount : 1;
        if (mLayoutManager == null) {
            mLayoutManager = new GridLayoutManager(getRecyclerView().getContext(), count, orientation, reverseLayout);
            /**
             * 智能填充
             * 根据VM设定的spanSize minSpanSize maxSpanSize,智能计算其layoutSpanSize
             * 优先保证当前视图正常显示,若不能充满整行，再进行压缩优先策略
             */
            GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    ViewModel viewModel = getViewModel(position);
                    if (viewModel == null) {
                        return 0;
                    }

                    int remaining = mSpanCount;
                    int spanIndex = 0;
                    int spanGroupIndex = 0;
                    ViewModel previousVM = getViewModel(position-1);
                    if (previousVM != null) {
                        remaining -= (previousVM.spanIndex + previousVM.layoutSpanSize);
                        if (remaining > 0) {
                            spanIndex = previousVM.spanIndex + previousVM.layoutSpanSize;
                            spanGroupIndex = previousVM.spanGroupIndex;
                        } else {//换行
                            remaining = mSpanCount;
                            spanGroupIndex = previousVM.spanGroupIndex+1;
                            spanIndex = 0;
                        }
                    }

                    //优化处理:如果viewmodel的span信息已经被提前计算好，则直接返回
                    if (viewModel.spanIndex != ViewModel.INDEX_UNDEFINED &&
                            viewModel.spanGroupIndex != ViewModel.INDEX_UNDEFINED &&
                            viewModel.layoutSpanSize > 0 &&
                            viewModel.layoutSpanSize <= remaining) {
                        return viewModel.layoutSpanSize;
                    }

                    //优先确保当前视图正常显示
                    int size = viewModel.getSpanSize();
                    final int minSize = viewModel.getMinSpanSize();
                    final int maxSize = viewModel.getMaxSpanSize();
                    if (size <= 0) {
                        size = 1;
                    }

                    boolean canZoomIn = maxSize > size;//放大
                    boolean canZoomOut = minSize < size;//缩小

                    if (size >= remaining) {
                        if (canZoomOut) {//缩小当前view至填充整行
                            size = Math.max(minSize, remaining);
                        }
                        if (size > remaining && remaining < mSpanCount) {//缩小到极限也放置不下，则考虑换行
                            spanIndex = 0;
                            spanGroupIndex++;

                            //换行后，重新计算
                            remaining = mSpanCount;
                            size = viewModel.getSpanSize();
                            if (size > remaining) {
                                if (canZoomOut) {
                                    size = Math.max(minSize, remaining);
                                }
                            }
                        }
                    } else {
                        ViewModel nextVM = getViewModel(position+1);
                        if (nextVM != null) {
                            nextVM.spanIndex = spanIndex;
                            nextVM.spanGroupIndex = spanGroupIndex;

                            final int nextSize = nextVM.getSpanSize();
                            final int nextMinSize = nextVM.getMinSpanSize();

                            //若两个view缩小到极限，都无法在本行显示，此时应该尽量放大当前view至填充整行
                            if (minSize + nextMinSize > remaining) {
                                if (canZoomIn) {
                                    size = Math.min(maxSize, remaining);
                                }
                                nextVM.clearSpanIndexCache();
                            } else if (minSize + nextMinSize == remaining) {
                                size = minSize;
                                nextVM.layoutSpanSize = nextMinSize;
                            } else if (minSize + nextMinSize < remaining) {
                                //若下一个view缩小到极限，都无法在本行显示，此时应该尽量放大当前view至填充整行
                                if (size + nextMinSize > remaining) {
                                    if (canZoomIn) {
                                        size = Math.min(maxSize, remaining);
                                    }
                                    nextVM.clearSpanIndexCache();
                                } else if (size + nextMinSize == remaining) {//完美填充，提前计算nextVM的span
                                    nextVM.layoutSpanSize = nextMinSize;
                                } else if (size + nextSize <= remaining) {//完美填充，提前计算nextVM的span
                                    nextVM.layoutSpanSize = nextSize;
                                } else {
                                    nextVM.layoutSpanSize = remaining -size;
                                }
                            }
                        }
                    }

                    viewModel.spanIndex = spanIndex;
                    viewModel.spanGroupIndex = spanGroupIndex;
                    viewModel.layoutSpanSize = size;
                    return viewModel.layoutSpanSize;
                }
            };

            mLayoutManager.setSpanSizeLookup(spanSizeLookup);
            return mLayoutManager;
        }

        mLayoutManager.setOrientation(orientation);
        mLayoutManager.setReverseLayout(reverseLayout);
        mLayoutManager.setSpanCount(count);
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
            ViewModel viewModel = getViewModel(iPos);
            if (viewModel == null) {
                return;
            }
            int spanIndex = viewModel.getSpanIndex()+1;//从1开始，便于计算
            int spanSize = viewModel.getSpanSize();

            int Li;
            int Ri;

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
