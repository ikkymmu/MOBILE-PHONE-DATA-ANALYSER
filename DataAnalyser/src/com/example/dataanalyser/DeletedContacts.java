package com.example.dataanalyser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DeletedContacts extends ActionBarActivity {
	
	private TextView contacts;
	private Button Save;
	
	StringBuffer sb = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analyse);
        
        contacts = (TextView)findViewById(R.id.analyse);
        
        gettingContacts();
        
        contacts.setText(sb);
        
        
        Save = (Button) findViewById(R.id.saveanalyse);

	    Save.setOnClickListener(new View.OnClickListener() {
	    		
		   @Override
	    	public void onClick(View v) {

		        try {
		            File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Deleted.txt");
		            myFile.createNewFile();
		            FileOutputStream fOut = new FileOutputStream(myFile);
		            OutputStreamWriter myOutWriter = 
		                                    new OutputStreamWriter(fOut);
		            myOutWriter.append(contacts.getText());
		            myOutWriter.close();
		            fOut.close();
		            Toast.makeText(getBaseContext(),
		                    "Done writing SD 'DeletedContacts.txt'",
		                    Toast.LENGTH_LONG).show();
		        } catch (Exception e) {
		            Toast.makeText(getBaseContext(), e.getMessage(),
		                    Toast.LENGTH_LONG).show();
		        }
  
 			}
		});
        
    }
    
   private void gettingContacts() {
 		new Thread(new Runnable() {
 	        @Override
 	        public void run() {
 	        	delContacts();
 	        }
 	    }).run();
 	}
    
   
  private void delContacts() {

	String[] projection = new String[] {
			RawContacts.DELETED, 
			RawContacts.DISPLAY_NAME_PRIMARY
			};

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(RawContacts.CONTENT_URI, projection, null, null, null);
        sb.append( "\n\nAny Deleted Contacts\n");
        
          if(cur.moveToFirst()) {
        		while(!cur.isAfterLast()) {
        			
        	        int deletedindex = cur.getColumnIndex(RawContacts.DELETED);
        	        String name = cur.getString(cur.getColumnIndex(RawContacts.DISPLAY_NAME_PRIMARY));

        			boolean deleted = (cur.getInt(deletedindex) == 1);
        			if(deleted) {
        			
                    sb.append( "\n Deleted Name: " +name);
                    
                    sb.append("\n-----");

        			 }

        			cur.moveToNext();
        		}

        		sb.append("\n-----");
      	}
           	cur.close();

         	System.out.println("complted method");
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
