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
import android.view.View;
import android.view.Window;

/**
 * Initial activity representing the title menu interface
 * @author Team 5
 */
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
        
        qrl.setCustomIDs(list);
    }

    /**
     * Output a list of level ID's to file
     * @param s name of the file
     * @param IDs list of the ID's
     */
    protected void writeIDs(String s, ArrayList<String> IDs){
		String writeString = "";
		OutputStreamWriter os;
		try {
			os = new OutputStreamWriter(openFileOutput(s, Context.MODE_PRIVATE));
		for(String ID: IDs){
			writeString += ID + "\n";
			os.write(ID + "\n");
		}
			os.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * Input a list of level ID's from file
     * @param s name of the file
     * @return IDs list of the ID's
     */
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
	        return null;
	    } catch (IOException e) {
	        return null;
	    }
		return IDs;
	}
	
	/**
	 * Writes default ID's to file
	 * @param s name of file
	 * @return IDs list of ID's
	 */
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
