package com.stepone.component.navigator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * FileName: NavigatorStack
 * Author: shiliang
 * Date: 2019-12-07 10:28
 */
class NavigatorStack {
    private final static List<PageStub<? extends INavigatorPage>> mStack = new ArrayList<>(30);

    private static class PageStub<T extends INavigatorPage> {
        private String pageTag;

        private WeakReference<T> pageRefrence;

        PageStub(String tag, T page) {
            if (page == null) {
                throw new NullPointerException("construct PageStub with null page");
            }
            this.pageTag = tag;
            this.pageRefrence = new WeakReference<>(page);
        }

        String getPageTag() {
            return pageTag;
        }

        T getPage() {
            return pageRefrence.get();
        }

        void finishPage() {
            if (!isDestroyed()) {
                getPage().finish();
            }
            pageRefrence.clear();
        }

        boolean isDestroyed() {
            return getPage() == null || getPage().isPageDestroyed();
        }
    }

    private static void push(String tag, INavigatorPage page) {
        if (page == null) {
            return;
        }

        mStack.add(new PageStub<>(tag, page));
    }

    private static void pop() {
        mStack.remove(mStack.size()-1);
    }

    private static PageStub<? extends INavigatorPage> getTopPageStub() {
        if (mStack.size() > 0) {
            return mStack.get(mStack.size() - 1);
        }

        return null;
    }

    private static PageStub<? extends INavigatorPage> getPageStub(INavigatorPage page) {
        if (page == null) {
            return null;
        }

        for (PageStub<? extends INavigatorPage> pageStub : mStack) {
            if (page.equals(pageStub.getPageTag())) {
                return pageStub;
            }
        }

        return null;
    }

    static void onPageCreate(String tag, INavigatorPage page) {
        push(tag, page);
    }

    /**
     * 如果page处于栈顶，直接pop，并处理前一个page。若前一个page也已经被其他原因销毁，则执行pop
     * 【如果前一个page被销毁，必然是系统行为，此时系统会重建该页面，因此需要执行pop操作，保证本地导航栈与AMS一致】
     *
     * 若page不处于栈顶，说明是非正常销毁（系统内存不足），则执行pageStub的销毁操作
     */
    static void onPageDestroy(INavigatorPage page) {
        if (page == null) {
            return;
        }

        PageStub<? extends INavigatorPage> pageStub = getPageStub(page);
        if (pageStub != null) {
            if (pageStub.equals(getTopPageStub())) {
                pop();

                /**
                 * 查看前一个page是否处于销毁状态，并进行处理
                 */
                PageStub<? extends INavigatorPage> currentPageStub = getTopPageStub();
                if (currentPageStub != null && currentPageStub.isDestroyed()) {
                    pop();
                }
            }
        }
    }


    /**
     * 最多回退到首页，并提示退出应用
     * @param step 回退层级
     */
    static void back(int step) {
        int stackSize = mStack.size();
        if (step <= 0) {
            return;
        }

        int fromIndex = stackSize - step;
        boolean needExitApp = false;
        if (fromIndex <= 0) {
            //TODO
            needExitApp = true;
            return;
        } else {
            List<PageStub<? extends INavigatorPage>> list = new ArrayList<>();
            for (int i = fromIndex; i < stackSize; i++) {
                list.add(mStack.get(i));
            }

            mStack.removeAll(list);

            for (int i = list.size()-1; i >= 0; i--) {
                list.get(i).finishPage();
            }
        }
    }

    /**
     * 根据tag获取最后入栈的page
     * 返回负数，表明没有找到
     */
    static int getPageIndex(String tag) {
        if (tag != null) {
            for (int i = mStack.size()-1; i >= 0; i--) {
                PageStub<? extends INavigatorPage> pageStub = mStack.get(i);
                if (tag.equals(pageStub.getPageTag())) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * 根据tag获取最早入栈的page
     * 返回负数，表明没有找到
     */
    static int getFirstPageIndex(String tag) {
        if (tag != null) {
            for (int i = 0; i < mStack.size(); i++) {
                PageStub<? extends INavigatorPage> pageStub = mStack.get(i);
                if (tag.equals(pageStub.getPageTag())) {
                    return i;
                }
            }
        }

        return -1;
    }

    static int getStackSize() {
        return mStack.size();
    }

    /**
     * 获取当前显示的页面
     */
     static INavigatorPage getCurrentVisiablePage() {
        PageStub<? extends INavigatorPage> pageStub = getTopPageStub();
        if (pageStub == null) {
            return null;
        }

        return pageStub.getPage();
    }
}
