package com.matatalab.matatacode;
import android.graphics.Bitmap;
import android.app.Activity;
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
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.matatalab.matatacode.model.FileStorageHelper;
import com.matatalab.matatacode.utils.MLog;
import com.matatalab.matatacode.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.inuker.bluetooth.library.Constants;

public class CodingActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BLOCKLY_URL = "file:///android_asset/blockly/index_test.html?lang=";
    private List<Map<String,Object>> devList;
    private String name;
    private boolean run_flag=true;
    private ImageButton imageButton_ac1;
    private ImageButton imagebutton_ac2;
    private ImageButton imagebutton_ac3;
    private ImageButton imagebutton_ac4;
    private Mdialog mdialog;
    private int SelectId=0;
    private boolean codesloaded=false;
    private Button button_ac5;
    private Button button_ac6;
    private Button button_ac7;
    private Button button_ac8;
    private Button button_ac9;
    private Thread myThread1;
    private boolean first=true,runfrist=true;
    private WebView webView;
    private String tempfile;
    private ProgressBar progressBar15;
    public interface JsCallback{
        public void Click(String b);
    }

    private Bitmap captureScreen(Activity context) {
        View cv = context.getWindow().getDecorView();

        cv.setDrawingCacheEnabled(true);
        cv.buildDrawingCache();
        Bitmap bmp = cv.getDrawingCache();
        if (bmp == null) {
            return null;
        }

        bmp.setHasAlpha(false);
        bmp.prepareToDraw();
        return bmp;
    }
    public class AndroidtoJs extends Object {
        @JavascriptInterface
        public void hello(String msg) {
            MLog.td("tjl","JS调用了Android的hello方法");
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coding);
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        initView(savedInstanceState);
        tempfile =AppConst.runDirPath+"main.py";
        progressBar15=findViewById(R.id.progressBar15);
        progressBar15.setVisibility(View.INVISIBLE);
        webView = findViewById(R.id.webview1);
       // webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        File file1 = new File("/data/data/"+getPackageName()+"/web/");
        if(file1.exists())MLog.td("tjl","文件夹存在");
        webView.getSettings().setAppCachePath("/data/data/"+getPackageName()+"/web/");
        webView.loadUrl(BLOCKLY_URL+AppConst.lang);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new AndroidtoJs(),"Android");

        //允许网页使用js
        webView.setWebChromeClient(new WebChromeClient() {//允许有alert弹出框
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            };
            @Override
            public boolean onJsConfirm(WebView view, String url, String message,final JsResult result)
            {
                MLog.td("tjl", "onJsConfirm");
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(CodingActivity.this);
                normalDialog.setIcon(android.R.drawable.ic_dialog_alert);
                normalDialog.setTitle(getString(R.string.ALERT_TITLE));
                normalDialog.setMessage(message);
                normalDialog.setPositiveButton(getString(R.string.ALERT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                                return;
                            }
                        });
                normalDialog.setNegativeButton(getString(R.string.ALERT_BUTTON_CANCEL),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                                return;
                            }
                        });
                normalDialog.setCancelable(false);
                AlertDialog dialog = normalDialog.create();
                dialog.show();
                return true;
            }
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,final JsPromptResult result) {
             //   MLog.td("tjl" , "onJsPrompt url=" + url + ",message =[" + message + "], defaultValue=[" + defaultValue + "]");
                if(message.indexOf("type")>-1) {
                    Uri uri = Uri.parse(message);
                    String type = uri.getQueryParameter("type");
                    String msg = uri.getQueryParameter("msg");
                    MLog.td("tjl" , "onJsPrompt type=" + type + ",msg =[" + msg + "]");
                    if (type != null) switch (type) {
                        case "runcode":
                            int offset=message.indexOf("msg=");
                            if(offset>-1)offset+=4;
                            String msg1 =message.substring(offset);
                            MLog.td("tjl" ,"runcode:"+AppConst.CODE_HEARD+msg1);
                           // Global.WriteFile(tempfile ,AppConst.CODE_HEARD+msg1);
                            FileStorageHelper.copyFolder("/sdcard/matatacode/","/data/data/com.matatalab.matatacode/run/");
                            mHandler.sendEmptyMessageAtTime(1006,1000);
                            result.confirm("js调用了Android的方法成功啦");
                            return true;
                        case "init":
                            if (first) {
                                try {
                                    String text = Global.readFileData(AppConst.EXAMPLE_PATH + name + ".txt");
                                    MLog.td("tjl",name+":read:"+text);
                                    // SavePicture(AppConst.EXAMPLE_PATH+ name + "_pic.jpg");
                                    text = text.replace("\"" , "\\\"");
                                    String js = "Blockly.Xml.domToWorkspace(Blockly.Xml.textToDom('" + text + "'), Code.workspace)";
                                    runJsCode("javascript:" + js , null);
                                } catch (Exception E) {
                                    MLog.td("tjl" , (E.getMessage()));
                                }
                                first = false;
                            }
                            result.confirm("js调用了Android的方法成功啦");
                            return true;
                        case "codesloaded":
                            result.confirm("js调用了Android的方法成功啦");
                            return true;
                        case "savecode":
                            String temp = AppConst.EXAMPLE_PATH + name + ".txt";
                            MLog.td("tjl" , "save " + msg + "\r\nwrite to" + temp);
                            result.confirm("js调用了Android的方法成功啦");
                            Global.writeFileData(temp , msg);
                            return true;
                        case "stopcode":
                          //  AppConst.cliPython.Stop();
                            AppConst.chaquoPython.Exit();
                            result.confirm("js调用了Android的方法成功啦");
                            return true;
                        case "codeend":
                            result.confirm("js调用了Android的方法成功啦");
                            return true;
                        default:
                            MLog.td("tjl" , "!@!@!@!@@!@!@!@!@!@!@!@1");

                    }
                }else
                {
                    final EditText inputServer = new EditText(CodingActivity.this);
                    inputServer.setText(defaultValue);
                    AlertDialog.Builder builder = new AlertDialog.Builder(CodingActivity.this);
                    builder.setTitle(message).setIcon(android.R.drawable.ic_dialog_alert).setView(inputServer)
                            .setNegativeButton(getString(R.string.ALERT_BUTTON_CANCEL), new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    MLog.td("tjl","内容:"+inputServer.getText().toString());
                                    result.cancel();
                                }
                            });
                    builder.setPositiveButton(getString(R.string.ALERT_BUTTON_OK), new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            MLog.td("tjl","内容:"+inputServer.getText().toString());
                            result.confirm(inputServer.getText().toString());
                        }
                    });
                    builder.setCancelable(false);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            };
            @Override
            public boolean onJsAlert(WebView view, String url, String message,final JsResult result) {
                MLog.td("tjl" , "onJsAlert url:"+url+",message:"+message);
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(CodingActivity.this);
                normalDialog.setIcon(android.R.drawable.ic_dialog_alert);
                normalDialog.setTitle(getString(R.string.ALERT_TITLE));
                normalDialog.setMessage(message);
                normalDialog.setPositiveButton(getString(R.string.ALERT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                                return;
                            }
                        });
                normalDialog.setCancelable(false);
                AlertDialog dialog = normalDialog.create();
                dialog.show();
                return true;
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100)codesloaded=true;
                MLog.td("tjl", "onProgressChanged="+newProgress);
            }
        });
    }
    public void runJsCode(final String js, final JsCallback listener){
        MLog.td("tjl", js);
        if (webView != null) {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.evaluateJavascript(js, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                          //  MLog.td("tjl", "onReceiveValue == " + s);
                            if(listener!=null)listener.Click(s);
                        }
                    });
                }
            });
        }
    }
    private void initView(@Nullable Bundle savedInstanceState) {
        imageButton_ac1=findViewById(R.id.imageButton_ac1);
        imagebutton_ac2=findViewById(R.id.imagebutton_ac2);
        imagebutton_ac3=findViewById(R.id.imagebutton_ac3);
        imagebutton_ac4=findViewById(R.id.imagebutton_ac4);
        button_ac5=findViewById(R.id.imagebutton_ac5);
        button_ac6=findViewById(R.id.imagebutton_ac6);
        button_ac7=findViewById(R.id.imagebutton_ac7);
        button_ac8=findViewById(R.id.imagebutton_ac8);
        button_ac9=findViewById(R.id.imagebutton_ac9);
        button_ac5.setText(this.name);
        Global.setHeightAsWidth(this,imageButton_ac1,10,40);
        Global.setHeightAsWidth(this,imagebutton_ac2,6,65);
        Global.setHeightAsWidth(this,imagebutton_ac4,6,65);
        Global.setHeightAsWidth(this,imagebutton_ac3,5,77);
        Global.setHeight(this,button_ac5,3);
        if(AppConst.mClient!=null)
        {
            SetBluetoothico(AppConst.DeviceName);
            AppConst.mClient.registerConnectStatusListener(AppConst.DeviceMac, mBleConnectStatusListener);
        }
        imageButton_ac1.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).getBackground().setAlpha(128);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).getBackground().setAlpha(255);
                    savecode();
                }
                return false;
            }

        });

        imagebutton_ac2.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).getBackground().setAlpha(128);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).getBackground().setAlpha(255);
                    runJsCode("javascript:Code.Undo()",null);
                }
                return false;
            }

        });
        imagebutton_ac3.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).getBackground().setAlpha(128);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).getBackground().setAlpha(255);
                    runJsCode("javascript:Code.discard()",null);
                }
                return false;
            }

        });
        imagebutton_ac4.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).getBackground().setAlpha(128);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).getBackground().setAlpha(255);
                    runJsCode("javascript:Code.Redo()",null);
                }
                return false;
            }
        });

        button_ac5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MLog.td("tjl", "button_ac5.down");
                final EditText inputServer = new EditText(CodingActivity.this);
                inputServer.setText(button_ac5.getText());
                AlertDialog.Builder builder = new AlertDialog.Builder(CodingActivity.this);
                builder.setTitle(getString(R.string.ALERT_TITLE)).setIcon(android.R.drawable.ic_dialog_alert).setView(inputServer)
                        .setNegativeButton(getString(R.string.ALERT_BUTTON_CANCEL), null);
                builder.setPositiveButton(getString(R.string.ALERT_BUTTON_OK), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        MLog.td("tjl","内容:"+inputServer.getText().toString());
                        File file = new File(AppConst.EXAMPLE_PATH + name + ".txt");
                        file.renameTo(new File(AppConst.EXAMPLE_PATH + inputServer.getText().toString() + ".txt"));
                        button_ac5.setText(inputServer.getText().toString());
                        CodingActivity.this.name=inputServer.getText().toString();
                    }
                });
                builder.show();
            }
        });
        button_ac6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(AppConst.mClient==null) {
                    Message tempMsg = new Message();
                    tempMsg.what = 1007;
                    mHandler.sendMessage(tempMsg);
                    progressBar15.setVisibility(View.VISIBLE);
                    button_ac6.setBackground(getResources().getDrawable(R.mipmap.pic_code_blebreak_p));
                    button_ac6.setEnabled(false);
                }else
                {
                    AppConst.mClient.disconnect(AppConst.DeviceMac);
                }
            }
        });
        button_ac7.setOnClickListener(new View.OnClickListener() {
            int SelectId=0;
            String[] lLists;
            String[] List;
            public void onClick(View v) {
                MLog.td("tjl", "button_ac7.down");
                button_ac7.setBackground(getResources().getDrawable(R.mipmap.pic_code_folder_p));
                File file=new File(AppConst.EXAMPLE_PATH);
                lLists=file.list();
                List=new String[Global.TextEndCount(lLists)];
                int n=0;
                for(int i=0;i<lLists.length;i++)
                {
                    if(lLists[i].toLowerCase().endsWith(".txt")) List[n++]=lLists[i].replace(".txt","");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(CodingActivity.this);
                builder.setTitle(CodingActivity.this.getString(R.string.ALERT_TITLE));
                builder.setSingleChoiceItems(List, 0, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        MLog.td("tjl" ,"选择:"+String.valueOf(which));
                        SelectId=which;
                    }
                });
                builder.setPositiveButton(CodingActivity.this.getString(R.string.ALERT_BUTTON_OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MLog.td("tjl" ,"确认 i="+SelectId);
                        try {
                            String text = Global.readFileData(AppConst.EXAMPLE_PATH + List[SelectId] + ".txt");
                            MLog.td("tjl" , AppConst.EXAMPLE_PATH + List[SelectId] + ".txt"+"text:"+text);
                            text = text.replace("\"" , "\\\"");
                            String js = "Blockly.Xml.domToWorkspace(Blockly.Xml.textToDom('" + text + "'), Code.workspace)";
                            runJsCode("javascript:" + js , null);
                        } catch (Exception E) {
                            MLog.td("tjl" , (E.getMessage()));
                        }
                        button_ac7.setBackground(getResources().getDrawable(R.mipmap.pic_code_folder));
                    }
                });
                builder.setNegativeButton(CodingActivity.this.getString(R.string.ALERT_BUTTON_CANCEL),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        button_ac7.setBackground(getResources().getDrawable(R.mipmap.pic_code_folder));
                    }
                });
                builder.show();
            }
        });
        button_ac9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                button_ac9.setBackground(getResources().getDrawable(R.mipmap.stopp));
                MLog.td("tjl", "button_ac7.down");
                runJsCode("javascript:Code.stopcode()", new JsCallback() {
                    public void Click(String b)
                    {
                        MLog.td("tjl","JsCallback:"+b);
                        button_ac9.setBackground(getResources().getDrawable(R.mipmap.stop));
                    }
                });
            }
        });
        button_ac9.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((Button)view).setBackground(getResources().getDrawable(R.mipmap.stopp));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((Button)view).setBackground(getResources().getDrawable(R.mipmap.stop));
                }
                return false;
            }
        });
        button_ac8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MLog.td("tjl", "button_ac8.down");
                runfrist=true;
                if(AppConst.mClient==null) {
                    mHandler.sendEmptyMessageDelayed(1001, 1000);
                    return;
                }
                runJsCode("javascript:Code.runPython()", new JsCallback() {
                    public void Click(String b)
                    {
                        MLog.td("tjl","JsCallback:"+b);
                    }
                });
            }
        });
        button_ac8.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((Button)view).setBackground(getResources().getDrawable(R.mipmap.playp));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((Button)view).setBackground(getResources().getDrawable(R.mipmap.play));
                }
                return false;
            }

        });

    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButton_ac1:break;
            default:Toast.makeText(CodingActivity.this,
                    view.getId()+"按钮被点击", Toast.LENGTH_SHORT).show();break;
        }

    }
    public void SetBluetoothico(String msg)
    {
        if(msg==null)return;
        if(msg.indexOf("MatataCon")>-1) button_ac6.setBackground(getResources().getDrawable(R.mipmap.pic_code_blelink_con));
        if(msg.indexOf("MatataBot")>-1) button_ac6.setBackground(getResources().getDrawable(R.mipmap.pic_code_blelink_bot));
        if(msg.indexOf("Matalab")>-1) button_ac6.setBackground(getResources().getDrawable(R.mipmap.pic_code_blelink_old));
    }
    public  void Click(View view){
        MLog.td("tjl","aaaaaaaaaaaaaa"+view.getId());
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            MLog.td("tjl" , "handleMessage what=" +String.valueOf(msg.what)+"msg="+String.valueOf(msg.obj));
            switch (msg.what) {
                case 1001:
                    if(runfrist) {
                        runfrist=false;
                        if (mdialog == null) {
                            mdialog = new Mdialog(CodingActivity.this);
                            mdialog.setTitle(getString(R.string.ALERT_TITLE));
                            mdialog.setMessage(getString(R.string.ALERT_MESSAGE_CODE_LINK_BEFORE_RUN));
                            mdialog.setBa(getString(R.string.ALERT_BUTTON_CANCEL));
                            mdialog.setBb(getString(R.string.ALERT_BUTTON_LINK));
                            mdialog.setOnButtonAListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (mdialog.isShowing()) {
                                        MLog.td("tjl" , "选择了取消链接");
                                        mdialog.dismiss();
                                        mdialog = null;
                                    }
                                }
                            });
                            mdialog.setOnButtonBListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (mdialog != null && mdialog.isShowing()) {
                                        if(AppConst.mClient==null) {
                                            Message tempMsg = new Message();
                                            tempMsg.what = 1007;
                                            mHandler.sendMessage(tempMsg);
                                            progressBar15.setVisibility(View.VISIBLE);
                                            button_ac6.setBackground(getResources().getDrawable(R.mipmap.pic_code_blebreak_p));
                                            button_ac6.setEnabled(false);
                                        }
                                        mdialog.dismiss();
                                        mdialog = null;
                                    }
                                }
                            });
                            mdialog.setCancelable(false);                //用户可以点击后退键关闭 Dialog
                            mdialog.setCanceledOnTouchOutside(false);   //用户不可以点击外部来关闭 Dialog
                            mdialog.show();
                        }
                    }
                    break;
                case 1006:
                    myThread1=new Thread(){
                        @Override
                        public void run() {
                           // MLog.td("tjl","运行:"+tempfile);
                           // AppConst.cliPython.Start(tempfile);
                            AppConst.chaquoPython.Start();
                          //  AppConst.cliPython.Stop();
                            MLog.td("tjl","运行完毕");
                        }
                    };
                    myThread1.start();
                    break;
                case 1007://连接蓝牙
                    AppConst.mClient = new BluetoothClient(CodingActivity.this);
                    if(!AppConst.mClient.isBluetoothOpened()) {
                        AppConst.mClient.openBluetooth();
                    }
                    SearchRequest request = new SearchRequest.Builder()
                            .searchBluetoothLeDevice(2000, 2)   // 先扫BLE设备3次，每次3s
                            .searchBluetoothClassicDevice(0) // 再扫经典蓝牙5s,在实际工作中没用到经典蓝牙的扫描
                            .searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                            .build();
                    AppConst.mClient.search(request, new SearchResponse() {
                        @Override
                        public void onSearchStarted() {//开始搜素
                            MLog.td("tjl","开始搜素");
                            devList = new ArrayList<Map<String,Object>>();
                        }
                        @Override
                        public void onDeviceFounded(SearchResult device) {//找到设备 可通过manufacture过滤
                           // Beacon beacon = new Beacon(device.scanRecord);
                            // MLog.td("tjl",String.format("beacon for %s\n%s", device.getAddress(), beacon.toString()));
                            if(Global.isMatataDevice(AppConst.BT_APP_NAME_ALL,device.getName())){
                                MLog.td("tjl", "Name:" + device.getName() + ",Mac:" + device.getAddress() + ",Rssi:" + device.rssi);
                                Map<String,Object> map1 = new HashMap<String,Object>();
                                map1.put("name" , device.getName());
                                map1.put("rssi" , device.rssi);
                                if(device.rssi>-100&&device.rssi<0) {
                                    map1.put("mac", String.valueOf(device.getAddress()));
                                    devList.add(map1);
                                    devList = Global.removeRepeatMapByKey(devList, "mac");
                                }
                            }
                        }
                        @Override
                        public void onSearchStopped() {//搜索停止
                            MLog.td("tjl","搜索停止");
                            MLog.td("tjl" , "devList.size()=" +devList.size());
                            if(devList.size()>0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CodingActivity.this);
                                builder.setTitle(getString(R.string.ALERT_TITLE));
                                final String[] List=new String[devList.size()];
                                SelectId=0;
                                for(int i=0;i<devList.size();i++)
                                {
                                    final Map<String,Object> map=devList.get(i);
                                    List[i]=(String) map.get("name")+"("+String.valueOf(map.get("rssi"))+")";
                                }
                                builder.setSingleChoiceItems(List, 0, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        MLog.td("tjl" ,"选择:"+String.valueOf(which));
                                        SelectId=which;
                                    }
                                });
                                builder.setPositiveButton(getString(R.string.ALERT_BUTTON_OK), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MLog.td("tjl" ,"选中 i="+SelectId);
                                        Map<String,Object> map=devList.get(SelectId);
                                        AppConst.DeviceName=(String)map.get("name");
                                        AppConst.DeviceMac=(String)map.get("mac");
                                        BleConnectOptions options = new BleConnectOptions.Builder()
                                                .setConnectRetry(3)   // 连接如果失败重试3次
                                                .setConnectTimeout(30000)   // 连接超时30s
                                                .setServiceDiscoverRetry(3)  // 发现服务如果失败重试3次
                                                .setServiceDiscoverTimeout(20000)  // 发现服务超时20s
                                                .build();
                                       AppConst.mClient.registerConnectStatusListener(AppConst.DeviceMac, mBleConnectStatusListener);
                                       AppConst.mClient.connect((String)map.get("mac"), options, new BleConnectResponse() {
                                            @Override
                                            public void onResponse(int code, BleGattProfile data) {
                                                MLog.td("tjl" ,"code:"+code);
                                            }
                                        });
                                    }
                                });
                                builder.setNegativeButton(getString(R.string.ALERT_BUTTON_CANCEL),new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i){
                                        button_ac6.setEnabled(true);
                                        progressBar15.setVisibility(View.INVISIBLE);
                                        button_ac6.setBackground(getResources().getDrawable(R.mipmap.pic_code_blebreak));
                                        AppConst.mClient=null;
                                    }
                                });
                                builder.setCancelable(false);
                                builder.show();
                            }else
                            {
                                ToastUtils.show(getString(R.string.ALERT_MESSAGE_BLE_FIND_NOTHING));
                                AppConst.mClient=null;
                                button_ac6.setEnabled(true);
                                progressBar15.setVisibility(View.INVISIBLE);
                                button_ac6.setBackground(getResources().getDrawable(R.mipmap.pic_code_blebreak));
                            }
                        }
                        @Override
                        public void onSearchCanceled() {//搜索取消
                            MLog.td("tjl","搜索取消");
                        }
                    });
                    break;
                default:break;
            }
        }
    };
    private final BleConnectStatusListener mBleConnectStatusListener = new BleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {
            MLog.td("tjl","mac:"+mac+",status:"+status);
          if(status==16)
          {
              SetBluetoothico(AppConst.DeviceName);
              button_ac6.setEnabled(true);
              progressBar15.setVisibility(View.INVISIBLE);
              AppConst.mClient.notify(AppConst.DeviceMac,  UUID.fromString(AppConst.UUID_SERVICE), UUID.fromString(AppConst.UUID_NOTIFY), new BleNotifyResponse() {
                  @Override
                  public void onNotify(UUID service, UUID character, byte[] value) {
                      String s = new String(value);
                      AppConst.onNotify=value;
                      MLog.td("tjl","notify:"+s);
                  }
                  @Override
                  public void onResponse(int code) {
                      MLog.td("tjl","notify:onResponse");
                  }
              });
          }
          if(status==32)
          {
              button_ac6.setEnabled(true);
              progressBar15.setVisibility(View.INVISIBLE);
              button_ac6.setBackground(getResources().getDrawable(R.mipmap.pic_code_blebreak));
              try {
                  AppConst.mClient.unregisterConnectStatusListener(AppConst.DeviceMac, mBleConnectStatusListener);
                  AppConst.mClient.unnotify(AppConst.DeviceMac,  UUID.fromString(AppConst.UUID_SERVICE), UUID.fromString(AppConst.UUID_NOTIFY), new BleUnnotifyResponse() {
                      @Override
                      public void onResponse(int code) {
                          MLog.td("tjl","notify:unnotify");
                      }
                  });
              }catch (Exception E){}
              AppConst.mClient=null;
          }
        }

    };
    public void savecode()
    {
        if(codesloaded==false)
        {
            startActivity(new Intent(CodingActivity.this,FileActivity.class));
            return;
        }
        Bitmap bitmap= captureScreen(CodingActivity.this);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int cropWidth=h*4/5;
        if (bitmap != null)
        {
            Bitmap _icon=Bitmap.createBitmap(bitmap, w / 4, h/5, cropWidth, cropWidth, null, false);
            Global.savepic(_icon,AppConst.EXAMPLE_PATH + name+"_icon.jpg");
            Global.savepic(bitmap,AppConst.EXAMPLE_PATH + name+"_pic.jpg");
        }
        runJsCode("javascript:Code.getCode()", new JsCallback() {
            public void Click(String b)
            {
                b=b.replace("\\\\","\\");
                b=b.replace("\"","");
                MLog.td("tjl","JsCallback:"+b);
                String text=Global.hexStr2Str(b);
                String temp = AppConst.EXAMPLE_PATH + name + ".txt";
                Global.writeFileData(temp , text);
                MLog.td("tjl","写文件:"+temp);
                MLog.td("tjl","解析:"+text);
                startActivity(new Intent(CodingActivity.this,FileActivity.class));

            }
        });
    }
    @Override
    public void onBackPressed() {
        MLog.td("tjl" , "onBackPressed()");
        //if(AppConst.bluetoothGatt!=null)AppConst.bluetoothGatt.disconnect();
        savecode();
    }
    @Override
    protected void onDestroy() {
        MLog.td("tjl" , "onDestroy()");
        webView.destroy();
        run_flag=false;
        if(AppConst.mClient!=null)AppConst.mClient.unregisterConnectStatusListener(AppConst.DeviceMac,mBleConnectStatusListener);
        super.onDestroy();
    }
}


