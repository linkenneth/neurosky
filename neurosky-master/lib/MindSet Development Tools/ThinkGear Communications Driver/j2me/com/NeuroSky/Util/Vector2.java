package com.NeuroSky.Util;

public class Vector2 {
	public float x;
	public float y;
	
	public Vector2(){
		x = 0.0f;
		y = 0.0f;
	}
	
	public Vector2(double x, double y){
		this.x = (float)x;
		this.y = (float)y;
	}
	
	public Vector2(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Vector2 copy){
		this.x = copy.x;
		this.y = copy.y;
	}
	
	public float length(){
		return (float)Math.sqrt((x * x) + (y * y));
	}
	
	public Vector2 normalize(){
		return new Vector2(x / length(), y / length());
	}
	
	public String toString(){
		return "x: " + this.x + ", y: " + this.y;
	}
	
	public void set(Vector2 copy){
		this.x = copy.x;
		this.y = copy.y;
	}
	
	public void set(double x, double y){
		this.x = (float)x;
		this.y = (float)y;
	}
}
