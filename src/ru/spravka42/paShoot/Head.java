/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.spravka42.paShoot;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alex
 */
public class Head {
    
    public static final int INIT_SUCCESS = 1;
    public static final int INIT_FAILED = 0;

    public static final int AXIS_YAW  = 1;
    public static final int AXIS_PITCH = 2;

    private BlueTooth device;
    private Axis yaw;
    private Axis pitch;
    private boolean initialized;
    public Head() {
    
    }
    
    public String init() {
        if (this.initialized) return "ok";
        
        device = new BlueTooth();
        String status = device.connect();
        if (status != "ok") return status;

        yaw = new Axis(AXIS_YAW, device);
        pitch = new Axis(AXIS_PITCH, device);
        
        yaw.init();
        pitch.init();
        
        initialized = true;
        return status;
    }
    
    public int driveTo(float yaw, float pitch) {
        //System.out.println("\rMoving to: "+yaw+" "+pitch);
        this.yaw.driveTo(yaw);
        this.pitch.driveTo(pitch);
        while (true) {
            if (this.yaw.isStopped()&& this.pitch.isStopped()) {
                break;
            } else {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Head.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return 1;
    }
    
    public void shoot() {
        yaw.setShutter("1");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Head.class.getName()).log(Level.SEVERE, null, ex);
        }
        yaw.setShutter("0");
    }
    
    public void stop() {
    	yaw.stopMoving();
    	pitch.stopMoving();
    	device.close();
    }
}
