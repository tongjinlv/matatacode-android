package com.matatalab.matatacode.model;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.matatalab.matatacode.AppConst;
import com.matatalab.matatacode.R;
import com.matatalab.matatacode.utils.MLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PublicWay {
    public static  List<Activity> activityLst = new ArrayList<Activity>();
}