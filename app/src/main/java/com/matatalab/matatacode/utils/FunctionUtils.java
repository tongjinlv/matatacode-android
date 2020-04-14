package com.matatalab.matatacode.utils;

import android.content.Context;

import java.util.Locale;

/**
 * 通用功能辅助类
 * 日期：2019-3-9
 */
public class FunctionUtils {

    private static final String TAG = FunctionUtils.class.getSimpleName();

    /**
     * 判断当前语言环境是否是中文环境
     * @param context
     * @return
     */
    public static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }

}
