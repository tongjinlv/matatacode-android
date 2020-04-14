package com.matatalab.matatacode.presenter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.matatalab.matatacode.AppConst;
import com.matatalab.matatacode.R;
import com.matatalab.matatacode.ble.BleController;
import com.matatalab.matatacode.ble.callback.ConnectCallback;
import com.matatalab.matatacode.ble.callback.OnWriteCallback;
import com.matatalab.matatacode.ble.callback.ScanCallback;
import com.matatalab.matatacode.dfu.DfuModel;
import com.matatalab.matatacode.interfaces.CommandParseInterface;
import com.matatalab.matatacode.interfaces.UpgradeViewInterface;
import com.matatalab.matatacode.model.CommandParse;
import com.matatalab.matatacode.utils.MLog;

import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuProgressListenerAdapter;

/**
 * @author hardy
 * @name MatataCode
 * @class name：com.matatalab.matatacode.presenter
 * @class describe: 升级逻辑处理
 * @time 2019/10/5 11:21
 * @change
 * @chang time
 * @class describe
 */
public class UpgradePresenter {
    private static final String TAG = UpgradePresenter.class.getSimpleName();

    private volatile static UpgradePresenter mUpgradePresenter = null;

    private Context mContext;
    private CommandParseInterface mCommandParse;

    private DfuModel mDfuModel;

    private BleController mBleController;

    private UpgradeViewInterface mUpgradeViewInterface;

    /**
     * 搜索蓝牙设备最低信号强度
     */
    private static final int BT_RSSI_NIMI_VALUE = -70; // -90;
    // 搜索信号最强的蓝牙设备
    private BluetoothDevice mBluetoothDevice;
    private int mCurrentRssi = 0;

    private int mUpgradeType;
    private boolean isDfuMode;
    private boolean sendCommandAgain;
    /**
     * 进入dfu模式
     */
    private static final int MESSAGE_START_DFU_MODE = 1001;
    /**
     * 启动升级
     */
    private static final int MESSAGE_START_UPGRADE = 1002;
    /**
     * 显示等待对话框
     */
    private static final int MESSAGE_SHOW_WAIT_INFO = 1003;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MLog.d(TAG, "handleMessage --- msg = " + msg);
            switch (msg.what) {
                case MESSAGE_START_DFU_MODE:
                    switch (mUpgradeType) {
                        case AppConst.UPGRADE_TYPE_BOT:
                            sendCommandToBt(mCommandParse.getBotDfuBtCommand());
                            break;

                        case AppConst.UPGRADE_TYPE_CONTROLLER:
                            sendCommandToBt(mCommandParse.getControllerDfuBtCommand());
                            break;

                        default:
                            break;
                    }

                    break;
                case MESSAGE_START_UPGRADE:
                    startDfuUpgrade(mUpgradeType);
                    break;

                case MESSAGE_SHOW_WAIT_INFO:
                    disconnectBt();
                    if (mUpgradeViewInterface != null) {
                        switch (mUpgradeType) {
                            case AppConst.UPGRADE_TYPE_BOT:
                                mUpgradeViewInterface.showWaitIntoDfuMode(mContext.getResources().getString(R.string.DFU_RESULT_ERROR));
                                break;

                            case AppConst.UPGRADE_TYPE_CONTROLLER:
                                mUpgradeViewInterface.showWaitIntoDfuMode(mContext.getResources().getString(R.string.DFU_RESULT_ERROR));
                                break;

                            default:
                                break;
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    };


    private UpgradePresenter(Context context) {
        MLog.d(TAG, "--- UpgradePresenter ---");
        mContext = context;
        init();
    }

    /**
     * 单例
     *
     * @return
     */
    public static UpgradePresenter getInstance(Context context) {
        if (mUpgradePresenter == null) {
            synchronized (UpgradePresenter.class) {
                if (mUpgradePresenter == null) {
                    mUpgradePresenter = new UpgradePresenter(context);
                }
            }
        }
        return mUpgradePresenter;
    }

    private void init() {
        mCommandParse = CommandParse.getInstance();

        mDfuModel = new DfuModel(mContext, mDfuProgressListener);
    }


    public void exit() {
        //释放资源
        if (mBleController != null) {
            mBleController.ScanBle(false, null);
            mBleController = null;
        }

        mDfuModel = null;
        mUpgradePresenter = null;
    }

    public void setUpgradeViewInterface(UpgradeViewInterface upgradeViewInterface) {
        mUpgradeViewInterface = upgradeViewInterface;
    }

    /**
     * 启动设备进入DFU状态
     *
     * @param upgradeType
     */
    public void startDeviceEnterDfu(int upgradeType) {
        MLog.d(TAG, "startDeviceEnterDfu --- upgradeType = " + upgradeType);

        mUpgradeType = upgradeType;
        isDfuMode = false;
        sendCommandAgain = true;

        if (isBtConnect() == false) {
            MLog.d(TAG, "startDeviceEnterDfu --- bt not connect.");
            scanAndConnectDevices();
            return;
        }
        MLog.d(TAG, "startDeviceEnterDfu --- isDfuMode = " + isDfuMode);


        mHandler.sendEmptyMessageDelayed(MESSAGE_START_DFU_MODE, 1000);


    }

    /**
     * 启动DFU
     */
    public void startDeviceEnterUpgrade(int upgradeType) {
        MLog.d(TAG, "startDeviceEnterUpgrade --- upgradeType = " + upgradeType);
        mUpgradeType = upgradeType;
        isDfuMode = true;

        if (isBtConnect() == false) {
            MLog.d(TAG, "startDeviceEnterUpgrade --- bt not connect.");
            scanAndConnectDevices();
            return;
        }

        MLog.d(TAG, "startDeviceEnterUpgrade --- isDfuMode = " + isDfuMode);
        mHandler.sendEmptyMessageDelayed(MESSAGE_START_UPGRADE, 1000);


    }

    // 蓝牙操作 --------------------------------------------------------------------------------------

    /**
     * 蓝牙连接状态
     */
    private boolean isBtConnect() {
        if (mBleController == null) {
            mBleController = BleController.getInstance().initble(mContext);
        }
        return mBleController.isConnected();
    }

    /**
     * 连接蓝牙
     */
    private void connectBt() {
        if (mBleController == null) {
            mBleController = BleController.getInstance().initble(mContext);
        }
        scanAndConnectDevices();

    }

    /**
     * 断开蓝牙连接
     */
    private void disconnectBt() {
        if (mBleController == null) {
            mBleController = BleController.getInstance().initble(mContext);
        }
        //开始
        if (mBleController.isConnected() == false) {
            MLog.d(TAG, "disconnectBt --- is not connect.");
            return;
        }
        mBleController.disConnectBleConn();
    }

    /**
     * 发送命令到蓝牙设备
     *
     * @param commands
     */
    private void sendCommandToBt(byte[] commands) {
        MLog.d(TAG, "sendCommandToBt --- commands = ", commands);
        if (commands == null) {
            MLog.e(TAG, "sendCommandToBt commands is null!");
            return;
        }
        sendCommand(commands);

    }

    /**
     * 扫描和绑定设备
     */
    private void scanAndConnectDevices() {
        MLog.d(TAG, "--- scanAndConnectDevices ---");

        if (mBleController == null) {
            mBleController = BleController.getInstance().initble(mContext);
        }

        if (mBleController.isBtEnabled() == false) {
            if (mUpgradeViewInterface != null) {
                mUpgradeViewInterface.showBtErrDialogMessage(mContext.getResources().getString(R.string.DFU_RESULT_ERROR));
            }
            return;
        }

        //开始
        if (mBleController.isConnected() == true) {
            MLog.d(TAG, "scanAndConnectDevices --- already connect.");
            return;
        }

        mCurrentRssi = BT_RSSI_NIMI_VALUE;
        mBluetoothDevice = null;

        mBleController.ScanBle(true, new ScanCallback() {
            @Override
            public void onSuccess() {
                if (mBluetoothDevice != null) {
                    MLog.d(TAG, "已经搜索到设备");
                    mBleController.Connect(mBluetoothDevice.getAddress(), new ConnectCallback() {
                        @Override
                        public void onConnSuccess() {
                            //todo
                            MLog.d(TAG, "--- onConnSuccess ---");
//                            ToastUtils.show("连接成功");
                            mBleController.setNotAutoConnect(true);

                            MLog.d(TAG, "onConnSuccess --- isDfuMode = " + isDfuMode);
                            if (isDfuMode == true) {
                                mHandler.sendEmptyMessageDelayed(MESSAGE_START_UPGRADE, 1000);
                            } else {
                                mHandler.sendEmptyMessageDelayed(MESSAGE_START_DFU_MODE, 1000);
                            }
                        }

                        @Override
                        public void onConnFailed() {
                            if ((isDfuMode == false) && (mUpgradeViewInterface != null)) {
                                mUpgradeViewInterface.showUpgradeMessage(mContext.getResources().getString(R.string.DFU_DEVICE_SIGNAL_WEAK));
                            }
                            MLog.d(TAG, "--- onConnFailed ---");
                            MLog.d(TAG, "连接断开或连接失败");
                        }
                    });

                } else if (mBleController.isConnected()) {
                    MLog.d(TAG, "设备已连接");

                } else {
                    if (mUpgradeViewInterface != null) {
                        if (isDfuMode == true) {
                            switch (mUpgradeType) {
                                case AppConst.UPGRADE_TYPE_BOT:
                                    mUpgradeViewInterface.showUpgradeMessage(mContext.getResources().getString(R.string.DFU_DEVICE_SIGNAL_WEAK));
                                    break;

                                case AppConst.UPGRADE_TYPE_TOWER:
                                    mUpgradeViewInterface.showUpgradeMessage(mContext.getResources().getString(R.string.DFU_DEVICE_SIGNAL_WEAK));
                                    break;

                                case AppConst.UPGRADE_TYPE_CONTROLLER:
                                    mUpgradeViewInterface.showUpgradeMessage(mContext.getResources().getString(R.string.DFU_DEVICE_SIGNAL_WEAK));
                                    break;

                                default:
                                    break;
                            }
                        } else {
                            switch (mUpgradeType) {
                                case AppConst.UPGRADE_TYPE_BOT:
                                    mUpgradeViewInterface.showWaitIntoDfuMode(mContext.getResources().getString(R.string.DFU_DEVICE_SIGNAL_WEAK));
                                    break;
                                case AppConst.UPGRADE_TYPE_CONTROLLER:
                                    mUpgradeViewInterface.showWaitIntoDfuMode(mContext.getResources().getString(R.string.DFU_DEVICE_SIGNAL_WEAK));
                                    break;

                                default:
                                    break;
                            }
                        }
                    }
                    MLog.d(TAG, "没有可连接的设备");
                }
            }

            @Override
            public void onScanning(BluetoothDevice device, int rssi, byte[] scanRecord) {
                if ((device != null) && (TextUtils.isEmpty(device.getName()) == false)) {
                    MLog.d(TAG, "onScanning xxxx device.getName() = " + device.getName() + ", rssi = " + rssi);

                    // dfu名称需要区分
                    if (isDfuMode == true) {
                        switch (mUpgradeType) {
                            case AppConst.UPGRADE_TYPE_BOT:
                                if ((device.getName().startsWith(AppConst.BT_NAME_DFU)) || (device.getName().startsWith(AppConst.BT_NAME_DFU_BOT))) {
                                    getBastDevice(device, rssi);
                                }
                                break;

                            case AppConst.UPGRADE_TYPE_TOWER:
                                if ((device.getName().startsWith(AppConst.BT_NAME_DFU)) || (device.getName().startsWith(AppConst.BT_NAME_DFU_TOW))) {
                                    getBastDevice(device, rssi);
                                }
                                break;

                            case AppConst.UPGRADE_TYPE_CONTROLLER:
                                if (device.getName().startsWith(AppConst.BT_NAME_DFU_CON)) {
                                    getBastDevice(device, rssi);
                                }
                                break;

                            default:
                                break;
                        }

                    } else {
                        switch (mUpgradeType) {
                            case AppConst.UPGRADE_TYPE_BOT:
                                if ((device.getName().startsWith(AppConst.BT_NAME_BOT_OLD)) || (device.getName().startsWith(AppConst.BT_NAME_BOT_NEW))) {
                                    getBastDevice(device, rssi);
                                }
                                break;

                            case AppConst.UPGRADE_TYPE_CONTROLLER:
                                if (device.getName().startsWith(AppConst.BT_NAME_CON)) {
                                    getBastDevice(device, rssi);
                                }
                                break;

                            default:
                                break;
                        }

                    }
                }
            }
        });
    }

    /**
     * 获取信号最佳蓝牙设备
     *
     * @param device
     * @param rssi
     */
    private void getBastDevice(BluetoothDevice device, int rssi) {
        MLog.d(TAG, "getBastDevice --- rssi：" + rssi);
        if ((rssi > BT_RSSI_NIMI_VALUE) && (rssi > mCurrentRssi)) {
            MLog.d(TAG, "getBastDevice --- mCurrentRssi = " + mCurrentRssi);
            mCurrentRssi = rssi;
            mBluetoothDevice = device;
        }
    }

    /**
     * 发送命令
     *
     * @param commands
     */
    private void sendCommand(byte[] commands) {
        MLog.d(TAG, "sendCommand commands：", commands);
        if ((mBleController != null) && (mBleController.isConnected())) {
            mBleController.WriteBuffer(commands, new OnWriteCallback() {
                @Override
                public void onSuccess() {
                    MLog.i(TAG, "onSuccess");
//                    disconnectBt();
                    mBleController.setNotAutoConnect(true);
                    mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_WAIT_INFO, 2000);
                }

                @Override
                public void onFailed(int state) {
//                    ToastUtils.show("send failed");
                    MLog.e(TAG, "onFailed" + state);
                    if(sendCommandAgain == true) {
                        sendCommandAgain = false;
                        mHandler.sendEmptyMessageDelayed(MESSAGE_START_DFU_MODE, 1000);
                    }else{
                        if (mUpgradeViewInterface != null) {
                            mUpgradeViewInterface.showUpgradeMessage(mContext.getResources().getString(R.string.DFU_DEVICE_SIGNAL_WEAK));
                        }
                    }
                }
            });
        } else {
            MLog.d(TAG, "not connect");
            if (mUpgradeViewInterface != null) {
                mUpgradeViewInterface.showUpgradeMessage(mContext.getResources().getString(R.string.DFU_DEVICE_SIGNAL_WEAK));
            }
        }
    }


    // 升级 --------------------------------------------------------------------------------------

    /**
     * 启动升级
     *
     * @param upgradeType
     */
    private void startDfuUpgrade(int upgradeType) {
        MLog.d(TAG, "startDfuUpgrade --- upgradeType = " + upgradeType);
        int upgradeRawId = 0;
        switch (upgradeType) {
            case AppConst.UPGRADE_TYPE_BOT:
                upgradeRawId = AppConst.UPGRADE_RAW_ID_BOT;
                break;

            case AppConst.UPGRADE_TYPE_TOWER:
                upgradeRawId = AppConst.UPGRADE_RAW_ID_TOWER;
                break;

            case AppConst.UPGRADE_TYPE_CONTROLLER:
                upgradeRawId = AppConst.UPGRADE__RAW_ID_CONTROLLER;
                break;

            default:
                break;
        }
        mDfuModel.startUpload(mBluetoothDevice, upgradeRawId);

        if (mUpgradeViewInterface != null) {
            mUpgradeViewInterface.updateProgressIndeterminate(false);
            mUpgradeViewInterface.updateProgressPercent(0);
        }
    }

    private final DfuProgressListener mDfuProgressListener = new DfuProgressListenerAdapter() {
        @Override
        public void onDeviceConnecting(final String deviceAddress) {
            MLog.d(TAG, "onDeviceConnecting --- deviceAddress = " + deviceAddress);
            if (mUpgradeViewInterface != null) {
                mUpgradeViewInterface.updateProgressIndeterminate(true);
            }
        }

        @Override
        public void onDfuProcessStarting(final String deviceAddress) {
            MLog.d(TAG, "onDfuProcessStarting --- deviceAddress = " + deviceAddress);
            if (mUpgradeViewInterface != null) {
                mUpgradeViewInterface.updateProgressIndeterminate(true);
            }
        }

        @Override
        public void onEnablingDfuMode(final String deviceAddress) {
            MLog.d(TAG, "onEnablingDfuMode --- deviceAddress = " + deviceAddress);
            if (mUpgradeViewInterface != null) {
                mUpgradeViewInterface.updateProgressIndeterminate(true);
            }
        }

        @Override
        public void onFirmwareValidating(final String deviceAddress) {
            MLog.d(TAG, "onFirmwareValidating --- deviceAddress = " + deviceAddress);
            if (mUpgradeViewInterface != null) {
                mUpgradeViewInterface.updateProgressIndeterminate(true);
                mUpgradeViewInterface.showMessage(mContext.getResources().getString(R.string.dfu_status_validating));
            }
        }

        @Override
        public void onDeviceDisconnecting(final String deviceAddress) {
            MLog.d(TAG, "onDeviceDisconnecting --- deviceAddress = " + deviceAddress);
            if (mUpgradeViewInterface != null) {
                mUpgradeViewInterface.updateProgressIndeterminate(true);
            }
        }

        @Override
        public void onDfuCompleted(final String deviceAddress) {
            MLog.d(TAG, "onDfuCompleted --- deviceAddress = " + deviceAddress);
            if (mUpgradeViewInterface != null) {
                mUpgradeViewInterface.showUpgradeMessage(mContext.getResources().getString(R.string.ALERT_BUTTON_BOT));
                mUpgradeViewInterface.updateProgressIndeterminate(false);
                mUpgradeViewInterface.updateProgressPercent(100);
            }
        }

        @Override
        public void onDfuAborted(final String deviceAddress) {
            MLog.d(TAG, "onDfuAborted --- deviceAddress = " + deviceAddress);
            if (mUpgradeViewInterface != null) {
                mUpgradeViewInterface.showUpgradeMessage(mContext.getResources().getString(R.string.dfu_status_aborted));
                mUpgradeViewInterface.updateProgressIndeterminate(false);
                mUpgradeViewInterface.updateProgressPercent(0);
            }
        }

        @Override
        public void onProgressChanged(final String deviceAddress, final int percent, final float speed, final float avgSpeed, final int currentPart, final int partsTotal) {
            MLog.d(TAG, "onProgressChanged --- deviceAddress = " + deviceAddress + ", percent = " + percent
                    + ", speed = " + speed + ", avgSpeed = " + avgSpeed + ", currentPart = " + currentPart + ", partsTotal = " + partsTotal);

            if (mUpgradeViewInterface != null) {
                mUpgradeViewInterface.updateProgressIndeterminate(false);
                mUpgradeViewInterface.updateProgressPercent(percent);
            }
        }

        @Override
        public void onError(final String deviceAddress, final int error, final int errorType, final String message) {
            MLog.d(TAG, "onError --- deviceAddress = " + deviceAddress + ", error = " + error + ", errorType = " + errorType + ", message = " + message);
            if (mUpgradeViewInterface != null) {
                mUpgradeViewInterface.showUpgradeMessage(message);
                mUpgradeViewInterface.updateProgressIndeterminate(false);
                mUpgradeViewInterface.updateProgressPercent(0);
            }
        }
    };


}
