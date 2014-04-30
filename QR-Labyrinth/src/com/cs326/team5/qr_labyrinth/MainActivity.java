package com.cs326.team5.qr_labyrinth;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity{

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.activity_main);
        QRLabyrinth qrl = ((QRLabyrinth)getApplicationContext());
        ArrayList<String> IDs;
        if((IDs = loadIDs(qrl.levelIDsFile)) == null){
        	IDs = writeDefaultIDs(qrl.levelIDsFile);
        }
        qrl.setLevelIDs(IDs);
        
        
        ArrayList<String> list = loadIDs(qrl.customIDsFile);
        
        if(list != null){
	        for(String s: list){
	        	Log.d("random tag", s);
	        }
        }
        
        qrl.setCustomIDs(list);


    }

    
    protected void writeIDs(String s, ArrayList<String> IDs){
		//BufferedWriter out;
		String writeString = "";
		OutputStreamWriter os;
		try {
			os = new OutputStreamWriter(openFileOutput(s, Context.MODE_PRIVATE));
		for(String ID: IDs){
			writeString += ID + "\n";
			Log.d("somethign", ID);
			os.write(ID + "\n");
		}
			Log.w("sting", writeString);
			//os.write(writeString);
			os.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	protected ArrayList<String> loadIDs(String s){
		ArrayList<String> IDs = new ArrayList<String>();

		try {

	        InputStream inputStream = openFileInput(s);

	        if ( inputStream != null ) {
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String receiveString = "";
	            String receiveString2 = "";
	            StringBuilder stringBuilder = new StringBuilder();

	            while ( (receiveString = bufferedReader.readLine()) != null ) {
	                stringBuilder.append(receiveString);
	            	if((receiveString2 = bufferedReader.readLine()) != null){
	                    IDs.add(receiveString + "\n\t" + receiveString2);
	            	}
	            }

	            inputStream.close();

	        }
	    }
	    catch (FileNotFoundException e) {
	        Log.e("login activity", "File not found: " + e.toString());
	        return null;
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
	        return null;
	    }
		return IDs;
	}
	
	
	private ArrayList<String> writeDefaultIDs(String s) {
		ArrayList<String> IDs = new ArrayList<String>();
		for(int i=1; i<=10; i++){
			IDs.add("Level " + i + "\n\t0");
		}
		
		writeIDs(s,IDs);
		
		return IDs;
	}
	
    /**
 	 * Handles button clicks for the UI
 	 * @param v view that was clicked
 	 */
 	public void handleButton(View v) {
 		int id = v.getId();

 		switch (id) {
 		case R.id.story: // start server activity
 			startActivity(new Intent(this, SelectLevelActivity.class));
 			break;
 		case R.id.custom_level: // start news activity
 			startActivity(new Intent(this, CustomLevelActivity.class));
 			break;
 		case R.id.exit: // start about activity
 			finish();
 			break;
 		}
 	}
    
}
