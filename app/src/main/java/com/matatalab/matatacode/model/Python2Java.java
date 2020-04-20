package com.matatalab.matatacode.model;

import com.matatalab.matatacode.AppConst;
import com.matatalab.matatacode.utils.MLog;

import java.util.ArrayList;
import java.util.List;

public class Python2Java {

    public Python2Java(String n){

    }
    public void write(Object object){
        MLog.td("tjl",String.valueOf(object));
    }
    public void blewrite(byte[] data)
    {
       // MLog.td("tjl","write:"+data.length);
        MyBluetooth.BleWrite(data);
    }
    public void blewait()
    {
       // MLog.td("tjl","wait");
        MyBluetooth.BleWait();
    }
    public void runover()
    {
        AppConst.pythonRun=false;
        MLog.td("tjl","run over");
    }
}