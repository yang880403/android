package com.stepone.uikit.view.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * FileName: DisplayUtils
 * Author: y.liang
 * Date: 2019-12-06 18:03
 */

public class DisplayUtils {
    public static int dp2px(Context context, float value) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
    public static int sp2px(Context context, float value) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.getResources().getDisplayMetrics());
    }

    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);// + 0.5f是为了让结果四舍五入
    }

    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
