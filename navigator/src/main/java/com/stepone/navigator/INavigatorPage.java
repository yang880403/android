package com.stepone.navigator;

import android.content.Context;

/**
 * FileName: INavigatorPage
 * Author: shiliang
 * Date: 2019-12-07 10:36
 */
public interface INavigatorPage {
    /**
     * 标记页面是否被销毁，一般来说，页面销毁之后，再次打开时，需要重建实例
     * 对activity来说，需要判断isFinishing()和isDestroy()
     */
    boolean isPageDestroyed();

    void finish();

    Context getContext();
}
