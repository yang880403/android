package com.stepone.uikit.view.tableview;

import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private final AsyncDiffer mDiffer;
    private RecyclerView mRecyclerView;
    private @RecyclerView.Orientation int mLayoutOrientation;
    private boolean mNeedReverseLayout;
    private boolean isUpdating = false;

    public RecyclerViewAdapter(@NonNull RecyclerView recyclerView) {
        this(recyclerView, null);
    }

    public RecyclerViewAdapter(@NonNull RecyclerView recyclerView, List<ViewModel> viewModels) {
        mDiffer = new AsyncDiffer();
        mViewModels = new ArrayList<>();

        //默认布局方向
        mLayoutOrientation = LinearLayout.VERTICAL;
        mNeedReverseLayout = false;

        mRecyclerView = recyclerView;
        bindLayoutManager();
        mRecyclerView.setAdapter(this);

        if (viewModels != null) {
            mViewModels.addAll(viewModels);
            notifyDataSetChanged();
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
    private void diffUpdate(List<ViewModel> oldList, List<ViewModel> newList) {
        isUpdating = true;
        mDiffer.doDiff(oldList, newList);
    }

    private void onDiffResult(DiffUtil.DiffResult result) {
        isUpdating = false;
        result.dispatchUpdatesTo(this);
    }

    public void reload() {
        notifyDataSetChanged();
    }

    public void reset(List<? extends ViewModel> models) {
        List<ViewModel> oldList = null;
        if (mViewModels.size() > 0) {
            oldList = new ArrayList<>(mViewModels);
        }

        mViewModels.clear();
        if (models != null) {
            mViewModels.addAll(models);
        }

        if (oldList == null) {
            notifyDataSetChanged();
        } else {
            diffUpdate(oldList, new ArrayList<>(mViewModels));
        }
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
        if (isUpdating) {
            return;
        }
        if (model != null) {
            mViewModels.add(position, model);
            notifyItemInserted(position);
        }
    }

    public void insert(int fromPosition, List<ViewModel> models) {
        if (isUpdating) {
            return;
        }
        if (models != null && models.size() > 0) {
            mViewModels.addAll(fromPosition, models);
            notifyItemRangeInserted(fromPosition, models.size());
        }
    }


    public void remove(IViewModel model) {
        if (model != null) {
            int index = mViewModels.indexOf(model);
            remove(index);
        }
    }

    public void remove(int positon) {
        if (isUpdating) {
            return;
        }
        mViewModels.remove(positon);
        notifyItemRemoved(positon);
    }

    public void remove(int fromPositon, int count) {
        if (isUpdating) {
            return;
        }
        for (int i = 0; i < count; i++) {
            mViewModels.remove(fromPositon+i);
        }
        notifyItemRangeRemoved(fromPositon, count);
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
        private List<ViewModel> mOldList;
        private Executor mDifferExecutor = Executors.newFixedThreadPool(2);
        private Handler mMainHandler = new Handler(Looper.getMainLooper());

        void doDiff(List<ViewModel> oldList, List<ViewModel> newList) {
            mOldList = oldList;
            doDiff(newList);
        }
        void doDiff(final List<ViewModel> newList) {
            mDifferExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return mOldList == null ? 0 : mOldList.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return mViewModels.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            ViewModel oldItem = mOldList.get(oldItemPosition);
                            ViewModel newItem = mViewModels.get(newItemPosition);
                            if (oldItem != null && newItem != null) {
                                return oldItem.isSame(newItem);
                            }

                            return oldItem == null && newItem == null;
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            ViewModel oldItem = mOldList.get(oldItemPosition);
                            ViewModel newItem = mViewModels.get(newItemPosition);
                            if (oldItem != null && newItem != null) {
                                return oldItem.isContentSame(newItem);
                            }

                            return oldItem == null && newItem == null;
                        }
                    });

                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onDiffResult(diffResult, newList);
                        }
                    });
                }
            });
        }

        void onDiffResult(DiffUtil.DiffResult result, List<ViewModel> newList) {
            mOldList = newList;
            RecyclerViewAdapter.this.onDiffResult(result);
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
