package com.stepone.uikit.view.tableview;

import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

public abstract class RecyclerViewAdapter extends RecyclerView.Adapter {
    private static final String TAG = "RecyclerViewAdapter";

    private final List<ViewModel> mViewModels;
    private final AsyncListDiffer<ViewModel> mDiffer;
    private final DiffUtil.ItemCallback<ViewModel> mItemCallback;
    private RecyclerView mRecyclerView;
    private @RecyclerView.Orientation int mLayoutOrientation;
    private boolean mNeedReverseLayout;
    private boolean isUpdating = false;//批量更新数据源，避免每次都进行更新操作

    public RecyclerViewAdapter(@NonNull RecyclerView recyclerView) {
        this(recyclerView, null);
    }

    public RecyclerViewAdapter(@NonNull RecyclerView recyclerView, List<ViewModel> viewModels) {
        mItemCallback = new DiffUtil.ItemCallback<ViewModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull ViewModel oldItem, @NonNull ViewModel newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull ViewModel oldItem, @NonNull ViewModel newItem) {
                return false;
            }
        };
        mDiffer = new AsyncListDiffer<>(this, mItemCallback);
        mViewModels = new ArrayList<>();

        //默认布局方向
        mLayoutOrientation = LinearLayout.VERTICAL;
        mNeedReverseLayout = false;

        mRecyclerView = recyclerView;
        bindLayoutManager();
        mRecyclerView.setAdapter(this);

        if (viewModels != null) {
            mViewModels.addAll(viewModels);
            mDiffer.submitList(mViewModels);
        }
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @RecyclerView.Orientation
    public int getRecyclerViewLayoutOrientation() {
        return mLayoutOrientation;
    }

    public boolean isRecyclerViewReverseLayout() {
        return mNeedReverseLayout;
    }

    /**
     * 子类需自定义合适的layout manager
     */
    @NonNull
    protected abstract RecyclerView.LayoutManager onBindLayoutManager(@RecyclerView.Orientation int orientation, boolean reverseLayout);

    private void bindLayoutManager() {
        mRecyclerView.setLayoutManager(onBindLayoutManager(mLayoutOrientation, mNeedReverseLayout));
    }

    protected void rebindLayoutManager() {
        bindLayoutManager();
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


    /**
     * 修改数据源，非线程安全，务必保证在主线程进行操作
     */
    public void beginUpdate() {
        isUpdating = true;
    }

    public void endUpdate() {
        if (isUpdating) {
            isUpdating = false;
            notifyDataSetChanged();
        }
    }

    public void reload() {
        isUpdating = false;
        notifyDataSetChanged();
    }

    public void reset(List<ViewModel> models) {
        isUpdating = false;
        mViewModels.clear();

        if (models != null) {
            mViewModels.addAll(models);
        }

        notifyDataSetChanged();
    }

    public void append(ViewModel model) {
        if (model != null) {
            insert(mViewModels.size(), model);
        }
    }

    public void append(List<ViewModel> models) {
        if (models != null && models.size() > 0) {
            insert(mViewModels.size(), models);
        }
    }

    public void insert(int position, ViewModel model) {
        if (model != null) {
            mViewModels.add(position, model);
            if (!isUpdating) {
                notifyItemInserted(position);
            }
        }
    }

    public void insert(int fromPosition, List<ViewModel> models) {
        if (models != null && models.size() > 0) {
            mViewModels.addAll(fromPosition, models);
            if (!isUpdating) {
                notifyItemRangeInserted(fromPosition, models.size());
            }
        }
    }


    public void remove(ViewModel model) {
        if (model != null) {
            int index = mViewModels.indexOf(model);
            remove(index);
        }
    }

    public void remove(int positon) {
        mViewModels.remove(positon);
        if (!isUpdating) {
            notifyItemRemoved(positon);
        }
    }

    public void remove(int fromPositon, int count) {
        for (int i = 0; i < count; i++) {
            mViewModels.remove(fromPositon+i);
        }
        if (!isUpdating) {
            notifyItemRangeRemoved(fromPositon, count);
        }
    }

    public void update(int positon, Object payload) {
        if (!isUpdating) {
            notifyItemChanged(positon, payload);
        }
    }

    public void update(int fromPositon, int count, Object payload) {
        if (!isUpdating) {
            notifyItemRangeChanged(fromPositon, count, payload);
        }
    }

    public void clear() {
        mViewModels.clear();
        notifyDataSetChanged();
    }


    private class AsyncDiffer {
        private List<ViewModel> mList;
        private Executor mDifferExecutor = Executors.newFixedThreadPool(2);
        private Handler mMainHandler = new Handler(Looper.getMainLooper());

        void asyncUpdate() {
            mDifferExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return 0;
                        }

                        @Override
                        public int getNewListSize() {
                            return 0;
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            return false;
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            return false;
                        }
                    });
                }
            });
        }
    }

    @Nullable
    public ViewModel getViewModel(int position) {
        if (position < 0 || position >= mViewModels.size()) {
            return null;
        }
        return mViewModels.get(position);
    }

    /**
     * adapter 重载方法
     */
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

        decorView.onInitialize(viewModel, position);
        decorView.onDisplay(viewModel, position);
    }

    @Override
    public int getItemCount() {
        return mViewModels.size();
    }
}
