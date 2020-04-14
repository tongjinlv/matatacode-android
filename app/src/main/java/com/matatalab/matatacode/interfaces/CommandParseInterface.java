package com.matatalab.matatacode.interfaces;

/**
 * @author hardy
 * @name MatataCode
 * @class name：com.matatalab.matatacode.interfaces
 * @class describe: 代码解析接口，blockly命令转蓝牙命令
 * @time 2019/9/28 17:16
 * @change
 * @chang time
 * @class describe
 */
public interface CommandParseInterface {


    /**
     * 获取停止蓝牙命令
     *
     * @return
     */
    byte[] getStopBtCommand();

    /**
     * 获取小车进入dfu蓝牙命令
     *
     * @return
     */
    byte[] getBotDfuBtCommand();

    /**
     * 获取控制台进入dfu蓝牙命令
     *
     * @return
     */
    byte[] getControllerDfuBtCommand();

    /**
     * 解析Js消息，转换成蓝牙命令
     * 转换协议：https://shimo.im/sheets/KjW9yHdYkP69xHgP/MODOC/
     *
     * @param commandStr ：js发送过来的命令参数
     * @return ：蓝牙命令二进制数组
     */
    byte[] parseRunCodeToBtCommand(String commandStr);

}
