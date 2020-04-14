package com.matatalab.matatacode.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matatalab.matatacode.R;
import com.matatalab.matatacode.interfaces.BlocklyViewInterface;
import com.matatalab.matatacode.interfaces.MainViewInterface;
import com.matatalab.matatacode.model.BrowserModel;
import com.matatalab.matatacode.presenter.BlocklyPresenter;
import com.matatalab.matatacode.utils.MLog;
import com.matatalab.matatacode.utils.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * @author hardy
 * @name MatataCode
 * @class name：com.matatalab.matatacode.view
 * @class describe:
 * @time 2019/10/3 10:36
 * @change
 * @chang time
 * @class describe
 */
public class BlocklyFragment extends RxFragment implements BlocklyViewInterface {
    public static final String TAG = BlocklyFragment.class.getSimpleName();

    public static final String BLOCKLY_URL = "file:///android_asset/blockly/index_code.html";

    private Context mContext;
    private View mRootView;
    private TextView mTvBtStatus;
    private RelativeLayout mRlHelp;


    private CommonDialogFragment mCommonDialogFragment;

    private BrowserModel mBrowserModel;
    private BlocklyPresenter mBlocklyPresenter;

    public static BlocklyFragment newInstance() {
        Bundle args = new Bundle();
        BlocklyFragment fragment = new BlocklyFragment();
        fragment.setArguments(args);
        return fragment;
    }
    

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MLog.d(TAG, "--- onViewCreated ---");
        mContext = getActivity();
        mRootView = view;
        initView();
        initData();

    }

    @Override
    public void onDestroyView() {
        mBlocklyPresenter.setBlocklyViewInterface(null);
        super.onDestroyView();
    }

    private void initView() {
        mRlHelp.setVisibility(View.GONE);
        mRlHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHelpViewShow(false);
            }
        });

    }

    private void initData() {
        mBrowserModel = BrowserModel.getInstance();
        mBrowserModel.showUrl(BLOCKLY_URL);

        mBlocklyPresenter = BlocklyPresenter.getInstance(mContext);
        mBlocklyPresenter.setBlocklyViewInterface(this);


//        mCommonDialogFragment = CommonDialogFragment.getInstance(null, getString(R.string.app_name), getString(R.string.dfu_file_status_invalid));
//        mCommonDialogFragment.show(getActivity().getSupportFragmentManager(), CommonDialogFragment.TAG);
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.show(message);
    }

    @Override
    public void showBtErrDialogMessage(String message) {
        showLoading(false);
        new AlertDialog.Builder(mContext).setTitle("dialog_title_bt_err")
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton(R.string.ALERT_BUTTON_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
    }

    @Override
    public void updateBtStatus(boolean isConnect) {
        MLog.d(TAG, "updateBtStatus --- isConnect = " + isConnect);
        if (isConnect == true) {
            mTvBtStatus.setText("CONNECT");
            mTvBtStatus.setTextColor(getResources().getColor(R.color.green));
        } else {
            mTvBtStatus.setText("DISCONNECT");
            mTvBtStatus.setTextColor(getResources().getColor(R.color.red));

        }
    }

    @Override
    public void setHelpViewShow(boolean isShow) {
        MLog.d(TAG, "setHelpViewShow --- isShow = " + isShow);
        if (mRlHelp == null) {
            MLog.e(TAG, "setHelpViewShow --- mIvHelp is null!");
            return;
        }
        if (isShow == true) {
            mRlHelp.setVisibility(View.VISIBLE);
        } else {
            mRlHelp.setVisibility(View.GONE);
        }

    }

    @Override
    public void showDisConnectView() {
        MLog.d(TAG, "--- showDisConnectView ---");
        setHelpViewShow(false);
        new AlertDialog.Builder(mContext).setTitle("R.string.dialog_title")
                .setMessage("R.string.dialog_message_disconnect")
                .setPositiveButton("R.string.dialog_btn_disconnect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        mBlocklyPresenter.disconnectBt();
                    }
                }).setNegativeButton(R.string.ALERT_BUTTON_CANCEL, null).show();
    }

    @Override
    public void showSaveCodeView(final String codes) {
        MLog.d(TAG, "--- showSaveCodeView ---");
        setHelpViewShow(false);
        final EditText etInput = new EditText(mContext);
        etInput.setHint("R.string.dialog_save_input_hint");
        new AlertDialog.Builder(mContext).setTitle("R.string.dialog_title_save")
//                .setIcon(android.R.drawable.sym_def_app_icon)
                .setMessage("R.string.dialog_message_save")
                .setView(etInput)
                .setPositiveButton("R.string.dialog_btn_save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        String name = etInput.getText().toString();
                        if (TextUtils.isEmpty(name) == true) {
                            showMessage("R.string.dialog_name_empty");
                        } else {
                            mBlocklyPresenter.saveCode(name, codes);
                        }
                    }
                }).setNegativeButton(R.string.ALERT_BUTTON_CANCEL, null).show();
    }

    @Override
    public void showLoadCodeView() {
        MLog.d(TAG, "--- showLoadCodeView ---");
        setHelpViewShow(false);
        final EditText etInput = new EditText(mContext);
        etInput.setHint("R.string.dialog_save_input_hint");
        new AlertDialog.Builder(mContext).setTitle("R.string.dialog_title_load")
//                .setIcon(android.R.drawable.sym_def_app_icon)
                .setMessage("R.string.dialog_message_save")
                .setView(etInput)
                .setPositiveButton("R.string.dialog_btn_load", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        String name = etInput.getText().toString();
                        if (TextUtils.isEmpty(name) == true) {
                            showMessage("R.string.dialog_name_empty");
                        } else {
                            mBlocklyPresenter.readCodeByName(name);
                        }
                    }
                }).setNegativeButton(R.string.ALERT_BUTTON_CANCEL, null).show();
    }

    @Override
    public void showDeleteCodeView() {
        MLog.d(TAG, "--- showDeleteCodeView ---");
        setHelpViewShow(false);
        final EditText etInput = new EditText(mContext);
        etInput.setHint("R.string.dialog_save_input_hint");
        new AlertDialog.Builder(mContext).setTitle("R.string.dialog_title_delete")
//                .setIcon(android.R.drawable.sym_def_app_icon)
                .setMessage("R.string.dialog_message_save")
                .setView(etInput)
                .setPositiveButton("R.string.dialog_btn_delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        String name = etInput.getText().toString();
                        if (TextUtils.isEmpty(name) == true) {
                            showMessage("R.string.dialog_name_empty");
                        } else {
                            mBlocklyPresenter.deleteCodeByName(name);
                        }
                    }
                }).setNegativeButton(R.string.ALERT_BUTTON_CANCEL, null).show();
    }

    @Override
    public void showQueryCodeView() {
        MLog.d(TAG, "--- showQueryCodeView ---");
        setHelpViewShow(false);

        String[] items = mBlocklyPresenter.getCodeFileList();

        AlertDialog dlg = new AlertDialog.Builder(mContext)
                .setTitle("R.string.dialog_title_list")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub


                    }
                })
                .show();
    }

    @Override
    public void showLoading(boolean isShow) {
        MLog.d(TAG, "showLoading --- isShow = " + isShow);
        ((MainViewInterface) getActivity()).showLoading(isShow);
    }
}
