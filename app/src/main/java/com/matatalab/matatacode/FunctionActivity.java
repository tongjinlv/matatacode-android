package com.matatalab.matatacode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matatalab.matatacode.interfaces.MainViewInterface;
import com.matatalab.matatacode.utils.MLog;
import com.matatalab.matatacode.utils.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

public class FunctionActivity extends RxAppCompatActivity implements MainViewInterface {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        initView(savedInstanceState);

    }

    @SuppressLint("CutPasteId")
    private void initView(@Nullable Bundle savedInstanceState) {

    }

    public void onBackPressed() {
        MLog.td("tjl" , "onBackPressed()");

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    public void setMainButtonEnable(boolean enable) {
    }

    @Override
    public void showLoading(boolean isShow) {
    }
}
