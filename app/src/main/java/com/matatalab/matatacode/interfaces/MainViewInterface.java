package com.matatalab.matatacode.interfaces;

/**
 * @author hardy
 * @name MatataCode
 * @class name：com.matatalab.matatacode.interfaces
 * @class describe: 主页面的view控制接口
 * @time 2019/10/11 17:13
 * @change
 * @chang time
 * @class describe
 */
public interface MainViewInterface {
    /**
     * 设置主按钮点击使能
     *
     * @param enable
     */
    void setMainButtonEnable(boolean enable);

    /**
     * 显示隐藏loading
     * @param isShow
     */
    void showLoading(boolean isShow);
}
