package com.matatalab.matatacode.interfaces;

/**
 * @author hardy
 * @name MatataCode
 * @class name：com.matatalab.matatacode.interfaces
 * @class describe: 升级回调View
 * @time 2019/10/4 9:07
 * @change
 * @chang time
 * @class describe
 */
public interface TestViewInterface {
    /**
     * 进度条状态
     *
     * @param indeterminate
     */
    void updateProgressIndeterminate(boolean indeterminate);

    /**
     * 进度条数值
     *
     * @param percent
     */
    void updateProgressPercent(int percent);

    /**
     * 显示信息
     *
     * @param message
     */
    void showMessage(String message);
    /**
     * 显示升级信息
     *
     * @param message
     */
    void showUpgradeMessage(String message);

    /**
     * 等待进入dfu模式
     *
     * @param message
     */
    void showWaitIntoDfuMode(String message);

    /**
     * 显示BT错误对话框信息
     *
     * @param message
     */
    void showBtErrDialogMessage(String message);
}
