package com.matatalab.matatacode.model;

import com.matatalab.matatacode.utils.MLog;

import java.util.ArrayList;
import java.util.List;

public class JavaBean {
    private String name;
    private List<String> data;

    public JavaBean(String n){
        this.name = n;
        data = new ArrayList<String>();
    }
    public void setData(String el){
        this.data.add(el);
    }

    public void print(){
        for (String it: data) {
            MLog.td("tjl",this.name+","+it);
        }
    }
}