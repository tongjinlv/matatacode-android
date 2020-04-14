package com.matatalab.matatacode;

import android.bluetooth.BluetoothAdapter;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.matatalab.matatacode.model.PublicWay;
import com.matatalab.matatacode.utils.FunctionUtils;
import com.matatalab.matatacode.utils.MLog;

import java.util.List;
import java.util.UUID;


public class UpgradeStartActivity extends AppCompatActivity {
    private ImageButton imageButton11;
    private ImageButton imageButton9;
    private ImageButton imageButton10;
    private String devMac,devName;
    private ImageView imageView1;
    private int devType,imgBg,imgLogo,imgStart,imgStartp,imgHelpp,imgHelp;
    private boolean dfuMode;
    private static final int  DEV_OLD= 501;
    private static final int  DEV_BOT= 502;
    private static final int  DEV_CON= 503;
    private static final int  DEV_TOWER= 504;
    private TextView textview_aus1;
    private TextView textview_name;
    private static final String UUID_SERVICE = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    private static final String UUID_NOTIFY = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    private static final String UUID_WRITE = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    private static final byte[] BOT_DFU_CMD= new byte[]{(byte)0x80 ,(byte)0xaa ,(byte)0x55 ,(byte)0x81 , (byte)0x00};
    private static final byte[] CON_DFU_CMD= new byte[]{(byte)0x80 ,(byte)0xaa ,(byte)0x55 ,(byte)0x13 , (byte)0x00};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppConst.bluetoothAdapter=null;
        MLog.td("tjl", "setContentView(R.layout.activity_upgrade);");
        setContentView(R.layout.activity_upgrade_start);
        Intent intent=getIntent();
        devMac=intent.getStringExtra("mac");
        devName= intent.getStringExtra("name");
        MLog.td("tjl" , "name="+devName+",mac="+devMac);
        if(devName.equals(AppConst.BT_DFU_NAME_OLD))devType=DEV_OLD;
        if(devName.equals(AppConst.BT_DFU_NAME_BOT))devType=DEV_BOT;
        if(devName.equals(AppConst.BT_DFU_NAME_TOWER))devType=DEV_TOWER;
        if(devName.equals(AppConst.BT_DFU_NAME_CON))devType=DEV_CON;
        if(devName.toLowerCase().indexOf("dfu")>-1)dfuMode=true;else dfuMode=false;
        MLog.td("tjl" , "devType:"+String.valueOf(devType)+"dfuMode:"+dfuMode);
        initView(savedInstanceState);
        initData();
    }

    private void initData() {

    }

    private Handler UploadDialog = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MLog.td("tjl" , "handleMessage --- msg = " + msg);
            switch (msg.what) {
                case 1001:
                    break;
                case 1002:break;
                default:break;
            }
        }
    };
    public void onBackPressed() {
        MLog.td("tjl" , "onBackPressed()");
        startActivity(new Intent(UpgradeStartActivity.this,UpgradeActivity.class));
        return;
    }
    protected void onDestroy() {
        super.onDestroy();
    }
    private void initView(@Nullable Bundle savedInstanceState) {

        imageButton11=findViewById(R.id.imageButton11);
        imageButton9=findViewById(R.id.imageButton9);
        imageButton10=findViewById(R.id.imageButton10);
        imageView1=findViewById(R.id.imageView1);
        textview_name=findViewById(R.id.textView_name);
        switch (devType)
        {
            case DEV_OLD:
                this.getWindow().setBackgroundDrawable(getResources().getDrawable(R.mipmap.pic_bg_bot));
                imgBg=R.mipmap.pic_bg_bot;
                imgStart=R.mipmap.pic_btn_upgrade_8;
                imgStartp=R.mipmap.pic_btn_upgrade_8p;
                imgHelp=R.mipmap.pic_btn_upgrade_9;
                imgHelpp=R.mipmap.pic_btn_upgrade_9p;
                imgLogo=R.mipmap.pic_logo_bot;
                imgStartp=R.mipmap.pic_btn_upgrade_8p;
                textview_name.setText("MatataBot");
                break;
            case DEV_CON:
                this.getWindow().setBackgroundDrawable(getResources().getDrawable(R.mipmap.pic_bg_con));
                imgBg=R.mipmap.pic_bg_con;
                imgStart=R.mipmap.pic_btn_upgrade_4;
                imgStartp=R.mipmap.pic_btn_upgrade_4p;
                imgHelp=R.mipmap.pic_btn_upgrade_3;
                imgHelpp=R.mipmap.pic_btn_upgrade_3p;
                imgLogo=R.mipmap.pic_logo_con;
                textview_name.setText("MatataCon");
                break;
            case DEV_BOT:
                this.getWindow().setBackgroundDrawable(getResources().getDrawable(R.mipmap.pic_bg_bot));
                imgBg=R.mipmap.pic_bg_bot;
                imgStart=R.mipmap.pic_btn_upgrade_8;
                imgStartp=R.mipmap.pic_btn_upgrade_8p;
                imgHelp=R.mipmap.pic_btn_upgrade_9;
                imgHelpp=R.mipmap.pic_btn_upgrade_9p;
                imgLogo=R.mipmap.pic_logo_bot;
                textview_name.setText("MatataBot");
                break;
            case DEV_TOWER: this.getWindow().setBackgroundDrawable(getResources().getDrawable(R.mipmap.pic_bg_tower));
                imgBg=R.mipmap.pic_bg_tower;
                imgStart=R.mipmap.pic_btn_upgrade_5;
                imgStartp=R.mipmap.pic_btn_upgrade_5p;
                imgHelp=R.mipmap.pic_btn_upgrade_6;
                imgHelpp=R.mipmap.pic_btn_upgrade_6p;
                imgLogo=R.mipmap.pic_logo_tower;
                textview_name.setText("MatataTower");
            break;
            default:break;
        }
        imageButton9.setBackground(getResources().getDrawable(imgStart));
        imageButton10.setBackground(getResources().getDrawable(imgHelp));
        imageView1.setImageDrawable(getResources().getDrawable(imgLogo));
        textview_aus1=findViewById(R.id.textview_aus1);
        textview_aus1.setText("v "+AppConst.versionName+"\n"+AppConst.All_RIGHT_TEXT);
        imageButton11.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).getBackground().setAlpha(128);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).getBackground().setAlpha(255);
                    try{
                        startActivity(new Intent(UpgradeStartActivity.this,UpgradeActivity.class));//启动MainActivity
                    }catch (Exception e){
                        MLog.td("tjl", String.valueOf(e.getMessage()));
                    }
                }
                return false;
            }
        });
        imageButton9.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).getBackground().setAlpha(128);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).getBackground().setAlpha(255);
                    MLog.td("tjl", "imageButton9.Dowm");
                    startActivity(new Intent(UpgradeStartActivity.this , UpgradingActivity.class).putExtra("mac" ,devMac).putExtra("name" ,devName));
                }
                return false;
            }
        });
        imageButton10.setOnTouchListener( new View.OnTouchListener() {
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
}