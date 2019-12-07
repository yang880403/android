package com.stepone.component.navigator;

/**
 * FileName: ViewDispatcher
 * Author: y.liang
 * Date: 2019-11-29 15:20
 */

import android.content.Intent;

import androidx.fragment.app.Fragment;

/**
 * 根据Intent和RouterMap进行Fragment分发
 */
public class ViewDispatcher {
    public static Fragment resolveFragment(Intent intent) {
        int routerId = intent.getIntExtra(RouterMap.MetaRouter.KEY_ROUTER_ID_INT, 0);

        RouterMap.MetaRouter metaRouter = RouterMap.getRouter(routerId);
        if (metaRouter == null) {
            return null;
        }

        Class clazz = metaRouter.getTargetClazz();
        if (clazz == null || !(Fragment.class.isAssignableFrom(clazz))) {
            return null;
        }

        try {
            return (Fragment) clazz.newInstance();
        } catch (Throwable t) {
            return null;
        }
    }
}
