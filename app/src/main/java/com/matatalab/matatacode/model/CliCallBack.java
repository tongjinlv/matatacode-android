package com.matatalab.matatacode.model;

import android.media.MediaPlayer;

import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.matatalab.matatacode.AppConst;
import com.matatalab.matatacode.CLIActivity;
import com.matatalab.matatacode.CodingActivity;
import com.matatalab.matatacode.Global;
import com.matatalab.matatacode.R;
import com.matatalab.matatacode.utils.MLog;

import java.util.UUID;

public class CliCallBack {
    public static int i=0;
    public CliCallBack(String Info)
    {
        MLog.td("tjl","start:"+Info);
    }
    public void printf(String msg)
    {
        MLog.td("tjl",msg);
    }
    public void Write(byte[] data)
    {
        MLog.td("tjl","write:"+data.length);
        MyBluetooth.BleWrite(data);
    }
    public void Wait()
    {
        MLog.td("tjl","wait");
        MyBluetooth.BleWait();
    }
    public void SetPythonObject(Object rb)
    {

    }
}
