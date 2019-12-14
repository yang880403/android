package com.stepone.uikit.view.tableview;

import android.view.View;

/**
 * FileName: IViewModel
 * Author: shiliang
 * Date: 2019-12-14 17:19
 */
public interface IViewModel {
    interface OnClickListener {
        void onClick(View view, IViewModel viewModel);
    }

    interface OnLongClickListener {
        boolean onLongClick(View view, IViewModel viewModel);
    }
}
