package com.example.pulsemeter;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
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
			
			try {
				estabilishConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			drawGraph();
			
		//	calculateResult(); // after minute of measurement? 
			
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
	
	public void estabilishConnection() throws IOException
	{
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		ArrayList<String> mArrayAdapter = new ArrayList<String>();
		BluetoothDevice arduino;
		
		
		if(pairedDevices.size() > 0){
			for(BluetoothDevice device : pairedDevices){
		        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		        if(device.getName().equals("MacBook Air (Michasia)")) // connect with device with this name
		        {
		        	arduino = device;
		        	getDataFromArduino(arduino);
		        	break;
		        }
			}
	        	
		}
		

	}
	
	public void getDataFromArduino(BluetoothDevice arduino) throws IOException
	{
		BluetoothSocket arduinoSocket;
		OutputStream send;
		InputStream receive;
		
		
    	UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //standard SerialPortService ID from android documentation
        arduinoSocket = arduino.createRfcommSocketToServiceRecord(uuid);
        arduinoSocket.connect();
        send = arduinoSocket.getOutputStream();
        receive = arduinoSocket.getInputStream();
        
		text = (TextView)findViewById(R.id.diagnosis);
        
        String ms = "Czesc, przybywam z Androida :)";
        text.setText(ms);
        send.write(ms.getBytes());
	}


}
