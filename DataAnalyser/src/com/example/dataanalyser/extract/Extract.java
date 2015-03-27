package com.example.dataanalyser.extract;

import com.example.dataanalyser.MainActivity;
import com.example.dataanalyser.R;
import com.example.dataanalyser.R.id;
import com.example.dataanalyser.R.layout;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;



public class Extract extends ActionBarActivity {
	
	ProgressBar layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extract);
        
        layout = (ProgressBar) findViewById(R.id.progressbar_view);
        
        layout.setVisibility(View.INVISIBLE);

    }

    public void CallHistory(View view) {
        Intent intent = new Intent(this, CallHistory.class);
        
        layout.setVisibility(View.VISIBLE);
        
        startActivity(intent);
    }
    
    public void Contacts(View view) {
        Intent intent = new Intent(this, Contacts.class);
        
        layout.setVisibility(View.VISIBLE);
        startActivity(intent);
    }
    
    public void Incoming(View view) {
        Intent intent = new Intent(this, IncomingSMS.class);
        
        layout.setVisibility(View.VISIBLE);
        startActivity(intent);
    }
    
    public void Outgoing(View view) {
        Intent intent = new Intent(this, OutgoingSMS.class);
        
        layout.setVisibility(View.VISIBLE);
        startActivity(intent);
    }
    
    public void backtomain (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        
        layout.setVisibility(View.VISIBLE);
        startActivity(intent);
    }
    
}