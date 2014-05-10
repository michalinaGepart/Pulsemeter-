package com.example.pulsemeter;


import java.util.ArrayList;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class measurement extends ActionBarActivity{
	
	GraphView graphView;
	TextView text;
	int result; // pulses per minute
	GraphViewSeries dataToDraw;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.measure);
		result = 0;
		

		
		graphView = new LineGraphView(
			    this // context
			    , "Pulses per second" // heading
			);
		graphView.setVerticalLabels(new String[]{"pulse", "rest"});
		graphView.setHorizontalLabels(new String[]{"0","10", "20","30","40","50","60"});
			 
			LinearLayout layout = (LinearLayout) findViewById(R.id.measureLayout);
	    
			layout.addView(graphView);
			
			getDataFromArduino();
			
	}
	
	public void drawGraph()
	{
		dataToDraw = new GraphViewSeries(new GraphViewData[] {
			    new GraphViewData(1, 2.0d)
			    , new GraphViewData(2, 1.5d)
			    , new GraphViewData(3, 2.5d)
			    , new GraphViewData(4, 1.0d)
			});
		
		graphView.addSeries(dataToDraw); // data
	}
	
	public void calculateResult()
	{
		String ex = "Your pulse is: ";
		
		//calculations
		result = 99; // TEMPORARY RES VALUE
		
		ex += result + " pulses/min";
		text = (TextView)findViewById(R.id.diagnosis);
		if(result >= 60 && result <= 100){
			ex += "\nThis is normal pulse rate.";
			text.setText(ex);
		}
		else if(result < 60){
			ex += "\nThis is low pulse rate.";
			text.setText(ex);
		}
		else{
			ex += "\nThis is high pulse rate.";
		}
		

	}
	
	public void getDataFromArduino()
	{
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		ArrayList<String> mArrayAdapter = new ArrayList<String>();
		
		if(pairedDevices.size() > 0){
			for(BluetoothDevice device : pairedDevices){
		        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		        //if(device.getName()== ????????) TO POLACZ SIE
			}
		}
		
		drawGraph();
		
		calculateResult(); // after minute of measurement? 
	}


}
