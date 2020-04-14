package com.matatalab.matatacode.model;

import android.content.Context;
import android.util.Log;

import com.matatalab.matatacode.interfaces.WebVIewCallback;


import java.net.MalformedURLException;
import java.net.URL;

/**
 * webview管理模块
 */
public class BrowserModel {
    private static final String TAG = BrowserModel.class.getSimpleName();

    /**
     * webview回调协议 格式头
     */
    private static final String WEBVIEW_PROTOCOL_SCHEME = "js";
    /**
     * webview回调协议 格式名
     */
    private static final String WEBVIEW_PROTOCOL_AUTHORITY = "webview";
    /**
     * webview回调协议 参数类型
     */
    private static final String WEBVIEW_PROTOCOL_TYPE = "type";
    /**
     * webview回调协议 参数内容
     */
    private static final String WEBVIEW_PROTOCOL_MSG = "msg";

    private volatile static BrowserModel mBrowserModel = null;

    
    private Context mContext;

    private static final int MAX_LENGTH = 14;
    private boolean mNeedTestPage = false;

    private final int disable = 120;
    private final int enable = 255;



    private URL mIntentUrl;

    private WebVIewCallback mWebVIewCallback;

    public void initView(Context context, android.webkit.WebView webView) {
        mContext = context;
    }

    private BrowserModel() {

    }

    /**
     * 单例
     *
     * @return
     */
    public static BrowserModel getInstance() {
        if (mBrowserModel == null) {
            synchronized (BrowserModel.class) {
                if (mBrowserModel == null) {
                    mBrowserModel = new BrowserModel();
                }
            }
        }
        return mBrowserModel;
    }

    public void setWebVIewCallback(WebVIewCallback webVIewCallback) {
        this.mWebVIewCallback = webVIewCallback;
    }

    public void showUrl(String url) {

        Log.e(TAG, "showUrl --- url = " + url);

        try {
//                mIntentUrl = new URL(intent.getData().toString());
            mIntentUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {

        } catch (Exception e) {
        }

//        setContentView(R.layout.ui_browser);
//        mViewParent = (ViewGroup) findViewById(R.id.webView1);

//    initBtnListenser();

//        mTestHandler.sendEmptyMessageDelayed(MSG_INIT_UI,10);

        init();

    }


    private void init() {




    }


    public void exit() {

    }

    public void runJsCode(final String js){

    }

}
