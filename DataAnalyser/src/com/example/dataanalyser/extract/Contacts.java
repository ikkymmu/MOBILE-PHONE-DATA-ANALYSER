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
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Contacts extends ActionBarActivity {

	private Button SaveContacts;
	private TextView contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        contacts = (TextView)findViewById(R.id.contacts);
        
        gettingContacts();

        SaveContacts = (Button) findViewById(R.id.savecontacts);

	    SaveContacts.setOnClickListener(new View.OnClickListener() {
	    		
		   @Override
	    	public void onClick(View v) {

		        try {
		            File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Contacts.txt");
		            myFile.createNewFile();
		            FileOutputStream fOut = new FileOutputStream(myFile);
		            OutputStreamWriter myOutWriter = 
		                                    new OutputStreamWriter(fOut);
		            myOutWriter.append(contacts.getText());
		            myOutWriter.close();
		            fOut.close();
		            Toast.makeText(getBaseContext(),
		                    "Done writing SD 'Contacts.txt'",
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
        
    	StringBuffer sb = new StringBuffer();
        ContentResolver cr = getContentResolver();
          Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
          sb.append( "Contacts");
          if (cur.getCount() > 0) {
              while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    
                    if (Integer.parseInt(cur.getString(
                          cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                       Cursor pCur = cr.query(
                                 ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                 null,
                                 ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                 new String[]{id}, null);
                       while (pCur.moveToNext()) {
                           String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                           
                           sb.append( "\n\nName: " +name+ "\nNumber: " +phoneNo);
                           
                           sb.append("\n\n---------------");
                       }
                      pCur.close();
                  }     	    	
              } 
           	cur.close();
         	contacts.setText(sb);
          }
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
