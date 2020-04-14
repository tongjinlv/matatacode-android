package com.matatalab.matatacode;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Mdialog extends Dialog {

    private Button btn_a, btn_b;//定义取消与确认按钮
    private TextView title;//定义标题文字
    private TextView msg;//定义标题文字
    private Context context;

    //构造方法
    public Mdialog(Context context) {

        //设置对话框样式
        super(context, R.style.mdialog);

        //通过LayoutInflater获取布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.mdialoglayout, null);
        title = view.findViewById(R.id.diglog_title);   //获取显示标题的文本框控件
        msg = view.findViewById(R.id.diglog_msg);   //获取显示标题的文本框控件
        btn_a = view.findViewById(R.id.btn_a);    //获取取消按钮
        btn_b = view.findViewById(R.id.btn_b);  //获取确认退出按钮
        this.context=context;
        setContentView(view);  //设置显示的视图

    }

    //设置标题
    public void setTitle(String content) {
        title.setText(content);
    }
    public void setMessage(String content) {
        msg.setText(content);
    }
    public void setBa(String content) {
        btn_a.setText(content);
    }
    public void setBb(String content) {
        btn_b.setText(content);
    }
    //取消监听
    public void setOnButtonAListener(View.OnClickListener listener) {
        btn_a.setOnClickListener(listener);
    }

    //退出监听
    public void setOnButtonBListener(View.OnClickListener listener) {
        btn_b.setOnClickListener(listener);
    }
}