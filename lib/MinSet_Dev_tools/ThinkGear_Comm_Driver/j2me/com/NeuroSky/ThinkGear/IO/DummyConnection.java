package com.NeuroSky.ThinkGear.IO;

import java.io.IOException;

/**
 * This class implements a dummy ThinkGear driver. You can use an instance of this
 * class as a live swap-in to perform testing and validation of your application
 * code without requiring a Bluetooth connection. Refer to the documentation for the
 * abstract {@link Connection} class for method details.
 * 
 * @author Horace Ko
 */
public class DummyConnection extends Connection {
	double startTime;
	
	public DummyConnection() {
		startTime = System.currentTimeMillis();
    }
    
    public boolean isConnected(){
    	return true;
    }
    
    public boolean openConnection(String deviceID, int channel) throws IOException {
    	return true;
    }
    
    public boolean openConnection(String deviceID) throws IOException {
        return true;
    }
    
    public int read(byte [] byteArray) throws IOException {
    	byteArray[0] = (byte)0xAA;		// sync
    	byteArray[1] = (byte)0xAA;		// sync
    	byteArray[2] = (byte)0x06;		// payload length
    	
    	// payload
    	byteArray[3] = (byte)0x02;		// signal quality
    	byteArray[4] = (byte)0x70;
    	
    	byteArray[5] = (byte)0x04;		// attention
    	byteArray[6] = (byte)(50 + (50 * Math.sin(System.currentTimeMillis() / 3000.0)));
    	
    	byteArray[7] = (byte)0x05;		// meditation
    	byteArray[8] = (byte)(50 + (50 * Math.cos(System.currentTimeMillis() / 3000.0)));
    	
    	int checksum = 0;
    	
    	for(int i = 3; i <= 8; i++)
    		checksum += byteArray[i];
    	
    	checksum &= 0xFF;
    	checksum = ~checksum;
    	checksum &= 0xFF;
    	
    	byteArray[9] = (byte)checksum;
    	
    	return 10;
    }
    
    public void write(int value) throws IOException {
    	return;
    }
    
    public void write(byte[] values, int off, int len) throws IOException {
    	return;
    }
    
    public void flush() throws IOException {
    	return;
    }
    
    public boolean closeConnection() throws IOException {
    	return true;
    }
}
