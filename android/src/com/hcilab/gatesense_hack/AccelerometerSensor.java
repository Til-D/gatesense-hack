package com.hcilab.gatesense_hack;

import java.io.FileWriter;
import java.io.IOException;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class AccelerometerSensor implements SensorEventListener {

	public final String TAG = getClass().getName();

	private SensorManager sensorManager;
	private boolean running;
	private long count;
	private int flushLevel = 100;
	
	public AccelerometerSensor() {
		running = false;
	}
	
	public boolean isAvailable(Context context) {
		SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		return !(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null);
	}
	
	public void start(Context context) {
		Log.d(TAG, "+ start accelerometer sensor");
		
//		this.writer = writer;
//		try {
//			writer.write("timestamp,x,y,z,reliable");
//			writer.write("\n");
//			writer.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);		
		running = true;
		count = 0;
	}
	
	public void stop() {
		Log.d(TAG, "- stop accelerometer sensor");
		
		if(running) {
			sensorManager.unregisterListener(this);
//			try {
//				writer.close();
//				writer = null;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			running = false;
		}	
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	public void onSensorChanged(SensorEvent event) {
//		Log.d(TAG, "sensor value changed");
		
		if(running) {
			
			DataPacketSingleton.getInstance().addAccelerometerData(event.values[0], event.values[1], event.values[2], System.currentTimeMillis());
			
//			Log.d(TAG, System.currentTimeMillis() + "," + event.values[0] + "," + event.values[1] + "," + event.values[2]);
			
//			try {
//				count++;
//				if(event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
//					writer.write(System.currentTimeMillis() + "," + event.values[0] + "," + event.values[1] + "," + event.values[2] + ",false");
//				} else {
//					writer.write(System.currentTimeMillis() + "," + event.values[0] + "," + event.values[1] + "," + event.values[2] + ",true");
//				}
//				writer.write("\n");				
//				if(count % flushLevel == 0) {
//					writer.flush();
//				}				
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		}
	
	}
}
	
