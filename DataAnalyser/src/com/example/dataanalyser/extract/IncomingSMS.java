package com.example.dataanalyser.extract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import com.example.dataanalyser.R;
import com.example.dataanalyser.R.id;
import com.example.dataanalyser.R.layout;

import android.support.v7.app.ActionBarActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class IncomingSMS extends ActionBarActivity {
	
	private Button SaveIncoming;
	private TextView SMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incoming);
        
        SMS = (TextView)findViewById(R.id.incoming);
        
        getIncomingSMS();
        
        SaveIncoming = (Button) findViewById(R.id.saveincoming);

  	    SaveIncoming.setOnClickListener(new View.OnClickListener() {
  	    		
  		   @Override
  	    	public void onClick(View v) {

  		        try {
  		            File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Incoming.txt");
  		            myFile.createNewFile();
  		            FileOutputStream fOut = new FileOutputStream(myFile);
  		            OutputStreamWriter myOutWriter = 
  		                                    new OutputStreamWriter(fOut);
  		            myOutWriter.append(SMS.getText());
  		            myOutWriter.close();
  		            fOut.close();
  		            Toast.makeText(getBaseContext(),
  		                    "Done writing SD 'Incoming.txt'",
  		                    Toast.LENGTH_LONG).show();
  		        } catch (Exception e) {
  		            Toast.makeText(getBaseContext(), e.getMessage(),
  		                    Toast.LENGTH_LONG).show();
  		        }
    
   			}
  		});
    }
    
    
    
    private void getIncomingSMS() {
		new Thread(new Runnable() {
	        @Override
	        public void run() {
	        	
	        	gettingIncomingSMS();	
	        }
	    }).run();
	}
    
    
    public void gettingIncomingSMS() {
    	
    	new Thread(new Runnable() {
	        @Override
	        public void run() {

    	  ContentResolver contentResolver = getContentResolver();
    	  Uri uri = Uri.parse("content://sms/inbox/");
    	  StringBuffer sb = new StringBuffer();
    	  int count = 0;
    	  
    	  Cursor cur = contentResolver.query(uri, null, null, null, null);
    	  if (cur.getCount() != 0) {
    	   if (cur.moveToFirst()) {
    	    do {
    	    	sb.append("Incoming message count: " + (count +1) + "\n");
    	     for (int m = 0; m < cur.getColumnCount(); m++) {
    	      if (cur.getColumnName(m).equalsIgnoreCase("address")
    	        || cur.getColumnName(m).equalsIgnoreCase("date")
    	        || cur.getColumnName(m).equalsIgnoreCase("body"))
    	        
    	      {
    	    	  sb.append(cur.getColumnName(m) + "  : "
    	         + cur.getString(m));
    	    	  sb.append("\n");
    	      }
    	     }
    	     sb.append("\n");
    	     count++;
    	    } while (cur.moveToNext());
    	   }
    	  }
    	  cur.close();
    	  cur = null;
    	  SMS.setText(sb);
    	  
	        }
	    }).run();
    }

    public void backtomain (View view) {
        Intent intent = new Intent(this, Extract.class);
        startActivity(intent);
    }
 
  	@Override
  	protected void onStart() {
  		// TODO Auto-generated method stub
  		super.onStart();
  	}
  	
  	@Override
  	protected void onPause() {
  		// TODO Auto-generated method stub
  		super.onPause();
  	}
  	
  	@Override
  	protected void onResume() {
  		// TODO Auto-generated method stub
  		super.onResume();
  	}

  	@Override
  	protected void onStop() {
  		// TODO Auto-generated method stub
  		super.onStop();
  	}

  	@Override
  	protected void onDestroy() {
  		// TODO Auto-generated method stub
  		super.onDestroy();
  	}
    
  	
}
