package com.NeuroSky.Util;

/**
 * This class implements a bunch of utility functions to perform various
 * transformation and computational operations. Some of the functions are
 * simply to fill holes in J2ME's implementation of the <code>Math</code> class.
 * 
 * @author Horace Ko
 */
public class MathHelper {
	
	/**
	 * Linearly interpolate a value between the current value and target value. This could
	 * be seen as a linear Bezier curve.
	 * 
	 * @param start the starting value 
	 * @param end the ending value
	 * @param t the parameter
	 * @return a value that represents a linearly interpolated value between "start" and "end"
	 */
	public static float lerp(float start, float end, float t){
		t = clamp(t, 0.0f, 1.0f);
		
		return start + ((end - start) * t);
	}
	
	/**
	 * Linearly interpolate between two colors.
	 * 
	 * @param startColor the starting color, defined by 0x00RRGGBB
	 * @param endColor the finishing color, defined by 0x00RRGGBB
	 * @param t the parameter
	 * @return a 0x00RRGGBB value that represents a linearly interpolated value
	 */
	public static int lerpColor(int startColor, int endColor, float t){
		t = clamp(t, 0.0f, 1.0f);
		
		int sr = (startColor >> 16) & 0xFF;
		int sg = (startColor >> 8) & 0xFF;
		int sb = startColor & 0xFF;
		
		int tr = (endColor >> 16) & 0xFF;
		int tg = (endColor >> 8) & 0xFF;
		int tb = endColor & 0xFF;
		
		int cr = (int)(sr + (tr - sr) * t);
		int cg = (int)(sg + (tg - sg) * t);
		int cb = (int)(sb + (tb - sb) * t);
		
		return (cr << 16) + (cg << 8) + cb;
	}
	
	/**
	 * Limit a value to between <code>floor</code> and <code>ceiling</code>. This may be useful
	 * for making sure that a number never exceeds a certain range.
	 * 
	 * @param t the value to be clamped
	 * @param floor the minimum value the returned value should take
	 * @param ceiling the maximum value the returned value should take
	 * @return the clamped value
	 */
	public static float clamp(float t, float floor, float ceiling){
		return t > ceiling ? ceiling : (t < floor ? floor : t);
	}
	
	/**
	 * Apply a multiplicative factor to each component in a color represented by
	 * an integer (0x00RRGGBB).
	 * 
	 * @param startColor the initial color, in the form 0x00RRGGBB
	 * @param dampFactor the multiplicative factor applied to the color
	 * @return the damped color (startColor * dampFactor)
	 */
	public static int dampColor(int startColor, float dampFactor){
		int sr = (startColor >> 16) & 0xFF;
		int sg = (startColor >> 8) & 0xFF;
		int sb = startColor & 0xFF;
		
		return ((int)(sr * dampFactor) << 16) + ((int)(sg * dampFactor) << 8) + (int)(sb * dampFactor);
	}
	
	/**
	 * Calculate the value of <code>e^x</code> given an input <code>x</code>. This only 
	 * gives us good precision to about x = +/- 2, since it's calculated using a fairly 
	 * coarse Power Series approximation.
	 * 
	 * @param x the value that e is raised to
	 * @return the result of the calculation e^x
	 */
	public static float exp(float x){
		if(x < -2.0)
			x = -2.0f;
		
		float x2 = x * x;
		float x3 = x2 * x;
		
		return 1.0f + x + (x2 / 2.0f) + (x3 / 6.0f);
	}
	
	/**
	 * Calculate the natural log of an input <code>x</code>, i.e. ln(x). This function gives
	 * us reasonably good precision, and is accurate to around 3 significant figures. The precision
	 * of this function is limited by the accuracy of a T(4) Taylor Series approximation of the 
	 * natural logarithm function between 1 and 2.
	 * 
	 * @param x the value from which the natural log should be calculated
	 * @return the natural log of <code>x</code>
	 */
	public static float log(float x){
		int bitMask = 0xA00000;
		
		// grab the raw bit representation of the FP number
		int rawFP = Float.floatToIntBits(x);
		
		// determine the exponent bits (the top eight bits after the one sign bit)
		int exponent = ((rawFP >> 23) & 0xFF) - 127;
		
		// determine the raw mantissa (the next 23 bits)
		// the 24th bit is added manually
		int rawMantissa = (rawFP & 0x7FFFFF) | bitMask;
		
		// variables to determine the actual mantissa
		float accumulatedValue = 1.0f;
		float mantissa = 0.0f;
		
		// iterate over each of the raw mantissa bits, adding (1/2)^i to
		// an accumulating value
		//
		// basically calculating the value of a fixed-point decimal
		for(int i = 0; i < 24; i++){
			if((rawMantissa & bitMask) >> (23 - i) != 0)
				mantissa += accumulatedValue;
			
			bitMask = bitMask >> 1;
			accumulatedValue /= 2.0f;
		}
		
		return log2to2(mantissa) + exponent * 0.693147181f;
	}
	
	private static float log2to2(float x){
		x -= 1.0f;
		
		float x2 = x * x;
		float x3 = x2 * x;
		float x4 = x3 * x;
		float x5 = x4 * x;
		
		return x - (x2 / 2) + (x3 / 3) - (x4 / 4) + (x5 / 5);
	}
	
	/**
	 * Critically damp a value towards a target value. This method uses the equation for a critically damped system:
	 * 
	 * x(t) = (A + Bt) * e^(-w0 * t), where
	 * A = x(0)
	 * B = x'(0) + x(0)
	 * 
	 * @param current the current value
	 * @param target the target value
	 * @param currentVelocity an array of floats storing the current velocity x'(0)
	 * @param index the index into the <code>currentVelocity</code> array referencing the desired float element
	 * @param smoothTime - the time taken to reach the target value
	 * @param deltaTime - the elapsed time since the last call to this method
	 * @return a critically damped value
	 */
	public static float smoothDamp(float current, float target, float[] currentVelocity, int index, float smoothTime, float deltaTime){
		float x0 = current - target;
		float xp0 = currentVelocity[index];
		
		// apply the damping function
		float result = (x0 + (deltaTime * (xp0 + (smoothTime * x0)))) * exp(-smoothTime * deltaTime) + target;
		
		currentVelocity[index] = (result - current) / deltaTime;
		
		return result;
	}
	
	/**
	 * Rotate a point about the origin (0,0) over an angle.
	 * 
	 * @param angle the angle (in radians) over which to rotate the point 
	 * @param point the point to be rotated
	 * @return a point representing the rotated original point
	 */
	public static Vector2 rotate(float angle, Vector2 point){
		return new Vector2(point.x * Math.cos(-angle) - point.y * Math.sin(-angle),
						   point.x * Math.sin(-angle) + point.y * Math.cos(-angle));
	}
	
	/**
	 * Generate a point in a cubic Bezier curve defined by two anchor and two
	 * control points, and a parameter.
	 * 
	 * @param p0 the starting anchor point
	 * @param p1 the first control point
	 * @param p2 the second control point
	 * @param p3 the ending anchor point
	 * @param t the parameter for the Bezier curve
	 * @return a point along the cubic Bezier curve
	 */
	public static Vector2 cubicBezier(Vector2 p0, Vector2 p1, Vector2 p2, Vector2 p3, float t){
		t = clamp(t, 0.0f, 1.0f);
		
		// figure out all the coefficients
		
		float tp = 1 - t;
		float tp2 = tp * tp;
		float tp3 = tp2 * tp;
		
		float t2 = t * t;
		float t3 = t2 * t;
		
		float c2 = 3 * t * tp2;
		float c3 = 3 * t2 * tp;
		
		float x = (tp3 * p0.x) + (c2 * p1.x) + (c3 * p2.x) + (t3 * p3.x);
		float y = (tp3 * p0.y) + (c2 * p1.y) + (c3 * p2.y) + (t3 * p3.y);
		
		return new Vector2(x, y);
	}
	
	/**
	 * Perform a custom normalization procedure on a value, involving applying
	 * a natural logarithm, then scaling the number to a range between 0 and 1.
	 * 
	 * @param value the value to be normalized
	 * @return a normalized float between <code>0</code> and <code>1</code>.
	 */
	public static float normalizeValue(float value){
		if(value == 0)
			return 6.0f / 7.0f;
		
		float tempValue = log(value);
		
		tempValue = clamp(tempValue, -6.0f, 1.0f);
		tempValue = (tempValue + 6.0f) / 7.0f;
		
		return tempValue;
	}
}
