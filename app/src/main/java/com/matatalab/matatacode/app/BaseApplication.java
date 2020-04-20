package com.matatalab.matatacode.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.matatalab.matatacode.Global;
import com.matatalab.matatacode.StartActivity;
import com.matatalab.matatacode.utils.MLog;
import com.matatalab.matatacode.utils.ProcessUtils;
import com.matatalab.matatacode.AppConst;
import com.matatalab.matatacode.Global;
import com.matatalab.matatacode.utils.MLog;
import com.matatalab.matatacode.utils.ProcessUtils;
import com.squareup.leakcanary.LeakCanary;
import com.matatalab.matatacode.utils.ToastUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * 创建人: hardy on 2017/02/28.
 * <p>
 * 描述:全局通用
 */
public class BaseApplication extends Application implements Thread.UncaughtExceptionHandler  {
    // 这里是主进程
    static final int PROCESS_MAIN = 0;
    private static final String TAG = BaseApplication.class.getSimpleName();
    private static BaseApplication mApp = null;
    private static Context mContext;
    private static Handler mMainThreadHandler;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // 进程标记 -1：未知 0：主进程(market所在进程) 1：连接进程
    private int mProcessFlag = -1;
    private ArrayList<WeakReference<OnLowMemoryListener>> mLowMemoryListeners;

    public static BaseApplication self() {
        return mApp;
    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 上下文
        mContext = this;
        mApp = this;
        Thread.setDefaultUncaughtExceptionHandler(this);
        mLowMemoryListeners = new ArrayList<WeakReference<OnLowMemoryListener>>();

        // 主线程handler
        mMainThreadHandler = new Handler();

        initProcessFlag();
        initQbSdk();
        //  if (LeakCanary.isInAnalyzerProcess(this)) {
        // This process is dedicated to LeakCanary for heap analysis.
        // You should not init your app in this process.
        //       return;
        //   }
        //  LeakCanary.install(this);

        // 初始化bugly,非测试环境才启动
//        if (Global.isDev() == false) {
        initBugly();
//        }


        // 截获全局异常提示，做更友好的提示。跟bugly不冲突。
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
        //mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //  Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理让系统默认的来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {

            }finally{

            }
            // 退出程序
            // android.os.Process.killProcess(android.os.Process.myPid());
            //非正常退出
            //  System.exit(1);

        }
    }
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        final String str = ex.toString();
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                MLog.td("tjl", "糟糕:"+str);
                Looper.loop();
            }
        }.start();
        return true;
    }
    /**
     * 初始化bugly
     */
    private void initBugly() {
        MLog.td("tjl", "--- initBugly ---");

        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = ProcessUtils.getProcessName(android.os.Process.myPid());

        // 附加信息


    }

    /**
     * 初始化进程状态
     */
    private void initProcessFlag(){

        // 主进程名
        String pkgName = this.getPackageName();

        // 当前启动的进程名
        String currentProcessName = ProcessUtils.getProcessName(android.os.Process.myPid());
        if (currentProcessName == null || !currentProcessName.startsWith(pkgName)) {
            currentProcessName = ProcessUtils.getProcessName(this, android.os.Process.myPid());
        }
        MLog.d(TAG, "currentProcessName = " + currentProcessName);
        // 主进程判断
        if (currentProcessName != null && (pkgName != null && currentProcessName.equals(pkgName))) {
            // 主进程才启动的服务
            if (pkgName != null && currentProcessName.equals(pkgName)) {

                mProcessFlag = PROCESS_MAIN;
            }
        }
    }

    public boolean isMainProcess() {
        return mProcessFlag == PROCESS_MAIN;
    }


    /**
     * 初始化X5浏览器
     */
    private void initQbSdk(){

        //非wifi情况下，主动下载x5内核

    }

    /**
     * Add a new listener to registered {@link OnLowMemoryListener}.
     *
     * @param listener The listener to unregister
     * @see OnLowMemoryListener
     */
    public void registerOnLowMemoryListener(OnLowMemoryListener listener) {
        if (listener != null) {
            mLowMemoryListeners.add(new WeakReference<OnLowMemoryListener>(listener));
        }
    }

    /**
     * Remove a previously registered listener
     *
     * @param listener The listener to unregister
     * @see OnLowMemoryListener
     */
    public void unregisterOnLowMemoryListener(OnLowMemoryListener listener) {
        if (listener != null) {
            int i = 0;
            while (i < mLowMemoryListeners.size()) {
                final OnLowMemoryListener l = mLowMemoryListeners.get(i).get();
                if (l == null || l == listener) {
                    mLowMemoryListeners.remove(i);
                } else {
                    i++;
                }
            }
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        int i = 0;
        while (i < mLowMemoryListeners.size()) {
            final OnLowMemoryListener listener = mLowMemoryListeners.get(i).get();
            if (listener == null) {
                mLowMemoryListeners.remove(i);
            } else {
                listener.onLowMemoryReceived();
                i++;
            }
        }
    }


    /**
     * Used for receiving low memory system notification. You should definitely
     * use it in order to clear caches and not important data every time the
     * system needs memory.
     *
     * @author Cyril Mottier
     * //     * @see GDApplication#registerOnLowMemoryListener(OnLowMemoryListener)
     * //     * @see GDApplication#unregisterOnLowMemoryListener(OnLowMemoryListener)
     */
    public static interface OnLowMemoryListener {

        /**
         * Callback to be invoked when the system needs memory.
         */
        public void onLowMemoryReceived();
    }

}


