package com.matatalab.matatacode.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.matatalab.matatacode.Mdialog;
import com.matatalab.matatacode.R;
import com.matatalab.matatacode.app.BaseApplication;

/**
 * Toast提示类，统一Toast背景
 * <p>
 * 注意：toast做了单例处理，如果是在不同线程调用toast之前，一定要先调用cancel方法，否则并发时候会出现非原创线程更新UI异常！
 */
public class DialogUtils {

    private static Toast mToast;
    private static TextView mTvTextInfo;



    /**
     * 直接展示一个Toast
     *
     * @param context
     * @param text
     */
    public static void show(Context context, String text) {
        show(text, Toast.LENGTH_LONG);
    }

    /**
     * 直接展示一个Toast
     *
     * @param context
     * @param text
     */
    public static void showShort(Context context, String text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 直接展示一个Toast
     *
     * @param StringId
     */
    public static void show(int StringId) {

        show(StringId, Toast.LENGTH_LONG);

    }

    /**
     * 直接展示一个Toast
     *
     * @param StringId
     */
    public static void show(int StringId, int duration) {
        String infoStr = null;
        try {
            infoStr = BaseApplication.self().getResources().getString(StringId);
        } catch (Resources.NotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if (infoStr != null) {
            show(infoStr, duration);
        }
    }

    /**
     * 直接展示一个Toast
     *
     * @param text
     */
    public static void show(String text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 直接展示一个Toast
     *
     * @param context
     * @param text
     * @param duration
     */
    public static void show(Context context, String text, int duration) {
        show(text, duration);
    }

    /**
     * 直接展示一个Toast
     *
     * @param text
     * @param duration
     */
    public static void show(final String text, final int duration) {

        // cancel();
        if ((text == null) || (text.trim().isEmpty() == true)) {
            return;
        }
        // 主线程中来完成内容，否则容易报android.view.ViewRootImpl$CalledFromWrongThreadException
//            HandlerUtils.getMainHandler().post(new Runnable() {
//                @Override
//                public void run() {
        makeText(text, duration);
        if (mToast != null) {
            mToast.show();
        }
//                }
//            });
    }

    /**
     * 返回一个Toast对象
     *
     * @param text
     * @param duration
     * @return
     */
    private static Toast makeText(String text, int duration) {

        if (mToast == null) {
            synchronized (DialogUtils.class) {
                if (mToast == null) {
                    mToast = new Toast(BaseApplication.self());
                    LayoutInflater inflater = LayoutInflater.from(BaseApplication.self());
                    View layout = inflater.inflate(R.layout.toast_common_only_text_layout, null);

                    mTvTextInfo = (TextView) layout.findViewById(R.id.tv_text_info);
                    mToast.setGravity(/* Gravity.FILL_HORIZONTAL | */Gravity.BOTTOM, 0, 50);
//					  mToast.setGravity(Gravity.CENTER, 0, 0);
                    mToast.setMargin(0, 0);
                    mToast.setView(layout);
                    mToast.setDuration(duration);
                }
            }
        }
        mTvTextInfo.setText(text);

        return mToast;
    }

    /**
     * 隐藏弹框
     */
    public static void cancel() {
        // 主线程中来完成UI内容，否则容易报android.view.ViewRootImpl$CalledFromWrongThreadException
//            HandlerUtils.getMainHandler().post(new Runnable() {
//                @Override
//                public void run() {
        if (mToast != null) {
            synchronized (DialogUtils.class) {
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;// 这儿先别置空，有个crash初步和这有关系(crashId:#502)
                    // //这行不能注释掉，toastcancel后不能复用了！做线程同步处理。
                }
            }
        }
//                }
//            });
    }
}