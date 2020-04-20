package com.matatalab.matatacode.model;

import android.app.Activity;

import com.matatalab.matatacode.AppConst;
import com.matatalab.matatacode.utils.MLog;
import com.chaquo.python.Kwarg;
import com.chaquo.python.PyObject;
import com.chaquo.python.android.AndroidPlatform;
import com.chaquo.python.Python;


public class ChaquoPython {

    Activity activity;
    private Python py;
    private boolean run=false;
    public ChaquoPython(Activity activity)
    {
        this.activity=activity;
        try {
            if (! Python.isStarted()) {
                Python.start(new AndroidPlatform(this.activity));
            }
            py = Python.getInstance();
            MLog.td("tjl","Python.start");
        }catch (Exception E)
        {
            MLog.td("tjl","ChaquoPython chaque error = "+E.getMessage());
        }
    }
   public void Start()
    {
        if(AppConst.pythonRun==true)
        {
            MLog.td("tjl","Python Run is readly");
            return;
        }
        Thread myThread1=new Thread(){
            @Override
            public void run() {
                try {
                    AppConst.pythonRun=true;
                    PyObject obj4 = py.getModule("hello").callAttr("matatalab_start",0);
                    MLog.td("tjl","Python Run");
                }catch (Exception E)
                {
                    AppConst.pythonRun=false;
                    MLog.td("tjl","Start chaque error = "+E.getMessage());
                }
            }
        };
        myThread1.start();
    }
    public void Exit()
    {
        try {
            MLog.td("tjl","Python Exit");
            PyObject obj1 = py.getModule("hello").callAttr("matatalab_stop", 0);
            AppConst.pythonRun=false;
        }catch (Exception E)
        {
            MLog.td("tjl","Exit chaque error = "+E.getMessage());
        }
    }
}