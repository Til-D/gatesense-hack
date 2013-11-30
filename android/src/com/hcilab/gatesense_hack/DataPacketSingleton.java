package com.hcilab.gatesense_hack;

import java.util.ArrayList;
import java.util.List;

public class DataPacketSingleton {
	
	private final static String DELIMITER = "\t\n";
	private final String TAG = DataPacketSingleton.class.toString();
	
	private static DataPacketSingleton instance = null;
	
	private ArrayList<QuadrupelTuple> accelerometer;
	private ArrayList<QuadrupelTuple> gps;
	
	protected DataPacketSingleton() {
		//exists only to defeat instantiation
	}
	
	public void addAccelerometerData(double x, double y, double z, long t) {
		if(this.accelerometer!=null) {
			addDataToList(x, y, z, t, this.accelerometer);
		} else {
			this.accelerometer = new ArrayList<QuadrupelTuple>();
		}
	}
	
	public void addGPSData(double x, double y, double z, long t) {
		if(this.gps!=null) {
			addDataToList(x, y, z, t, this.gps);
		} else {
			this.gps = new ArrayList<QuadrupelTuple>();
		}
	}
	
	public void resetDataPacket() {
		this.accelerometer = new ArrayList<QuadrupelTuple>();
		this.gps = new ArrayList<QuadrupelTuple>();
	}
	
	private void addDataToList(double x, double y, double z, long t, ArrayList<QuadrupelTuple> list) {
		list.add(new QuadrupelTuple(x, y, z, t));
	}
	
	public String toString() {
		String ret = "";
		ret += "** Accelerometer Data: (size: " + this.accelerometer.size() + ")" + DELIMITER;
//		for(int i=0; i<this.accelerometer.size(); i++) {
//			ret += this.accelerometer.get(i).toString() + DELIMITER;
//		}
		ret += "** GPS Data: (size: " + this.gps.size() + ")" + DELIMITER;
//		for(int i=0; i<this.gps.size(); i++) {
//			ret += this.gps.get(i).toString() + DELIMITER;
//		}
		return ret;
	}
	
	public static DataPacketSingleton getInstance() {
		if(instance == null) {
			instance = new DataPacketSingleton();
		}
		return instance;
   }
}

class QuadrupelTuple {
	double x;
	double y;
	double z;
	long t;
	
	public QuadrupelTuple(double x, double y, double z, long t) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.t = t;
	}
	
	public String toString() {
		return "(" + x + "," + y + "," + z + "," + t + ")";
	}
}