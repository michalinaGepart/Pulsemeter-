package com.example.pulsemeter;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class measurement extends ActionBarActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.measure);
		
		
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
			    new GraphViewData(1, 2.0d)
			    , new GraphViewData(2, 1.5d)
			    , new GraphViewData(3, 2.5d)
			    , new GraphViewData(4, 1.0d)
			});
		
		GraphView graphView = new LineGraphView(
			    this // context
			    , "GraphViewDemo" // heading
			);
			graphView.addSeries(exampleSeries); // data
			 
			LinearLayout layout = (LinearLayout) findViewById(R.id.measureLayout);
			
//		    LinearLayout.LayoutParams layoutParams =
//	                (RelativeLayout.LayoutParams) graphView.getLayoutParams();
//	    layoutParams.addRule(LinearLayout.BOTTOM, 1);
	    
			layout.addView(graphView);
		
		
		//jesli nie ma danych - ustaw textview na odpowiednia wiadomosc
		
		// pobieranie danych z bluetootha:
			// zapisz jako zmienna
			// zapisz w sql lite? 

		
		//rysowanie wykresu 
	
		

	}
	
	public void drawGraph()
	{
		
	}


}
