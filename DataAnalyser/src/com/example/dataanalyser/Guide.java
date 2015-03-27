package com.example.dataanalyser;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;


public class Guide extends ActionBarActivity {
	
	private TextView guideinfo;
	StringBuffer sb = new StringBuffer();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        
       guideinfo = (TextView)findViewById(R.id.guide);

       sb.append("---------------");
       sb.append("\nWelcome To The Mobile Data Analyser User Guide\n");
       sb.append("---------------\n\n");
       sb.append("This is the first ever forensic analyser application for android. This app can extract and analyse various data from your android device."); 
       sb.append("\nIt can extract messages, call logs and contacts and analyse this data.");
       sb.append("\nIts easy to use. Simply tap the buttons provided to navigate through the app."
       		+ " The save button creates a text file with the current data to your directory on the phone. A pop up meesage will appear to confirm this.");
    
       guideinfo.setText(sb);
    
    }
    
    public void backtomain (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    
}