package com.matatalab.matatacode.model;


import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import java.util.LinkedList;
public class DuiLie {
    private LinkedList list;
    public DuiLie(){
        list = new LinkedList();
    }
    public void add(Object obj){
        list.add(obj);
    }
    public Object get(){
        return list.removeLast();
    }
    public boolean isNull(){
        return list.isEmpty();
    }
}
