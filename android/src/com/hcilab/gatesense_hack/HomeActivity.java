package com.hcilab.gatesense_hack;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class HomeActivity extends Activity {
	
	private static final String TAG = HomeActivity.class.toString();
	
	private ToggleButton buttonRecordTransportation;
	private RadioGroup radioGroupTransportation;
	
	private TextView homeStatusLabel;
	private TextView labelLongitude;
	private TextView labelLatitude;
	private TextView labelAltitude;
	private TextView labelAccX;
	private TextView labelAccY;
	private TextView labelAccZ;
	
	private boolean recording;
	private AccelerometerSensor accelerometerSensor;
	private LocationSensor locationSensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		//UI Elements
		homeStatusLabel = (TextView) findViewById(R.id.homeStatusLabel);
		labelLongitude = (TextView) findViewById(R.id.labelLong);
		labelLatitude = (TextView) findViewById(R.id.labelLat);
		labelAltitude = (TextView) findViewById(R.id.labelAlt);
		labelAccX = (TextView) findViewById(R.id.labelAccX);
		labelAccY = (TextView) findViewById(R.id.labelAccY);
		labelAccZ = (TextView) findViewById(R.id.labelAccZ);
		
		buttonRecordTransportation = (ToggleButton) findViewById(R.id.toggleButtonTransportationGo);
		radioGroupTransportation = (RadioGroup) findViewById(R.id.radioTransportation);
		
		//initializers
		recording = false;
		homeStatusLabel.setText(getString(R.string.ready));
		labelLongitude.setText("");
		labelLatitude.setText("");
		labelAltitude.setText("");
		labelAccX.setText("");
		labelAccY.setText("");
		labelAccZ.setText("");
		
		accelerometerSensor = new AccelerometerSensor();
		locationSensor = new LocationSensor();
		
		//Listeners
		buttonRecordTransportation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		            startRecordingTransportation();
		        } else {
		        	stopRecordingTransportation();
		        }
		    }
		});
	}

	protected void startRecordingTransportation() {
		
		int transportId = radioGroupTransportation.getCheckedRadioButtonId();
		String selectedTransport = "";
		
		if(transportId!=-1) {
			Log.i(TAG, "+ Start recording mode of transportation: " + transportId);
			
			View radioButton = radioGroupTransportation.findViewById(transportId);
		    int radioId = radioGroupTransportation.indexOfChild(radioButton);
		    RadioButton btn = (RadioButton) radioGroupTransportation.getChildAt(radioId);
		    selectedTransport = (String) btn.getText();
		    
		    Log.i(TAG, "selected mode: " + selectedTransport);
		    
		    if(accelerometerSensor.isAvailable(HomeActivity.this)) {
		    	accelerometerSensor.start(HomeActivity.this);
		    } else {
		    	Log.w(TAG, "Accelerometer not available.");
		    }
		    
		    if(locationSensor.isAvailable(HomeActivity.this)) {
		    	locationSensor.start(HomeActivity.this);
		    } else {
		    	Log.w(TAG, "Location sensor not available.");
		    }
		    
//			if (selectedTransport.equals(getString(R.string.walk))) {
//	    	
//	    	}
//			else if (selectedTransport.equals(getString(R.string.bike))) {
//			
//			}
//			else if (selectedTransport.equals(getString(R.string.bus))) {
//				
//			}
//			else if (selectedTransport.equals(getString(R.string.rail))) {
//				
//			}
//			else if (selectedTransport.equals(getString(R.string.taxi))) {
//				
//			}
//			else {
//				//default
//			}
			
			recording = true;
			homeStatusLabel.setText(getString(R.string.recording));
			
			//disable radiogroup
			enableRadioGroupTransportation(!recording);
		} else {
			Log.w(TAG, "no transportation mode selected");
			homeStatusLabel.setText(getString(R.string.noTransportationModeSelected));
		}
	}
	
	protected void stopRecordingTransportation() {
    	Log.i(TAG, "- stop recording mode of transport");
    	
    	accelerometerSensor.stop();
    	
		recording = false;
		homeStatusLabel.setText(getString(R.string.ready));
		
		//enable radiogroup
		enableRadioGroupTransportation(!recording);
	}
	
	private void enableRadioGroupTransportation (boolean enabled) {
		for(int i = 0; i < radioGroupTransportation.getChildCount(); i++){
            ((RadioButton)radioGroupTransportation.getChildAt(i)).setEnabled(enabled);
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
