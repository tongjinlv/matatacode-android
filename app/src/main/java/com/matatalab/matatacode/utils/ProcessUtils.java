package com.matatalab.matatacode.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessUtils {
    // 5~10ms spent on nexus 4, slow device may spent 20ms
    // this api work on android, depends system_activity_service
    public static String getProcessName(Context context, int pid) {
        String str = null;
        ActivityManager am = null;
        {
            Object obj = context.getSystemService(Context.ACTIVITY_SERVICE);
            if (obj != null) {
                try {
                    am = (ActivityManager) obj;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        if (am == null)
            return null;
        List<RunningAppProcessInfo> list = am.getRunningAppProcesses();
        if (list != null && list.size() > 0) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                RunningAppProcessInfo info = list.get(i);
                if (info != null && info.pid == pid) {
                    str = info.processName;
                    break;
                }
            }
        }
        return str;
    }


    // fastest way to get processName
    //0-2ms spent
    // this api work well on linux
    public static String getProcessName(int pid) {
        String line = "/proc/" + pid + "/cmdline";
        FileInputStream fis = null;
        String processName = null;
        byte[] bytes = new byte[512];
        int read = 0;
        try {
            fis = new FileInputStream(line);
            read = fis.read(bytes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (read > 0) {
            processName = new String(bytes, 0, read);
            processName = processName.trim();

        }
        return processName;
    }

    public static int getProcessAdj(int pid) {
        String line = "/proc/" + pid + "/oom_adj";
        FileInputStream fis = null;
        int adj = 100;
        byte[] bytes = new byte[128];
        int read = 0;
        try {
            fis = new FileInputStream(line);
            read = fis.read(bytes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (read > 0) {
            String processName = new String(bytes, 0, read);
            processName = processName.trim();
            adj = Integer.parseInt(processName);
        }
        return adj;
    }

    public static int[] getProcessThreads(int pid) {
        String line = "/proc/" + pid + "/task";
        int[] threads = null;
        File task = new File(line);
        String[] strs = task.list();
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (strs != null) {
            for (int i = 0; i < strs.length; i++) {
                if (strs[i] != null) {
                    try {
                        int id = Integer.parseInt(strs[i]);
                        list.add(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        int size = list.size();
        if (size > 0) {
            threads = new int[size];
            for (int i = 0; i < size; i++) {
                threads[i] = list.get(i);
            }
        }
        return threads;
    }

}
