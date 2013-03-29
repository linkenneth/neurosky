package com.NeuroSky.ThinkGear.IO;

import java.io.*;

import javax.microedition.io.*;

/**
 * This class implements a live ThinkGear driver. Refer to the documentation for the
 * abstract {@link Connection} class for method details.
 * 
 * @author Horace Ko
 */
public class HeadsetConnection extends Connection {
	private StreamConnection conn;
    private InputStream input;
    private OutputStream output;
    
    public HeadsetConnection(){}
    
    public boolean isConnected(){
    	return (conn != null && input != null && output != null);
    }
    
    public boolean openConnection(String deviceID, int channel) throws IOException {
    	String inURL = "btspp://" + deviceID + ":" + channel;
    	
    	conn = (StreamConnection)Connector.open(inURL);
        input = conn.openInputStream(); 
        output = conn.openOutputStream();
        
        return true;
    }
    
    public boolean openConnection(String deviceID) throws IOException {
    	String inURL = "btspp://" + deviceID + ":1";
    	
    	conn = (StreamConnection)Connector.open(inURL);
        input = conn.openInputStream(); 
        output = conn.openOutputStream();
        
        return true;
    }
    
    public int read(byte[] byteArray) throws IOException {
    	return input.read(byteArray);
    }
    
    public void write(int value) throws IOException {
    	output.write(value);
    }
    
    public void write(byte[] values, int off, int len) throws IOException {
    	output.write(values, off, len);
    }
    
    public void flush() throws IOException {
    	output.flush();
    }
    
    public boolean closeConnection() throws IOException {
    	input.close();
    	output.close();
    	conn.close();
    	
    	return true;
    }
}
