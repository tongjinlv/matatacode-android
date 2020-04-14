package com.matatalab.matatacode.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class HandlerUtils {

    private static final String TAG = "HandlerUtils";

    private static Handler mManinHandler;

    private static Object mMainHandlerLock = new Object();

    private static final AtomicInteger currentHandlerIndex = new AtomicInteger(0);

    /**
     * 取得UI线程Handler
     *
     * @return
     */
    public static Handler getMainHandler() {
        if (mManinHandler == null) {
            synchronized (mMainHandlerLock) {
                if (mManinHandler == null) {
                    mManinHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return mManinHandler;
//		return new Handler(Looper.getMainLooper());
    }

    private static Map<String, Handler> handlerMap = Collections.synchronizedMap(new HashMap<String, Handler>());

    /**
     * 取得一个非主线Handler，每次获得这个handler都会产生一个线程，所以获得到的这个handler必须做复用！！！
     *
     * @param threadName
     * @return
     */
    public static Handler getHandler(String threadName) {
        if (TextUtils.isEmpty(threadName)) {
            threadName = "default-thread";
        }
        MLog.d(TAG, "getHandler(" + threadName + ") exists at cache:" + handlerMap.containsKey(threadName));
        Handler handler = null;
        if (handlerMap.containsKey(threadName)) {
            handler = handlerMap.get(threadName);
        } else {
            HandlerThread handlerThread = new HandlerThread(threadName);
            handlerThread.start();
            Looper loop = handlerThread.getLooper();
            if (loop != null) {
                handler = new Handler(loop);
                handlerMap.put(threadName, handler);
            } else {
                handlerThread.quit();
                handlerThread = null;
            }
        }
        return handler;
    }
}