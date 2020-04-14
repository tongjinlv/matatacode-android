package com.matatalab.matatacode;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;

import android.provider.Settings;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.matatalab.matatacode.model.MyAdapter;
import com.matatalab.matatacode.model.PublicWay;
import com.matatalab.matatacode.utils.MLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UpgradeActivity extends AppCompatActivity {
    private static final String TAG = UpgradeActivity.class.getSimpleName();
    private ImageButton imageButton3;
    private ImageButton imageButton4;
    private ImageButton imageButton8;
    private String devName;
    private String devMac;
    private ProgressBar progressBar1;
    private List<Map<String,Object>> devList;
    private ListView list_view1;
    private TextView textview_au11;
    private boolean startopen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MLog.td("tjl", "setContentView(R.layout.activity_upgrade);");
        setContentView(R.layout.activity_upgrade);
        initView(savedInstanceState);
        initData();

    }

    private void initData() {
        AppConst.bluetoothAdapter=null;
        devList = new ArrayList<Map<String,Object>>();
        AppConst.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!AppConst.bluetoothAdapter.isEnabled())AppConst.bluetoothAdapter.enable();
        if (AppConst.bluetoothAdapter == null )
        {
            startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
        }
        if (AppConst.bluetoothAdapter == null)
        {
            Toast.makeText(this, "Not Find Bluetooth Device", Toast.LENGTH_SHORT).show();
        }
    }


    private BluetoothAdapter.LeScanCallback mBLEScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
         String name=String.valueOf(device.getName());
         if(name.length()>1&&rssi>-75)
         {
             if(Global.isMatataDevice(AppConst.BT_APP_NAME_ALL,name)||Global.isMatataDevice(AppConst.BT_DFU_NAME_ALL,name))
             {
                 MLog.td("tjl" , "mac:" + device.getAddress() + " rssi=" + rssi + " name=" + name);
                 Map<String,Object> map1 = new HashMap<String,Object>();
                 map1.put("name" , name);
                 map1.put("rssi" , String.valueOf(rssi));
                 map1.put("mac", String.valueOf(device.getAddress()));
                 if(rssi>-60)map1.put("pic" , R.mipmap.rssi0);
                 else if(rssi>-70)map1.put("pic" , R.mipmap.rssi1);
                 else if(rssi>-80)map1.put("pic" , R.mipmap.rssi2);
                 else if(rssi>-90)map1.put("pic" , R.mipmap.rssi3);
                 devList.add(map1);
                 devList=Global.removeRepeatMapByKey(devList,"mac");

             }
         }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MLog.td("tjl" , "handleMessage what=" +String.valueOf(msg.what));
            switch (msg.what) {
                case 1001:
                    if(devList.size()>0) {
                        list_view1.setVisibility(View.VISIBLE);
                        progressBar1.setVisibility(View.INVISIBLE);
                        SimpleAdapter simpleAdapter = new SimpleAdapter(UpgradeActivity.this , devList , R.layout.list_item ,
                                new String[]{"name" , "mac" , "pic"} , new int[]{R.id.tv1 , R.id.tv2 , R.id.iv1});
                        list_view1.setAdapter(simpleAdapter);
                        list_view1.setVisibility(View.VISIBLE);
                        imageButton8.setVisibility(View.VISIBLE);
                        imageButton3.setVisibility(View.INVISIBLE);
                    }else
                    {
                        imageButton8.setVisibility(View.INVISIBLE);
                        imageButton3.setVisibility(View.VISIBLE);
                        progressBar1.setVisibility(View.INVISIBLE);
                        final Mdialog mdialog = new Mdialog(UpgradeActivity.this);
                        //退出
                        mdialog.setTitle(getString(R.string.ALERT_TITLE));
                        mdialog.setMessage(getString(R.string.ALERT_MESSAGE_BLE_FIND_NOTHING));
                        mdialog.setBa(getString(R.string.ALERT_BUTTON_CANCEL));
                        mdialog.setBb(getString(R.string.ALERT_BUTTON_QUIT));
                        mdialog.setOnButtonAListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mdialog.isShowing()) {
                                    mdialog.dismiss();
                                }
                            }
                        });
                        mdialog.setOnButtonBListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (mdialog != null && mdialog.isShowing()) {
                                    mdialog.dismiss();
                                }
                            }
                        });
                        mdialog.setCancelable(false);                //用户可以点击后退键关闭 Dialog
                        mdialog.setCanceledOnTouchOutside(false);   //用户不可以点击外部来关闭 Dialog
                        mdialog.show();
                    }
                    break;
                case 1002:break;
                default:break;
            }
        }
    };
    private void ShufflePlayback(){

        MediaPlayer mMediaPlayer;
        mMediaPlayer=MediaPlayer.create(this, R.raw.du);
        mMediaPlayer.start();
    }
    protected void onDestroy() {
        MLog.td("tjl" , "onDestroy()");
        AppConst.bluetoothAdapter=null;
        super.onDestroy();
    }
    public void onBackPressed() {
        MLog.td("tjl" , "onBackPressed()");
        AppConst.bluetoothAdapter=null;
        startActivity(new Intent(UpgradeActivity.this,FristActivity.class));
        return;
    }
    private void initView(@Nullable Bundle savedInstanceState) {

        imageButton3=findViewById(R.id.imageButton3);
        imageButton4=findViewById(R.id.imageButton4);
        imageButton8=findViewById(R.id.imageButton8);
        progressBar1=findViewById(R.id.progressBar1);
        list_view1=findViewById(R.id.list_view1);
        progressBar1.setVisibility(View.INVISIBLE);
        list_view1.setVisibility(View.INVISIBLE);
        imageButton8.setVisibility(View.INVISIBLE);
        textview_au11=findViewById(R.id.textview_au11);
        Global.setHeightAsWidth(this,imageButton3,20,33);
        Global.setHeightAsWidth(this,imageButton8,20,33);
        textview_au11.setText("v "+AppConst.versionName+"\n"+AppConst.All_RIGHT_TEXT);
        this.getWindow().setBackgroundDrawable(getResources().getDrawable(R.mipmap.item25));
        imageButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MLog.td("tjl", "imagebutton3.down");
                imageButton3.setVisibility(View.INVISIBLE);
                imageButton8.setVisibility(View.INVISIBLE);
                progressBar1.setVisibility(View.VISIBLE);
                AppConst.bluetoothAdapter.startLeScan(mBLEScanCallback);
                ShufflePlayback();
                Thread myThread=new Thread(){
                    public void run() {
                        while(true) {
                            try {
                                sleep(2000);
                                if(devList.size()==0)sleep(2000);
                                if(devList.size()==0)sleep(2000);
                                AppConst.bluetoothAdapter.stopLeScan(mBLEScanCallback);
                                sleep(300);
                                mHandler.sendEmptyMessageDelayed(1001, 1000);
                                return;
                            } catch (Exception e) {
                                MLog.td("tjl" , String.valueOf(e.getMessage()));
                            }
                        }
                    }
                };
                myThread.start();
            }
        });
        imageButton8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MLog.td("tjl", "imagebutton3.down");
                progressBar1.setVisibility(View.VISIBLE);
                imageButton8.setVisibility(View.INVISIBLE);
                list_view1.setVisibility(View.INVISIBLE);
                devList.clear();
                AppConst.bluetoothAdapter.startLeScan(mBLEScanCallback);
                ShufflePlayback();
                Thread myThread=new Thread(){
                    public void run() {
                        while(true) {
                            try {
                                sleep(2000);
                                if(devList.size()==0)sleep(2000);
                                if(devList.size()==0)sleep(2000);
                                AppConst.bluetoothAdapter.stopLeScan(mBLEScanCallback);
                                sleep(300);
                                mHandler.sendEmptyMessageDelayed(1001, 1000);
                                return;
                            } catch (Exception e) {
                                MLog.td("tjl" , String.valueOf(e.getMessage()));
                            }
                        }
                    }
                };
                myThread.start();
            }
        });
        list_view1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i=0;

                for (Map<String, Object> m : devList) {
                    MLog.td("tjl" , String.valueOf(position) + "--," + m.get("name") + "," + m.get("rssi") + "," + m.get("mac"));
                    if(i++==position) {
                        MLog.td("tjl" , String.valueOf(position) + "+++," + m.get("name") + "," + m.get("rssi") + "," + m.get("mac"));
                        devName=String.valueOf(m.get("name"));
                        devMac=String.valueOf(m.get("mac"));
                        try {
                            if(Global.isMatataDevice(AppConst.BT_APP_NAME_ALL,devName))startActivity(new Intent(UpgradeActivity.this , UpgradeReadyActivity.class).putExtra("mac" ,String.valueOf(m.get("mac"))).putExtra("name" ,String.valueOf(m.get("name"))));
                            if(Global.isMatataDevice(AppConst.BT_DFU_NAME_ALL,devName))
                            {
                                if(Global.isMatataDevice(AppConst.BT_DFU_NAME_OLD,devName))
                                {
                                    if(startopen==false)
                                    {
                                        startopen=true;
                                        AlertDialog.Builder builder = new AlertDialog.Builder(UpgradeActivity.this);
                                        builder.setTitle(getString(R.string.DFU_MATADFU_TYPE));
                                        devName="mdfutow";
                                        builder.setSingleChoiceItems(new String[]{"Tower","Bot"}, 0, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int which) {
                                                MLog.td("tjl" ,"Select:"+String.valueOf(which));
                                                switch (which)
                                                {
                                                    case 0:devName="mdfutow";break;
                                                    case 1:devName="mdfubot";break;
                                                }
                                            }
                                        });
                                        builder.setPositiveButton(getString(R.string.ALERT_BUTTON_OK), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(UpgradeActivity.this ,UpgradeStartActivity.class).putExtra("mac" ,devMac).putExtra("name",devName));
                                            }
                                        });
                                        builder.setNegativeButton(getString(R.string.ALERT_BUTTON_CANCEL),null);
                                        builder.setCancelable(false);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                        startopen=false;
                                    }
                                }else
                                {
                                    startActivity(new Intent(UpgradeActivity.this , UpgradeStartActivity.class).putExtra("mac" ,devMac).putExtra("name" ,devName));
                                }
                            }
                        } catch (Exception e) {
                            MLog.td("tjl" , String.valueOf(e.getMessage()));
                        }
                    }
                }
            }
        });
        imageButton4.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    ((ImageButton)view).getBackground().setAlpha(128);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    ((ImageButton)view).getBackground().setAlpha(255);
                    try{
                        AppConst.bluetoothAdapter=null;
                        startActivity(new Intent(UpgradeActivity.this,FristActivity.class));//启动MainActivity
                    }catch (Exception e){
                        MLog.td("tjl", String.valueOf(e.getMessage()));
                    }
                }
                return false;
            }
        });
    }
}