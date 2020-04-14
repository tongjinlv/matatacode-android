package com.matatalab.matatacode.interfaces;

/**
 * @author hardy
 * @name MatataCode
 * @class name：com.matatalab.matatacode.interfaces
 * @class describe: blockly 页面回调
 * @time 2019/10/2 19:51
 * @change
 * @chang time
 * @class describe
 */
public interface BlocklyViewInterface {
    /**
     * 显示信息
     *
     * @param message
     */
    void showMessage(String message);
    /**
     * 显示loading
     *
     * @param isShow
     */
    void showLoading(boolean isShow);
    /**
     * 更新蓝牙状态
     *
     * @param isConnect
     */
    void updateBtStatus(boolean isConnect);

    /**
     * 设置help显示隐藏
     *
     * @param isShow
     */
    void setHelpViewShow(boolean isShow);

    /**
     * 显示断开连接 view
     */
    void showDisConnectView();

    /**
     * 显示保存code view
     * @param codes
     */
    void showSaveCodeView(final String codes);

    /**
     * 显示载入code view
     */
    void showLoadCodeView();

    /**
     * 显示删除code view
     */
    void showDeleteCodeView();

    /**
     * 显示查询code view
     */
    void showQueryCodeView();

    /**
     * 显示BT错误对话框信息
     *
     * @param message
     */
    void showBtErrDialogMessage(String message);

}
