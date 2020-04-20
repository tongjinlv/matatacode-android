package com.matatalab.matatacode;

import android.bluetooth.BluetoothAdapter;

import com.inuker.bluetooth.library.BluetoothClient;
import com.matatalab.matatacode.model.ChaquoPython;
import com.matatalab.matatacode.model.MyBluetooth;

public class AppConst {

    // -------------- 对外接口常量 --------------


    // -------------- 对外接口常量 结束 --------------


    // -------------- 应用内全局常量 --------------

    /**
     * Bugly(bugly.qq.com)注册产品获取的AppId
     */
    public static final String BUGLY_APP_ID = "5bcea21545";

    /**
     * 蓝牙名称
     * 老小车
     */
    public static final String BT_NAME_BOT_OLD = "Matalab";
    /**
     * 蓝牙名称
     * 新小车
     */
    public static final String BT_NAME_BOT_NEW = "MatataBot";
    /**
     * 蓝牙名称
     * 控制器
     */
    public static final String BT_NAME_CON = "MatataCon";




    /**
     * 蓝牙名称基础
     * 设备名称以此开始
     */
    public static final String BT_NAME_BASE = "Mata";

    /**
     * 童金吕使用的几个变量
     *
     */
    public static final String BT_APP_NAME_ALL = "Matalab|MatataCon|MatataBot";
    public static final String BT_DFU_NAME_ALL = "MataDfu|mdfubot|mdfutow|mdfucon";
    public static final String BT_APP_NAME_OLD="Matalab";
    public static final String BT_APP_NAME_CON="MatataCon";
    public static final String BT_APP_NAME_BOT="MatataBot";
    public static final String BT_DFU_NAME_OLD="MataDfu";
    public static final String BT_DFU_NAME_TOWER="mdfutow";
    public static final String BT_DFU_NAME_BOT="mdfubot";
    public static final String BT_DFU_NAME_CON="mdfucon";
    public static final String BT_BE_NAME_ALL[]={"MatataCon|mdfucon","MatataBot|mdfubot","Matalab|mdfubot"};
    public static final String BT_BE_PNAME_ALL[]={"MatataCon|mdfucon","MatataBot|mdfubot","Matalab|mdfubot"};
    public static final String BT_BE_NAME_BOT="MatataBot|mdfubot";
    public static final String BT_BE_NAME_OLD="Matalab|mdfubot";
    public static String EXAMPLE_PATH="";
    public static final String UNTITLED_NAME="     ";
    public static String APP_DOWNLOAD_URL="http://typeecho.trtos.com/MatataCode_release_v";
    public static String All_RIGHT_TEXT="This application is built on Blockly. All rights reserved \n @Matatalab.";
    public static String versionName="";
    public static boolean NeedUpdate=false;
    public static BluetoothAdapter bluetoothAdapter;
    public static MyBluetooth myBluetooth;
    public  static String  devMac;
    public  static String lang;
    public static String runDirPath;
    public static BluetoothClient mClient;
    public static String DeviceName;
    public static String DeviceMac;
    public static ChaquoPython chaquoPython;
    public static final String CODE_HEARD1= "# -*- coding: utf-8 -*-\r\nimport math\n" +
            "matatabot = MatataBotJavaClass(\"from python\")\r\nmatatabot.music.play_treble(\"do\",\"meter1\")\r\nmatatabot.music.play_treble(\"do\",\"meter1\")\r\n" +
            "matatabot.music.test2()\r\n"+
            "matatabot.music.test2()\r\n";
    public static final String CODE_HEARD= "# -*- coding: utf-8 -*-\nimport math\n" +
            "cli = JavaClass(\"from python\")\n" +
            "matatabot = MatataBotJavaClass(\"from python\")\n" +
            "cli.test()\n";
    public static final String UUID_SERVICE = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    public static final String UUID_NOTIFY = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    public static final String UUID_WRITE = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    public static byte[] onNotify;
    public static Boolean pythonRun=false;

    /**
     * 蓝牙DFU名称
     * 旧灯塔和旧小车
     */
    public static final String BT_NAME_DFU = "MataDfu";
    /**
     * 蓝牙DFU名称
     * 新小车
     */
    public static final String BT_NAME_DFU_BOT = "mdfubot";
    /**
     * 蓝牙DFU名称
     * 新的灯塔
     */
    public static final String BT_NAME_DFU_TOW = "mdfutow";
    /**
     * 蓝牙DFU名称
     * 控制器
     */
    public static final String BT_NAME_DFU_CON = "mdfucon";


    /**
     * 升级类型 —— bot
     */
    public static final int UPGRADE_TYPE_BOT = 100;
    /**
     * 升级类型 —— tower
     */
    public static final int UPGRADE_TYPE_TOWER = 101;
    /**
     * 升级类型 —— controller
     */
    public static final int UPGRADE_TYPE_CONTROLLER = 102;


    /**
     * 升级文件ID —— bot
     */
    public static final int UPGRADE_RAW_ID_BOT = R.raw.car_update;
    /**
     * 升级文件ID —— tower
     */
    public static final int UPGRADE_RAW_ID_TOWER = R.raw.tower_update;
    /**
     * 升级文件ID —— controller
     */
    public static final int UPGRADE__RAW_ID_CONTROLLER = R.raw.mata_con;


    /**
     * 视频链接 —— youtube
     */
    public static final String VIDEO_URL_YOUTU = "https://youtu.be/zBRkFO3qnN8/";

    /**
     * 视频链接 —— bilibili
     */
    public static final String VIDEO_URL_BILIBILI = "https://www.bilibili.com/video/av68882667/";


}
