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
        int routerId = intent.getIntExtra(RouterMap.Entry.KEY_ENTRY_ID, 0);

        RouterMap.Entry entry = RouterMap.getRouter(routerId);
        if (entry == null) {
            return null;
        }

        Class clazz = entry.getTargetClazz();
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
