package com.matatalab.matatacode;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.matatalab.matatacode.model.PublicWay;
import com.matatalab.matatacode.utils.FunctionUtils;
import com.matatalab.matatacode.utils.MLog;
import com.matatalab.matatacode.utils.ToastUtils;

import java.util.List;
import java.util.UUID;


public class UpgradeReadyActivity extends AppCompatActivity {
    private static final String TAG = UpgradeReadyActivity.class.getSimpleName();
    private ImageButton imageButton5;
    private ImageButton imageButton6;
    private ImageButton imageButton7;
    private String devMac,devName;
    private ImageView imageView1;
    private int devType,imgBg,imgLogo,imgStart,imgStartp,imgHelpp,imgHelp;
    private boolean dfuMode;
    private ProgressBar progressBar_au1;
    private static final int  DEV_OLD= 501;
    private static final int  DEV_BOT= 502;
    private static final int  DEV_CON= 503;
    private TextView textview_aur1;
    private TextView textview_name;
    private static final String UUID_SERVICE = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    private static final String UUID_NOTIFY = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    private static final String UUID_WRITE = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    private static final byte[] BOT_DFU_CMD= new byte[]{(byte)0x80 ,(byte)0xaa ,(byte)0x55 ,(byte)0x81 , (byte)0x00};
    private static final byte[] CON_DFU_CMD= new byte[]{(byte)0x80 ,(byte)0xaa ,(byte)0x55 ,(byte)0x13 , (byte)0x00};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MLog.td("tjl", "setContentView(R.layout.activity_upgrade);");
        setContentView(R.layout.activity_upgrade_ready);
        Intent intent=getIntent();
        AppConst.bluetoothAdapter=null;
        devMac=intent.getStringExtra("mac");
        devName= intent.getStringExtra("name");
        MLog.td("tjl" , "name="+devName+",mac="+devMac);
        if(devName.equals(AppConst.BT_APP_NAME_BOT))devType=DEV_BOT;
        if(devName.equals(AppConst.BT_APP_NAME_OLD))devType=DEV_OLD;
        if(devName.equals(AppConst.BT_APP_NAME_CON))devType=DEV_CON;
        if(devName.toLowerCase().indexOf("dfu")>-1)dfuMode=true;else dfuMode=false;
        MLog.td("tjl" , "devType:"+String.valueOf(devType)+"dfuMode:"+dfuMode);
        initView(savedInstanceState);
        initData();
    }

    private void initData() {

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            MLog.td("tjl" , "handleMessage --- msg = " + msg);
            switch (msg.what) {
                case 1000:

                    final Mdialog mdialog = new Mdialog(UpgradeReadyActivity.this);
                    mdialog.setTitle(getString(R.string.ALERT_TITLE));
                    mdialog.setMessage(getString(R.string.DFU_CONTROLLER_READY));
                    mdialog.setBa(getString(R.string.ALERT_BUTTON_CANCEL));
                    mdialog.setBb(getString(R.string.ALERT_BUTTON_OK));
                    mdialog.setOnButtonAListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mdialog.isShowing()) {
                                MLog.td("tjl", "ab");
                                Message tempMsg = mHandler.obtainMessage();
                                tempMsg.what = 1004;
                                mHandler.sendMessage(tempMsg);
                                mdialog.dismiss();
                            }
                        }
                    });
                    mdialog.setOnButtonBListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mdialog != null && mdialog.isShowing()) {
                                Message tempMsg = mHandler.obtainMessage();
                                tempMsg.what = 1005;
                                mHandler.sendMessage(tempMsg);
                                mdialog.dismiss();
                            }
                        }
                    });
                    mdialog.setCancelable(false);                //用户可以点击后退键关闭 Dialog
                    mdialog.setCanceledOnTouchOutside(false);   //用户不可以点击外部来关闭 Dialog
                    mdialog.show();
                    break;
                case 1002:break;
                case 1004:
                    progressBar_au1.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(UpgradeReadyActivity.this,UpgradeActivity.class));
                    break;
                case 1005:
                    progressBar_au1.setVisibility(View.INVISIBLE);
                    MLog.td("tjl", "devMac="+devMac+",devName="+devName);
                    startActivity(new Intent(UpgradeReadyActivity.this , UpgradeStartActivity.class).putExtra("mac" ,devMac).putExtra("name" ,devName));
                    break;
                case 1003:
                    progressBar_au1.setVisibility(View.INVISIBLE);
                    ToastUtils.show(getString(R.string.DFU_RESULT_ERROR));
                    break;
                default:break;
            }
        }
    };
    public void onBackPressed() {
        MLog.td("tjl" , "onBackPressed()");
        startActivity(new Intent(UpgradeReadyActivity.this,UpgradeActivity.class));
        return;
    }
    protected void onDestroy() {
        AppConst.bluetoothAdapter=null;
        super.onDestroy();
    }
    private void initView(@Nullable Bundle savedInstanceState) {

        imageButton5=findViewById(R.id.imageButton5);
        imageButton6=findViewById(R.id.imageButton6);
        imageButton7=findViewById(R.id.imageButton7);
        imageView1=findViewById(R.id.imageView1);
        textview_name=findViewById(R.id.textView_name);
        progressBar_au1=findViewById(R.id.progressBar_au1);
        progressBar_au1.setVisibility(View.INVISIBLE);
        switch (devType)
        {
            case DEV_OLD:
                this.getWindow().setBackgroundDrawable(getResources().getDrawable(R.mipmap.pic_bg_bot));
                imgBg=R.mipmap.pic_bg_bot;
                imgStart=R.mipmap.pic_btn_upgrade_7;
                imgStartp=R.mipmap.pic_btn_upgrade_7p;
                imgHelp=R.mipmap.pic_btn_upgrade_9;
                imgHelpp=R.mipmap.pic_btn_upgrade_9p;
                imgLogo=R.mipmap.pic_logo_bot;
                textview_name.setText("MatataBot");
                break;
            case DEV_CON:
                this.getWindow().setBackgroundDrawable(getResources().getDrawable(R.mipmap.pic_bg_con));
                imgBg=R.mipmap.pic_bg_con;
                imgStart=R.mipmap.pic_btn_upgrade_2;
                imgStartp=R.mipmap.pic_btn_upgrade_2p;
                imgHelp=R.mipmap.pic_btn_upgrade_3;
                imgHelpp=R.mipmap.pic_btn_upgrade_3p;
                imgLogo=R.mipmap.pic_logo_con;
                textview_name.setText("MatataCon");
                break;
            case DEV_BOT:
                this.getWindow().setBackgroundDrawable(getResources().getDrawable(R.mipmap.pic_bg_bot));
                imgBg=R.mipmap.pic_bg_bot;
                imgStart=R.mipmap.pic_btn_upgrade_7;
                imgStartp=R.mipmap.pic_btn_upgrade_7p;
                imgHelp=R.mipmap.pic_btn_upgrade_9;
                imgHelpp=R.mipmap.pic_btn_upgrade_9p;
                imgLogo=R.mipmap.pic_logo_bot;
                textview_name.setText("MatataBot");
                break;
            default:break;
        }
        imageButton6.setBackground(getResources().getDrawable(imgStart));
        imageButton7.setBackground(getResources().getDrawable(imgHelp));
        imageView1.setImageDrawable(getResources().getDrawable(imgLogo));
        textview_aur1=findViewById(R.id.textview_aur1);
        textview_aur1.setText("v "+AppConst.versionName+"\n"+AppConst.All_RIGHT_TEXT);
        imageButton5.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).getBackground().setAlpha(128);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).getBackground().setAlpha(255);
                    try{
                        startActivity(new Intent(UpgradeReadyActivity.this,UpgradeActivity.class));//启动MainActivity
                    }catch (Exception e){
                        MLog.td("tjl", String.valueOf(e.getMessage()));
                    }
                }
                return false;
            }
        });
        imageButton6.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).getBackground().setAlpha(128);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    progressBar_au1.setVisibility(View.VISIBLE);
                    ((ImageButton)view).getBackground().setAlpha(255);
                   BlueStartDFU();
                }
                return false;
            }
        });
        imageButton7.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).getBackground().setAlpha(128);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).getBackground().setAlpha(255);
                    onClickWatchGuideVideo();
                }
                return false;
            }

        });

    }
    private void onClickWatchGuideVideo() {
        try {
            MLog.td("tjl", "--- onClickWatchGuideVideo ---");
            String urlStr = AppConst.VIDEO_URL_YOUTU;
            if (FunctionUtils.isZh(this) == true) {
                urlStr = AppConst.VIDEO_URL_BILIBILI;
            }
            MLog.td("tjl", "onClickWatchGuideVideo --- urlStr = " + urlStr);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(urlStr);
            intent.setData(content_url);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            MLog.e("tjl", "onClickWatchGuideVideo failed error =  " + e.getCause());
        }
    }
    private void BlueStartDFU()
    {
        try{
            AppConst.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            AppConst.bluetoothAdapter.enable();
            byte[] mac=Global.getMacBytes(devMac);
            MLog.td("tjl" ,"Connect to:"+Global.getMacString(mac));
            BluetoothDevice bluetoothDevice = AppConst.bluetoothAdapter.getRemoteDevice(mac);
            BluetoothGatt bluetoothGatt;
            if(bluetoothDevice != null)
            {
                bluetoothGatt = bluetoothDevice.connectGatt(this,false,gattCallback);
            }
            else{
                MLog.td("tjl" , "onItemClick: 没有该设备");
            }
        }catch (Exception e){
            MLog.td("tjl", String.valueOf(e.getMessage()));
        }
    }
    private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {

        public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                            int newState) {
            MLog.td("tjl", "连接完成 U,"+status+","+newState);
            if (newState == BluetoothGatt.STATE_CONNECTED) {
                MLog.td("tjl", "开始扫描服务");
                gatt.discoverServices();
            }
            if(newState==0&&status==0){
                Message tempMsg = mHandler.obtainMessage();
                tempMsg.what = 1003;
                mHandler.sendMessage(tempMsg);
            }
        }
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt,
                                          BluetoothGattCharacteristic characteristic, int status) {
            MLog.td("tjl", "onCharacteristicWrite（）");
         //   super.onCharacteristicWrite(gatt, characteristic, status);

        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt,
                                      BluetoothGattDescriptor descriptor, int status) {
        };

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            MLog.td("tjl", "扫描完成");
            List<BluetoothGattService> servicesList;
            servicesList = gatt.getServices();
            for (BluetoothGattService S : servicesList) {
                MLog.td("tjl", "UUID:"+String.valueOf(S.getUuid())+" TYPE:"+S.getType());
            }
            BluetoothGattService service = gatt.getService(UUID.fromString(UUID_SERVICE));
            MLog.td("tjl","选中服务:"+String.valueOf(service.getUuid()));
            BluetoothGattCharacteristic writeCharacteristic;
            writeCharacteristic =  service.getCharacteristic(UUID.fromString(UUID_WRITE));
            MLog.td("tjl","选中写UUID:"+String.valueOf(writeCharacteristic.getUuid()));
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (devType)
            {
                case DEV_OLD:writeCharacteristic.setValue(BOT_DFU_CMD);break;
                case DEV_CON:writeCharacteristic.setValue(CON_DFU_CMD);break;
                case DEV_BOT:writeCharacteristic.setValue(BOT_DFU_CMD);break;
                default:break;
            }
            boolean b=gatt.writeCharacteristic(writeCharacteristic);
            MLog.td("tjl", "发送完数据:"+b);
            final String name=Global.getDFUNameByName(AppConst.BT_BE_NAME_ALL,devName);
            AppConst.bluetoothAdapter=null;
            if(devType==DEV_CON)
            {
                Message tempMsg = mHandler.obtainMessage();
                tempMsg.what = 1000;
                devName=name;
                mHandler.sendMessage(tempMsg);
                MLog.td("tjl", "这是一个控制器1:"+name+","+devName);

            }else
            startActivity(new Intent(UpgradeReadyActivity.this , UpgradeStartActivity.class).putExtra("mac" ,devMac).putExtra("name" ,name));
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {

        }
    };
}