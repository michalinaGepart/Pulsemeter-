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
	ArrayList<Integer> measurements;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.measure);
		result = 0;
		
		measurements = new ArrayList<Integer>();
	
		graphView = new LineGraphView(
			    this // context
			    , "Pulses per second" // heading
			);
		graphView.setVerticalLabels(new String[]{"pulse", "rest"});
		graphView.setHorizontalLabels(new String[]{"0","10", "20","30","40","50","60"});
			 
			LinearLayout layout = (LinearLayout) findViewById(R.id.measureLayout);
	    
			layout.addView(graphView);
			
			estabilishConnection();
			
		//	drawGraph();
			
		calculateResult(); // after minute of measurement? 
			
	}
	
	public void drawGraph()
	{
		
		// append data 
		dataToDraw = new GraphViewSeries(new GraphViewData[] {
			});
		graphView.addSeries(dataToDraw); // data
	}
	
	public void calculateResult()
	{
		String ex = "Your pulse is: ";
		result = 0;
		int prev = 0;
		
		for(int val : measurements){
			if(prev > val)
				result++;
		}
		
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
	
	public void estabilishConnection()
	{
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		ArrayList<String> mArrayAdapter = new ArrayList<String>();
		BluetoothDevice arduino;
		text = (TextView)findViewById(R.id.diagnosis);
		
		
		if(pairedDevices.size() > 0){
			for(BluetoothDevice device : pairedDevices){
		        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		        if(device.getName().equals("HC-05")) // connect with device with this name, else: use getAddress() to obtain MAC address
		        {
		        	arduino = device;
						try {
							getDataFromArduino(arduino);
						} catch (IOException e) {
							text.setText("Error occured");
							e.printStackTrace();
						}
		        	break;
		        }
		        else
		    		text.setText("The needed device couldn't be found. The measurements won't be made.");
			}
		}
        else
    		text.setText("The bluetooth didn't detect any devices.");
	}
	
	public void getDataFromArduino(BluetoothDevice arduino) throws IOException
	{
		BluetoothSocket arduinoSocket;
	//	OutputStream send;
		InputStream receive;
		
		
    	UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //standard SerialPortService ID from android documentation
        arduinoSocket = arduino.createRfcommSocketToServiceRecord(uuid);
        arduinoSocket.connect();
     //   send = arduinoSocket.getOutputStream();
        receive = arduinoSocket.getInputStream();
        
		text = (TextView)findViewById(R.id.diagnosis);
        String ms = "Czesc, przybywam z Androida :)";
        text.setText(ms);
   //     send.write(ms.getBytes());
        
        
        // measurements.add(value);
        //drawGraph(value);
	}
	
	public final class FeedReaderContract{
		public FeedReaderContract(){}
		
	}


}
