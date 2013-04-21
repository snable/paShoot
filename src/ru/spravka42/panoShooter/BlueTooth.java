/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.spravka42.panoShooter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
//import android.content.Intent;
import android.bluetooth.BluetoothDevice;
import android.util.Log;


/**
 *
 * @author alex
 */
public class BlueTooth {
	
	private BluetoothAdapter bt; 
	private BluetoothDevice device;
	private BluetoothSocket socket;
	private InputStream in;
	private OutputStream out;
	private Pattern p = Pattern.compile("=(.*)\\r");
	private byte[] buffer = new byte[1024];
	
	public String connect() {
		bt = BluetoothAdapter.getDefaultAdapter();
		if (bt == null) return "no adapter";
		
		Set<BluetoothDevice> pairedDevices = bt.getBondedDevices();
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        if (device.getName().equalsIgnoreCase("PapyMerlin")) {
		        	this.device = device;
		        }
		    }
		    
		}
		if (device == null) return "no device";
		
		final UUID spp_uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
		try {
			socket = device.createRfcommSocketToServiceRecord(spp_uuid);
			bt.cancelDiscovery();
			socket.connect();
			in = socket.getInputStream();
			out = socket.getOutputStream();
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
		
		return "ok";
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public String sendCommand(String cmd) {
        String command = ":"+cmd+"\r";
        String response = "";
        byte[] bCmd = command.getBytes();
        try {
        	//Log.i("sendCommand", "in sendCommand");
			out.write(bCmd);
			Thread.sleep(100);
			int bytes = in.read(buffer);
			String ReadBuffer = new String(buffer, 0, bytes);
			Matcher m = p.matcher(ReadBuffer);
			if (m.find()) {
				response = m.group(1);
			}
			//Log.i("sendCommand", new String(bCmd));
			//Log.i("sendCommand", new String(ReadBuffer));
			//Log.i("sendCommand", response);
		} catch (Exception e) {
			//Log.i("sendCommand", "got an exception: " + e);
			return e.getMessage();
		}
        return response;
    }
}
