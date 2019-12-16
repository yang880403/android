package com.stepone.uikit.view.tableview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stepone.uikit.view.utils.DisplayUtils;

import java.net.PortUnreachableException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * FileName: RecyclerViewAdapter
 * Author: y.liang
 * Date: 2019-12-13 15:58
 */


/**
 * 将RecyclerView与adapter、LayoutManager打包成整体，统一对外提供服务
 * 统一管理ViewModel
 * 并提供常用的功能及配置，如布局方向、item间距、动画等。
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private List<ViewModel> mViewModels = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private @RecyclerView.Orientation int mLayoutOrientation;
    private boolean mNeedReverseLayout;
    private ItemSpaceDecoration mItemSpaceDecoration;


    public RecyclerViewAdapter(@NonNull RecyclerView recyclerView) {
        this(recyclerView, null);
    }

    public RecyclerViewAdapter(@NonNull RecyclerView recyclerView, List<ViewModel> viewModels) {
        mRecyclerView = recyclerView;
        if (viewModels != null) {
            mViewModels.addAll(viewModels);
        }

        //默认布局方向
        mLayoutOrientation = LinearLayout.VERTICAL;
        mNeedReverseLayout = false;

        bindLayoutManager();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * 子类可自定义合适的layout manager
     */
    protected RecyclerView.LayoutManager onCreateLayoutManager(@RecyclerView.Orientation int orientation, boolean reverseLayout) {
        return new LinearLayoutManager(mRecyclerView.getContext(), orientation, reverseLayout);
    }

    private void bindLayoutManager() {
        mRecyclerView.setLayoutManager(onCreateLayoutManager(mLayoutOrientation, mNeedReverseLayout));
    }

    public void setLayoutOrientation(@RecyclerView.Orientation int orientation) {
        mLayoutOrientation = orientation;
        bindLayoutManager();
    }

    public void setNeedReverseLayout(boolean need) {
        mNeedReverseLayout = need;
        bindLayoutManager();
    }

    public void setLayoutOrientationAndNeedReverse(@RecyclerView.Orientation int orientation, boolean reverseLayout) {
        mLayoutOrientation = orientation;
        mNeedReverseLayout = reverseLayout;
        bindLayoutManager();
    }

    public class ItemSpaceDecoration extends RecyclerView.ItemDecoration {
        public static final int POSITION_SPEC_FIRST = 0;
        public static final int POSITION_SPEC_LAST = 1;
        public static final int POSITION_SPEC_NORMAL = 2;
        /**
         * 单位均为px
         */
        protected int itemSpace;//item间距
        protected int firstItemStartSpace;//item与页面起始位置间距
        protected int lastItemEndSpace;//item与页面结束位置间距

        private Drawable spaceDrawable = new ColorDrawable(Color.RED);

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int pos = parent.getChildLayoutPosition(view);
            if (pos == 0) {
                if (mLayoutOrientation == RecyclerView.VERTICAL) {
                    outRect.top = firstItemStartSpace;
                    outRect.bottom = itemSpace;
                } else {
                    outRect.left = firstItemStartSpace;
                    outRect.right = itemSpace;
                }
            } else if (pos == state.getItemCount()-1) {
                if (mLayoutOrientation == RecyclerView.VERTICAL) {
                    outRect.bottom = lastItemEndSpace;
                } else {
                    outRect.right = lastItemEndSpace;
                }
            } else {
                if (mLayoutOrientation == RecyclerView.VERTICAL) {
                    outRect.bottom = itemSpace;
                } else {
                    outRect.right = itemSpace;
                }
            }

            if (mNeedReverseLayout) {
                int tmp = outRect.top;
                outRect.top = outRect.bottom;
                outRect.bottom = tmp;

                tmp = outRect.left;
                outRect.left = outRect.right;
                outRect.right = tmp;
            }
        }

        @Override
        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

            int left;
            int right;
            int top;
            int bottom;
            Rect rect = new Rect(0, 0, 0 , 0);
            if (mLayoutOrientation == LinearLayoutManager.HORIZONTAL) {
                top = parent.getPaddingTop();
                bottom = parent.getHeight() - parent.getPaddingBottom();
                final int childCount = parent.getChildCount();
                for (int i = 0; i < childCount - 1; i++) {
                    final View child = parent.getChildAt(i);
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    left = child.getRight() + params.rightMargin;
//                    right = left + mSize;
//                    mDivider.setBounds(left, top, right, bottom);
//                    mDivider.draw(c);
                }
            } else {
                left = parent.getPaddingLeft();
                right = parent.getWidth() - parent.getPaddingRight();
                final int childCount = parent.getChildCount();
                for (int i = 0; i < childCount - 1; i++) {
                    final View child = parent.getChildAt(i);
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int pos = parent.getChildLayoutPosition(child);

                    rect.left = left;
                    rect.right = right;
                    if (pos == 0) {
                        bottom = child.getTop() - params.topMargin;
                        top = bottom - firstItemStartSpace;
                        rect.bottom = bottom;
                        rect.top = top;
                        onDrawSpace(c, rect, pos, POSITION_SPEC_FIRST);

                        top = child.getBottom() + params.bottomMargin;
                        bottom = top + itemSpace;
                        rect.top = top;
                        rect.bottom = bottom;
                        onDrawSpace(c,rect, pos, POSITION_SPEC_NORMAL);
                    } else {

                    }

                }
            }

            int pos = state.getTargetScrollPosition();
            if (pos == 0) {

            } else if (pos == state.getItemCount()-1) {

            } else {

            }
            
//            super.onDraw(c, parent, state);

        }

        public void onDrawSpace(@NonNull Canvas c, @NonNull Rect rect, int positon, int positionSpec) {
            if (spaceDrawable != null) {
                spaceDrawable.setBounds(rect);
                spaceDrawable.draw(c);
            }

        }
    }

    @NonNull
    ItemSpaceDecoration onCreateItemSpaceDecoration() {
        return new ItemSpaceDecoration();
    }

    /**
     * 设置item的间距，单位为dp
     * @param space item 间距
     * @param firstItemTopSpace 第一个item与top的间距
     * @param lastItemBottomSpace 最后一个item与bottom的间距
     */
    public void setItemSpace(float space, float firstItemTopSpace, float lastItemBottomSpace) {
        if (mItemSpaceDecoration == null) {
            mItemSpaceDecoration = onCreateItemSpaceDecoration();
            mRecyclerView.addItemDecoration(mItemSpaceDecoration);
        }

        mItemSpaceDecoration.itemSpace = DisplayUtils.dp2px(mRecyclerView.getContext(), space);
        mItemSpaceDecoration.firstItemStartSpace = DisplayUtils.dp2px(mRecyclerView.getContext(), firstItemTopSpace);
        mItemSpaceDecoration.lastItemEndSpace = DisplayUtils.dp2px(mRecyclerView.getContext(), lastItemBottomSpace);
    }

    /**
     * 修改数据源
     */

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
