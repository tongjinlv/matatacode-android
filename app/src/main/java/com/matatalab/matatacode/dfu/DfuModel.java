/*
 * Copyright (c) 2015, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.matatalab.matatacode.dfu;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.matatalab.matatacode.AppConst;
import com.matatalab.matatacode.R;
import com.matatalab.matatacode.dfu.fragment.UploadCancelFragment;
import com.matatalab.matatacode.utils.MLog;
import com.matatalab.matatacode.utils.ToastUtils;

import java.util.UUID;

import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuProgressListenerAdapter;
import no.nordicsemi.android.dfu.DfuServiceInitiator;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;


public class DfuModel implements
        UploadCancelFragment.CancelFragmentListener {
    private static final String TAG = "tjl";


    private Context mContext;
    private BluetoothDevice mSelectedDevice;
    private int mFileRawId = 0;
    private int mFileType;
    private Integer mScope;


    private  DfuProgressListener mDfuProgressListener;

    public DfuModel(Context context, DfuProgressListener dfuProgressListener) {
        mContext = context;

        mFileType = DfuService.TYPE_AUTO; // Default

        mDfuProgressListener =dfuProgressListener;
        if(mDfuProgressListener != null) {
            DfuServiceListenerHelper.registerProgressListener(mContext, mDfuProgressListener);
        }
    }

    protected void exit() {
        if(mDfuProgressListener != null) {
            DfuServiceListenerHelper.unregisterProgressListener(mContext, mDfuProgressListener);
        }
    }


    /**
     * Updates the file information on UI
     *
     * @param fileName file name
     * @param fileSize file length
     */
    private void updateFileInfo(final String fileName, final long fileSize, final int fileType) {

        final String extension = mFileType == DfuService.TYPE_AUTO ? "(?i)ZIP" : "(?i)HEX|BIN"; // (?i) =  case insensitive
         boolean statusOk = MimeTypeMap.getFileExtensionFromUrl(fileName).matches(extension);

        // Ask the user for the Init packet file if HEX or BIN files are selected. In case of a ZIP file the Init packets should be included in the ZIP.
        if (statusOk) {
            if (fileType != DfuService.TYPE_AUTO) {
                mScope = null;
                new AlertDialog.Builder(mContext).setTitle(R.string.dfu_file_init_title).setMessage(R.string.dfu_file_init_message)
                        .setNegativeButton(R.string.ALERT_BUTTON_CANCEL, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).setPositiveButton(R.string.ALERT_BUTTON_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType(DfuService.MIME_TYPE_OCTET_STREAM);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                    }
                }).show();
            } else {
                new AlertDialog.Builder(mContext).setTitle(R.string.dfu_file_scope_title).setCancelable(false)
                        .setSingleChoiceItems(R.array.dfu_file_scope, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        mScope = null;
                                        break;
                                    case 1:
                                        mScope = DfuServiceInitiator.SCOPE_SYSTEM_COMPONENTS;
                                        break;
                                    case 2:
                                        mScope = DfuServiceInitiator.SCOPE_APPLICATION;
                                        break;
                                }
                            }
                        }).setPositiveButton(R.string.ALERT_BUTTON_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int index;
                        if (mScope == null) {
                            index = 0;
                        } else if (mScope == DfuServiceInitiator.SCOPE_SYSTEM_COMPONENTS) {
                            index = 1;
                        } else {
                            index = 2;
                        }
                    }
                }).show();
            }
        }
    }


    /**
     * Callback of UPDATE/CANCEL button on DfuActivity
     */
    public void startUpload(BluetoothDevice selectedDevice, int fileRawId) {
        MLog.td(TAG, "startUpload --- fileRawId = " + fileRawId);
        if(fileRawId == 0){
            MLog.td("tjl", "startUpload --- fileRawId is 0! return.");
            return;
        }
        if (isDfuServiceRunning()) {
            MLog.td(TAG, "startUpload --- isDfuServiceRunning is run! return.");
            showUploadCancelDialog();
            return;
        }

        mFileRawId = fileRawId;
//		showProgressBar();
        if (selectedDevice == null) {
            MLog.td("tjl", "startUpload --- selectedDevice is null! return.");
            return;
        }
        MLog.td(TAG, "startUpload --- selectedDevice.getAddress() = " + selectedDevice.getAddress() + ", selectedDevice.getName() = " + selectedDevice.getName());

        mSelectedDevice = selectedDevice;

        mScope = DfuServiceInitiator.SCOPE_APPLICATION;
        int numberOfPackets = 10;//DfuServiceInitiator.DEFAULT_PRN_VALUE;

        final DfuServiceInitiator starter =
//				new DfuServiceInitiator(mSelectedDevice.getAddress())
                new DfuServiceInitiator(mSelectedDevice.getAddress())
                        .setDeviceName(mSelectedDevice.getName())

//				.setCustomUuidsForButtonlessDfuWithBondSharing(dfuServiceUUIDString, ANCSServiceUUIDString )
                        .setKeepBond(true)
                        .setForceDfu(false)
                        .setPacketsReceiptNotificationsEnabled(true)
                        .setPacketsReceiptNotificationsValue(numberOfPackets)
                        .setUnsafeExperimentalButtonlessServiceInSecureDfuEnabled(true);

        starter.setZip(mFileRawId);
        starter.setScope(mScope);
        starter.start(mContext, DfuService.class);
    }
    public void startUpload1(String mac,String name, int fileRawId) {
        MLog.td(TAG, "startUpload --- fileRawId = " + fileRawId);
        if(fileRawId == 0){
            MLog.td("tjl", "startUpload --- fileRawId is 0! return.");
            return;
        }
        if (isDfuServiceRunning()) {
            MLog.td(TAG, "startUpload --- isDfuServiceRunning is run! return.");
            showUploadCancelDialog();
            return;
        }

        mFileRawId = fileRawId;
        mScope = DfuServiceInitiator.SCOPE_APPLICATION;
        int numberOfPackets = 10;//DfuServiceInitiator.DEFAULT_PRN_VALUE;

        final DfuServiceInitiator starter =
//				new DfuServiceInitiator(mSelectedDevice.getAddress())
                new DfuServiceInitiator(mac)
                        .setDeviceName("龟孙子的nordic 这个名字是个假的")

//				.setCustomUuidsForButtonlessDfuWithBondSharing(dfuServiceUUIDString, ANCSServiceUUIDString )
                        .setKeepBond(true)
                        .setForceDfu(false)
                        .setPacketsReceiptNotificationsEnabled(true)
                        .setPacketsReceiptNotificationsValue(numberOfPackets)
                        .setUnsafeExperimentalButtonlessServiceInSecureDfuEnabled(true);

        starter.setZip(mFileRawId);
        starter.setScope(mScope);
        starter.start(mContext, DfuService.class);
        //這裏開始啓動升級，餘下的事情都交給庫操作
    }
    private void showUploadCancelDialog() {
        final LocalBroadcastManager manager = LocalBroadcastManager.getInstance(mContext);
        final Intent pauseAction = new Intent(DfuService.BROADCAST_ACTION);
        pauseAction.putExtra(DfuService.EXTRA_ACTION, DfuService.ACTION_PAUSE);
        manager.sendBroadcast(pauseAction);

        final UploadCancelFragment fragment = UploadCancelFragment.getInstance();
        fragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), TAG);
    }




    private void onTransferCompleted() {
        ToastUtils.show(R.string.dfu_success);
    }

    public void onUploadCanceled() {
        ToastUtils.show(R.string.dfu_aborted);
    }

    @Override
    public void onCancelUpload() {
        //mProgressBar.setIndeterminate(true);
        //mTextUploading.setText(R.string.dfu_status_aborting);
    }

    private void showErrorMessage(final String message) {
        ToastUtils.show("Upload failed: " + message);
    }



    private boolean isDfuServiceRunning() {
        final ActivityManager manager = (ActivityManager) mContext.getSystemService(mContext.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (DfuService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
