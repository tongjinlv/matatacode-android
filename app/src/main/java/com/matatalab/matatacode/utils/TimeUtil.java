package com.matatalab.matatacode.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {

    /**
     * 获取当前时间，秒数
     *
     * @return
     */
    public static long getNowTimeSecond() {
        return (System.currentTimeMillis() / 1000);
    }

    /**
     * 获取当前时间，返回yyyy-MM-dd HH:mm:ss格式
     *
     * @return
     */
    public static String getNowTime() {
        return getDateandSecondFromMillisecond(System.currentTimeMillis());
    }

    /**
     * 获取当前时间，返回yyyy-MM-dd HH:mm:ss.SSS格式
     *
     * @return
     */
    public static String getNowTimeMills() {
        try {
            return getDateandMillisecondFromMillisecond(System.currentTimeMillis());
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 获取当前时间，返回yyyy-MM-dd_HH_mm_ss_SSS格式
     *
     * @return
     */
    public static String getNowTimeMillsLink() {
        try {
            return getDateandMillisecondFromMillisecondLink(System.currentTimeMillis());
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 将毫秒换为年月日 时分
     *
     * @param time
     * @return
     */

    public static String getDateandMinuteFromMillisecond(Long time) {
        if (null == time) {
            return null;
        }
        final SimpleDateFormat df = yyyy_MM_dd_HH_mmTimeFormat.get();
        return df.format(time);
    }

    /**
     * 将毫秒换为年月日 时分
     *
     * @param time
     * @return
     */

    public static String getDateAndMinuteFromMillisecondText(Long time) {
        if (null == time) {
            return null;
        }
        final SimpleDateFormat df = yyyyMMddHH_mmTimeFormat.get();
        return df.format(time);
    }

    /**
     * 将毫秒换为年月日 时分
     *
     * @param timeStr
     * @return
     */

    public static Long getMillisecondFromDateandMinute(String timeStr) {
        Long timeSecond = 0L;
        if (null == timeStr) {
            return timeSecond;
        }
        final SimpleDateFormat df = yyyy_MM_dd_HH_mmTimeFormat.get();
        try {
            Date date = df.parse(timeStr);
            timeSecond = date.getTime();
            return timeSecond;
        } catch (ParseException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            return timeSecond;
        }
    }

    /**
     * 将毫秒换为年月日 时分秒
     *
     * @param time
     * @return
     */

    public static String getDateandSecondFromMillisecond(Long time) {
        if (null == time) {
            return null;
        }
        final SimpleDateFormat df = yyyy_MM_dd_HH_mm_ssTimeFormat.get();
        return df.format(time);
    }

    /**
     * 将毫秒换为年月日 时分秒
     *
     * @param time
     * @return
     */

    public static String getDateandMillisecondFromMillisecond(Long time) {
        if (null == time) {
            return null;
        }
        final SimpleDateFormat df = yyyy_MM_dd_HH_mm_ssSSSTimeFormat.get();
        return df.format(time);
    }

    /**
     * 将毫秒换为年月日 时分秒毫秒，下划线连接起来
     *
     * @param time
     * @return
     */

    public static String getDateandMillisecondFromMillisecondLink(Long time) {
        if (null == time) {
            return null;
        }
        final SimpleDateFormat df = yyyy_MM_dd_HH_mm_ssSSSTimeFormatLink.get();
        return df.format(time);
    }

    /**
     * 将毫秒换算为年月
     */
    public static String getDateFromMillisecondYM(Long time) {
        if (null == time) {
            return null;
        }
        final SimpleDateFormat df = yyyyMMTimeFormat.get();
        return df.format(time);
    }

    /**
     * 将毫秒换算为年月日
     */
    public static String getDateFromMillisecond(Long time) {
        if (null == time) {
            return null;
        }
        final SimpleDateFormat df = yyyy_MM_ddTimeFormat.get();
        return df.format(time);
    }

    /**
     * 将毫秒换算为年月日
     */
    public static String getDateFromMillisecondText(Long time) {
        if (null == time) {
            return null;
        }
        final SimpleDateFormat df = otherYearTimeFormat.get();
        return df.format(time);
    }

    public static String getDateFromMillisecondMD(Long time) {
        if (null == time) {
            return null;
        }
        final SimpleDateFormat df = MM_ddTimeFormat.get();
        return df.format(time);
    }

    /**
     * 将秒转换成 时：分：秒的显示方式
     *
     * @param time
     * @return
     */
    public static String getHMSDataFromMillisecondFull(long time) {
        time = time / 1000; // 转换成秒为单位
        // 先得到最大的小时数
        int hour = (int) (time / (60f * 60f));
        // 得到剩下时间的分钟数
        int minute = (int) ((time - hour * 60 * 60) / 60.f);
        // 得到最后剩下的秒
        int second = (int) (time - hour * 60 * 60 - minute * 60);

        if (hour > 0) {
            return String.format("%02d:%02d:%02d", hour, minute, second);
        } else {
            if (minute > 0) {
                return String.format("%02d:%02d", minute, second);
            } else {
                return String.format("00:%02d", second);
            }
        }
    }

    /**
     * 判断改时间是否在当天
     *
     * @param time
     * @return
     */
    public static boolean isInThisDay(long time) {
        return getDayFromCurrent(time) == 0;
    }

    /**
     * 判断该时间是否在这个星期内
     *
     * @param time
     * @return
     */
    public static boolean isInThisWeek(long time) {
        if (getDayFromCurrent(time) < 7) {
            return true;
        }
        return false;
    }

    /**
     * 判断该时间是否在制定的周期内
     *
     * @param time
     * @param period
     * @return
     */
    public static boolean isInThePeriod(long time, int period) {
        if (getDayFromCurrent(time) < period) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否在一个月以前
     *
     * @param time
     * @return
     */
    public static boolean beforeAMonth(long time) {
        if (getDayFromCurrent(time) > 30) {
            return true;
        }
        return false;
    }

    /**
     * 得到与time距离的时间，因为getTruncateTimeToday()比较耗时，为大量调用这个方法的函数做优化 0为今天以内，n 大于 0
     * 即表示n天前
     *
     * @param time
     * @param todayTime
     * @return
     */
    public static int getDayFromCurrent(long time, long todayTime) {
        if (time - todayTime > 0) {
            return 0;
        }

        long dayTime = 1000 * 60 * 60 * 24;
        long deltaTime = todayTime - time;
        return (int) (deltaTime / (float) dayTime) + 1;
    }

    /**
     * 得到与time距离的时间 0为今天以内，n 大于 0 即表示n天前
     *
     * @param time
     * @return
     */
    public static int getDayFromCurrent(long time) {
        long todayTime = getTruncateTimeToday();
        if (time - todayTime > 0) {
            return 0;
        }

        long dayTime = 1000 * 60 * 60 * 24;
        long deltaTime = todayTime - time;
        return (int) (deltaTime / (float) dayTime) + 1;
    }

    /**
     * get current day formate is yyyyMMdd
     *
     * @return
     */
    public static String getCurrentDay() {
        Date dt = new Date();
        SimpleDateFormat df = yyyyMMddTimeFormat.get();
        String timestamp = df.format(dt);
        return timestamp;
    }

    public static String getCurrentDayLogTest() {
        return "launche_time_log_" + TimeUtil.getCurrentDay() + ".txt";
    }

    /**
     * 得到今天0点0时0分0秒0毫秒的时间值
     *
     * @return
     */
    public static long getTruncateTimeToday() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime();
    }

    public static long getTimeBeforeDays(int days) {
        long currentTime = System.currentTimeMillis();
        long resultTime = currentTime - 1000l * 60 * 60 * 24 * days;
        if (resultTime < 0) {
            return 0;
        }
        return resultTime;
    }

    // /**
    // * 日期格式化。今天的显示格式
    // */
    // private static ThreadLocal<SimpleDateFormat> todayTimeFormat =new
    // ThreadLocal<SimpleDateFormat>(){
    // @Override
    // protected SimpleDateFormat initialValue()
    // {
    // return new SimpleDateFormat("今天 HH:mm");
    // }
    // };

    private static ThreadLocal<SimpleDateFormat> currentYearTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM月dd日");
        }
    };

    private static ThreadLocal<SimpleDateFormat> otherYearTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yy年MM月dd日");
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyyMMTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMM");
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyyMMddTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyy_MM_ddTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private static ThreadLocal<SimpleDateFormat> MM_ddTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM-dd");
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyy_MM_dd_HH_mmTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyyMMddHH_mmTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyy_MM_dd_HH_mm_ssTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyy_MM_dd_HH_mm_ssSSSTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        }
    };

    private static ThreadLocal<SimpleDateFormat> onlyYearTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy");
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyy_MM_dd_HH_mm_ssSSSTimeFormatLink = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss_SSS");
        }
    };

    /**
     * 时间显示,基准时间为本地时间
     *
     * @param time 时间戳ms
     * @return
     */
    public static String getTimeDisplay(long time) {
        if (time == 0) {
            return "";
        }
        long now = System.currentTimeMillis();

        return TimeToDisplay(now, time);
    }

    /**
     * 时间显示转换
     *
     * @param now  当前基准时间
     * @param time 需要显示的时间
     * @return
     */
    private static String TimeToDisplay(long now, long time) {

        long timeInterval = now - time;
        // 时间显示规则：
        // 1分钟以内：刚刚
        // 1小时内：XX分钟前
        // 24小时内： XX小时前
        // 一年内：X月X日
        // 大于一年：XX年X月X日
        if (timeInterval < 60 * 1000) {
            return "刚刚";
        } else if (timeInterval < 60 * 60 * 1000) {
            return (timeInterval / (60 * 1000)) + "分钟前";
        }

        if (timeInterval < 24 * 60 * 60 * 1000) {
            return (timeInterval / (60 * 60 * 1000)) + "小时前";
        }

        Date date = new Date(now);
        Date saveDate = new Date(time);

        // if(date.getYear()==saveDate.getYear() &&
        // date.getMonth()==saveDate.getMonth() &&
        // date.getDate()==saveDate.getDate())
        // {
        // return todayTimeFormat.get().format(saveDate);
        // }
        if (date.getYear() == saveDate.getYear()) {
            // return currentYearTimeFormat.get().format(saveDate);
            return MM_ddTimeFormat.get().format(saveDate);

        }

        // return otherYearTimeFormat.get().format(saveDate);
        return yyyy_MM_ddTimeFormat.get().format(saveDate);
    }

    /**
     * 获取time与当前时间相隔的天数
     *
     * @param time
     * @return
     */
    public static long getDifferDay(long time) {
        long currentTime = System.currentTimeMillis();
        long differ = currentTime - time;
        return differ / (1000 * 60 * 60 * 24);
    }

    public static void main(String[] args) {
        System.out.println(getTimeDisplay(System.currentTimeMillis() - 3 * 1000));
        System.out.println(getTimeDisplay(System.currentTimeMillis() - 70 * 1000));
        System.out.println(getTimeDisplay(System.currentTimeMillis() - 3600 * 1000 * 15));
        System.out.println(getTimeDisplay(System.currentTimeMillis() - 3600l * 1000 * 24 * 35));
        System.out.println(getTimeDisplay(System.currentTimeMillis() - 3600l * 1000 * 24 * 456));
    }

    /**
     * 时间显示,只显示月日
     *
     * @param time 时间戳ms
     * @return
     */
    public static String getTimeExceptYear(long time) {
        if (time == 0) {
            return "";
        }
        return MM_ddTimeFormat.get().format(time);
    }

    /**
     * 时间显示,只显示月日
     *
     * @param time 时间戳ms
     * @return
     */
    public static String getTimeExceptYearChat(long time) {
        if (time == 0) {
            return "";
        }
        return currentYearTimeFormat.get().format(time);
    }

}