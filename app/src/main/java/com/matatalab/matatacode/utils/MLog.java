package com.matatalab.matatacode.utils;

import android.content.Context;
import android.text.TextUtils;

import com.matatalab.matatacode.Global;
import com.matatalab.matatacode.app.BaseApplication;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class MLog {

    public static final String LOG_TAG = "MatataCode";

    public static final String PROTOCAL_LOG_TAG = "video_protocal";

    /* 统计上报的日志打印标签 */
    public static final String STAT_LOG_TAG = "video_stat";

    public static final boolean LOG_FILE = false;

    // Log.v
    public static void v(String tag, String msg) {
        if (Global.APP_LOG_DEBUG) {
            android.util.Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (Global.APP_LOG_DEBUG) {
            android.util.Log.v(tag, msg, tr);
        }
    }

    public static void v(String msg) {
        v(LOG_TAG, msg);
    }

    // Log.d
    public static void d(String tag, String msg) {
        if (Global.APP_LOG_DEBUG) {
//            android.util.Log.d(tag, msg);
            android.util.Log.i(tag, msg);
        }
    }

    // Log.d
    public static void d(String tag, String msg, byte[] bytes) {
        if (Global.APP_LOG_DEBUG) {
            android.util.Log.i(LOG_TAG, msg + bytes2hex(bytes));
        }
    }

    // Log.d
    public static void d(String tag, String msg, String[] strings) {
        if (Global.APP_LOG_DEBUG) {
            android.util.Log.i(LOG_TAG, msg + StringArrayToString(strings));
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (Global.APP_LOG_DEBUG) {
            android.util.Log.d(tag, msg, tr);
        }
    }
    public static void td(String tag, String msg) {
        android.util.Log.d(tag, msg);
    }
    public static void hd(String tag,String title, byte[] b) {
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            title+=hex.toUpperCase();
        }
        android.util.Log.d(tag, title);
    }

    public static void te(String tag, String msg) {
        android.util.Log.e(tag, msg);
    }
    public static void tw(String tag, String msg) {
        android.util.Log.w(tag, msg);
    }
    public static void d(String msg) {
        d(LOG_TAG, msg);
    }

    // Log.i
    public static void i(String tag, String msg) {
        if (Global.APP_LOG_DEBUG) {
            android.util.Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (Global.APP_LOG_DEBUG) {
            android.util.Log.i(tag, msg, tr);
        }
    }

    public static void i(String msg) {
        i(LOG_TAG, msg);
        if (LOG_FILE) {
            f(msg, "assistant.log", true);
        }
    }

    // Log.w
    public static void w(String tag, String msg) {
        if (Global.APP_LOG_DEBUG) {
            android.util.Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (Global.APP_LOG_DEBUG) {
            android.util.Log.w(tag, msg, tr);
        }
    }

    public static void w(String msg) {
        w(LOG_TAG, msg);
        if (LOG_FILE) {
            f(msg, "assistant.log", true);
        }
    }

    // Log.e
    public static void e(String tag, String msg) {
        if (Global.APP_LOG_DEBUG) {
            android.util.Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (Global.APP_LOG_DEBUG) {
            android.util.Log.e(tag, msg, tr);
        }
    }

    public static void e(String msg) {
        e(LOG_TAG, msg);
        if (LOG_FILE) {
            f(msg, "assistant.log", true);
        }
    }

    public static void f(String msg, String filename, boolean append) {
        writeToFile(msg, filename, append);
    }

    public static void f(String msg, String filename) {
        writeToFile(msg, filename, false);
    }

    public static void writeToFile(String msg, String filename, boolean append) {
        BufferedWriter bos = null;
        try {
            bos = new BufferedWriter(new FileWriter(FileUtil.getLogDir() + "/" + filename, append));
            bos.write(TimeUtil.getNowTime() + " " + msg + "\r\n");
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 打印调用栈
     */
    public static void printCallTraces(String tag) {
        if (Global.APP_LOG_DEBUG) {
            java.util.Map<Thread, StackTraceElement[]> ts = Thread.getAllStackTraces();
            StackTraceElement[] ste = ts.get(Thread.currentThread());
            v(tag, "======================start============================");
            for (StackTraceElement s : ste) {
                v(tag, s.toString());
            }
            v(tag, "=======================end============================");
        }
    }

    /**
     * 将log信息打印到文件中，并加上时间戳、调用堆栈等信息
     *
     * @param tag
     * @param msg
     * @param filename
     */
    public static void fLog(String tag, String msg, String filename) {
        fLog(tag, msg, filename, true, true, false);
    }

    public static void fLog(String tag, String msg, boolean stackToLogcat) {
        fLog(tag, msg, null, true, true, stackToLogcat);
    }

    public static void fLog(String tag, String msg, String filename, boolean printStack, boolean toLogcat) {
        fLog(tag, msg, filename, printStack, toLogcat, false);
    }

    /**
     * 打印日志到文件或者logcat。每行会附上线程、进程相关信息
     *
     * @param tag
     * @param msg           日志信息
     * @param filename      需要保存到的日志文件
     * @param printStack    是否打印调用盏信息
     * @param toLogcat      是否也将日志打印到logcat
     * @param stackToLogcat 详细堆栈是否到logcat？
     */
    public static void fLog(String tag, String msg, String filename, boolean printStack, boolean toLogcat, boolean stackToLogcat) {
        if (!Global.APP_LOG_DEBUG) {
            return;
        }
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        tag = tag + " " + TimeUtil.getNowTimeMills() + " " + android.os.Process.myPid() + "(" + BaseApplication.self().isMainProcess() + ")/"
                + Thread.currentThread().getName() + "," + Thread.currentThread().getId() + " ";
        stringBuilder.append(tag).append(" -------->start<--------.\n");
        stringBuilder.append(tag).append("msg:").append(msg).append("\n");
        if (toLogcat) {
            d(tag, tag + " msg:" + msg);
        }
        if (printStack) {
            stringBuilder.append(tag).append(" info stack:\n");
            boolean start = false;// 简单跳过自身堆栈信息的打印
            for (StackTraceElement traceElement : stackTraceElements) {
                String stack = traceElement.toString();
                if (start) {
                    stringBuilder.append(tag).append(" ").append(stack).append("\n");
                }
                if (!start) {
                    if (stack.contains("MLog")) {
                        start = true;
                    }
                }
            }
        }
        stringBuilder.append(tag).append(" -------->end<--------.\n\n");
        if (stackToLogcat) {
            d(tag, stringBuilder.toString());
        }
        if (!TextUtils.isEmpty(filename)) {
            f(stringBuilder.toString(), filename, true);
        }
    }

    /**
     * 记录异常到文件,一些程序中出现的异常需要记录
     *
     * @param e
     */
    public static void f(final Throwable e) {
        Writer writer = null;
        PrintWriter printWriter = null;
        try {
            writer = new StringWriter();
            printWriter = new PrintWriter(writer);
            e.printStackTrace(printWriter);
            String msg = writer.toString();
            fLog("p.com.qq.connect", "exception INFO: " + msg, "dkevin.txt", true, false);
        } catch (Exception ie) {
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ie) {
                    e.printStackTrace();
                }
            }
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    public static Context context = BaseApplication.self();

    public static String bytes2hex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte b : bytes) {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1) {
                tmp = "0" + tmp;
            }
            sb.append(tmp);
        }
        return sb.toString();

    }

    public static String StringArrayToString(String[] strings) {
        if (strings == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s);
            sb.append(",");
        }
        return sb.toString();

    }
}
