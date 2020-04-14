package com.matatalab.matatacode.presenter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.matatalab.matatacode.AppConst;
import com.matatalab.matatacode.Global;
import com.matatalab.matatacode.R;
import com.matatalab.matatacode.ble.BleController;
import com.matatalab.matatacode.ble.callback.ConnectCallback;
import com.matatalab.matatacode.ble.callback.OnWriteCallback;
import com.matatalab.matatacode.ble.callback.ScanCallback;
import com.matatalab.matatacode.dfu.DfuModel;
import com.matatalab.matatacode.interfaces.CommandParseInterface;
import com.matatalab.matatacode.UpgradingActivity;
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
public class UpgradingPresenter {
    private volatile static UpgradingPresenter mUpgradePresenter = null;
    private Context mContext;
    private DfuModel mDfuModel;
    private BleController mBleController;
    private UpgradingActivity upgradingActivity;
    /**
     * 搜索蓝牙设备最低信号强度
     */
    private static final int BT_RSSI_NIMI_VALUE = -99; // -90;
    // 搜索信号最强的蓝牙设备
    private BluetoothDevice mBluetoothDevice;
    private int mCurrentRssi = 0;
    private int notFindCount=0;
    private int mUpgradeType;
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
            MLog.td("tjl", "handleMessage --- msg = " + msg);
            switch (msg.what) {

                case MESSAGE_START_UPGRADE:
                    startDfuUpgrade(mUpgradeType);
                    break;
                default:
                    break;
            }
        }
    };


    private UpgradingPresenter(Context context) {
        MLog.td("tjl", "--- UpgradePresenter ---");
        mContext = context;
        init();
    }

    /**
     * 单例
     *
     * @return
     */
    public static UpgradingPresenter getInstance(Context context) {
        if (mUpgradePresenter == null) {
            synchronized (UpgradingPresenter.class) {
                if (mUpgradePresenter == null) {
                    mUpgradePresenter = new UpgradingPresenter(context);
                }
            }
        }
        return mUpgradePresenter;
    }

    private void init() {
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
        mContext=null;
    }

    public void setUpgradeViewInterface(UpgradingActivity upgradingViewInterface) {
        upgradingActivity = upgradingViewInterface;
    }


    /**
     * 启动DFU
     */
    public void startDeviceEnterUpgrade(int upgradeType) {
        MLog.td("tjl", "startDeviceEnterUpgrade --- upgradeType = " + upgradeType);
        mUpgradeType = upgradeType;

       if (isBtConnect() == false) {
            MLog.td("tjl", "startDeviceEnterUpgrade --- bt not connect.");
            scanAndConnectDevices();
            return;
        }

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
     * 断开蓝牙连接
     */
    private void disconnectBt() {
        if (mBleController == null) {
            mBleController = BleController.getInstance().initble(mContext);
        }
        //开始
        if (mBleController.isConnected() == false) {
            MLog.td("tjl", "disconnectBt --- is not connect.");
            return;
        }
        mBleController.disConnectBleConn();
    }


    /**
     * 扫描和绑定设备
     */
    private void scanAndConnectDevices() {
        MLog.td("tjl", "--- scanAndConnectDevices ---");

        if (mBleController == null) {
            mBleController = BleController.getInstance().initble(mContext);
        }

        if (mBleController.isBtEnabled() == false) {
            if (upgradingActivity != null) {
                try{
                upgradingActivity.showBtErrDialogMessage(mContext.getResources().getString(R.string.DFU_RESULT_ERROR));
                } catch (Exception e) {
                    MLog.td("tjl", e.getMessage());
                }
            }
            return;
        }

        //开始
        if (mBleController.isConnected() == true) {
            MLog.td("tjl", "scanAndConnectDevices --- already connect.");
            return;
        }

        mCurrentRssi = BT_RSSI_NIMI_VALUE;
        mBluetoothDevice = null;
        notFindCount=0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    if(notFindCount==0){
                        MLog.td("tjl", "没有发现 要升级的设备");
                        try {
                            upgradingActivity.showBtErrDialogMessage(mContext.getResources().getString(R.string.DFU_RESULT_ERROR));
                        } catch (Exception e) {
                            MLog.td("tjl", e.getMessage());
                        }
                    }
                }catch (Exception E){}
            }
        }, 1000);
          mBleController.ScanBle(true, new ScanCallback() {
            @Override
            public void onSuccess() {

            }
            @Override
            public void onScanning(BluetoothDevice device, int rssi, byte[] scanRecord) {
                if ((device != null) && (TextUtils.isEmpty(device.getName()) == false)) {
                    MLog.td("tjl", "onScanning --- device.getName() = " + device.getName() + ", rssi = " + rssi+",mac="+device.getAddress());
                    String mac=String.valueOf(device.getAddress());
                    MLog.td("tjl", AppConst.devMac+" "+device.getAddress()+" "+Global.CompareCount(mac,AppConst.devMac));

                    if(Global.CompareCount(mac,AppConst.devMac)>13) {
                        if(mBluetoothDevice==null)
                        {
                            notFindCount=1;
                            mCurrentRssi = rssi;
                            mBluetoothDevice = device;
                            mHandler.sendEmptyMessageDelayed(MESSAGE_START_UPGRADE, 1000);
                            MLog.td("tjl", "+++++++++++++++++++Find Device+++++++++++++++++++++" + device.getAddress());
                            return;
                        }
                    }
                }
            }
        });
    }



    /**
     * 启动升级
     *
     * @param upgradeType
     */
    private void startDfuUpgrade(int upgradeType) {
        MLog.td("tjl", "startDfuUpgrade --- upgradeType = " + upgradeType);
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
        try{
        if (upgradingActivity != null) {
            upgradingActivity.updateProgressIndeterminate(false);
            upgradingActivity.updateProgressPercent(0);
        }} catch (Exception e) {
            MLog.td("tjl", e.getMessage());
        }
    }

    private final DfuProgressListener mDfuProgressListener = new DfuProgressListenerAdapter() {
        @Override
        public void onDeviceConnecting(final String deviceAddress) {
            MLog.td("tjl", "onDeviceConnecting --- deviceAddress = " + deviceAddress);
            try{
            if (upgradingActivity != null) {
                upgradingActivity.updateProgressIndeterminate(true);
            }} catch (Exception e) {
                MLog.td("tjl", e.getMessage());
            }
        }

        @Override
        public void onDfuProcessStarting(final String deviceAddress) {
            MLog.td("tjl", "onDfuProcessStarting --- deviceAddress = " + deviceAddress);
            try{
            if (upgradingActivity != null) {
                upgradingActivity.updateProgressIndeterminate(true);
            }} catch (Exception e) {
                MLog.td("tjl", e.getMessage());
            }
        }

        @Override
        public void onEnablingDfuMode(final String deviceAddress) {
            MLog.td("tjl", "onEnablingDfuMode --- deviceAddress = " + deviceAddress);
            try{
            if (upgradingActivity != null) {
                upgradingActivity.updateProgressIndeterminate(true);
            }} catch (Exception e) {
                MLog.td("tjl", e.getMessage());
            }
        }

        @Override
        public void onFirmwareValidating(final String deviceAddress) {
            MLog.td("tjl", "onFirmwareValidating --- deviceAddress = " + deviceAddress);
            try{
            if (upgradingActivity != null) {
                upgradingActivity.updateProgressIndeterminate(true);
                upgradingActivity.showMessage(mContext.getResources().getString(R.string.dfu_status_validating));
            }} catch (Exception e) {
                MLog.td("tjl", e.getMessage());
            }
        }

        @Override
        public void onDeviceDisconnecting(final String deviceAddress) {
            MLog.td("tjl", "onDeviceDisconnecting --- deviceAddress = " + deviceAddress);
            try{
            if (upgradingActivity != null) {
                upgradingActivity.updateProgressIndeterminate(true);
            }} catch (Exception e) {
                MLog.td("tjl", e.getMessage());
            }
        }

        @Override
        public void onDfuCompleted(final String deviceAddress) {
            MLog.td("tjl", "onDfuCompleted --- deviceAddress = " + deviceAddress);
            try{
            if (upgradingActivity != null) {
                upgradingActivity.showUpgradeMessage(mContext.getResources().getString(R.string.DFU_RESULT_SUCCESS));
                upgradingActivity.updateProgressIndeterminate(false);
                upgradingActivity.updateProgressPercent(100);
            }} catch (Exception e) {
                MLog.td("tjl", e.getMessage());
            }
        }

        @Override
        public void onDfuAborted(final String deviceAddress) {
            MLog.td("tjl", "onDfuAborted --- deviceAddress = " + deviceAddress);
            try{
            if (upgradingActivity != null) {
                upgradingActivity.showUpgradeMessage(mContext.getResources().getString(R.string.dfu_status_aborted));
                upgradingActivity.updateProgressIndeterminate(false);
                upgradingActivity.updateProgressPercent(0);
            }} catch (Exception e) {
                MLog.td("tjl", e.getMessage());
            }
        }

        @Override
        public void onProgressChanged(final String deviceAddress, final int percent, final float speed, final float avgSpeed, final int currentPart, final int partsTotal) {
            MLog.td("tjl", "onProgressChanged --- deviceAddress = " + deviceAddress + ", percent = " + percent
                    + ", speed = " + speed + ", avgSpeed = " + avgSpeed + ", currentPart = " + currentPart + ", partsTotal = " + partsTotal);

            try {
                if (upgradingActivity != null) {
                    upgradingActivity.updateProgressIndeterminate(false);
                    upgradingActivity.updateProgressPercent(percent);
                }
            } catch (Exception e) {
                MLog.td("tjl", e.getMessage());
            }
        }

        @Override
        public void onError(final String deviceAddress, final int error, final int errorType, final String message) {
            MLog.td("tjl", "onError --- deviceAddress = " + deviceAddress + ", error = " + error + ", errorType = " + errorType + ", message = " + message);
            try {
            if (upgradingActivity != null) {
                upgradingActivity.showUpgradeMessage(message);
                upgradingActivity.updateProgressIndeterminate(false);
                upgradingActivity.updateProgressPercent(0);
            }
            } catch (Exception e) {
                MLog.td("tjl", e.getMessage());
            }
        }
    };


}
