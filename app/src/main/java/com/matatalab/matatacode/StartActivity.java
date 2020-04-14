package com.matatalab.matatacode;

import android.Manifest;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.matatalab.matatacode.model.BrowserModel;
import com.matatalab.matatacode.model.ChaquoPython;
import com.matatalab.matatacode.model.CliPython;
import com.matatalab.matatacode.model.FileStorageHelper;
import com.matatalab.matatacode.model.MyAdapter;
import com.matatalab.matatacode.model.PublicWay;
import com.matatalab.matatacode.utils.MLog;
import com.matatalab.matatacode.utils.ToastUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import bsh.Interpreter;

public class StartActivity extends AppCompatActivity {
    private TextView textview_as1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //setLang(Locale.JAPANESE);
        checkGps();
        checkStorageWrite();
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        String local = Locale.getDefault().toString();
        String country =getResources().getConfiguration().locale.getCountry();
        MLog.td("tjl","tjCountry: language:"+ language+",local:"+local+",country:"+country);
        AppConst.lang="en";
        if (local.indexOf("zh_")>-1)AppConst.lang="zh-hans";//中文
        else if (language.indexOf("zh")>-1)AppConst.lang="zh-hans";//中文
        else if(language.indexOf("en")>-1)AppConst.lang="en";//英文
        else if(language.indexOf("fr")>-1)AppConst.lang="fr";//英文
        else if(language.indexOf("de")>-1)AppConst.lang="de";//德语
        else if(language.indexOf("es")>-1)AppConst.lang="es";//西班牙
        else if(language.indexOf("pt")>-1)AppConst.lang="pt-pt";//葡萄牙
        else if(language.indexOf("pt")>-1)AppConst.lang="pt-pt";//葡萄牙
        else if(language.indexOf("br")>-1)AppConst.lang="pt-br";//葡语（巴西）
        else if(language.indexOf("it")>-1)AppConst.lang="it";//意大利
        else if(language.indexOf("ko")>-1)AppConst.lang="ko";//韩国
        else if(language.indexOf("ja")>-1)AppConst.lang="ja";//日语
        else if(language.indexOf("tr")>-1)AppConst.lang="tr";//土耳其
        else if(language.indexOf("uk")>-1)AppConst.lang="uk";//乌克兰
        else if(language.indexOf("ru")>-1)AppConst.lang="ru";//俄国
        else if(language.indexOf("th")>-1)AppConst.lang="th";//泰国

        MLog.td("tjl","language blockly:"+AppConst.lang);
        MLog.td("tjl","开发公司:"+this.getString(R.string.ALERT_TITLE));
        AppConst.EXAMPLE_PATH="/data/data/"+getPackageName()+"/text/";
        MLog.td("tjl","EXAMPLE_PATH="+AppConst.EXAMPLE_PATH);

        Thread myThread1=new Thread(){
            @Override
            public void run() {
                File file1 = new File(AppConst.EXAMPLE_PATH);
                if(file1.exists())MLog.td("tjl","文件夹存在");
                else
                {
                    file1.mkdir();
                    File file=new File(AppConst.EXAMPLE_PATH);
                    File[] files=file.listFiles();
                    for(int i =0;i<files.length;i++){
                        MLog.td("tjl",files[i].getName());
                        File file4 = new File(AppConst.EXAMPLE_PATH+files[i].getName());
                        file4.delete();
                    }
                    File destDir1 = new File(AppConst.EXAMPLE_PATH);
                    if(!destDir1.exists()) destDir1.mkdirs();
                    FileStorageHelper.copyAssetsto(StartActivity.this, "example", AppConst.EXAMPLE_PATH );
                }
                try
                {
                    PackageManager packageManager = getPackageManager();
                    PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
                    String version = packInfo.versionName;
                    AppConst.versionName=version;
                }catch (Exception E){AppConst.versionName="01.01.01";}
                try {
                    //"http://dm.trtos.com/php/value.php?name=matatacode&value=2.1.1"
                    URL url = new URL("http://dm.trtos.com/php/value.php?name=matacode");
                    String resultData=Global.readNetValue("matacode");
                    String[] a=resultData.split(",");
                    MLog.td("tjl","resultData:"+resultData);
                    MLog.td("tjl","version name:"+a[0]);
                    MLog.td("tjl","online version:"+a[1]);
                    MLog.td("tjl","downloard url:"+a[2]);
                    MLog.td("tjl","local version:"+AppConst.versionName);
                    int cp=Global.compareVersion(a[1],AppConst.versionName);
                    if(cp==0)MLog.td("tjl","已经是最新版本");
                    if(cp>0)
                    {
                       AppConst.NeedUpdate=true;
                        MLog.td("tjl","有新版本");
                        AppConst.APP_DOWNLOAD_URL=a[2];
                    }

                }catch (Exception E)
                {
                    MLog.td("tjl","error"+E.getMessage());
                }
                try{
                    sleep(1000);
                }catch (Exception e){
                    MLog.td("tjl", String.valueOf(e.getMessage()));
                }
                AppConst.runDirPath = "/data/data/" + getPackageName() + "/run/";
                File destDir2 = new File(AppConst.runDirPath);
                if (destDir2.exists()) MLog.td("tjl" , "run 文件夹存在");
                else destDir2.mkdirs();
               // AppConst.cliPython = new CliPython(StartActivity.this , true);
                ChaquoPython cp=new  ChaquoPython(StartActivity.this);
                startActivity(new Intent(StartActivity.this,FristActivity.class));//启动MainActivity
            }
        };
        myThread1.start();
    }
    private void setLang(Locale lang){
        Locale.setDefault(lang);//设置本地为英文环境
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.locale = lang;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

    }
    private void checkGps() {
        final int REQUEST_CODE_ACCESS_COARSE_LOCATION = 1;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ACCESS_COARSE_LOCATION);
            }
        }
    }

    /**
     * 开启SD卡写权限
     */
    private void checkStorageWrite() {
        final int REQUEST_CODE_ACCESS_COARSE_STORAGE = 2;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ACCESS_COARSE_STORAGE);
            }
        }
    }
}
