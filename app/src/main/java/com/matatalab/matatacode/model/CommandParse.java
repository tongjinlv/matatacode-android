package com.matatalab.matatacode.model;

import android.text.TextUtils;

import com.matatalab.matatacode.interfaces.CommandParseInterface;
import com.matatalab.matatacode.utils.MLog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hardy
 * @name MatataCode
 * @class name：com.matatalab.matatacode.model
 * @class describe:
 * @time 2019/9/28 17:16
 * @change
 * @chang time
 * @class describe
 */
public class CommandParse implements CommandParseInterface {
    private static final String TAG = CommandParse.class.getSimpleName();

    //    private ArrayList<Integer> expression = new ArrayList<>();
//    private StringBuilder expression = new StringBuilder();
    private List<Byte> mCommandBuilder = new ArrayList<Byte>();

    private volatile static CommandParseInterface mControlPresenter = null;

    private CommandParse() {
        MLog.d(TAG, "--- CommandParse ---");
    }

    /**
     * 单例
     *
     * @return
     */
    public static CommandParseInterface getInstance() {
        if (mControlPresenter == null) {
            synchronized (CommandParse.class) {
                if (mControlPresenter == null) {
                    mControlPresenter = new CommandParse();
                }
            }
        }
        return mControlPresenter;
    }


    /**
     * 获取停止蓝牙命令
     *
     * @return
     */
    @Override
    public byte[] getStopBtCommand() {
        MLog.d(TAG, "--- getStopBtCommand ---");
        mCommandBuilder.clear();
        mCommandBuilder.add((byte) 132);
        byte[] bytes = getByteArrayFromByteList(mCommandBuilder);
        MLog.d(TAG, "getStopBtCommand --- bytes =", bytes);
        return bytes;
    }

    /**
     * 获取小车进入dfu蓝牙命令
     *
     * @return
     */
    @Override
    public byte[] getBotDfuBtCommand() {
        MLog.d(TAG, "--- getBotDfuBtCommand ---");
        byte[] dfuCommand = {(byte) 0x80, (byte) 0xaa, (byte) 0x55, (byte) 0x81, (byte) 0x00};

        MLog.d(TAG, "getBotDfuBtCommand --- dfuCommand =", dfuCommand);
        return dfuCommand;
    }

    /**
     * 获取控制台进入dfu蓝牙命令
     *
     * @return
     */
    @Override
    public byte[] getControllerDfuBtCommand() {
        MLog.d(TAG, "--- getControllerDfuBtCommand ---");
        byte[] dfuCommand = {(byte) 0x80, (byte) 0xaa, (byte) 0x55, (byte) 0x13};

        MLog.d(TAG, "getControllerDfuBtCommand --- dfuCommand =", dfuCommand);
        return dfuCommand;
    }

    /**
     * byte 列表转数组
     *
     * @param byteList
     * @return
     */
    private byte[] getByteArrayFromByteList(List<Byte> byteList) {
        if ((byteList == null) || (byteList.size() == 0)) {
            MLog.e(TAG, "getByteArrayFromByteList --- byteList is empty!");
            return null;
        }
        byte[] bytes = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            bytes[i] = (byteList.get(i));
        }
        return bytes;
    }

    /**
     * 解析Js消息，转换成蓝牙命令
     * 转换协议：https://shimo.im/sheets/KjW9yHdYkP69xHgP/MODOC/
     *
     * @param commandStr ：js发送过来的命令参数
     * @return ：蓝牙命令二进制数组
     */
    @Override
    public byte[] parseRunCodeToBtCommand(String commandStr) {
        MLog.d(TAG, "parseRunCodeToBtCommand --- commandStr = " + commandStr);
        mCommandBuilder.clear();
        if (TextUtils.isEmpty(commandStr) == true) {
            MLog.e(TAG, "parseRunCodeToBtCommand --- message is empty!");
            return null;
        }

        String[] msgArray = commandStr.split("_");

        if ((msgArray == null) || (msgArray.length == 0)) {
            MLog.e(TAG, "parseRunCodeToBtCommand --- msgArray is empty!");
            return null;
        }

        for (int i = 0; i < msgArray.length; i++) {
            MLog.d(TAG, "parseRunCodeToBtCommand --- msgArray[" + i + "] = " + msgArray[i]);
        }


        int tone = 1;
        int meter = 1;

        int angle = 90;
        boolean step = true;

        int metho = 1; //1-all 2-plus 3-minus
        int color = 1;
        int level = 1;

        int data = 1;


        switch (msgArray[0]) {
            case "moveforward":
                switch (msgArray[msgArray.length - 1]) {
                    case "1":
                        moveForward();
                        break;
                    case "2":
                        moveForward(2);
                        break;
                    case "3":
                        moveForward(3);
                        break;
                    case "4":
                        moveForward(4);
                        break;
                    case "5":
                        moveForward(5);
                        break;
                    case "6":
                        moveForward(6);
                        break;
                    default:
                        moveForward();
                        break;
                }
                break;

            case "movebackward":
                switch (msgArray[msgArray.length - 1]) {
                    case "1":
                        moveBackward();
                        break;
                    case "2":
                        moveBackward(2);
                        break;
                    case "3":
                        moveBackward(3);
                        break;
                    case "4":
                        moveBackward(4);
                        break;
                    case "5":
                        moveBackward(5);
                        break;
                    case "6":
                        moveBackward(6);
                        break;
                    default:
                        moveBackward();
                        break;
                }
                break;


            case "wait":
                switch (msgArray[msgArray.length - 1]) {
                    case "1":
                        wait(1);
                        break;
                    case "2":
                        wait(2);
                        break;
                    case "3":
                        wait(3);
                        break;
                    case "4":
                        wait(4);
                        break;
                    case "5":
                        wait(5);
                        break;
                    case "6":
                        wait(6);
                        break;
                    case "7":
                        wait(7);
                        break;
                    case "8":
                        wait(8);
                        break;
                    case "9":
                        wait(9);
                        break;
                    case "10":
                        wait(10);
                        break;
                    default:
                        wait(1);
                        break;
                }
                break;

            case "turnleft":
                switch (msgArray[msgArray.length - 1]) {
                    case "30degree":
                        turnLeft(30);
                        break;
                    case "36degree":
                        turnLeft(36);
                        break;
                    case "45degree":
                        turnLeft(45);
                        break;
                    case "60degree":
                        turnLeft(60);
                        break;
                    case "72degree":
                        turnLeft(72);
                        break;
                    case "90degree":
                        turnLeft();
                        break;
                    case "108degree":
                        turnLeft(108);
                        break;
                    case "120degree":
                        turnLeft(120);
                        break;
                    case "135degree":
                        turnLeft(135);
                        break;
                    case "144degree":
                        turnLeft(144);
                        break;
                    case "150degree":
                        turnLeft(150);
                        break;
                    default:
                        turnLeft();
                        break;
                }
                break;

            case "turnright":
                switch (msgArray[msgArray.length - 1]) {
                    case "30degree":
                        turnRight(30);
                        break;
                    case "36degree":
                        turnRight(36);
                        break;
                    case "45degree":
                        turnRight(45);
                        break;
                    case "60degree":
                        turnRight(60);
                        break;
                    case "72degree":
                        turnRight(72);
                        break;
                    case "90degree":
                        turnRight();
                        break;
                    case "108degree":
                        turnRight(108);
                        break;
                    case "120degree":
                        turnRight(120);
                        break;
                    case "135degree":
                        turnRight(135);
                        break;
                    case "144degree":
                        turnRight(144);
                        break;
                    case "150degree":
                        turnRight(150);
                        break;
                    default:
                        turnRight();
                        break;
                }
                break;

            case "doaction":
                switch (msgArray[msgArray.length - 1]) {
                    case "action1":
                        doAction(1);
                        break;
                    case "action2":
                        doAction(2);
                        break;
                    case "action3":
                        doAction(3);
                        break;
                    case "action4":
                        doAction(4);
                        break;
                    case "action5":
                        doAction(5);
                        break;
                    case "action6":
                        doAction(6);
                        break;
                    default:
                        doAction(1);
                        break;
                }
                break;

            case "dodance":
                switch (msgArray[msgArray.length - 1]) {
                    case "dance1":
                        doDance(1);
                        break;
                    case "dance2":
                        doDance(2);
                        break;
                    case "dance3":
                        doDance(3);
                        break;
                    case "dance4":
                        doDance(4);
                        break;
                    case "dance5":
                        doDance(5);
                        break;
                    case "dance6":
                        doDance(6);
                        break;
                    default:
                        doDance(1);
                        break;
                }
                break;

            case "playmusic":
                switch (msgArray[msgArray.length - 1]) {
                    case "music1":
                        playMusic(1);
                        break;
                    case "music2":
                        playMusic(2);
                        break;
                    case "music3":
                        playMusic(3);
                        break;
                    case "music4":
                        playMusic(4);
                        break;
                    case "music5":
                        playMusic(5);
                        break;
                    case "music6":
                        playMusic(6);
                        break;
                    default:
                        playMusic(1);
                        break;
                }
                break;

            case "optimizeline":
                switch (msgArray[msgArray.length - 1]) {
                    case "true":
                        adjustmentStraight(true);
                        break;
                    case "false":
                        adjustmentStraight(false);
                        break;
                    default:
                        adjustmentStraight(true);
                        break;
                }
                break;

            case "playmelody":
                switch (msgArray[msgArray.length - 1]) {
                    case "melody1":
                        playMelody(1);
                        break;
                    case "melody2":
                        playMelody(2);
                        break;
                    case "melody3":
                        playMelody(3);
                        break;
                    case "melody4":
                        playMelody(4);
                        break;
                    case "melody5":
                        playMelody(5);
                        break;
                    case "melody6":
                        playMelody(6);
                        break;
                    case "melody7":
                        playMelody(7);
                        break;
                    case "melody8":
                        playMelody(8);
                        break;
                    case "melody9":
                        playMelody(9);
                        break;
                    case "melody10":
                        playMelody(10);
                        break;
                    default:
                        playMelody(1);
                        break;
                }
                break;


            case "playalto":
                tone = 1;
                meter = 1;
                switch (msgArray[1]) {
                    case "do":
                        tone = 1;
                        break;
                    case "re":
                        tone = 2;
                        break;
                    case "mi":
                        tone = 3;
                        break;
                    case "fa":
                        tone = 4;
                        break;
                    case "sol":
                        tone = 5;
                        break;
                    case "la":
                        tone = 6;
                        break;
                    case "ti":
                        tone = 7;
                        break;
                    default:
                        tone = 1;
                        break;
                }
                switch (msgArray[msgArray.length - 1]) {
                    case "meter1":
                        meter = 1;
                        break;
                    case "meter2":
                        meter = 2;
                        break;
                    case "meter3":
                        meter = 3;
                        break;
                    case "meter4":
                        meter = 4;
                        break;
                    case "meter5":
                        meter = 5;
                        break;
                    case "meter6":
                        meter = 6;
                        break;
                    default:
                        meter = 1;
                        break;
                }
                playAlto(tone, meter);
                break;

            case "playtreble":
                tone = 1;
                meter = 1;
                switch (msgArray[1]) {
                    case "do":
                        tone = 1;
                        break;
                    case "re":
                        tone = 2;
                        break;
                    case "mi":
                        tone = 3;
                        break;
                    case "fa":
                        tone = 4;
                        break;
                    case "sol":
                        tone = 5;
                        break;
                    case "la":
                        tone = 6;
                        break;
                    case "ti":
                        tone = 7;
                        break;
                    default:
                        tone = 1;
                        break;
                }
                switch (msgArray[msgArray.length - 1]) {
                    case "meter1":
                        meter = 1;
                        break;
                    case "meter2":
                        meter = 2;
                        break;
                    case "meter3":
                        meter = 3;
                        break;
                    case "meter4":
                        meter = 4;
                        break;
                    case "meter5":
                        meter = 5;
                        break;
                    case "meter6":
                        meter = 6;
                        break;
                    default:
                        meter = 1;
                        break;
                }
                playTreble(tone, meter);
                break;

            case "optimizeright":
                angle = 90;
                step = true;
                switch (msgArray[1]) {
                    case "30degree":
                        angle = 30;
                        break;
                    case "36degree":
                        angle = 36;
                        break;
                    case "45degree":
                        angle = 45;
                        break;
                    case "60degree":
                        angle = 60;
                        break;
                    case "72degree":
                        angle = 72;
                        break;
                    case "90degree":
                        angle = 90;
                        break;
                    case "108degree":
                        angle = 108;
                        break;
                    case "120degree":
                        angle = 120;
                        break;
                    case "135degree":
                        angle = 135;
                        break;
                    case "144degree":
                        angle = 144;
                        break;
                    case "150degree":
                        angle = 150;
                        break;
                    default:
                        angle = 90;
                        break;
                }
                switch (msgArray[msgArray.length - 1]) {
                    case "true":
                        step = true;
                        break;
                    case "false":
                        step = false;
                        break;
                    default:
                        step = true;
                        break;
                }
                adjustmentTurn(Dircetion.right, angle, step);
                break;

            case "optimizeleft":
                angle = 90;
                step = true;
                switch (msgArray[1]) {
                    case "30degree":
                        angle = 30;
                        break;
                    case "36degree":
                        angle = 36;
                        break;
                    case "45degree":
                        angle = 45;
                        break;
                    case "60degree":
                        angle = 60;
                        break;
                    case "72degree":
                        angle = 72;
                        break;
                    case "90degree":
                        angle = 90;
                        break;
                    case "108degree":
                        angle = 108;
                        break;
                    case "120degree":
                        angle = 120;
                        break;
                    case "135degree":
                        angle = 135;
                        break;
                    case "144degree":
                        angle = 144;
                        break;
                    case "150degree":
                        angle = 150;
                        break;
                    default:
                        angle = 90;
                        break;
                }
                switch (msgArray[msgArray.length - 1]) {
                    case "true":
                        step = true;
                        break;
                    case "false":
                        step = false;
                        break;
                    default:
                        step = true;
                        break;
                }
                adjustmentTurn(Dircetion.left, angle, step);
                break;

            case "eyeshowscolor":
                /*下位机未实现*/
                if (msgArray.length == 4) {
                    setBothEye(Integer.parseInt(msgArray[2]),
                            Integer.parseInt(msgArray[1]),
                            Integer.parseInt(msgArray[3]));
                } else {
                    String colorStr = msgArray[msgArray.length - 1];
                    setBothEye(colorStr);
                }
                break;


            //--------2019年09月06日16:02:17 新增协议 sensor add on

            case "wheel":
                int lspeed = 15;
                int rspeed = 15;
                switch (msgArray[1]) {
                    case "0":
                        lspeed = 0;
                        break;
                    case "1":
                        lspeed = 1;
                        break;
                    case "2":
                        lspeed = 2;
                        break;
                    case "3":
                        lspeed = 3;
                        break;
                    case "4":
                        lspeed = 4;
                        break;
                    case "5":
                        lspeed = 5;
                        break;
                    case "6":
                        lspeed = 6;
                        break;
                    case "7":
                        lspeed = 7;
                        break;
                    case "8":
                        lspeed = 8;
                        break;
                    case "9":
                        lspeed = 9;
                        break;
                    case "10":
                        lspeed = 10;
                        break;
                    case "11":
                        lspeed = 11;
                        break;
                    case "12":
                        lspeed = 12;
                        break;
                    default:
                        lspeed = 15;
                        break;
                }
                switch (msgArray[2]) {
                    case "0":
                        rspeed = 0;
                        break;
                    case "1":
                        rspeed = 1;
                        break;
                    case "2":
                        rspeed = 2;
                        break;
                    case "3":
                        rspeed = 3;
                        break;
                    case "4":
                        rspeed = 4;
                        break;
                    case "5":
                        rspeed = 5;
                        break;
                    case "6":
                        rspeed = 6;
                        break;
                    case "7":
                        rspeed = 7;
                        break;
                    case "8":
                        rspeed = 8;
                        break;
                    case "9":
                        rspeed = 9;
                        break;
                    case "10":
                        rspeed = 10;
                        break;
                    case "11":
                        rspeed = 11;
                        break;
                    case "12":
                        rspeed = 12;
                        break;
                    default:
                        rspeed = 15;
                        break;
                }
                setWheels(lspeed, rspeed);
                break;

            case "sensorwaitdata":
                data = 1;
                switch (msgArray[1]) {
                    case "1":
                        data = 1;
                        break;
                    case "2":
                        data = 2;
                        break;
                    case "3":
                        data = 3;
                        break;
                    case "4":
                        data = 4;
                        break;
                    case "5":
                        data = 5;
                        break;
                    case "6":
                        data = 6;
                        break;
                    default:
                        data = 1;
                        break;
                }
                setSensorWaitData(data);
                break;

            case "sensorsenddata":
                data = 1;
                switch (msgArray[1]) {
                    case "1":
                        data = 1;
                        break;
                    case "2":
                        data = 2;
                        break;
                    case "3":
                        data = 3;
                        break;
                    case "4":
                        data = 4;
                        break;
                    case "5":
                        data = 5;
                        break;
                    case "6":
                        data = 6;
                        break;
                    default:
                        data = 1;
                        break;
                }
                setSensorSendData(data);
                break;

            case "sensorwait":
                int condition = 1;
                switch (msgArray[1]) {
                    case "1":
                        condition = 1;
                        break;
                    case "2":
                        condition = 2;
                        break;
                    case "3":
                        condition = 3;
                        break;
                    case "4":
                        condition = 4;
                        break;
                    case "5":
                        condition = 5;
                        break;
                    case "6":
                        condition = 6;
                        break;
                    case "7":
                        condition = 7;
                        break;
                    case "8":
                        condition = 8;
                        break;
                    case "9":
                        condition = 9;
                        break;
                    case "10":
                        condition = 10;
                        break;
                    default:
                        condition = 1;
                        break;
                }
                setSensorWaitCondition(condition);
                break;

            //sensorled matataboteye
            case "sensorled":
                metho = 1; //1-all 2-plus 3-minus
                color = 1;
                level = 1;
                switch (msgArray[1]) {
                    case "all":
                        metho = 1;
                        break;
                    case "plus":
                        metho = 2;
                        break;
                    case "minus":
                        metho = 3;
                        break;
                    default:
                        metho = 1;
                        break;
                }
                switch (msgArray[2]) {
                    case "1":
                        color = 1;
                        break;
                    case "2":
                        color = 2;
                        break;
                    case "3":
                        color = 3;
                        break;
                    case "4":
                        color = 4;
                        break;
                    case "5":
                        color = 5;
                        break;
                    case "6":
                        color = 6;
                        break;
                    case "7":
                        color = 7;
                        break;
                    default:
                        color = 1;
                        break;
                }
                switch (msgArray[3]) {
                    case "1":
                        level = 1;
                        break;
                    case "2":
                        level = 2;
                        break;
                    case "3":
                        level = 3;
                        break;
                    case "4":
                        level = 4;
                        break;
                    case "5":
                        level = 5;
                        break;
                    case "6":
                        level = 6;
                        break;
                    default:
                        level = 1;
                        break;
                }
                setSensorLed(metho, color, level);
                break;

            case "matataboteye":
                int eye = 1;//1-both 2-left 3-right
                color = 1;
                level = 1;
                switch (msgArray[1]) {
                    case "both":
                        eye = 1;
                        break;
                    case "left":
                        eye = 2;
                        break;
                    case "right":
                        eye = 3;
                        break;
                    default:
                        eye = 1;
                        break;
                }
                switch (msgArray[2]) {
                    case "1":
                        color = 1;
                        break;
                    case "2":
                        color = 2;
                        break;
                    case "3":
                        color = 3;
                        break;
                    case "4":
                        color = 4;
                        break;
                    case "5":
                        color = 5;
                        break;
                    case "6":
                        color = 6;
                        break;
                    case "7":
                        color = 7;
                        break;
                    default:
                        color = 1;
                        break;
                }
                switch (msgArray[3]) {
                    case "1":
                        level = 1;
                        break;
                    case "2":
                        level = 2;
                        break;
                    case "3":
                        level = 3;
                        break;
                    case "4":
                        level = 4;
                        break;
                    case "5":
                        level = 5;
                        break;
                    case "6":
                        level = 6;
                        break;
                    default:
                        level = 1;
                        break;
                }
                setBotEye(eye, color, level);
                break;


            default:
                break;
        }

        byte[] bytes = getByteArrayFromByteList(mCommandBuilder);
        MLog.d(TAG, "parseRunCodeToBtCommand --- bytes = ", bytes);
        return bytes;
    }


    //:MARK 基础运动
    private void moveForward() {
        mCommandBuilder.add((byte) 0);
    }

    private void moveForward(int step) {
        mCommandBuilder.add((byte) 32);
        mCommandBuilder.add((byte) step);
    }

    private void moveBackward() {
        mCommandBuilder.add((byte) 3);
    }

    private void moveBackward(int step) {
        mCommandBuilder.add((byte) 35);
        mCommandBuilder.add((byte) step);
    }

    private void turnLeft() {
        mCommandBuilder.add((byte) 1);
    }

    private void turnRight() {
        mCommandBuilder.add((byte) 2);
    }

    private void turnLeft(int angle) {
        mCommandBuilder.add((byte) 33);
        mCommandBuilder.add((byte) angle);
    }

    private void turnRight(int angle) {
        mCommandBuilder.add((byte) 34);
        mCommandBuilder.add((byte) angle);
    }

    private void doAction(int action) {
        mCommandBuilder.add((byte) 59);
        int value = action;
        if (value <= 1) {
            value = 1;
        }
        if (value >= 6) {
            value = 6;
        }
        mCommandBuilder.add((byte) value);
    }

    //:MARK 艺术模块
    private void doDance(int dance) {
        mCommandBuilder.add((byte) 58);
        int value = dance;
        if (value <= 1) {
            value = 1;
        }
        if (value >= 6) {
            value = 6;
        }
        mCommandBuilder.add((byte) value);

    }


    private void playMusic(int music) {
        mCommandBuilder.add((byte) 57);
        int value = music;
        if (value <= 1) {
            value = 1;
        }
        if (value >= 6) {
            value = 6;
        }
        mCommandBuilder.add((byte) value);
    }

    private void playMelody(int music) {
        mCommandBuilder.add((byte) 84);
        int value = music;
        if (value <= 1) {
            value = 1;
        }
        if (value >= 10) {
            value = 10;
        }
        mCommandBuilder.add((byte) value);
    }

    private void playAlto(int alto, int meter) {
        mCommandBuilder.add((byte) 112);
        int value = alto;
        if (value <= 1) {
            value = 1;
        }
        if (value >= 7) {
            value = 7;
        }
        mCommandBuilder.add((byte) value);
        int value_m = meter;
        if (value_m <= 1) {
            value_m = 1;
        }
        if (value_m >= 6) {
            value_m = 6;
        }
        mCommandBuilder.add((byte) value_m);
    }

    private void playAlto(int alto) {
        mCommandBuilder.add((byte) 80);
        int value = alto;
        if (value <= 1) {
            value = 1;
        }
        if (value >= 7) {
            value = 7;
        }
        mCommandBuilder.add((byte) value);
    }

    private void playTreble(int treble, int meter) {
        mCommandBuilder.add((byte) 113);
        int value = treble;
        if (value <= 1) {
            value = 1;
        }
        if (value >= 7) {
            value = 7;
        }
        mCommandBuilder.add((byte) value);
        int value_m = meter;
        if (value_m <= 1) {
            value_m = 1;
        }
        if (value_m >= 6) {
            value_m = 6;
        }
        mCommandBuilder.add((byte) value_m);
    }

    private void playTreble(int treble) {
        mCommandBuilder.add((byte) 81);
        int value = treble;
        if (value <= 1) {
            value = 1;
        }
        if (value >= 7) {
            value = 7;
        }
        mCommandBuilder.add((byte) value);
    }

    //:MARK 控制
    private void stop() {
        mCommandBuilder.add((byte) 132);
    }

    private void start() {
        mCommandBuilder.add((byte) 133);
    }

    private void wait(int second) {
        mCommandBuilder.add((byte) 62);
        mCommandBuilder.add((byte) second);
    }

    //        private void setLeftEye(r: Int, g: Int, b: Int){
//            mCommandBuilder.add((byte) 132)
//        }
    //        private void setRightEye(r: Int, g: Int, b: Int){
    //            mCommandBuilder.add((byte) 132)
    //        }
    private void setBothEye(String color_str) {
        MLog.d(TAG, "setBothEye --- color_str = " + color_str);
        //使用单指令的工厂模式，待协议更新后适配
        //80 aa 55 06 03 ff ff ff
        mCommandBuilder.add((byte) 128);
        mCommandBuilder.add((byte) 170);
        mCommandBuilder.add((byte) 85);
        mCommandBuilder.add((byte) 6);
        mCommandBuilder.add((byte) 3);

        // 待处理 2019-9-28
        byte[] color_ar = color_str.getBytes();
        mCommandBuilder.add(color_ar[1]);
        mCommandBuilder.add(color_ar[0]);
        mCommandBuilder.add(color_ar[2]);

        MLog.d(TAG, "setBothEye --- color_ar = ", color_ar);
    }

    private void setBothEye(int eye, int color, int level) {
        //使用单指令的工厂模式，待协议更新后适配
        //左右眼单控未实现
        mCommandBuilder.add((byte) 61);
        int data = level * 16 + color;
        MLog.d(TAG, "setBothEye --- data = " + data);
        mCommandBuilder.add((byte) data);
    }

    private void adjustmentTurn(int direction, int angle, boolean step) {
        if (direction == Dircetion.left) {
            mCommandBuilder.add((byte) 97);
        } else if (direction == Dircetion.right) {
            mCommandBuilder.add((byte) 98);
        }
        mCommandBuilder.add((byte) angle);
        if (step) {
            mCommandBuilder.add((byte) 0);
        } else {
            mCommandBuilder.add((byte) 1);
        }
    }

    private void adjustmentStraight(boolean step) {
        mCommandBuilder.add((byte) 96);
        mCommandBuilder.add((byte) 1);
        if (step) {
            mCommandBuilder.add((byte) 0);
        } else {
            mCommandBuilder.add((byte) 1);
        }
    }
    //-------2019年09月06日10:29:20 sensor add on新增协议--gty--重做左右眼

    private void setBotEye(int which, int color, int level) {
        mCommandBuilder.add((byte) 100);
        int data_color;
        data_color = (color - 1) * 16 + level;
        mCommandBuilder.add((byte) data_color);
        switch (which) {
            case 1:
                mCommandBuilder.add((byte) 11);
                break;
            case 2:
                mCommandBuilder.add((byte) 5);
                break;
            case 3:
                mCommandBuilder.add((byte) 6);
                break;
            default:
                mCommandBuilder.add((byte) 11);
                break;
        }
    }

    private void setSensorLed(int metho, int color, int level) {
        mCommandBuilder.add((byte) 104);
        ;
        mCommandBuilder.add((byte) metho);
        int data_color;
        data_color = (color - 1) * 16 + level;
        mCommandBuilder.add((byte) data_color);
    }

    private void setWheels(int lspeed, int rspeed) {
        mCommandBuilder.add((byte) 100);
        int data_speed;
        data_speed = lspeed * 16 + rspeed;
        mCommandBuilder.add((byte) data_speed);
        mCommandBuilder.add((byte) 1);
    }

    private void setSensorWaitCondition(int condition) {
        mCommandBuilder.add((byte) 102);
        mCommandBuilder.add((byte) 16);
        mCommandBuilder.add((byte) condition);
    }

    private void setSensorSendData(int data) {
        mCommandBuilder.add((byte) 103);
        mCommandBuilder.add((byte) 1);
        mCommandBuilder.add((byte) data);
    }

    private void setSensorWaitData(int data) {
        mCommandBuilder.add((byte) 102);
        mCommandBuilder.add((byte) 16);
        mCommandBuilder.add((byte) (data + 10));
    }

    protected class Dircetion {
        public static final int left = 0;
        public static final int right = 1;
        public static final int line = 2;
    }
}
