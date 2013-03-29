package com.NeuroSky.ThinkGear.Util;

/**
 * This class is a convenience class to store data coming from a ThinkGear
 * EM headset.
 * 
 * @author 		Horace Ko
 */
public class HeadsetData {
	/**
	 * The interpreted meditation level (a value between 0 and 100, 
	 * where 100 indicates high meditation).
	 */
	public int meditation;
	
	/**
	 * The interpreted attention level (a value between 0 and 100, 
	 * where 100 indicates high attention).
	 */
	public int attention;
	
	/**
	 * The signal error rate of the headset (a value between 0 and 128, where 128
	 * indicates that the signal is completely bad).
	 */
	public int errorRate;
	
	/**
	 * The battery level (a value between 0 and 128, where 128 indicates a full
	 * battery).
	 */
	public int batteryLevel;
	
	/**
	 * The delta wave component of an EEG (0 to 3 Hz). The value is a floating
	 * point number greater than 0.
	 */
	public float delta;
	
	/**
	 * The theta wave component of an EEG (4 to 7 Hz). The value is a floating
	 * point number greater than 0.
	 */
	public float theta;
	
	/**
	 * The alpha wave component of an EEG. <code>alpha1</code>
	 * represents waves from 8 to 10 Hz. The value is a floating point number
	 * greater than 0.
	 */
	public float alpha1;
	
	/**
	 * The alpha wave component of an EEG. <code>alpha2</code>
	 * represents waves from 10 to 12 Hz. The value is a floating point number
	 * greater than 0.
	 */
	public float alpha2;
	
	/**
	 * The beta wave component of an EEG. <code>beta1</code>
	 * represents waves from 12 to 20 Hz. The value is a floating point number
	 * greater than 0.
	 */
	public float beta1;
	
	/**
	 * The beta wave component of an EEG. <code>beta2</code>
	 * represents waves from 20 to 30 Hz. The value is a floating point number
	 * greater than 0.
	 */
	public float beta2;
	
	/**
	 * The gamma wave component of an EEG. <code>gamma1</code>
	 * represents waves from 30 to 40 Hz. The value is a floating point number
	 * greater than 0.
	 */
	public float gamma1;
	
	/**
	 * The gamma wave component of an EEG. <code>gamma2</code>
	 * represents waves from 40 to 50 Hz. The value is a floating point number
	 * greater than 0.
	 */
	public float gamma2;
	
	/**
	 * Class constructor taking no parameters and initializing all instance
	 * variables to invalid (negative) values.
	 */
	public HeadsetData(){
		this(-1, -1, 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	/**
	 * Class constructor specifying values for each of the instance variables.
	 * @param meditation the desired meditation level
	 * @param attention the desired attention level
	 * @param errorRate the rate at which errors are seen (lower is better)
	 * @param batteryLevel the battery level (higher is better)
	 */
	public HeadsetData(int meditation, int attention, 
						 int errorRate, int batteryLevel, float delta, float theta, float alpha1,
						 float alpha2, float beta1, float beta2, float gamma1, float gamma2){
		this.meditation = meditation;
		this.attention = attention;
		this.errorRate = errorRate;
		this.batteryLevel = batteryLevel;
		this.delta = delta;
		this.theta = theta;
		this.alpha1 = alpha1;
		this.alpha2 = alpha2;
		this.beta1 = beta1;
		this.beta2 = beta2;
		this.gamma1 = gamma1;
		this.gamma2 = gamma2;
	}
	
	/**
	 * Overridden <code>toString()</code> method to print out all of the
	 * instance variables in a comprehensible manner. This is just a concatenated
	 * version of the elements returned from {@link #toStringArray()}.
	 * 
	 * @return a string describing the object
	 */
	public String toString(){
		String[] value = toStringArray();
		
		String outputString = "";
		
		for(int i = 0; i < value.length; i++)
			outputString += value[i] + "\n";
		
		return outputString;
	}
	
	/**
	 * Generate debug output where the different debug strings are tokenized
	 * into separate <code>String[]</code> elements. This is useful for displaying debug
	 * output to a <code>Graphics</code> context using <code>Graphics.drawString()</code>
	 * 
	 * @return an array storing different components of the debug string
	 */
	public String[] toStringArray(){
		String [] output = {"Battery: " + batteryLevel,
							"Signal: " + errorRate,
							"Attention: " + attention,
							"Meditation: " + meditation,
							"Delta: " + delta,
							"Theta: " + theta,
							"Alpha1: " + alpha1,
							"Alpha2: " + alpha2,
							"Beta1: " + beta1,
							"Beta2: " + beta2,
							"Gamma1: " + gamma1,
							"Gamma2: " + gamma2};
		
		return output;
	}
	
	/**
	 * Check whether the headset is off the head. This simply interprets the headset
	 * data, and checks to see whether both <code>attention</code> and <code>meditation</code>
	 * are <code>0</code>.
	 * 
	 * @return <code>true</code> if the headset is detected to be off the head, 
	 * 			<code>false</code> otherwise
	 */
	public boolean isOffHead(){
		return attention == 0 && meditation == 0;
	}
}
