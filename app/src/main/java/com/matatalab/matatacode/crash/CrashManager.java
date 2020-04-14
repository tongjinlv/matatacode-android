package com.matatalab.matatacode.crash;


public class CrashManager {

    /**
     * 主动上报Catch到的异常到Bugly上（主动上报Catched异常初始化SDK后，用户可以通过
     * postCatchedException(Throwable thr)接口主动上报Catched的异常，该异常不会计算的Crash率。）
     *
     * @param e
     */
    public static void postCatchedException2Bugly(Throwable e) {
        try {
        } catch (Throwable ex) {
            //ex.printStackTrace();
        }
    }
}
