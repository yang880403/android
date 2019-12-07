package com.stepone.component.navigator;

import android.net.Uri;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * FileName: RouterMap
 * Author: y.liang
 * Date: 2019-11-29 17:02
 */

final public class RouterMap {
    //分组存储路由
    private final static Map<String, Map<String, MetaRouter>> mRouterMaps = new HashMap<>(8);
    private final static SparseArray<MetaRouter> mRouterCache = new SparseArray<>();

    public static void addRouter(MetaRouter metaRouter) {
        Map<String, MetaRouter> routerMap = mRouterMaps.get(metaRouter.group);
        if (routerMap == null) {
            routerMap = new HashMap<>();
            mRouterMaps.put(metaRouter.group, routerMap);
        }

        routerMap.put(metaRouter.path, metaRouter);
    }

    static MetaRouter getRouter(String path, String group) {
        if (path == null) {
            return null;
        }

        MetaRouter metaRouter = mRouterMaps.get(group == null ? "" : group).get(path);
        if (metaRouter != null) {
            mRouterCache.put(metaRouter.hashCode(), metaRouter);
        }

        return metaRouter;
    }

    static MetaRouter getRouter(int cacheId) {
        return mRouterCache.get(cacheId);
    }

    static MetaRouter parseUri(Uri uri) {
        return null;
    }


    public final static class Security {
        public final static int NONE = 0;
        public final static int USER_LOGIN = 1;
    }
    public static class MetaRouter {
        /**
         * 使用MetaRouter的hash值作为routerID即可
         */
        public final static String KEY_ROUTER_ID_INT = "__KEY_ROUTER_ID_INT__";
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

        public MetaRouter(@NonNull String group, @NonNull String path, @NonNull Class targetClazz, Class parentClazz, int security) {
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
