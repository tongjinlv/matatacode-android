package com.matatalab.matatacode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.matatalab.matatacode.interfaces.MainViewInterface;
import com.matatalab.matatacode.model.PublicWay;
import com.matatalab.matatacode.presenter.BlocklyPresenter;
import com.matatalab.matatacode.presenter.UpgradePresenter;
import com.matatalab.matatacode.utils.MLog;
import com.matatalab.matatacode.utils.ToastUtils;
import com.matatalab.matatacode.view.BlocklyFragment;
import com.matatalab.matatacode.view.UpgradeFragment;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class MainActivity extends RxAppCompatActivity implements MainViewInterface {

    private static final int REQUEST_CODE_ACCESS_COARSE_LOCATION = 1;
    private static final int REQUEST_CODE_ACCESS_COARSE_STORAGE = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
        checkGps();
        checkStorageWrite();
    }

    @SuppressLint("CutPasteId")
    private void initView(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onDestroy() {
        BlocklyPresenter.getInstance(this).exit();
        UpgradePresenter.getInstance(this).exit();
        super.onDestroy();
    }

    /**
     * 开启位置权限
     */
    private void checkGps() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ACCESS_COARSE_LOCATION);
            }
        }
    }

    /**
     * 开启SD卡写权限
     */
    private void checkStorageWrite() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ACCESS_COARSE_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ACCESS_COARSE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //ToastUtils.show(R.string.location_premission_granted);
            } else {
                //ToastUtils.show(R.string.location_premission_denied);
            }
        } else if (requestCode == REQUEST_CODE_ACCESS_COARSE_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public void setMainButtonEnable(boolean enable) {
    }

    public void showLoading(boolean isShow) {
    }
}
