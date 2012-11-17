package com.NeuroSky.ThinkGear.IO;

import java.io.IOException;

/**
 * This class defines an implementation of the
 * ThinkGear driver. Both ThinkGear.Driver and ThinkGear.DummyDriver extend 
 * from this class, allowing a programmer to (possibly programmatically) 
 * swap between debug non-live code and live code by changing which driver 
 * type gets instantiated.
 * 
 * @author Horace Ko
 */
public abstract class Connection {
    
    /**
     * Open a connection to the ThinkGear headset using a unique device identifier. The 
     * identifier takes the form of 12 hexadecimal digits with no delimiters. Implementations
     * of this method assume that the serial IO communications are performed on channel 1.
     * 
     * @param deviceID a 12-digit hexadecimal value representing the unique device identifier
     * @return <code>true</code> if the connection attempt was successful,
     * 			<code>false</code> otherwise
     * @throws IOException
     */
    public abstract boolean openConnection(String deviceID) throws IOException;
    
    /**
     * Open a connection to the ThinkGear headset using a unique device identifier and
     * a channel number. The identifier takes the form of 12 hexadecimal digits with no 
     * delimiters.
     *  
     * @param deviceID a 12-digit hexadecimal value representing the unique device identifier
     * @param channel an integer representing the desired channel to connect on 
     * @return <code>true</code> if the connection attempt was successful, 
     * 			<code>false</code> otherwise
     * @throws IOException
     */
    public abstract boolean openConnection(String deviceID, int channel) throws IOException;
    
    /**
     * Closes the connection to the ThinkGear headset.
     * 
     * @return <code>true</code> if the connection was successfully closed, 
     * 			<code>false</code> otherwise
     * @throws IOException
     */
    public abstract boolean closeConnection() throws IOException;
    
    /**
     * Reads data from the headset into a byte array reference passed in as a parameter.
     * 
     * @param byteArray the byte array that is to be written to
     * @return the total number of bytes read into the buffer, 
     * 			or <code>-1</code> if there is no more data because 
     * 			the end of the stream has been reached.
     * @throws IOException
     */
    public abstract int read(byte[] byteArray) throws IOException;
    
    /**
     * Writes a value to the headset.
     * 
     * @param values the value to be written to the headset
     * @throws IOException
     */
    public abstract void write(int value) throws IOException;
    
    /**
     * Writes a value to the headset.
     * 
     * @param values the value to be written to the headset
     * @throws IOException
     */
    public abstract void write(byte[] values, int off, int len) throws IOException;
    
    /**
     * Flushes data in the output buffer directly to the headset. Use this after
     * a {@link #write(int)} to perform an immediate write.
     * 
     * @throws IOException
     */
    public abstract void flush() throws IOException;
    
    /**
	 * Check whether the driver is connected to a ThinkGear headset.
	 * 
	 * @return <code>true</code> if the headset is connect, 
	 * 			<code>false</code> otherwise.
	 */
    public abstract boolean isConnected();
}
