package com.matatalab.matatacode.model;

import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.matatalab.matatacode.AppConst;
import com.matatalab.matatacode.CLIActivity;
import com.matatalab.matatacode.utils.MLog;

import java.util.UUID;

public class MatataLabCallBack {
    public Music music=new Music();
    public MatataLabCallBack(String Info)
    {
        MLog.td("tjl","start:"+Info);
    }
    public void SetPythonObject(Object rb)
    {

    }
    public void test()
    {
        MLog.td("tjl","matata.test()");
        byte[] bytes=new byte[2];
        bytes[0]=0x54;
        bytes[1]=0x01;
        MyBluetooth.BleWrite(bytes);
    }
    public class Music {
        public double test()
        {
            MLog.td("tjl","Music.test");
            byte[] bytes=new byte[2];
            bytes[0]=0x54;
            bytes[1]=0x02;
            MyBluetooth.BleWrite(bytes);
            return 565.5;
        }
        public void play_treble(String val0,String val1)
        {
            MLog.td("tjl","Music.play_treble("+val0+","+val1+")");
            byte[] bytes=new byte[3];
            bytes[0]=113;
            int random=0;
            switch (val0) {
                case "do":
                    bytes[1]=1;
                    break;
                case "re":
                    bytes[1] = 2;
                    break;
                case "mi":
                    bytes[1] = 3;
                    break;
                case "fa":
                    bytes[1] = 4;
                    break;
                case "sol":
                    bytes[1] = 5;
                    break;
                case "la":
                    bytes[1] = 6;
                    break;
                case "ti":
                    bytes[1] = 7;
                    break;
                case "random":
                    random=(int)(1+Math.random()*(7-1+1));
                    MLog.td("tjl","parseRunCodeToBtCommand --- msgArray[1] = "+random);
                    bytes[1] =(byte)random;
                    break;
                default:
                    bytes[1] = 1;
                    break;
            }
            switch (val1) {
                case "meter1":
                    bytes[2] = 1;
                    break;
                case "meter2":
                    bytes[2] = 2;
                    break;
                case "meter3":
                    bytes[2] = 3;
                    break;
                case "meter4":
                    bytes[2] = 4;
                    break;
                case "meter5":
                    bytes[2] = 5;
                    break;
                case "meter6":
                    bytes[2] = 6;
                    break;
                case "random":
                    random=(int)(1+Math.random()*(6-1+1));
                    MLog.td("tjl","parseRunCodeToBtCommand --- msgArray[1] = "+random);
                    bytes[2] =(byte) random;
                    break;
                default:
                    bytes[2] = 1;
                    break;
            }
            if(bytes[1]>7)bytes[1]=7;
            if(bytes[2]>6)bytes[2]=6;
            MyBluetooth.BleWrite(bytes);
        }
    }
}
