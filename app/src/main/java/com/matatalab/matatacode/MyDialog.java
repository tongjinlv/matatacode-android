package com.matatalab.matatacode;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.matatalab.matatacode.utils.MLog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDialog extends Dialog {
    private static final String TAG = MainActivity.class.getSimpleName();

    private MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private View mLayout;
        private Context context;
        private String code;
        private ImageButton imagebutton_ud1;
        private EditText editText_ud1;
        private EditText editText_ud2;
        private String bar_value;
        private View.OnClickListener mButtonClickListener;

        private MyDialog mDialog;

        public Builder(Context context) {
            this.context=context;
            mDialog = new MyDialog(context, R.style.Theme_AppCompat_Dialog);
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //加载布局文件
            mLayout = inflater.inflate(R.layout.my_dialog, null, false);
            //添加布局文件到 Dialog
            mDialog.addContentView(mLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

          /*  mIcon = mLayout.findViewById(R.id.dialog_icon);
            mTitle = mLayout.findViewById(R.id.dialog_title);
            mMessage = mLayout.findViewById(R.id.dialog_message);
            mButton = mLayout.findViewById(R.id.dialog_button);*/
            imagebutton_ud1=mLayout.findViewById(R.id.imagebutton_ud1);
            editText_ud1=mLayout.findViewById(R.id.editText_ud1);
            editText_ud2=mLayout.findViewById(R.id.editText_ud2);
        }

        /**
         * 通过 ID 设置 Dialog 图标
         */
        public Builder setIcon(int resId) {
           // mIcon.setImageResource(resId);
            return this;
        }

        /**
         * 用 Bitmap 作为 Dialog 图标
         */
        public Builder setIcon(Bitmap bitmap) {
           // mIcon.setImageBitmap(bitmap);
            return this;
        }

        /**
         * 设置 Dialog 标题
         */
        public Builder setTitle(@NonNull String title) {
            editText_ud1.setText(title);
            return this;
        }

        /**
         * 设置 Message
         */
        public Builder setMessage(@NonNull String message) {
            code=message;
            return this;
        }

        /**
         * 设置按钮文字和监听
         */
        public Builder setButton(@NonNull String text, View.OnClickListener listener) {
            bar_value=text;
            mButtonClickListener = listener;
            return this;
        }

        public MyDialog create() {
          /*  mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mButtonClickListener.onClick(v);
                }
            });*/
            editText_ud1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setBackgroundColor(Color.WHITE);
                }
            });
            editText_ud2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setBackgroundColor(Color.WHITE);
                }
            });
            imagebutton_ud1.setOnTouchListener( new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        //重新设置按下时的背景图片
                        ((ImageButton)view).getBackground().setAlpha(128);
                    }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        //再修改为抬起时的正常图片
                        ((ImageButton)view).getBackground().setAlpha(255);
                        if(String.valueOf(editText_ud1.getText()).length()<1)
                        {
                            editText_ud1.setBackgroundColor(Color.RED);
                            return true;
                        }else editText_ud1.setBackgroundColor(Color.WHITE);
                        if(String.valueOf(editText_ud2.getText()).length()<5)
                        {
                            editText_ud2.setBackgroundColor(Color.RED);
                            return true;
                        }else editText_ud2.setBackgroundColor(Color.WHITE);
                        Thread myThread1=new Thread(){
                            @Override
                            public void run() {
                                SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                                String date = formatter.format(curDate);
                                String name=String.valueOf(editText_ud1.getText());
                                String note= String.valueOf(editText_ud2.getText());
                                mButtonClickListener.onClick(imagebutton_ud1);
                                mDialog.dismiss();
                            }
                        };
                        myThread1.start();
                    }
                    return false;
                }
            });
            mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            mDialog.setContentView(mLayout);
            mDialog.setCancelable(true);                //用户可以点击后退键关闭 Dialog
            mDialog.setCanceledOnTouchOutside(true);   //用户不可以点击外部来关闭 Dialog
            return mDialog;
        }
    }
}
