package com.matatalab.matatacode;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.matatalab.matatacode.component.WebViewJavaScriptFunction;
import com.matatalab.matatacode.model.BrowserModel;
import com.matatalab.matatacode.model.MyAdapter;
import com.matatalab.matatacode.model.PublicWay;
import com.matatalab.matatacode.utils.MLog;
import com.matatalab.matatacode.utils.ToastUtils;

import org.json.JSONArray;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.AdapterView.*;

public class FileActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private GridView gridView1;
    private ImageButton imageButton_af1;
    private  String[] Listname;
    private String[] list;
    private boolean[] deleteselect;
    private TextView textview_af1;
    private ImageButton imagebutton_af2,imagebutton_af3;
    private String SelectName;
    private boolean editenable=false;
    private boolean backbusy=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        initView(savedInstanceState);
        initData();
    }
    private void initView(@Nullable Bundle savedInstanceState) {
        gridView1=findViewById(R.id.gridView1);
        imageButton_af1=findViewById(R.id.imageButton_af1);
        textview_af1=findViewById(R.id.textview_af1);
        textview_af1.setText("v "+AppConst.versionName+"\n"+AppConst.All_RIGHT_TEXT);
        imagebutton_af2=findViewById(R.id.imagebutton_af2);
        imagebutton_af3=findViewById(R.id.imagebutton_af3);
        Global.setHeightAsHeight(this,imagebutton_af2,8,100);
        Global.setHeightAsHeight(this,imagebutton_af3,8,100);
        imagebutton_af2.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    ((ImageButton)view).getBackground().setAlpha(128);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    ((ImageButton)view).getBackground().setAlpha(255);
                    if(editenable==false)
                    {
                        imagebutton_af3.setVisibility(View.VISIBLE);
                        editenable=true;
                        for(int i=0;i<deleteselect.length;i++)deleteselect[i]=false;
                        gridView1.setAdapter(new MyAdapter(FileActivity.this,R.layout.grid_list,
                                Listname,1));
                    }else
                    {
                        gridView1.setAdapter(new MyAdapter(FileActivity.this,R.layout.grid_list,
                                Listname,0));
                        imagebutton_af3.setVisibility(View.INVISIBLE);
                        editenable=false;
                    }
                }
                return false;
            }
        });
        imagebutton_af3.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    ((ImageButton)view).getBackground().setAlpha(128);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    ((ImageButton)view).getBackground().setAlpha(255);
                    MLog.td("tjl","imagebutton_af3 down");
                    for(int i=1;i<deleteselect.length;i++)
                    {
                        if(deleteselect[i]==true) {
                            boolean r=Global.delete(AppConst.EXAMPLE_PATH + Listname[i] + ".txt");
                            MLog.td("tjl","delete:"+r+AppConst.EXAMPLE_PATH + Listname[i] + ".txt");
                        }
                    }
                    gridLoad();
                    gridView1.setAdapter(new MyAdapter(FileActivity.this,R.layout.grid_list,
                            Listname,0));
                    imagebutton_af3.setVisibility(INVISIBLE);
                    editenable=false;
                }
                return false;
            }
        });
        imageButton_af1.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    ((ImageButton)view).getBackground().setAlpha(128);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    ((ImageButton)view).getBackground().setAlpha(255);
                    if(backbusy==false) {
                        startActivity(new Intent(FileActivity.this, FristActivity.class));//启动MainActivity
                        backbusy=true;
                    }
                }
                return false;
            }
        });

        gridLoad();
        gridView1.setAdapter(new MyAdapter(FileActivity.this,R.layout.grid_list,
                Listname,0));
        gridView1.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    ImageView imageView = (ImageView) view.findViewById(R.id.imageView_gl1);
                    imageView.getBackground().setAlpha(128);
                    MLog.td("tjl","gridView1 down");
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    ImageView imageView = (ImageView) view.findViewById(R.id.imageView_gl1);
                    imageView.getBackground().setAlpha(255);
                    MLog.td("tjl","gridView1 up");
                }
                return false;
            }
        });
        gridView1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent , View view , int position , long id) {
                MLog.td("tjl","position"+position+",id"+id+"name="+Listname[position]);
               // view.setBackgroundColor(Color.TRANSPARENT);
                if(editenable) {
                    TextView textView = (TextView) view.findViewById(R.id.main_grid_item_tv);
                    ImageView imageView = (ImageView) view.findViewById(R.id.imageView_gl1);
                    imageView.getBackground().setAlpha(128);
                   // textView.setText("aaaaaa");
                    if(position==0) {
                        final Mdialog mdialog = new Mdialog(FileActivity.this);
                        mdialog.setTitle(getString(R.string.ALERT_TITLE));
                        mdialog.setMessage(getString(R.string.ALERT_MESSAGE_CODE_STOP_EDIT));
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
                                    gridView1.setAdapter(new MyAdapter(FileActivity.this,R.layout.grid_list,
                                            Listname,0));
                                    imagebutton_af3.setVisibility(View.INVISIBLE);
                                    editenable=false;
                                    mdialog.dismiss();
                                }
                            }
                        });
                        mdialog.setCancelable(false);                //用户可以点击后退键关闭 Dialog
                        mdialog.setCanceledOnTouchOutside(false);   //用户不可以点击外部来关闭 Dialog
                        mdialog.show();
                        return;
                    }
                    imageView.setVisibility(View.VISIBLE);
                    if(deleteselect[position]) {
                        deleteselect[position]=false;
                        imageView.setBackground(getResources().getDrawable(R.mipmap.unselectedmdpi));
                    }else
                    {
                        deleteselect[position]=true;
                        imageView.setBackground(getResources().getDrawable(R.mipmap.selectedmdpi));
                    }
                    return;
                }
                SelectName=Listname[position];
                if(AppConst.UNTITLED_NAME.equals(SelectName))
                {
                    int i=0;
                    while(true) {
                        File f = new File(AppConst.EXAMPLE_PATH +"new_codes("+i+").txt");
                        if (!f.exists()) {
                            SelectName="new_codes("+i+")";
                            startActivity(new Intent(FileActivity.this,CodingActivity.class).putExtra("name" ,SelectName));
                            finish();
                            return;
                        }
                        i++;
                    }

                }else
                {
                    startActivity(new Intent(FileActivity.this,CodingActivity.class).putExtra("name" ,SelectName));
                    finish();
                }
            }
        });

    }
   private void gridLoad()
   {
       try {
           File file=new File(AppConst.EXAMPLE_PATH);
           String[] flLists=file.list();
           int count=Global.TextEndCount(flLists);
           MLog.td("tjl","收集到样本个数:"+count);
           Listname = new String[count+1];
           deleteselect=new boolean[count+1];
           int n=0;
           Listname[n++]=AppConst.UNTITLED_NAME;
           for(int i=0;i<flLists.length;i++)
           {
               if(flLists[i].toLowerCase().endsWith(".txt"))
               {
                   String temp=flLists[i].replace(".txt","");
                   deleteselect[n]=false;
                   Listname[n++]=temp;
               }
           }
       }catch (Exception E)
       {
           MLog.td("tjl",E.getMessage());
       }
   }
    private void initData() {
       // mBrowserModel = BrowserModel.getInstance();
       // mBrowserModel.initView(this, (X5WebView) mRootView.findViewById(R.id.x5_webview1));
       // mBrowserModel.showUrl(BLOCKLY_URL);
    }
    public void onBackPressed() {
        if(backbusy==false) {
            startActivity(new Intent(FileActivity.this, FristActivity.class));//启动MainActivity
            backbusy=true;
        }
    }
}

