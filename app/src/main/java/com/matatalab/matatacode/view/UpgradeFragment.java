package com.matatalab.matatacode.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matatalab.matatacode.AppConst;
import com.matatalab.matatacode.BuildConfig;
import com.matatalab.matatacode.R;
import com.matatalab.matatacode.interfaces.MainViewInterface;
import com.matatalab.matatacode.interfaces.UpgradeViewInterface;
import com.matatalab.matatacode.presenter.UpgradePresenter;
import com.matatalab.matatacode.utils.FunctionUtils;
import com.matatalab.matatacode.utils.MLog;
import com.matatalab.matatacode.utils.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * @author hardy
 * @name MatataCode
 * @class name：com.matatalab.matatacode.view
 * @class describe:
 * @time 2019/10/3 11:06
 * @change
 * @chang time
 * @class describe
 */
public class UpgradeFragment extends RxFragment implements View.OnClickListener, UpgradeViewInterface {
    public static final String TAG = UpgradeFragment.class.getSimpleName();

    private UpgradePresenter mUpgradePresenter;

    private Context mContext;
    private View mRootView;
    private TextView mTvTitleBot;
    private TextView mTvTitleTower;
    private TextView mTvTitleController;
    private TextView mTvVersionCode;

    private ImageView mIvStartUPgradeMask;
    private ImageView mBtnStartUPgrade;
    private ProgressBar mPbUpgtadeProgress;

    private TextView mTvGuideVideo;

    private int mUpgradeType = AppConst.UPGRADE_TYPE_BOT;

    private AlertDialog mWarningDialog;

    private boolean isFirstShow = true;

    public static UpgradeFragment newInstance() {
        Bundle args = new Bundle();
        UpgradeFragment fragment = new UpgradeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.upgrade_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MLog.d(TAG, "--- onViewCreated ---");
        mContext = getActivity();//在碎片里获取当前碎片所依附的Activity
        mRootView = view;

        initView();
        initData();

    }

    @Override
    public void onResume() {
        super.onResume();
        MLog.d(TAG, "--- onResume ---");
        if (isFirstShow == true) {
            showMatataWarningDialog();
            setUpgradeButtonEnable(false);
            isFirstShow = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        MLog.d(TAG, "onHiddenChanged --- hidden = " + hidden);
        super.onHiddenChanged(hidden);

        if (hidden == false) {
            showMatataWarningDialog();
            setUpgradeButtonEnable(false);
            setShowUpgradeType(mUpgradeType);
        }
    }

    @Override
    public void onDestroyView() {
        mUpgradePresenter.setUpgradeViewInterface(null);
        super.onDestroyView();
    }

    private void initView() {
        mTvGuideVideo = mRootView.findViewById(R.id.tv_video_url);
        mTvGuideVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickWatchGuideVideo();
            }
        });

        mTvTitleBot = mRootView.findViewById(R.id.tv_matata_bot);
        mTvTitleTower = mRootView.findViewById(R.id.tv_matata_tower);
        mTvTitleController = mRootView.findViewById(R.id.tv_matata_controller);
        mTvTitleBot.setOnClickListener(this);
        mTvTitleTower.setOnClickListener(this);
        mTvTitleController.setOnClickListener(this);

        mIvStartUPgradeMask = mRootView.findViewById(R.id.btn_upgrade_mask);

        mBtnStartUPgrade = mRootView.findViewById(R.id.btn_upgrade);
        mBtnStartUPgrade.setOnClickListener(this);

        mPbUpgtadeProgress = mRootView.findViewById(R.id.pb_upgtade_progress);

        mTvVersionCode = mRootView.findViewById(R.id.tv_version_code);

    }

    private void initData() {
        mUpgradePresenter = UpgradePresenter.getInstance(mContext);
        mUpgradePresenter.setUpgradeViewInterface(this);

    }

    /**
     * 进入升级页面警告对话框
     */
    private void showMatataWarningDialog() {
        MLog.d(TAG, "--- showMatataWarningDialog ---");


    }

    /**
     * 升级设备提示看视频对话框
     *
     * @param upgradeType
     */
    private void showWarningDialog(int upgradeType) {
        MLog.d(TAG, "showWarningDialog --- upgradeType = " + upgradeType);

        int messageId = R.string.ALERT_BUTTON_BOT;
        switch (upgradeType) {
            case AppConst.UPGRADE_TYPE_BOT:
                messageId = R.string.ALERT_BUTTON_BOT;
                break;

            case AppConst.UPGRADE_TYPE_TOWER:
                messageId = R.string.ALERT_BUTTON_TOWER;
                break;

            case AppConst.UPGRADE_TYPE_CONTROLLER:
                messageId = R.string.ALERT_BUTTON_BOT;
                break;

            default:
                break;
        }

        new AlertDialog.Builder(mContext).setTitle(R.string.ALERT_TITLE)
//                .setIcon(android.R.drawable.sym_def_app_icon)
                .setMessage(messageId)
                .setCancelable(false)
                .setPositiveButton(R.string.ALERT_BUTTON_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        setUpgradeButtonEnable(true);
                    }
                }).setNegativeButton(R.string.ALERT_BUTTON_CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //按下确定键后的事件
                setUpgradeButtonEnable(false);

            }
        }).show();
    }

    /**
     * 升级设备启动对话框
     *
     * @param upgradeType
     */
    private void showStartUpgradeWarningDialog(int upgradeType) {
        MLog.d(TAG, "showStartUpgradeWarningDialog --- upgradeType = " + upgradeType);

       final int messageId=R.string.DFU_BOT_READY;

        new AlertDialog.Builder(mContext).setTitle("R.string.dialog_title_dfu")
                .setMessage(messageId)
                .setCancelable(false)
                .setPositiveButton(R.string.ALERT_BUTTON_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        setUpgradeUiEnable(false);
                        // 灯塔不需要发送蓝牙命令进入dfu
                        if (mUpgradeType == AppConst.UPGRADE_TYPE_TOWER) {
                            mUpgradePresenter.startDeviceEnterUpgrade(mUpgradeType);
                        } else {
                            ((MainViewInterface) getActivity()).showLoading(true);
                            mUpgradePresenter.startDeviceEnterDfu(mUpgradeType);
                        }
                    }
                }).setNegativeButton(R.string.ALERT_BUTTON_CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //按下取消键后的事件
                setUpgradeUiEnable(true);

            }
        }).show();
    }


    /**
     * 点击跳转视频链接
     */
    private void onClickWatchGuideVideo() {
        MLog.d(TAG, "--- onClickWatchGuideVideo ---");

        String urlStr = AppConst.VIDEO_URL_YOUTU;
        if (FunctionUtils.isZh(mContext) == true) {
            urlStr = AppConst.VIDEO_URL_BILIBILI;
        }
        MLog.d(TAG, "onClickWatchGuideVideo --- urlStr = " + urlStr);

        try {
            //代码实现跳转
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(urlStr);//此处填链接
            intent.setData(content_url);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            MLog.e(TAG, "onClickWatchGuideVideo failed error =  " + e.getCause());
        }
    }

    @Override
    public void onClick(View v) {
        MLog.d(TAG, "onClick --- v.getId = " + v.getId());
        switch (v.getId()) {
            case R.id.tv_matata_bot:
                setShowUpgradeType(AppConst.UPGRADE_TYPE_BOT);
                showWarningDialog(AppConst.UPGRADE_TYPE_BOT);
                break;
            case R.id.tv_matata_tower:
                setShowUpgradeType(AppConst.UPGRADE_TYPE_TOWER);
                showWarningDialog(AppConst.UPGRADE_TYPE_TOWER);
                break;
            case R.id.tv_matata_controller:
                setShowUpgradeType(AppConst.UPGRADE_TYPE_CONTROLLER);
                showWarningDialog(AppConst.UPGRADE_TYPE_CONTROLLER);
                break;
            case R.id.btn_upgrade:
//                if (mUpgradeType == AppConst.UPGRADE_TYPE_BOT) {
                showStartUpgradeWarningDialog(mUpgradeType);
//                } else {
//                    showMessage("功能暂时关闭");
//                }
                break;
            default:
                break;
        }

    }

    /**
     * 显示升级类型UI
     *
     * @param upgradeType
     */
    private void setShowUpgradeType(int upgradeType) {
        MLog.d(TAG, "setShowUpgradeType --- upgradeType = " + upgradeType);
        mUpgradeType = upgradeType;
        switch (upgradeType) {
            case AppConst.UPGRADE_TYPE_BOT:
                mTvTitleBot.setBackgroundColor(getResources().getColor(R.color.common_text_color_yellow));
                mTvTitleTower.setBackgroundColor(getResources().getColor(R.color.transparent));
                mTvTitleController.setBackgroundColor(getResources().getColor(R.color.transparent));
                mBtnStartUPgrade.setImageResource(R.mipmap.botdfu);
                break;

            case AppConst.UPGRADE_TYPE_TOWER:
                mTvTitleBot.setBackgroundColor(getResources().getColor(R.color.transparent));
                mTvTitleTower.setBackgroundColor(getResources().getColor(R.color.common_text_color_yellow));
                mTvTitleController.setBackgroundColor(getResources().getColor(R.color.transparent));
                mBtnStartUPgrade.setImageResource(R.mipmap.towerupdate);
                break;

            case AppConst.UPGRADE_TYPE_CONTROLLER:
                mTvTitleBot.setBackgroundColor(getResources().getColor(R.color.transparent));
                mTvTitleTower.setBackgroundColor(getResources().getColor(R.color.transparent));
                mTvTitleController.setBackgroundColor(getResources().getColor(R.color.common_text_color_yellow));
                break;

            default:
                break;
        }
    }

    private void setUpgradeButtonEnable(boolean enable) {
        MLog.d(TAG, "setUpgradeButtonEnable --- enable = " + enable);

        if (enable == true) {
            mIvStartUPgradeMask.setVisibility(View.GONE);
            mBtnStartUPgrade.setClickable(true);
        } else {
            mIvStartUPgradeMask.setVisibility(View.VISIBLE);
            mBtnStartUPgrade.setClickable(false);
        }

    }

    /**
     * 设置升级状态的按钮使能
     *
     * @param enable
     */
    private void setUpgradeUiEnable(boolean enable) {
        MLog.d(TAG, "setUpgradeUiEnable --- enable = " + enable);

        setUpgradeButtonEnable(enable);
        mTvTitleBot.setClickable(enable);
        mTvTitleTower.setClickable(enable);
        mTvTitleController.setClickable(enable);

        mTvGuideVideo.setClickable(enable);

        ((MainViewInterface) getActivity()).setMainButtonEnable(enable);
    }

    @Override
    public void updateProgressIndeterminate(boolean indeterminate) {
        mPbUpgtadeProgress.setIndeterminate(indeterminate);
    }

    @Override
    public void updateProgressPercent(int percent) {
        mPbUpgtadeProgress.setProgress(percent);
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.show(message);
    }

    @Override
    public void showUpgradeMessage(String message) {
        ((MainViewInterface) getActivity()).showLoading(false);
        new AlertDialog.Builder(mContext).setTitle("R.string.dialog_title_dfu")
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("R.string.ALERT_BUTTON_OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setUpgradeUiEnable(true);
                    }
                }).show();
    }

    @Override
    public void showBtErrDialogMessage(String message) {
        ((MainViewInterface) getActivity()).showLoading(false);
        new AlertDialog.Builder(mContext).setTitle("R.string.dialog_title_bt_err")
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton(R.string.ALERT_BUTTON_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setUpgradeUiEnable(true);
                    }
                }).show();
    }

    @Override
    public void showWaitIntoDfuMode(String message) {
        ((MainViewInterface) getActivity()).showLoading(false);
        new AlertDialog.Builder(mContext).setTitle("R.string.dialog_title_dfu")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.ALERT_BUTTON_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setUpgradeUiEnable(false);
                        //按下确定键后的事件
                        mUpgradePresenter.startDeviceEnterUpgrade(mUpgradeType);
                    }
                }).setNegativeButton(R.string.ALERT_BUTTON_CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //按下确定键后的事件
                setUpgradeUiEnable(true);

            }
        }).show();
    }

}
