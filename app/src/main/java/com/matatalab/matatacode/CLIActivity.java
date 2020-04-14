package com.matatalab.matatacode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.matatalab.matatacode.utils.MLog;

public class CLIActivity extends Activity {
    private Button button_f6;

    private EditText editText1;
    public static CLIActivity Host;
    public static Handler uiHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cli);
        Host=this;
        uiHandler=new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                MLog.td("tjl",String.valueOf(msg.what));
                switch (msg.what) {
                    case 10:
                        editText1.append(msg.obj.toString() + "\r\n");
                        super.handleMessage(msg);
                        break;
                    case 11:
                        editText1.setText(msg.obj.toString() + "\r\n");
                        super.handleMessage(msg);
                        break;
                }
            }

        };

        button_f6=findViewById(R.id.button_f6);
        button_f6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MLog.td("tjl", "button_f6.down");
                editText1.setText("");
                Thread myThread1=new Thread(){
                    @Override
                    public void run() {
                        try {
                            String tempfile = "/data/data/com.trtos.pylycode/files/main.py";
                            String resultData="# -*- coding: UTF-8 -*-\r\nimport imp\r\npy = JavaClass(\"from python\")\r\n";
                          //  resultData+=Global.urlget("http://192.168.0.176:81/pyly_blockly/demos/code/javascript/run_java.py");
                            MLog.td("tjl","get:"+resultData);
                            Global.writeFileData(tempfile,resultData);
                          //  AppConst.cliPython.Service._DoFile("python" , tempfile , "");

                        }catch (Exception E)
                        {
                            MLog.td("tjl","error"+E.getMessage());
                        }
                    }
                };
                myThread1.start();
            }
        });
        editText1=findViewById(R.id.editText1);
    }

    @Override
    public void onBackPressed() {
        MLog.td("tjl" , "onBackPressed()");
        startActivity(new Intent(CLIActivity.this, FristActivity.class));
    }
    @Override
    protected void onDestroy() {
        MLog.td("tjl" , "onDestroy()");
        super.onDestroy();
    }

}


