package com.matatalab.matatacode.model;


import android.content.Context;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.BaseAdapter;

import com.google.android.gms.wearable.Asset;
import com.matatalab.matatacode.AppConst;
import com.matatalab.matatacode.FileActivity;
import android.widget.ImageView;

import android.widget.TextView;

import com.matatalab.matatacode.R;
import com.matatalab.matatacode.utils.MLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


public class MyAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    String[] name;
    Context context;
    int grid_list;
    int type;


    public MyAdapter(Context context , int grid_list , String[] name ,int type) {

        this.inflater = LayoutInflater.from(context);
        this.name = name;
        this.context=context;
        this.grid_list=grid_list;
        this.type=type;
    }

    @Override

    public int getCount() {
        return name.length;

    }



    @Override

    public Object getItem(int position) {
        return position;

    }



    @Override

    public long getItemId(int position) {
        return position;

    }



    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView==null) {
            holder=new ViewHolder();
            convertView=this.inflater.inflate(grid_list, null);
            holder.iv=(ImageView) convertView.findViewById(R.id.main_grid_item_iv);
            holder.tv=(TextView) convertView.findViewById(R.id.main_grid_item_tv);
            holder.sv=(ImageView) convertView.findViewById(R.id.imageView_gl1);
            convertView.setTag(holder);

        }
        else {

            holder=(ViewHolder) convertView.getTag();
        }
        try {
            if(name[position].equals(AppConst.UNTITLED_NAME))
            {
                holder.iv.setImageResource(R.mipmap.untitled);
            }else {
                if(this.type==1)
                {
                    holder.sv.setBackground(context.getResources().getDrawable(R.mipmap.unselectedmdpi));
                    holder.sv.setVisibility(View.VISIBLE);
                }else holder.sv.setVisibility(View.INVISIBLE);
                String temp = AppConst.EXAMPLE_PATH + name[position] + "_logo.jpg";
                File f=new File(temp);
                if(!f.exists())temp=AppConst.EXAMPLE_PATH + name[position] + "_icon.jpg";
                File nl = new File(temp);
                InputStream inputStream = new FileInputStream(nl);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                holder.iv.setImageBitmap(bitmap);
            }

        } catch (IOException e) {
            MLog.td("tjl","error:"+e.getMessage());
            e.printStackTrace();
        }

        holder.tv.setText(name[position]);
        return convertView;

    }

    private class ViewHolder{

        ImageView iv;
        TextView tv;
        ImageView sv;
    }



}