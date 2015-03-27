package com.example.dataanalyser.extract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import com.example.dataanalyser.R;
import com.example.dataanalyser.R.id;
import com.example.dataanalyser.R.layout;

import android.support.v7.app.ActionBarActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class CallHistory extends ActionBarActivity {
	
	private Button SaveCallHistory;
	private TextView call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.callhistory);
        
        call = (TextView)findViewById(R.id.call);
        
        gettingCallDetails();

        SaveCallHistory = (Button) findViewById(R.id.savehistory);

	    SaveCallHistory.setOnClickListener(new View.OnClickListener() {
	    		
		   @Override
	    	public void onClick(View v) {

		        try {
		            File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/CallHistroy.txt");
		            myFile.createNewFile();
		            FileOutputStream fOut = new FileOutputStream(myFile);
		            OutputStreamWriter myOutWriter = 
		                                    new OutputStreamWriter(fOut);
		            myOutWriter.append(call.getText());
		            myOutWriter.close();
		            fOut.close();
		            Toast.makeText(getBaseContext(),
		                    "Done writing SD 'CallHistory.txt'",
		                    Toast.LENGTH_LONG).show();
		        } catch (Exception e) {
		            Toast.makeText(getBaseContext(), e.getMessage(),
		                    Toast.LENGTH_LONG).show();
		        }
  
 			}
		});
    }
    
    private void gettingCallDetails() {
		new Thread(new Runnable() {
	        @Override
	        public void run() {
	        	
	        	getCallDetails();	
	        }
	    }).run();
	}
    
    private void getCallDetails() {

    	StringBuffer sb = new StringBuffer();
    	ContentResolver cr = getContentResolver();
    	Cursor cur = cr.query( CallLog.Calls.CONTENT_URI,null, null,null, null);
    	int number = cur.getColumnIndex( CallLog.Calls.NUMBER ); 
    	int type = cur.getColumnIndex( CallLog.Calls.TYPE );
    	int date = cur.getColumnIndex( CallLog.Calls.DATE);
    	int duration = cur.getColumnIndex( CallLog.Calls.DURATION);
    	sb.append( "Call History :");
	    	while ( cur.moveToNext() ) {
	    	String phNumber = cur.getString( number );
	    	String callType = cur.getString( type );
	    	String callDate = cur.getString( date );
	    	Date callDayTime = new Date(Long.valueOf(callDate));
	    	String callDuration = cur.getString( duration );
	    	String dir = null;
	    	int dircode = Integer.parseInt( callType );
		    	switch( dircode ) {
		    	
		    	case CallLog.Calls.OUTGOING_TYPE:
		    	dir = "OUTGOING";
		    	break;
		
		    	case CallLog.Calls.INCOMING_TYPE:
		    	dir = "INCOMING";
		    	break;
		
		    	case CallLog.Calls.MISSED_TYPE:
		    	dir = "MISSED";
		    	break;
		    	}
	    	sb.append( "\nPhone Number: "+phNumber +" \nCall Type: "+dir+" \nCall Date: "+callDayTime+" \nCall duration in sec : "+callDuration );
	    	sb.append("\n\n------------------");
	    	}
    	
	    cur.close();
    	call.setText(sb);
    		
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