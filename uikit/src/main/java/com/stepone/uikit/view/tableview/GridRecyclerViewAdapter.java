package com.stepone.uikit.view.tableview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * FileName: GridRecyclerViewAdapter
 * Author: y.liang
 * Date: 2019-12-17 10:56
 */

public class GridRecyclerViewAdapter extends LinearRecyclerViewAdapter {
    private static final String TAG = "GridRecyclerViewAdapter";
    private int mSpanCount;
    private GridLayoutManager mLayoutManager;
    private ItemSpaceInRowDecoration mItemSpaceDecoration;

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
             * 根据VM设定的spanSize minSpanSize maxSpanSize,智能计算其layoutSpanSize，同时对spanIndex和spanGroupIndex进行缓存
             * 优先保证当前视图正常显示,若不能充满整行，再进行压缩优先策略，即压缩当前view，尽量让下一个view与当前view显示在同一行，但尽量降低当前view压缩度
             * 若spanSize设置为0，将导致recyclerview获取position出现偏差，所以，不允许spanSize小于1
             */
            GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    ViewModel viewModel = getViewModel(position);
                    if (viewModel == null) {
                        return 0;
                    }

                    if (viewModel.isPositionChanged()) {//传递UI的位置改变
                        ViewModel next = getViewModel(position+1);
                        if (next != null) {
                            next.notifyPositionChanged();
                        }
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
                    if (!viewModel.isPositionChanged() &&
                            viewModel.spanIndex != ViewModel.UNSPECIFIC &&
                            viewModel.spanGroupIndex != ViewModel.UNSPECIFIC &&
                            viewModel.layoutSpanSize > 0 &&
                            viewModel.layoutSpanSize <= remaining) {
                        viewModel.fixPosition();//修复位置
                        return viewModel.layoutSpanSize;
                    }

                    if (viewModel.isFullSpan()) {
                        if (remaining < mSpanCount) {
                            spanGroupIndex++;
                            spanIndex = 0;
                        }

                        viewModel.spanIndex = spanIndex;
                        viewModel.spanGroupIndex = spanGroupIndex;
                        viewModel.layoutSpanSize = mSpanCount;
                        return viewModel.layoutSpanSize;
                    }

                    //优先确保当前视图正常显示 (此时remaining > 0)
                    int size = viewModel.getSpanSize();//大于0
                    final int minSize = viewModel.getMinSpanSize();//大于0
                    final int maxSize = viewModel.getMaxSpanSize();//大于0

                    boolean canZoomIn = viewModel.canZoomIn();//放大
                    boolean canZoomOut = viewModel.canZoomOut();//缩小

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
                    } else {//size < remaining
                        ViewModel nextVM = getViewModel(position+1);
                        if (nextVM != null) {
                            nextVM.spanGroupIndex = spanGroupIndex;

                            final int nextSize = nextVM.getSpanSize();
                            final int nextMinSize = nextVM.getMinSpanSize();

                            //若两个view缩小到极限，都无法在本行显示，此时应该尽量放大当前view至填充整行
                            if (minSize + nextMinSize > remaining) {
                                if (canZoomIn) {
                                    size = Math.min(maxSize, remaining);
                                }
                                nextVM.spanGroupIndex = ViewModel.UNSPECIFIC;
                            } else if (minSize + nextMinSize == remaining) {//可以完整填充，提前计算nextVM的span
                                size = minSize;
                                nextVM.layoutSpanSize = nextMinSize;
                            } else {// minSize + nextMinSize < remaining 此时两个view可显示在同一行进行填充
                                //若下一个view缩小到极限，当前view无法正常显示，则压缩当前view
                                if (size + nextMinSize > remaining) { //由于此时size < remaining，nextMinSize > 0
                                    size = remaining - nextMinSize;
                                    nextVM.layoutSpanSize = minSize;
                                } else if (size + nextMinSize == remaining) {//可以完整填充，提前计算nextVM的span, nextMinSize > 0
                                    nextVM.layoutSpanSize = nextMinSize;
                                } else if (size + nextSize <= remaining) {//完美填充，提前计算nextVM的span
                                    nextVM.layoutSpanSize = nextSize;
                                } else {//size + nextMinSize < remaining && size + nextSize > remaining
                                    nextVM.layoutSpanSize = remaining -size;
                                }
                            }

                            if (nextVM.spanGroupIndex != ViewModel.UNSPECIFIC) {
                                nextVM.spanIndex = spanIndex + size;

                                if (nextVM.isPositionChanged()) {//传递位置改变
                                    ViewModel byNext = getViewModel(position+2);
                                    if (byNext != null) {
                                        byNext.notifyPositionChanged();
                                    }
                                }

                                nextVM.fixPosition();//修复位置
                            }
                        }
                    }

                    viewModel.spanIndex = spanIndex;
                    viewModel.spanGroupIndex = spanGroupIndex;
                    viewModel.layoutSpanSize = size;
                    viewModel.fixPosition();
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

    public static final int SPACE_STRATEGY_ALL = 0;
    public static final int SPACE_STRATEGY_CENTER = 1;//两边间距为0，中间间距均分

    /**
     * 根据spanCount及targetSpace，自动计算每个item的左右缩进，确保各个item之间的间距相等
     *
     * 每个spanIndex处，item的左右缩进是确定值
     * AVERAGER_ALL模式下的计算公式
     * Li = itemSpace*(spanCount+1-i)/spanCount
     * Ri = itemSpace*(i/spanCount)
     *
     * AVERAGER_CENTER模式下的计算公式
     * Li = itemSpace*(i-1)/spanCount
     * Ri = itemSpace*(spanCount-i)/spanCount
     */
    private class ItemSpaceInRowDecoration extends RecyclerView.ItemDecoration {
        //间距单位均为px
        private int itemSpace;
        private int spaceStrategy;
        private Drawable spaceDrawable;
        private Rect rect;

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int layoutOrientation = getRecyclerViewLayoutOrientation();
            int iPos = parent.getChildAdapterPosition(view);
            ViewModel viewModel = getViewModel(iPos);
            if (viewModel == null || viewModel.layoutSpanSize <= 0
                    || !viewModel.useAutoAverageSpace()
                    || (viewModel.layoutSpanSize >= mSpanCount && spaceStrategy == SPACE_STRATEGY_CENTER)) {
                return;
            }

            //UI局部更新时候，需要主动调用SpanSizeLookup去刷新位置缓存
            if (viewModel.spanIndex == ViewModel.UNSPECIFIC) {
                mLayoutManager.getSpanSizeLookup().getSpanSize(iPos);
            }

            final int spanIndex = viewModel.spanIndex+1;//从1开始，便于计算
            final int spanSize = viewModel.layoutSpanSize;

            int Li;
            int Ri;

            //默认为AVERAGER_SPACE_STRATEGY_CENTER
            if (spaceStrategy == SPACE_STRATEGY_ALL) {
                Li = Math.round(itemSpace * (mSpanCount+1-spanIndex) * 1.0f / mSpanCount);
                Ri = Math.round(itemSpace * ((spanIndex+spanSize-1)*1.0f/mSpanCount));
            } else {
                Li = Math.round(itemSpace * (spanIndex-1) * 1.0f / mSpanCount);
                Ri = Math.round(itemSpace * (mSpanCount - spanIndex) * 1.0f / mSpanCount);
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

        private Rect obtainRect() {
            if (rect == null) {
                rect = new Rect();
            }

            rect.left = 0;
            rect.right = 0;
            rect.top = 0;
            rect.bottom = 0;
            return rect;
        }

        @Override
        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDraw(c, parent, state);
            if (spaceDrawable != null) {
                for (int i = 0; i < parent.getChildCount(); i++) {
                    View child = parent.getChildAt(i);
                    Rect outRect = obtainRect();
                    parent.getDecoratedBoundsWithMargins(child, outRect);
                    spaceDrawable.setBounds(outRect);
                    spaceDrawable.draw(c);
                }
            }
        }
    }

    /**
     * 设置item的间距，单位为px
     *
     * @param space item间距
     * @param strategy 间距策略 SPACE_STRATEGY_ALL、SPACE_STRATEGY_CENTER
     * @param drawable 间距绘制的内容
     */
    public void setItemSpaceInRow(int space, int strategy, Drawable drawable) {
        if (mItemSpaceDecoration == null) {
            mItemSpaceDecoration = new ItemSpaceInRowDecoration();
            getRecyclerView().addItemDecoration(mItemSpaceDecoration);
        }

        mItemSpaceDecoration.itemSpace = space;
        mItemSpaceDecoration.spaceStrategy = strategy;
        mItemSpaceDecoration.spaceDrawable = drawable;
    }
}
