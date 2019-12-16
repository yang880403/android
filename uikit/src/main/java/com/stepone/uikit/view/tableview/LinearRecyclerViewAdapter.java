package com.stepone.uikit.view.tableview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stepone.uikit.view.utils.DisplayUtils;

import java.util.List;

/**
 * FileName: LinearRecyclerViewAdapter
 * Author: shiliang
 * Date: 2019-12-16 22:47
 */
public class LinearRecyclerViewAdapter extends RecyclerViewAdapter {
    private ItemSpaceDecoration mItemSpaceDecoration;

    public LinearRecyclerViewAdapter(@NonNull RecyclerView recyclerView) {
        super(recyclerView);
    }

    public LinearRecyclerViewAdapter(@NonNull RecyclerView recyclerView, List<ViewModel> viewModels) {
        super(recyclerView, viewModels);
    }


    @NonNull
    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager(int orientation, boolean reverseLayout) {
        return new LinearLayoutManager(getRecyclerView().getContext(), orientation, reverseLayout);
    }

    public interface OnItemSpaceDrawObserver {
        boolean onDraw(@NonNull Canvas c, @NonNull Rect rect, int positon, int positionSpec);
    }

    public static final int POSITION_SPEC_FIRST_TOP = 0;
    public static final int POSITION_SPEC_LAST_BOTTOM = 1;
    public static final int POSITION_SPEC_NORMAL_DIVIDER = 2;

    public class ItemSpaceDecoration extends RecyclerView.ItemDecoration {
        private Rect mRect;

        //间距单位均为px
        protected int itemSpace;//item间距
        protected int firstItemStartSpace;//item与页面起始位置间距
        protected int lastItemEndSpace;//item与页面结束位置间距
        protected Drawable spaceDrawable = new ColorDrawable(Color.RED);
        protected OnItemSpaceDrawObserver drawObserver;

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int layoutOrientation = getRecyclerViewLayoutOrientation();
            int pos = parent.getChildLayoutPosition(view);
            if (pos == 0) {
                if (layoutOrientation == RecyclerView.VERTICAL) {
                    outRect.top = firstItemStartSpace;
                    outRect.bottom = itemSpace;
                } else {
                    outRect.left = firstItemStartSpace;
                    outRect.right = itemSpace;
                }
            } else if (pos == state.getItemCount()-1) {
                if (layoutOrientation == RecyclerView.VERTICAL) {
                    outRect.bottom = lastItemEndSpace;
                } else {
                    outRect.right = lastItemEndSpace;
                }
            } else {
                if (layoutOrientation == RecyclerView.VERTICAL) {
                    outRect.bottom = itemSpace;
                } else {
                    outRect.right = itemSpace;
                }
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

        @Override
        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            if (drawObserver == null) {
                super.onDraw(c, parent, state);
                return;
            }

            int left, right, top, bottom;
            int layoutOrientation = getRecyclerViewLayoutOrientation();

            if (layoutOrientation == LinearLayoutManager.HORIZONTAL) {
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
//                    DividerItemDecoration
                    final View child = parent.getChildAt(i);
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int pos = parent.getChildLayoutPosition(child);

                    inal View child = parent.getChildAt(i);
                    parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
                    final int right = mBounds.right + Math.round(child.getTranslationX());
                    final int left = right - mDivider.getIntrinsicWidth();

                    rect.left = left;
                    rect.right = right;
                    if (pos == 0) {
                        bottom = child.getTop() - params.topMargin;
                        top = bottom - firstItemStartSpace;
                        rect.bottom = bottom;
                        rect.top = top;
                        onDrawSpace(c, rect, pos, POSITION_SPEC_FIRST_TOP);

                        top = child.getBottom() + params.bottomMargin;
                        bottom = top + itemSpace;
                        rect.top = top;
                        rect.bottom = bottom;
                        onDrawSpace(c,rect, pos, POSITION_SPEC_NORMAL_DIVIDER);
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
    protected ItemSpaceDecoration onCreateItemSpaceDecoration() {
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
            getRecyclerView().addItemDecoration(mItemSpaceDecoration);
        }

        mItemSpaceDecoration.itemSpace = DisplayUtils.dp2px(getRecyclerView().getContext(), space);
        mItemSpaceDecoration.firstItemStartSpace = DisplayUtils.dp2px(getRecyclerView().getContext(), firstItemTopSpace);
        mItemSpaceDecoration.lastItemEndSpace = DisplayUtils.dp2px(getRecyclerView().getContext(), lastItemBottomSpace);
    }
}
