package com.matatalab.matatacode;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.matatalab.matatacode.interfaces.MainViewInterface;
import com.matatalab.matatacode.model.PublicWay;
import com.matatalab.matatacode.presenter.UpgradingPresenter;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.matatalab.matatacode.utils.FunctionUtils;
import com.matatalab.matatacode.utils.MLog;
import com.matatalab.matatacode.utils.ToastUtils;

import java.io.IOException;
import java.nio.charset.CoderMalfunctionError;
import java.util.List;
import java.util.UUID;


public class UpgradingActivity extends AppCompatActivity {
    private static final String TAG = UpgradingActivity.class.getSimpleName();
    private ImageView imageView_au1;
    private ProgressBar progressBar_au1;
    private TextView textView_au1;
    private int devType,imgBg,imgLogo;
    private String devMac,devName;
    private UpgradingPresenter upgradingPresenter;
    private int ProgressPercent;
    private boolean dfuBusy=true;
    private boolean dfuMode;
    private static final int  DEV_OLD= 501;
    private static final int  DEV_BOT= 502;
    private static final int  DEV_CON= 503;
    private static final int  DEV_TOWER= 504;
    private static final String UUID_SERVICE = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    private static final String UUID_NOTIFY = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    private static final String UUID_WRITE = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    private static final byte[] BOT_DFU_CMD= new byte[]{(byte)0x80 ,(byte)0xaa ,(byte)0x55 ,(byte)0x81 , (byte)0x00};
    private static final byte[] CON_DFU_CMD= new byte[]{(byte)0x80 ,(byte)0xaa ,(byte)0x55 ,(byte)0x13 , (byte)0x00};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MLog.td("tjl", "setContentView(R.layout.activity_upgrade);");
        setContentView(R.layout.activity_upgrading);
        Intent intent=getIntent();
        AppConst.bluetoothAdapter=null;
        devMac=intent.getStringExtra("mac");
        devName= intent.getStringExtra("name");
        MLog.td("tjl" , "name="+devName+",mac="+devMac);
        if(devName.equals(AppConst.BT_DFU_NAME_OLD))devType=DEV_OLD;
        if(devName.equals(AppConst.BT_DFU_NAME_BOT))devType=DEV_BOT;
        if(devName.equals(AppConst.BT_DFU_NAME_TOWER))devType=DEV_TOWER;
        if(devName.equals(AppConst.BT_DFU_NAME_CON))devType=DEV_CON;
        if(devName.toLowerCase().indexOf("dfu")>-1)dfuMode=true;else dfuMode=false;
        MLog.td("tjl" , "devType:"+String.valueOf(devType)+"dfuMode:"+dfuMode);
        AppConst.devMac=devMac;
        ProgressPercent=0;
        initView(savedInstanceState);
        initData();
    }

    private void initData() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        imageView_au1=findViewById(R.id.imageView_au1);
        progressBar_au1=findViewById(R.id.progressBar_au1);
        textView_au1=findViewById(R.id.textView_au1);
        progressBar_au1.setProgress(ProgressPercent);
        textView_au1.setText(this.getString(R.string.DFU_STATUS_START));
       // mHandler.sendEmptyMessageDelayed(1001, 1000);
        MLog.td("tjl","progressBar_au1:"+progressBar_au1.getMax());
        switch (devType)
        {
            case DEV_OLD:
                imgBg=R.mipmap.item10;
                imgLogo=R.mipmap.pic_logo_bot_dfu;
                break;
            case DEV_CON:
                imgBg=R.mipmap.item9;
                imgLogo=R.mipmap.pic_logo_con_dfu;
                break;
            case DEV_BOT:
                imgBg=R.mipmap.item10;
                imgLogo=R.mipmap.pic_logo_bot_dfu;
                break;
            case DEV_TOWER:
                imgBg=R.mipmap.item8;
                imgLogo=R.mipmap.pic_logo_tower_dfu;
                break;
            default:
                imgBg=R.mipmap.item8;
                imgLogo=R.mipmap.pic_logo_tower_dfu;
                break;
        }
        this.getWindow().setBackgroundDrawable(getResources().getDrawable(imgBg));
        imageView_au1.setImageDrawable(getResources().getDrawable(imgLogo));
        upgradingPresenter = UpgradingPresenter.getInstance(this);
        upgradingPresenter.setUpgradeViewInterface(this);
        MLog.td("tjl" , "devType= " + devType);
        try{
        switch(devType)
        {
            case DEV_OLD:upgradingPresenter.startDeviceEnterUpgrade(AppConst.UPGRADE_TYPE_BOT);break;
            case DEV_BOT:upgradingPresenter.startDeviceEnterUpgrade(AppConst.UPGRADE_TYPE_BOT);break;
            case DEV_TOWER:upgradingPresenter.startDeviceEnterUpgrade(AppConst.UPGRADE_TYPE_TOWER);break;
            case DEV_CON:upgradingPresenter.startDeviceEnterUpgrade(AppConst.UPGRADE_TYPE_CONTROLLER);break;
        }} catch (Exception e) {
            MLog.td(TAG, e.getMessage());
        }
    }
    public void showBtErrDialogMessage(String message) {
        final Mdialog mdialog = new Mdialog(this);
        mdialog.setTitle(getString(R.string.ALERT_TITLE));
        mdialog.setMessage(getString(R.string.DFU_RESULT_FAIL));
        mdialog.setBa(getString(R.string.ALERT_BUTTON_CANCEL));
        mdialog.setBb(getString(R.string.ALERT_BUTTON_QUIT));
        mdialog.setOnButtonAListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mdialog.isShowing()) {
                    upgradingPresenter.exit();
                    startActivity(new Intent(UpgradingActivity.this,UpgradeActivity.class));
                    mdialog.dismiss();
                }
            }
        });
        mdialog.setOnButtonBListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mdialog != null && mdialog.isShowing()) {
                    upgradingPresenter.exit();
                    startActivity(new Intent(UpgradingActivity.this,UpgradeActivity.class));
                    mdialog.dismiss();
                }
            }
        });
        mdialog.setCancelable(false);                //用户可以点击后退键关闭 Dialog
        mdialog.setCanceledOnTouchOutside(false);   //用户不可以点击外部来关闭 Dialog
        mdialog.show();
    }
    public void showWaitIntoDfuMode(String message) {
        MLog.td("tjl" , "showWaitIntoDfuMode= " + message);
    }
    public void showUpgradeMessage(String message) {

        final Mdialog mdialog = new Mdialog(this);
        mdialog.setTitle(getString(R.string.ALERT_TITLE));
        mdialog.setMessage(message);
        mdialog.setBa(getString(R.string.ALERT_BUTTON_CANCEL));
        mdialog.setBb(getString(R.string.ALERT_BUTTON_QUIT));
        mdialog.setOnButtonAListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mdialog.isShowing()) {
                    upgradingPresenter.exit();
                    startActivity(new Intent(UpgradingActivity.this,UpgradeActivity.class));
                    mdialog.dismiss();
                }
            }
        });
        mdialog.setOnButtonBListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mdialog != null && mdialog.isShowing()) {
                    upgradingPresenter.exit();
                    startActivity(new Intent(UpgradingActivity.this,UpgradeActivity.class));
                    mdialog.dismiss();
                }
            }
        });
        mdialog.setCancelable(false);                //用户可以点击后退键关闭 Dialog
        mdialog.setCanceledOnTouchOutside(false);   //用户不可以点击外部来关闭 Dialog
        mdialog.show();
        MLog.td("tjl" , "showUpgradeMessage= " + message);
    }
    public void updateProgressIndeterminate(boolean indeterminate) {
        if(indeterminate)textView_au1.setText(this.getString(R.string.DFU_STATUS_UPLOAD)+" "+ProgressPercent+"%");
        if(indeterminate)dfuBusy=true;
    }
    public void updateProgressPercent(int percent) {
        progressBar_au1.setProgress(percent);
        ProgressPercent=percent;
        textView_au1.setText(this.getString(R.string.DFU_STATUS_UPLOAD)+" "+ProgressPercent+"%");
    }
    public void showMessage(String message) {
        ToastUtils.show(message);
    }
    public void onBackPressed() {
        MLog.td("tjl" , "onBackPressed()");
        if(!dfuBusy)
        {
            upgradingPresenter.exit();
            startActivity(new Intent(UpgradingActivity.this,UpgradeActivity.class));
            finish();
        }
        return;
    }
    private  void killProcess( String killName) {

        // 获取一个ActivityManager 对象
        ActivityManager activityManager = (ActivityManager)UpgradingActivity.this.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取系统中所有正在运行的进程
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager
                .getRunningAppProcesses();
        // 对系统中所有正在运行的进程进行迭代，如果进程名所要杀死的进程，则Kill掉
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            String processName = appProcessInfo.processName;
            if (processName.equals(killName)) {
                killProcessByPid(appProcessInfo.pid);
            }
            MLog.td("tjl","process name:"+processName);
        }
    }

    /**
     * 根据要杀死的进程id执行Shell命令已达到杀死特定进程的效果
     *
     * @param pid
     */
    private static void killProcessByPid(int pid) {
        String command = "kill -9 " + pid + "\n";
        Runtime runtime = Runtime.getRuntime();
        Process proc;
        try {
            proc = runtime.exec(command);
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
    protected void onDestroy() {
        upgradingPresenter.exit();
        killProcess("a");
        super.onDestroy();
    }
    private void initView(@Nullable Bundle savedInstanceState) {

    }
}