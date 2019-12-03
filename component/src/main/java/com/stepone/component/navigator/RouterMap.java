package com.stepone.component.navigator;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * FileName: RouterMap
 * Author: y.liang
 * Date: 2019-11-29 17:02
 */

final public class RouterMap {
    private final static Map<String, Map<String, Entry>> mRouterMaps = new HashMap<>(8);

    public static void addRouter(Entry entry) {
        Map<String, Entry> routerMap = mRouterMaps.get(entry.group);
        if (routerMap == null) {
            routerMap = new HashMap<>();
            mRouterMaps.put(entry.group, routerMap);
        }

        routerMap.put(entry.path, entry);
    }

    static Entry getRouter(String path, String group) {
        if (path == null) {
            return null;
        }

        return mRouterMaps.get(group == null ? "" : group).get(path);
    }

    static Entry parseUri(Uri uri) {
        return null;
    }


    public final static class Security {
        public final static int NONE = 0;
        public final static int USER_LOGIN = 1;
    }
    public static class Entry {
        /**
         * 组名 @NonNull
         */
        private String group;

        /**
         * path @NonNull
         */
        private String path;

        /**
         * 目标页面 @NonNull
         */
        private Class targetClazz;

        /**
         * 只有当targetClazz为Fragment的时候起作用
         */
        private Class parentClazz;

        /**
         * 安全级别
         */
        private int security;

        public Entry(@NonNull String group, @NonNull String path, @NonNull Class targetClazz, Class parentClazz, int security) {
            this.group = group;
            this.path = path;
            this.targetClazz = targetClazz;
            this.parentClazz = parentClazz;
            this.security = security;
        }

        public String getGroup() {
            return group;
        }

        public String getPath() {
            return path;
        }

        public Class getTargetClazz() {
            return targetClazz;
        }

        public Class getParentClazz() {
            return parentClazz;
        }

        public int getSecurity() {
            return security;
        }
    }

}
