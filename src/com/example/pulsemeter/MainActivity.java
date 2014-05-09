package com.example.pulsemeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	
	View measurePulse, measurementsHistory, measurementsInformation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		measurePulse = (Button) findViewById(R.id.measure);
		measurePulse.setOnClickListener(this);
		
		measurementsHistory = (Button) findViewById(R.id.history);
		measurementsHistory.setOnClickListener(this);
		
		measurementsInformation = (Button) findViewById(R.id.info);
		measurementsInformation.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.measure){
		Intent i = new Intent(this, measurement.class);
		startActivity(i);
		
		}
		else if(v.getId() == R.id.history){
			Intent i = new Intent(this, history.class);
			startActivity(i);
			
		}
		else if(v.getId() == R.id.info){
			Intent i = new Intent(this, information.class);
			startActivity(i);
			
		}
	}

}
