package com.matatalab.matatacode.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.matatalab.matatacode.Global;
import com.matatalab.matatacode.app.BaseApplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.RandomAccessFile;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 文件相关操作的工具类
 */
public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();

    // 有sd卡时应用程序的存储根目录
    public static final String APP_SDCARD_AMOUNT_ROOT_PATH = "/matatalab/";
    // 无sd卡时应用程序的存储根目录
    public static final String APP_SDCARD_UNAMOUNT_ROOT_PATH = "/matatalab/";
    // 有sd卡时应用程序缓存文件的存储跟目录
    public static final String APP_SDCARD_AMOUNT_TMP_ROOT_PATH = "/tmp";
    // Apk保存目录
    public static final String APK_DIT_PATH = "/apk";
    // 插件保存目录
    public static final String PLUGIN_DIR_PATH = "/plugin";
    // Apk解压文件临时缓存目录
    public static final String APK_TMP_CACHE_PATH = "/apk/tmp";
    // 无缝连接服务配置文件信息存放目录
    public static final String WCS_CONFIG_DIR_PATH = "/wcsconfig";
    // 日志文件保存目录
    public static final String LOG_DIR_PATH = "/log";
    // 图片保存目录
    public static final String PIC_DIR_PATH = "/pic";
    // Gif保存目录
    public static final String GIF_DIR_PATH = "/gif";

    // 截图保存目录
    public static final String CATCH_DIR_PATH = "/Pictures";
    // 头像保存目录
    public static final String HEAD_DIR_PATH = "/head";

    // tab页面预加载数据缓存目录
    public static final String CACHE_DIR_PATH = "/cache";
    // 大嘴巴图片下载后的存储路径
    public static final String PIC_DOWNLOAD_DIR_PATH = "/videocomm_download";
    // 保存后台下发的permission目录
    public static final String PERMISSION_DIR_PATH = "/permission";
    // webview开启本地存储时也需要一个cache目录
    public static final String WEBVIEW_CACHE_PATH = "/webview_cache";
    // 插件安装包名
    public static final String PLUGIN_DIR_NAME = "plugin";

    //code扩展名
    public static final String CODE_SUFFIX = ".code";

    /**
     * 指定要扫描的文件路径与文件扩展名列表, 返回指定扩展名的文件名的列表, 调用方不能是主线程
     *
     * @param root       文件路径,可以是文件目录或文件
     * @param suffix 文件扩展名列表
     * @return 特殊情况, 如果文件扩展 名列表为null则返回所有文件
     */
    public static String[] scanFileName(String root, String suffix) throws IOException, OutOfMemoryError {
        // 优化中
        if (TextUtils.isEmpty(root)) {
            return null;
        }

        File rootDir = new File(root);
        if (rootDir != null && rootDir.exists()) {
            List<String> scanResultList = new ArrayList<String>();

            File[] files = rootDir.listFiles();

            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];

                    String name = file.getName(); // file.getCanonicalPath()改为getAbsolutePath(),
                    // 要gengeng确认

                    if (file.isFile()) {
                        if (name.endsWith(suffix)) {
                            scanResultList.add(name.replace(suffix, ""));
                        }
                    }
                }
            }

            if(scanResultList.size() > 0){
                return scanResultList.toArray(new String[scanResultList.size()]);
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * 指定要扫描的文件路径与文件扩展名列表, 返回指定扩展名的文件名的列表, 调用方不能是主线程
     *
     * @param root       文件路径,可以是文件目录或文件
     * @param suffixList 文件扩展名列表
     * @return 特殊情况, 如果文件扩展 名列表为null则返回所有文件
     */
    public static List<String> scanFile(String root, List<String> suffixList) throws IOException, OutOfMemoryError {
        // 优化中
        if (TextUtils.isEmpty(root)) {
            return null;
        }

        File rootDir = new File(root);
        if (rootDir != null && rootDir.exists()) {
            List<String> scanResultList = new ArrayList<String>();

            File[] files = rootDir.listFiles();

            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];

                    String path = file.getAbsolutePath(); // file.getCanonicalPath()改为getAbsolutePath(),
                    // 要gengeng确认

                    if (file.isFile()) {
                        if (isSpecfiedSuffixExist(path, suffixList)) {
                            scanResultList.add(path);
                        }
                    } else if (file.isDirectory() && path.indexOf("/.") == -1) {
                        // 忽略点文件（隐藏文件/文件夹）
                        List<String> childList = scanFile(path, suffixList);
                        if (childList != null && !childList.isEmpty()) {
                            scanResultList.addAll(childList);
                        }
                    }
                }
            }

            return scanResultList;
        } else {
            return null;
        }
    }

    /**
     * 判断一个文件是否是指定的后缀类型的文件
     *
     * @param path       文件路径
     * @param suffixList 扩展名列表
     * @return
     */
    public static boolean isSpecfiedSuffixExist(String path, List<String> suffixList) {
        if (TextUtils.isEmpty(path) || suffixList == null || suffixList.isEmpty()) {
            return true;
        }

        for (String suffix : suffixList) {
            try {
                if (path.endsWith(suffix)) {
                    return true;
                }
            } catch (NullPointerException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean copy(String from, String dest) throws IOException {
        if (TextUtils.isEmpty(from) || TextUtils.isEmpty(dest)) {
            return false;
        }
        File file = new File(from);
        if (!file.exists()) {
            return false;
        }
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(new FileInputStream(file));
            outBuff = new BufferedOutputStream(new FileOutputStream(new File(dest)));

            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
            return true;
        } finally {
            // 关闭流
            if (inBuff != null)
                try {
                    inBuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (outBuff != null)
                try {
                    outBuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    // 判断SDCard是否存在并且是可写的
    public static boolean isSDCardExistAndCanWrite() {
        boolean result = false;
        try {
            result = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && Environment.getExternalStorageDirectory().canWrite();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    // 判断SDCard是否存在并且是可写的
    public static boolean isSDCardExistAndCanRead() {
        boolean result = false;
        try {
            result = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && Environment.getExternalStorageDirectory().canRead();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    /**
     * 获取程序运行期间文件保存的根目录
     *
     * @return SD卡可用的时候返回的是 /mnt/sdcard/videocomm，SD卡不可用返回的是内存的路径data/data
     * /packagename/files
     */
    public static String getCommonRootDir() {
        String dirPath = null;

        // 判断SDCard是否存在并且是可用的
        try {
            if (isSDCardExistAndCanWrite()) {
                dirPath = Environment.getExternalStorageDirectory().getPath() + APP_SDCARD_AMOUNT_ROOT_PATH;
            } else {
                dirPath = BaseApplication.self().getFilesDir().getAbsolutePath() + APP_SDCARD_UNAMOUNT_ROOT_PATH;
            }
        } catch (Exception e) {
            return null;
        }
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
//        return file.getAbsolutePath();
        return dirPath;
    }

    public static String getRootDir() {
        String dirPath = null;

        // 判断SDCard是否存在并且是可用的
        if (isSDCardExistAndCanWrite()) {
            dirPath = Environment.getExternalStorageDirectory().getPath();
        } else {
            dirPath = BaseApplication.self().getFilesDir().getAbsolutePath();
        }
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取程序运行期间临时文件保存的根目录
     *
     * @return SD卡可用的时候返回的是 /mnt/sdcard/dazuiba，SD卡不可用返回的是内存的路径data/data
     * /packagename/cache
     */
    public static String getTmpRootDir() {
        String dirPath = null;

        // 判断SDCard是否存在并且是可用的
        if (isSDCardExistAndCanWrite()) {
            dirPath = Environment.getExternalStorageDirectory().getPath() + APP_SDCARD_AMOUNT_ROOT_PATH + APP_SDCARD_AMOUNT_TMP_ROOT_PATH;
        } else {
            dirPath = FileUtil.getInternalCachePath();
        }
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取常驻文件路径 常驻根目录 + 业务所需路径
     *
     * @param path
     * @return
     */
    public static String getCommonPath(String path) {
        final String rootDir = getCommonRootDir();
        String fullPath = null;
        if (!TextUtils.isEmpty(path)) {
            fullPath = rootDir + path;
        } else {
            fullPath = rootDir;
        }
        return getPath(fullPath, false);
    }

    /**
     * 获取临时文件路径 临时根目录 + 业务所需路径
     *
     * @return
     */
    public static String getTmpPath(String path) {
        final String rootDir = getTmpRootDir();
        String fullPath = null;
        if (!TextUtils.isEmpty(path)) {
            fullPath = rootDir + path;
        } else {
            fullPath = rootDir;
        }
        return getPath(fullPath, false);
    }

    /**
     * 指定路径创建目录, 并提供指定momedia的接口
     *
     * @param nomedia : 是否需要nomedia文件，只有存图片的目录需要，有nomedia图片在相册不可见
     * @return 完整路径
     */
    public static String getPath(String path, boolean nomedia) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
            if (nomedia) {
                File nomediaFile = new File(path + File.separator + ".nomedia");
                try {
                    nomediaFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getAbsolutePath();
    }

    // 取得apk保存目录
    public static String getAPKDir() {
        return getCommonPath(APK_DIT_PATH);
    }

    // 取得apk解压文件临时缓存目录
    public static String getAPKTmpCacheDir() {
        return getCommonPath(APK_TMP_CACHE_PATH);
    }

    // 取得log保存目录
    public static String getLogDir() {
        return getCommonPath(LOG_DIR_PATH);
    }

    // 取得配置文件保存目录，配置文件不存SDCARD，存应用安装目录下
    public static String getWcsConfigDir() {
        String dirPath = BaseApplication.self().getFilesDir().getAbsolutePath() + WCS_CONFIG_DIR_PATH;
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    // 取得图片下载后的保存目录
    public static String getPicDownloadDir() {
        return getPath(getCommonRootDir() + PIC_DOWNLOAD_DIR_PATH, false);
    }

    // 取得图片保存目录
    public static String getPicDir() {
        return getPath(getCommonRootDir() + PIC_DIR_PATH, true);
    }

    // 取得gif保存目录
    public static String getGifDir() {
        return getPath(getCommonRootDir() + GIF_DIR_PATH, true);
    }


    /**
     * 取得头像保存目录
     *
     * @return
     */
    public static String getHeadDir() {
        return getPath(getCommonRootDir() + HEAD_DIR_PATH, true);
    }

    /**
     * 取得截图保存目录
     * <p>
     * 默认获取的是SD卡根目录的Pictures： /mnt/sdcard/Pictures/视频通讯截图
     * <p>
     * SD卡不可用时返回null
     *
     * @return
     */
    public static String getCatchImageDir() {
        String dirPath = null;

        // 判断SDCard是否存在并且是可用的
        try {
            if (isSDCardExistAndCanWrite()) {
                dirPath = Environment.getExternalStorageDirectory().getPath() + CATCH_DIR_PATH;
            }
        } catch (Exception e) {
            MLog.e(TAG, "getCatchImageDir err:" + e.getMessage());
            return null;
        }

        if (dirPath == null) {
            MLog.e(TAG, "getCatchImageDir err: dirPath is null.");
            return null;
        }

        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String dirName = "";//BaseApplication.self().getString(R.string.catch_image_folder_name);

        return getPath(file.getAbsolutePath() + "/" + dirName, false);
    }

    // 取得tab页面预加载数据缓存目录
    public static String getCacheDir() {
        return getCommonPath(CACHE_DIR_PATH);
    }

    /**
     * 获得插件所保存的目录
     *
     * @return
     */
    public static String getPluginDir() {
        File file = BaseApplication.self().getDir(PLUGIN_DIR_NAME, Context.MODE_PRIVATE);
        if (!file.exists()) {
            file.mkdirs();
        }

        return file.getAbsolutePath();
    }

    public static String getWebViewCacheDir() {
        return getCommonPath(WEBVIEW_CACHE_PATH);
    }

    // 保存后台下发的permission目录
    public static String getPermissiomDir() {
        return getCommonPath(PERMISSION_DIR_PATH);
    }

    // 得到系统分配的程序缓存目录
    public static String getInternalCachePath() {
        String dirPath = BaseApplication.self().getCacheDir() + "";
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dirPath;
    }

    // 清空缓存目录
    public static void clearInternalCache(String dir) {
        File f = new File(getInternalCachePath() + File.separator + dir);
        if (f.exists()) {
            File files[] = f.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
        }
    }

    /**
     * 获得指定文件路径的文件名
     *
     * @param path 文件的完整路径，包括文件名和后缀
     * @return 文件名
     */
    public static String getFileName(String path) {
        String file = path.substring(path.lastIndexOf("/") + 1);
        if (file.contains(".")) {
            file = file.substring(0, file.indexOf("."));
        }
        return file;
    }

    /**
     * 获得指定文件路径的文件后缀名
     *
     * @param path 文件的完整路径，包括文件名和后缀
     * @return 文件后缀，如果无后缀return null
     */
    public static String getFileExtension(String path) {
        String file = path.substring(path.lastIndexOf("/") + 1);
        if (file.contains(".")) {
            return file.substring(file.indexOf(".") + 1);
        } else {
            return null;
        }
    }

    /**
     * 获取制定路径文件的大小
     *
     * @param path 文件的完整路径，包括文件名和后缀
     * @return 文件大小，文件不存在return 0
     */
    public static long getFileSize(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        } else {
            return file.length();
        }
    }

    /*
     * 获取指定路径文件的最近修改时间
     *
     * @param path文件的完整路径，包括文件名和后缀
     *
     * @return 文件最近修改时间，文件不存在返回0
     */
    public static long getFileLastModified(String path) {
        long ret = 0;

        if (null != path) {
            File file = new File(path);
            if (file.exists()) {
                ret = file.lastModified();
            }
        }

        return ret;
    }

    /**
     * 将字节流数据写入到文件缓存 baos从外部来，外部close，这里不要close baos
     *
     * @param baos 字节流数据
     * @param dest 要保存的文件完整路径，包括文件名和后缀
     * @return 是否成功
     */
    public static boolean write2File(ByteArrayOutputStream baos, String dest) {
        if (baos == null) {
            return false;
        }
        File file = new File(dest);
        if (file.exists()) {
            // 文件已存在的话，说明图片已经下载过了，不用再保存
            return true;
        } else {
            FileOutputStream fos = null;
            try {
                file.createNewFile();
                fos = new FileOutputStream(file);
                baos.writeTo(fos);
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 将byte[]数据写入到文件缓存
     *
     * @param data 数据
     * @param dest 要保存的文件完整路径，包括文件名和后缀
     * @return 是否成功
     */
    public static boolean write2File(byte[] data, String dest) {
        if (data == null) {
            return false;
        }
        File file = new File(dest);
        if (file.exists()) {
            // 文件已存在的话，说明图片已经下载过了，不用再保存
            // return true;
            // } else {
            /*
             * Q&D : 上述假设是不完整的，如遇断点续传或者希望覆盖现有文件的case咋整。
             * 暂时采取删除已存在的文件并重新写入解决；20130707 leofan
             */
            deleteFile(dest);
        }
        FileOutputStream fos = null;
        try {
            file.createNewFile();
            fos = new FileOutputStream(file);
            fos.write(data);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // add by suceedzhang 有可能写文件写到一半的时候空间不足，导致不完整的文件留在sd卡
            try {
                file.delete();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // }
        return false;
    }

    /**
     * 将byte[]数据写入到文件缓存, 支持文件内容追加
     *
     * @param data 要写入的内容
     * @param dest 目标文件
     * @return
     */
    public static boolean append2File(byte[] data, String dest) {
        if (data == null || TextUtils.isEmpty(dest)) { // 容错
            return false;
        }

        File file = new File(dest);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return false;
            }
        }

        RandomAccessFile randomFile = null;
        long fileLength;

        try {
            randomFile = new RandomAccessFile(dest, "rw");
            // 文件长度，字节数
            fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.write(data);

        } catch (FileNotFoundException e1) {
        } catch (IOException e1) {
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e) {
                }
            }
        }

        return false;
    }

    /**
     * 删除指定路径文件
     *
     * @param path 文件的完整路径，包括文件名和后缀
     * @return boolean，true代表成功
     */
    public static boolean deleteFile(String path) {
        File dir = new File(path);
        if (dir.exists() && dir.isFile()) {
            return dir.delete();
        }
        return false;
    }

    /**
     * 更新指定路径文件最近修改时间
     *
     * @param path             文件的完整路径，包括文件名和后缀
     * @param lastModifiedTime 文件最后修改时间
     * @return boolean，true代表成功 Q&D ：很多Android系统不支持对文件lastmodified的修改，此接口。。。
     */
    public static boolean updateFileLastModified(String path, Long lastModifiedTime) {
        File file = new File(path);
        boolean ret = false;

        if (file.exists()) {
            ret = file.setLastModified(lastModifiedTime);
        }

        return ret;
    }

    /* 计算sdcard上的剩余空间，返回单位为MB，整型 */
    public static int freeSpaceOnSd() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()) / (1024 * 1024);

        return (int) sdFreeMB;
    }

    /**
     * 读取文件到字节流 baos baos从外部来，外部close，这里不要close baos
     *
     * @param baos 字节流数据
     * @param dest 要读取的文件完整路径，包括文件名和后缀
     */
    public static boolean readFile(String dest, ByteArrayOutputStream baos) {
        File file = new File(dest);
        if (null == baos || !file.exists()) {

            return false;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            while (true) {
                int numread = fis.read(buf);
                if (-1 == numread) {
                    break;
                }
                baos.write(buf, 0, numread);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (baos.size() > 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 创建一个指定路径的文件，并且指定大小
     *
     * @param path
     * @param size
     * @throws IOException
     */
    public static boolean createFileWithSpecialSize(String path, long size) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(path, "rw");
            accessFile.setLength(size);
            return true;
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError error) {
        } finally {
            if (accessFile != null) {
                try {
                    accessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 往指定路径写一个字符串
     *
     * @param path
     * @param text
     * @return
     */
    public static boolean write(String path, String text) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        FileWriter fw = null;
        try {
            file.createNewFile();
            fw = new FileWriter(file);
            fw.write(text);
            return true;
        } catch (IOException e) {
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 读取指定文件里的字符串
     *
     * @param path
     * @return
     */
    public static String read(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return "";
        }
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            return br.readLine();
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 获得文件路径，不存在直接返回，存在给文件名加数字，返回个不存在的文件路径
     *
     * @param path 文件的完整路径，包括文件名和后缀
     * @return
     */
    public static String getUnusedFilePath(String path) {
        String temp = path;
        File file;
        for (int i = 0; ; i++) {
            file = new File(temp);
            if (file.exists()) {
                if (temp.contains(".")) {
                    int point = temp.lastIndexOf(".");
                    temp = temp.substring(0, point) + i + temp.substring(point);
                } else {
                    temp += i;
                }
            } else {
                return temp;
            }
        }
    }

    /**
     * 写数据到文件
     *
     * @param file
     * @param bytes
     * @return
     */
    public static boolean writeToFile(File file, byte[] bytes) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        boolean bool = true;
        try {
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            bool = false;
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public static byte[] compressBitmap(Bitmap bitmap, Bitmap.CompressFormat format, int bitRate) {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(format, bitRate, localByteArrayOutputStream);
        byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
        try {
            localByteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayOfByte;
    }

    /**
     * 写对象到指定文件
     *
     * @param object
     * @param path
     */
    public static void writeObj2File(Object object, String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            File file = new File(path);
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            // SystemEventManager.getInstance().onLowMemory();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从文件读取对象
     *
     * @param path
     * @return
     */
    public static Object readObjFromFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        File file = new File(path);
        if (file.exists() == false) {
            return null;
        }

        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 写对象数组到指定文件
     *
     * @param objectList
     * @param path
     */
    public static void writeObjList2File(List<Object> objectList, String path) {
        if (TextUtils.isEmpty(path) || objectList == null) {
            return;
        }

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            File file = new File(path);
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            for (Object obj : objectList) {
                oos.writeObject(obj);
            }
            // 结尾加上写个null是为了在读的时候不会报EofException
            oos.writeObject(null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从文件读取对象数组
     *
     * @param path
     * @return
     */
    public static List<Object> readObjListFromFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        File file = new File(path);
        if (file.exists() == false) {
            return null;
        }

        List<Object> objList = new ArrayList<Object>();
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            Object obj = null;
            while (true) {
                obj = (Object) ois.readObject();
                if (obj != null) {
                    objList.add(obj);
                    continue;
                } else {
                    break;
                }
            }
            return objList;
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return objList;
    }

    public static Map<String, String> readConfigFile(String path) {
        Map<String, String> data = new HashMap<String, String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "gb2312"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line == null || line.length() == 0 || line.startsWith("#")) {
                    continue;
                }
                String[] strs = line.split("#");
                if (strs.length >= 1) {
                    String[] ss = strs[0].split("=");
                    data.put(ss[0].trim(), ss[1].trim());
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    /**
     * 在data/data/packageName/files/目录下创建文件
     *
     * @param fileName
     * @param text
     * @param visitPermission 访问权限
     * @return
     */
    public static boolean writeToAppData(String fileName, String text, int visitPermission) {
        if (TextUtils.isEmpty(fileName) || TextUtils.isEmpty(text)) {
            return false;
        }

        FileOutputStream os = null;
        try {
            os = BaseApplication.self().openFileOutput(fileName, visitPermission);

            byte[] buffer = text.getBytes();
            os.write(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;

    }


    /**
     * 获取应用程序文件目录。因为写localstorage的js文件，放到SD卡可能不安全，<B>这里只能获取到应用程序私有目录，如果要使用SD卡，
     * 可调用{@link #getCommonRootDir()}</B>
     *
     * @return 返回的是/data/data/packagename/files目录
     */
    public static String getFilesDir() {
        String dirPath = BaseApplication.self().getFilesDir().getAbsolutePath();
        return dirPath;
    }

    /**
     * 判断文件是否存在
     */
    public static boolean isFileExists(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 将从sourceFilePath中获得的so文件释放到指定的路径
     *
     * @param sourceFilePath
     * @param targetSaveSoDir
     */
    public static void extractSoToFilePath(String sourceFilePath, String targetSaveSoDir) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(sourceFilePath);
            Enumeration<JarEntry> entryEnumeration = jarFile.entries();
            byte[] buffer = new byte[1024];
            while (entryEnumeration.hasMoreElements()) {
                JarEntry jarEntry = entryEnumeration.nextElement();
                String fullPath = jarEntry.getName();
                String name = fullPath.substring(fullPath.lastIndexOf('/') + 1);
                boolean isMatchCpuABI = matchCpuABI(fullPath);
                if (name.endsWith(".so") && fullPath.startsWith("lib/") && isMatchCpuABI) {
                    InputStream inputStream = null;
                    FileOutputStream fileOutputStream = null;
                    try {
                        inputStream = jarFile.getInputStream(jarEntry);
                        fileOutputStream = new FileOutputStream(targetSaveSoDir + File.separator + name);
                        if (Global.APP_LOG_DEBUG) {
                            MLog.d("BaoSo", "extract so:" + name + " to path:" + targetSaveSoDir);
                        }
                        int length = 0;
                        while ((length = inputStream.read(buffer)) != -1) {
                            fileOutputStream.write(buffer, 0, length);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                }
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * so路径是否符合cpu指令集。判断规则是如果当前平台是x86，则路径需要包含有x86.其他的都认为是arm
     *
     * @param fullPath
     * @return
     */
    private static boolean matchCpuABI(String fullPath) {
        String cpuABI = android.os.Build.CPU_ABI;
        if (cpuABI != null && cpuABI.equals("x86")) {
            return fullPath != null && fullPath.contains("x86");
        } else// 当前非x86指令集机器，如果是x86，mips目录，不做释放
        {
            return fullPath != null && !fullPath.contains("x86") && !fullPath.contains("mips");
        }
    }

    /**
     * 对象转数组
     *
     * @param obj
     * @return
     */
    public static byte[] objectToByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    /**
     * 数组转对象
     *
     * @param bytes
     * @return
     */
    public static Object byteArrayToObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public static String getFileNameByDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());

    }

    public static String getFileNameByTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSS");
        return df.format(new Date());

    }

    /**
     * 判断该文件是否是一个图片
     *
     * @param fileName
     * @return
     */
    public static boolean isImageFile(String fileName) {
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
    }

    public static InputStream getFileInputStream(String path) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileInputStream;
    }
}
