package com.matatalab.matatacode.presenter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.Toast;

import com.matatalab.matatacode.AppConst;
import com.matatalab.matatacode.R;
import com.matatalab.matatacode.ble.BleController;
import com.matatalab.matatacode.ble.callback.ConnectCallback;
import com.matatalab.matatacode.ble.callback.OnWriteCallback;
import com.matatalab.matatacode.ble.callback.ScanCallback;
import com.matatalab.matatacode.interfaces.BlocklyViewInterface;
import com.matatalab.matatacode.interfaces.CommandParseInterface;
import com.matatalab.matatacode.interfaces.WebVIewCallback;
import com.matatalab.matatacode.model.BrowserModel;
import com.matatalab.matatacode.model.CommandParse;
import com.matatalab.matatacode.utils.FileUtil;
import com.matatalab.matatacode.utils.MLog;
import com.matatalab.matatacode.utils.ToastUtils;
import com.matatalab.matatacode.view.BlocklyFragment;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hardy
 * @name MatataCode
 * @class name：com.matatalab.matatacode.presenter
 * @class describe: 主逻辑模块
 * @time 2019/9/28 20:58
 * @change
 * @chang time
 * @class describe
 */
public class BlocklyPresenter implements WebVIewCallback {
    private static final String TAG = BlocklyPresenter.class.getSimpleName();

    private volatile static BlocklyPresenter mBlocklyPresenter = null;

    private Context mContext;
    private CommandParseInterface mCommandParse;

    private BleController mBleController;

    private BlocklyViewInterface mBlocklyViewInterface;

    /**
     * 搜索蓝牙设备最低信号强度
     */
    private static final int BT_RSSI_NIMI_VALUE = -70;//90;
    // 搜索信号最强的蓝牙设备
    private BluetoothDevice mBluetoothDevice;
    private int mCurrentRssi = 0;

    private ExecutorService mSendThread;
    private LinkedBlockingQueue<Runnable> mSendQueue = new LinkedBlockingQueue<Runnable>();

    private BlocklyPresenter(Context context) {
        MLog.d(TAG, "--- BlocklyPresenter ---");
        mContext = context;
        init();
    }

    /**
     * 单例
     *
     * @return
     */
    public static BlocklyPresenter getInstance(Context context) {
        if (mBlocklyPresenter == null) {
            synchronized (BlocklyPresenter.class) {
                if (mBlocklyPresenter == null) {
                    mBlocklyPresenter = new BlocklyPresenter(context);
                }
            }
        }
        return mBlocklyPresenter;
    }

    private void init() {
        BrowserModel.getInstance().setWebVIewCallback(this);
        mCommandParse = CommandParse.getInstance();

        mSendThread = new ThreadPoolExecutor(0, 1,
                10, TimeUnit.MINUTES,
                mSendQueue /* new LinkedBlockingQueue<Runnable>()*/);
    }


    public void exit() {
        //释放资源
        if (mBleController != null) {
            mBleController.ScanBle(false, null);
            mBleController = null;
        }

        BrowserModel.getInstance().setWebVIewCallback(null);

        mBlocklyPresenter = null;
    }

    public void setBlocklyViewInterface(BlocklyViewInterface blocklyViewInterface) {
        mBlocklyViewInterface = blocklyViewInterface;
    }

    // web 命令接口 --------------------------------------------------------------------------------------


    @Override
    public void sendWebCommand(String type, String msg) {
        MLog.d(TAG, "sendWebCommand --- type = " + type + ", msg = " + msg);

        switch (type) {
            case "runcode":
                sendCommandToBt(mCommandParse.parseRunCodeToBtCommand(msg));
                break;
            case "stopcode":
                sendCommandToBt(mCommandParse.getStopBtCommand());
                break;
            case "savecode":
                if (mBlocklyViewInterface != null) {
                    mBlocklyViewInterface.showSaveCodeView(msg);
                }
                break;
            case "loadcode":
                if (mBlocklyViewInterface != null) {
                    mBlocklyViewInterface.showLoadCodeView();
                }
                break;
            case "checkcode":
                if (mBlocklyViewInterface != null) {
                    mBlocklyViewInterface.showQueryCodeView();
                }
                break;
            case "deletecode":
                if (mBlocklyViewInterface != null) {
                    mBlocklyViewInterface.showDeleteCodeView();
                }
                break;
            case "connectbot":
                if (mBleController == null) {
                    mBleController = BleController.getInstance().initble(mContext);
                }
                if (mBleController.isConnected() == false) {
                    connectBt();
                } else {
                    if (mBlocklyViewInterface != null) {
                        mBlocklyViewInterface.showDisConnectView();
                    }
                }

                break;
            case "disconectbot":
                disconnectBt();
                break;
            case "helpbtn":
                if (mBlocklyViewInterface != null) {
                    mBlocklyViewInterface.setHelpViewShow(true);
                }

                break;
            default:
                break;
        }
    }


    // 文件操作 --------------------------------------------------------------------------------------

    /**
     * 保存code文档
     *
     * @param name
     * @param codes
     */
    public void saveCode(String name, String codes) {
        MLog.d(TAG, "saveCode --- name = " + name + ", codes = " + codes);
        if (TextUtils.isEmpty(name) == true) {
            MLog.d(TAG, "saveCode --- name is empty.");
            return;
        }
        if (TextUtils.isEmpty(codes) == true) {
            MLog.d(TAG, "saveCode --- codes is empty.");
            return;
        }
        String filePath = FileUtil.getCommonRootDir() + name + FileUtil.CODE_SUFFIX;
        MLog.d(TAG, "saveCode --- filePath = " + filePath);
        boolean isSave = FileUtil.write(filePath, codes);

        if (mBlocklyViewInterface != null) {
            if (isSave == true) {
                mBlocklyViewInterface.showMessage(mContext.getResources().getString(R.string.ALERT_BUTTON_OK));
            } else {
                mBlocklyViewInterface.showMessage(mContext.getResources().getString(R.string.DFU_RESULT_ERROR));
            }
        }
    }

    /**
     * 获取指定名称code文件
     *
     * @param name
     */
    public void readCodeByName(String name) {
        if (TextUtils.isEmpty(name) == true) {
            MLog.d(TAG, "readCodeByName --- name is empty.");
            return;
        }

        String filePath = FileUtil.getCommonRootDir() + name + FileUtil.CODE_SUFFIX;
        MLog.d(TAG, "readCodeByName --- filePath = " + filePath);
        String codes = FileUtil.read(filePath);
        MLog.d(TAG, "readCodeByName --- codes = " + codes);

        if (TextUtils.isEmpty(codes) == true) {
            MLog.d(TAG, "readCodeByName --- codes is empty.");
            if (mBlocklyViewInterface != null) {
                mBlocklyViewInterface.showMessage(mContext.getResources().getString(R.string.DFU_RESULT_ERROR));
            }
            return;
        }
        String js = "Blockly.Xml.domToWorkspace(Blockly.Xml.textToDom('" + codes + "'), Code.workspace);";
        BrowserModel.getInstance().runJsCode(js);
    }

    /**
     * 删除指定名称的code文件
     *
     * @param name
     */
    public void deleteCodeByName(String name) {
        if (TextUtils.isEmpty(name) == true) {
            MLog.d(TAG, "deleteCodeByName --- name is empty.");
            return;
        }

        String filePath = FileUtil.getCommonRootDir() + name + FileUtil.CODE_SUFFIX;
        MLog.d(TAG, "deleteCodeByName --- filePath = " + filePath);
        boolean isDelete = FileUtil.deleteFile(filePath);
        MLog.d(TAG, "readCodeByName --- isDelete = " + isDelete);

        if (mBlocklyViewInterface != null) {
            if (isDelete == true) {
                mBlocklyViewInterface.showMessage(mContext.getResources().getString(R.string.DFU_RESULT_ERROR));
            } else {
                mBlocklyViewInterface.showMessage(mContext.getResources().getString(R.string.DFU_RESULT_ERROR));
            }
        }
    }

    /**
     * 获取保存的文件名称列表
     *
     * @return
     */
    public String[] getCodeFileList() {

        String filePath = FileUtil.getCommonRootDir();
        MLog.d(TAG, "deleteCodeByName --- filePath = " + filePath);
        String[] items = {""};
        String[] itemsDefault = {""};

        try {
            items = FileUtil.scanFileName(filePath, FileUtil.CODE_SUFFIX);
            if (items == null) {
                items = itemsDefault;
            }
            MLog.d(TAG, "deleteCodeByName --- items = ", items);
        } catch (IOException e) {
            MLog.e(TAG, "getCodeFileList e: " + e.getMessage());
        } catch (Exception e) {
            MLog.e(TAG, "getCodeFileList e: " + e.getMessage());
        } finally {
            return items;
        }


    }


    // 蓝牙操作 --------------------------------------------------------------------------------------

    /**
     * 连接蓝牙
     */
    private void connectBt() {
        if (mBleController == null) {
            mBleController = BleController.getInstance().initble(mContext);
        }
        if (mBleController.isBtEnabled() == false) {
            if (mBlocklyViewInterface != null) {
                mBlocklyViewInterface.showBtErrDialogMessage(mContext.getResources().getString(R.string.DFU_RESULT_ERROR));
            }
            return;
        }
        scanAndConnectDevices();

    }

    /**
     * 断开蓝牙连接
     */
    public void disconnectBt() {
        if (mBleController == null) {
            mBleController = BleController.getInstance().initble(mContext);
        }
        //开始
        if (mBleController.isConnected() == false) {
            MLog.d(TAG, "disconnectBt --- is not connect.");
            return;
        }
        mBleController.disConnectBleConn();

        if (mBlocklyViewInterface != null) {
            mBlocklyViewInterface.updateBtStatus(false);
        }
    }

    /**
     * 发送命令到蓝牙设备
     *
     * @param commands
     */
    private void sendCommandToBt(final byte[] commands) {
        MLog.d(TAG, "sendCommandToBt --- commands = ", commands);
        if (commands == null) {
            MLog.e(TAG, "sendCommandToBt commands is null!");
            return;
        }

        if ((mBleController != null) && (mBleController.isConnected())) {
            mSendThread.execute(new Runnable() {
                @Override
                public void run() {
                    sendCommand(commands);

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        MLog.e(TAG, "sendCommandToBt sleep is err!");
                    }
                }

            });

        } else {
            MLog.d(TAG, "sendCommandToBt --- not connect");
            if (mBlocklyViewInterface != null) {
                mBlocklyViewInterface.showMessage(mContext.getResources().getString(R.string.DFU_RESULT_ERROR));
            }
        }


    }

    /**
     * 扫描和绑定设备
     */
    private void scanAndConnectDevices() {
        MLog.d(TAG, "--- scanAndConnectDevices ---");
        //开始
        if (mBleController.isConnected() == true) {
            MLog.d(TAG, "scanAndConnectDevices --- already connect.");
            return;
        }

        mCurrentRssi = BT_RSSI_NIMI_VALUE;
        mBluetoothDevice = null;

        if (mBlocklyViewInterface != null) {
            mBlocklyViewInterface.showLoading(true);
        }

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
                            if (mBlocklyViewInterface != null) {
                                mBlocklyViewInterface.updateBtStatus(true);
                                mBlocklyViewInterface.showMessage(mContext.getResources().getString(R.string.DFU_RESULT_ERROR));
                                mBlocklyViewInterface.showLoading(false);
                            }
                        }

                        @Override
                        public void onConnFailed() {
                            if (mBlocklyViewInterface != null) {
                                mBlocklyViewInterface.updateBtStatus(false);
                                mBlocklyViewInterface.showMessage(mContext.getResources().getString(R.string.DFU_RESULT_ERROR));
                                mBlocklyViewInterface.showLoading(false);
                            }
                            MLog.d(TAG, "--- onConnFailed ---");
                            MLog.d(TAG, "连接断开或连接失败");
                        }
                    });

                } else if (mBleController.isConnected()) {
                    MLog.d(TAG, "设备已连接");
                    if (mBlocklyViewInterface != null) {
                        mBlocklyViewInterface.updateBtStatus(true);
                        mBlocklyViewInterface.showLoading(false);
                    }
                } else {
                    if (mBlocklyViewInterface != null) {
                        mBlocklyViewInterface.updateBtStatus(false);
                        mBlocklyViewInterface.showMessage(mContext.getResources().getString(R.string.DFU_RESULT_ERROR));
                        mBlocklyViewInterface.showLoading(false);
                    }
                    MLog.d(TAG, "没有可连接的设备");
                }
            }

            @Override
            public void onScanning(BluetoothDevice device, int rssi, byte[] scanRecord) {
                if ((device != null) && (TextUtils.isEmpty(device.getName()) == false)) {
                    MLog.d(TAG, "onScanning device.getName() = " + device.getName() + ", rssi = " + rssi);
                    if ((device.getName().startsWith(AppConst.BT_NAME_BASE)) && (device.getName().startsWith(AppConst.BT_NAME_DFU) == false) && (rssi > BT_RSSI_NIMI_VALUE)) {
                        if (rssi > mCurrentRssi) {
                            MLog.d(TAG, "onScanning --- mCurrentRssi = " + mCurrentRssi);
                            mCurrentRssi = rssi;
                            mBluetoothDevice = device;
                        }
                    }
                }
            }
        });
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
                    MLog.d(TAG, "WriteBuffer --- onSuccess");
                }

                @Override
                public void onFailed(int state) {
//                    ToastUtils.show("send failed");
                    MLog.e(TAG, "WriteBuffer --- onFailed " + state);
                }
            });
        } else {
            MLog.d(TAG, "sendCommand --- not connect");
//            ToastUtils.show(mContext.getResources().getString(R.string.bt_not_connect_info));
        }
    }


}
