/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.spravka42.paShoot;

import android.annotation.SuppressLint;

/**
 *
 * @author alex
 */
@SuppressLint("DefaultLocale")
public class Axis {
    
    public static final int STATUS_STOPPED = 0;
    public static final int STATUS_RUNNING = 1;
    public static final int COMPLETE_TURN = 942803;
    public static final int START_POINT = 8388608;
    
    private int index;
    private BlueTooth device;
    
    public Axis(int index, BlueTooth device) {
        this.index = index;
        this.device = device;
    }
    
    public String sendSimpleCommand(String cmd) {
        return device.sendCommand(cmd+index);
    }
    
    public String sendSimpleCommand(String cmd, String opts) {
        return device.sendCommand(cmd+index+opts);
    }
    
    public String init() {
        String response = "";
        response += sendSimpleCommand("F");
        response += "$" + sendSimpleCommand("a");
        response += "$" + sendSimpleCommand("D");
        return response;
    }
    
    public String readPos() {
        return sendSimpleCommand("j");
    }
    
    public String readStatus() {
        return sendSimpleCommand("f");
    }
    
    public boolean isStopped() {
        String status = readStatus();
        if (status.matches("\\d0\\d")) {
            return true;
        } else {
            return false;
        }
    }
    
    public String stopMoving() {
        return sendSimpleCommand("L");
    }
    
//    public String startMoving(int direction) {
//        String response = "";
//        response += sendSimpleCommand("L");
//        response += "$" + device.sendCommand("G"+index+"3"+direction);
//        response += "$" + device.sendCommand("I"+index+Head.SPEED_NORMAL);
//        response += "$" + sendSimpleCommand("J");
//        return response;
//    }
    
    private String convertHexOrder(String str) {
        while (str.length() < 6) str = "0" + str;
        String hexStr = "";
        hexStr += str.substring(4, 6);
        hexStr += str.substring(2, 4);
        hexStr += str.substring(0, 2);
        return hexStr.toUpperCase();
    }
    
    private String angleToPos(float angle) {
        int decPos = (int)(Axis.COMPLETE_TURN / 360.0 * angle + START_POINT);
        String hexPos = Integer.toHexString(decPos);
        String hexPosConv = convertHexOrder(hexPos);
        return hexPosConv;
    }
    
    public String driveTo(float angle) {
        String pos = angleToPos(angle);
        String response = "";
        response += sendSimpleCommand("L");
        response += "$" + sendSimpleCommand("G", "00");
        response += "$" + sendSimpleCommand("S", pos);
        response += "$" + sendSimpleCommand("J");
        return response;
    }
    
    public String setShutter(String state) {
        if (index != Head.AXIS_YAW) return "FAIL: Shutter not on Yaw Axis";
        return device.sendCommand("O"+index+state);
    }
}
