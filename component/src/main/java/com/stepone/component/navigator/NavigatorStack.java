package com.stepone.component.navigator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * FileName: NavigatorStack
 * Author: shiliang
 * Date: 2019-12-07 10:28
 */
public class NavigatorStack {
    private final static List<PageStub<? extends INavigatorPage>> mStack = new ArrayList<>(30);

    private static class PageStub<T extends INavigatorPage> {
        private String pageTag;
        private int routerId;//Page对应的MetaRouter信息
        private WeakReference<T> pageRefrence;

        PageStub(int routerId, T page) {
            if (page == null) {
                throw new NullPointerException("construct PageStub with null page");
            }

            this.routerId = routerId;
            this.pageRefrence = new WeakReference<>(page);
        }

        void setPageTag(String tag) {
            this.pageTag = tag;
        }

        String getPageTag() {
            return pageTag;
        }

        int getRouterId() {
            return routerId;
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

    private static void push(int routerId, INavigatorPage page) {
        if (page == null) {
            return;
        }

        mStack.add(new PageStub<>(routerId, page));
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
            if (page.equals(pageStub.getPage())) {
                return pageStub;
            }
        }

        return null;
    }

    /**
     * 构建导航栈
     */
    public static void onPageCreate(int routerId, INavigatorPage page) {
        push(routerId, page);
    }

    /**
     * 如果page处于栈顶，直接pop，并处理前一个page。若前一个page也已经被其他原因销毁，则执行pop
     * 【如果前一个page被销毁，必然是系统行为，此时系统会重建该页面，因此需要执行pop操作，保证本地导航栈与AMS一致】
     *
     * 若page不处于栈顶，说明是非正常销毁（系统内存不足），则执行pageStub的销毁操作
     */
    public static void onPageDestroy(INavigatorPage page) {
        if (page == null) {
            return;
        }

        PageStub<? extends INavigatorPage> pageStub = getPageStub(page);
        if (pageStub != null) {
            if (pageStub.equals(getTopPageStub())) {
                pop();

                /**
                 * 若前一个page是否处于销毁状态，则一并进行POP操作
                 */
                PageStub<? extends INavigatorPage> currentPageStub = getTopPageStub();
                if (currentPageStub != null && currentPageStub.isDestroyed()) {
                    pop();
                }
            }
        }
    }


    /**
     * 若回退层级大于等于导航栈大小，直接退出应用，可通过拦截器进行功能扩展
     * @param step 回退层级
     */
    public static void back(int step) {
        int stackSize = mStack.size();
        if (step <= 0) {
            return;
        }

        int fromIndex = stackSize - step;
        if (fromIndex <= 0) {//此时会退出APP
            fromIndex = 0;
        }

        List<PageStub<? extends INavigatorPage>> list = new ArrayList<>();
        for (int i = fromIndex; i < stackSize; i++) {
            list.add(mStack.get(i));
        }

        mStack.removeAll(list);

        for (int i = list.size()-1; i >= 0; i--) {
            list.get(i).finishPage();
        }
    }

    /**
     * 根据tag获取最后入栈的page
     * 返回负数，表明没有找到
     */
    public static int getPageIndex(String tag) {
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
    public static int getFirstPageIndex(String tag) {
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

    public static int getStackSize() {
        return mStack.size();
    }

    /**
     * 获取当前显示的页面
     */
    public static INavigatorPage getCurrentVisiablePage() {
        PageStub<? extends INavigatorPage> pageStub = getTopPageStub();
        if (pageStub == null) {
            return null;
        }

        return pageStub.getPage();
    }
}
