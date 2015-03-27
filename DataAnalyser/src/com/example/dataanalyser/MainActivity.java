package com.example.dataanalyser;

import com.example.dataanalyser.extract.Extract;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	
	private TextView information;
	
	String _OSVERSION = System.getProperty("os.version");
	String _RELEASE = android.os.Build.VERSION.RELEASE;
	String _MODEL = android.os.Build.MODEL; 
	String _PRODUCT = android.os.Build.PRODUCT; 
	String _BRAND = android.os.Build.BRAND; 
	String _MANUFACTURER = android.os.Build.MANUFACTURER; 
	String _CPU_ABI = android.os.Build.CPU_ABI; 
	String _HARDWARE = android.os.Build.HARDWARE;
	String _ID = android.os.Build.ID;  
	String _HOST = android.os.Build.HOST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        information = (TextView)findViewById(R.id.phoneinfo);
        
        getPhoneInformation();
    }
    
    public void Extract(View view) {
        Intent intent = new Intent(this, Extract.class);
        startActivity(intent);
    }
    
    public void Analyse (View view) {
        Intent intent = new Intent(this, Analyse.class);
        startActivity(intent);
    }
    
    
    public void Guide (View view) {
        Intent intent = new Intent(this, Guide.class);
        startActivity(intent);
    }
    
    private void getPhoneInformation() {

    	StringBuffer sb = new StringBuffer();
    	
    	sb.append( "Phone Information :");
    	
    	sb.append( "\nVersion : "+_OSVERSION +" \nRelease : "+_RELEASE+""
    				+ "\nModel : "+_MODEL +" \nBrand : "+_BRAND+" "
    				+ "\nCPU : "+_CPU_ABI+ "\nHardware : "+_HARDWARE+ "\nID : "+_ID+ "\nManufacturer : "+_MANUFACTURER+ ""
    				+ "\nHost : "+_HOST);
    	
    	information.setText(sb);
    		
    	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
