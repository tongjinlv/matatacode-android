package com.matatalab.matatacode.model;


import android.app.Activity;

import com.matatalab.matatacode.utils.MLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CheckedOutputStream;


public class CliPython{


    Activity activity;
    boolean overwrite;

    private void copyFile(Activity c,String Source,String Name) throws IOException {
        File outfile = null;
        outfile = new File("/data/data/"+activity.getPackageName()+"/files/"+Name);
        MLog.td("tjl","/data/data/"+activity.getPackageName()+"/files/"+Name);
        if (!outfile.exists()&this.overwrite) {
        outfile.createNewFile();
        FileOutputStream out = new FileOutputStream(outfile);
        byte[] buffer = new byte[1024];
        InputStream in;
        int readLen = 0;
        String sourcefile=Source+Name;
        in = c.getAssets().open(sourcefile);
        MLog.td("tjl","复制:"+sourcefile);
        while((readLen = in.read(buffer)) != -1){
            out.write(buffer, 0, readLen);
        }
        out.flush();
        in.close();
        out.close();
        }
    }

    public CliPython(Activity activity,Boolean overwrite) {
        this.overwrite=overwrite;
        this.activity=activity;
        MLog.td("tjl","开始复制1");
        File destDir = new File("/data/data/"+activity.getPackageName()+"/files");
        if(!destDir.exists())
            destDir.mkdirs();

        File python2_7_libFile = new File("/data/data/"+activity.getPackageName()+"/files/python2.7.zip");
        if( !python2_7_libFile.exists() ){
            try{
                copyFile(activity,"python2.7/","python2.7.zip");
            }
            catch(Exception e){
                MLog.td("tjl","复制错误:"+e.getMessage());
            }
        }
        File destDir1 = new File("/data/data/"+activity.getPackageName()+"/libs");
        if(!destDir1.exists())
        try{
            destDir1.mkdirs();
            MLog.td("tjl","开始复制3");
            FileStorageHelper.copyAssetsto(activity, "python2.7/dynload", "/data/data/"+activity.getPackageName()+"/libs" );
            File file=new File("/data/data/"+activity.getPackageName()+"/libs");
            File[] files=file.listFiles();
            if (files == null){MLog.td("tjl","空目录");return;}
            for(int i =0;i<files.length;i++){
                MLog.td("tjl","check:"+files[i].getName());
            }

        }
        catch(Exception e){
            MLog.td("tjl","复制错误:"+e.getMessage());
        }
        MLog.td("tjl","python 复制完毕 ");
        /*----init starcore----*/

        MLog.td("tjl","SrvGroup._InitRaw");


    }

    public String Stop()
    {
        MLog.td("tjl","killall python");
        // SrvGroup._ClearService();
        //if(python._GetLastError()==1)
        return "";
    }
    public static String read(String fileName) {

        FileInputStream fis = null;
        String result = null;
        try {

            File file = new File(fileName);
            fis = new FileInputStream(file);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            result = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public String Start(String name)
    {
        try {

            MLog.td("tjl","Code:\r\n"+read(name));

        }catch (Exception E)
        {
            return  E.getMessage();
        }
        return "";
    }
}