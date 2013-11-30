package com.hcilab.gatesense_hack;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationSensor implements SensorEventListener {

	public final String TAG = getClass().getName(); 
	private boolean running;
	private LocationManager locationManager;
	private LocationListener locationListener;
	
	private double lastLong;
	private double lastLat;
	private double lastAlt;
	
	public LocationSensor() {
		running = false;
	}
	
	public boolean isAvailable(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
	}
	
	public void start(Context context) {
		Log.d(TAG, "+ fire up location sensor");
		
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		locationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				Log.d(TAG, "latitude: " + location.getLatitude() + ", long: " + location.getLongitude() + ", alt: " + location.getAltitude());
				
				setLastAlt(location.getAltitude());
				setLastLong(location.getLongitude());
				setLastLat(location.getLatitude());
				
				DataPacketSingleton.getInstance().addGPSData(location.getLatitude(), location.getLongitude(), location.getAltitude(), System.currentTimeMillis());
				
				Log.d(TAG, DataPacketSingleton.getInstance().toString());
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				Log.d(TAG, "onProviderDisabled()");
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				Log.d(TAG, "onProviderEnabled()");
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				Log.d(TAG, "onStatusChanged()");
				
			}
		    
		  };

		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		
		running = true;
	}
	
	public void stop(Context context) {
		Log.d(TAG, "+ stop location sensor");
		
		locationManager.removeUpdates(locationListener);
		running = false;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}

	public double getLastLong() {
		return lastLong;
	}

	public void setLastLong(double lastLong) {
		this.lastLong = lastLong;
	}

	public double getLastLat() {
		return lastLat;
	}

	public void setLastLat(double lastLat) {
		this.lastLat = lastLat;
	}

	public double getLastAlt() {
		return lastAlt;
	}

	public void setLastAlt(double lastAlt) {
		this.lastAlt = lastAlt;
	}
	
}