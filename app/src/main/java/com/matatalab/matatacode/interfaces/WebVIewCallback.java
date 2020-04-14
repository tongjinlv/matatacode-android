package com.matatalab.matatacode.interfaces;

import android.widget.Button;

/**
 * @author hardy
 * @name MatataCode
 * @class name：com.matatalab.matatacode.interfaces
 * @class describe: 网页回调
 * @time 2019/9/28 20:54
 * @change
 * @chang time
 * @class describe
 */
public interface WebVIewCallback {
    /**
     * 发送网页命令
     * @param type 类型
     * @param msg 命令值
     */
    void sendWebCommand(String type, String msg);
}
