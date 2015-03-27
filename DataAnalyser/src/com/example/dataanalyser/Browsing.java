package com.example.dataanalyser;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Browser;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Browsing extends ActionBarActivity {

	private TextView history;
	StringBuffer sb = new StringBuffer();
	private Button Save;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browsing);
        
        history = (TextView)findViewById(R.id.browsing);
        
        gettingHistory();
        
        history.setText(sb);
        
        
        Save = (Button) findViewById(R.id.saveBrow);

  	    Save.setOnClickListener(new View.OnClickListener() {
  	    		
  		   @Override
  	    	public void onClick(View v) {

  		        try {
  		            File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/BrowsingHistory.txt");
  		            myFile.createNewFile();
  		            FileOutputStream fOut = new FileOutputStream(myFile);
  		            OutputStreamWriter myOutWriter = 
  		                                    new OutputStreamWriter(fOut);
  		            myOutWriter.append(history.getText());
  		            myOutWriter.close();
  		            fOut.close();
  		            Toast.makeText(getBaseContext(),
  		                    "Done writing SD 'Browsing.txt'",
  		                    Toast.LENGTH_LONG).show();
  		        } catch (Exception e) {
  		            Toast.makeText(getBaseContext(), e.getMessage(),
  		                    Toast.LENGTH_LONG).show();
  		        }
    
   			}
  		});
    }
    
   private void gettingHistory() {
 		new Thread(new Runnable() {
 	        @Override
 	        public void run() {
 	        	displayHistory();
 	        }
 	    }).run();
 	}
    
  private void displayHistory() {
	  
    	ContentResolver cr = getContentResolver();
    	Cursor cur = cr.query(Browser.BOOKMARKS_URI,null, null,null, Browser.BookmarkColumns.VISITS + " DESC"+ " LIMIT 25");
    	sb.append( "Most Visted Websites\n");
    	if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
            	
        String Title = cur.getString(cur.getColumnIndex(Browser.BookmarkColumns.TITLE)); 
        String UrL = cur.getString(cur.getColumnIndex(Browser.BookmarkColumns.URL)); 
        String NumberVisits = cur.getString(cur.getColumnIndex(Browser.BookmarkColumns.VISITS)); 
    	
        sb.append( "\nTitle: "+Title +" \nURL: "+UrL+" \nNumber of Visits : "+NumberVisits );
    	sb.append("\n\n------------------");
    	}
    }	
	    cur.close();	
   }
  
    
    public void backtomain (View view) {
        Intent intent = new Intent(this, Analyse.class);
        startActivity(intent);
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