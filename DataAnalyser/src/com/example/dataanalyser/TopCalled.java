package com.example.dataanalyser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

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


public class TopCalled extends ActionBarActivity {
	
	private TextView contacts;
	private Button Save;

	
	StringBuffer sb = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topcalled);
        
        contacts = (TextView)findViewById(R.id.top);
        
        gettingContacts();
        
        contacts.setText(sb);
        
        Save = (Button) findViewById(R.id.saveTop);

	    Save.setOnClickListener(new View.OnClickListener() {
	    		
		   @Override
	    	public void onClick(View v) {

		        try {
		            File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/TopCalled.txt");
		            myFile.createNewFile();
		            FileOutputStream fOut = new FileOutputStream(myFile);
		            OutputStreamWriter myOutWriter = 
		                                    new OutputStreamWriter(fOut);
		            myOutWriter.append(contacts.getText());
		            myOutWriter.close();
		            fOut.close();
		            Toast.makeText(getBaseContext(),
		                    "Done writing SD 'TopCalled.txt'",
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
 	        	displayContacts();
 	        }
 	    }).run();
 	}
    
  private void displayContacts() {

    	//StringBuffer sb = new StringBuffer();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, ContactsContract.Contacts.TIMES_CONTACTED + " DESC"+ " LIMIT 5");
        sb.append( "Most called Contacts\n");
          System.out.println("1");
          if (cur.getCount() > 0) {
              while (cur.moveToNext()) {
            	  System.out.println("2");
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String timesC = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.TIMES_CONTACTED));
                    String lastC = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LAST_TIME_CONTACTED));
                    Date callC = new Date(Long.valueOf(lastC));
                    
                    if (Integer.parseInt(cur.getString(
                          cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                       Cursor pCur = cr.query(
                                 ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                 null,
                                 ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                 new String[]{id}, null);
                       
                       System.out.println("3");
                       
                       while (pCur.moveToNext()) {
                           String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                           
                           sb.append( "\nName: " +name+ "  Number: " +phoneNo);
                           
                           sb.append("\nTimes Contacted: " +timesC+  "\nLast Contacted: " +callC);
                           
                           sb.append("\n-----");
                           }
                      pCur.close();} 
                    } 
           	cur.close();
         	System.out.println("complted method");
           	
          }
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
