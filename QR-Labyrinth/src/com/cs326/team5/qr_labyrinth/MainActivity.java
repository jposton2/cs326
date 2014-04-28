package com.cs326.team5.qr_labyrinth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity{
	private int qrheight = 400;
	private int qrwidth = 400;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
       /* super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        QRLabyrinth qrl = ((QRLabyrinth)getApplication());
        qrl.setCustomList(checkCustomFiles());
        qrl.setLevelList(checkFiles());
        */
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
        for(String s: list){
        	Log.d("random tag", s);
        }
        
    //    qrl.setCustomIDs(loadIDs(qrl.customIDsFile));

    }

	/*public ArrayList<Grid> checkCustomFiles(){
    	ArrayList<Grid> gridList = new ArrayList<Grid>();
        	File dir = getBaseContext().getFilesDir();
        	int currNum = 0;
        	for(File f: dir.listFiles()){
        		if(f.isFile()){
        			if(f.getName().length() > 7){
        			if(f.getName().substring(0,6).equals("custom")){
        				Log.w("Name", f.getName());
        				int temp = Integer.parseInt(f.getName().substring(7));
        				currNum = (temp > currNum ? temp : currNum);
        			}
        			}
        		}
        	}
        for(int i = 1; i <= currNum; i++){
        	File file = getBaseContext().getFileStreamPath("custom_" + Integer.toString(i));
            if(!file.exists()){
//                      Grid g = h.getGrid("lol", 400, 400);
	        	Grid g = QRHandler.getGrid(QRHandler.getLevel(i), 400, 400, "custom_" + Integer.toString(i));
	        	
	        	Log.w("Array", Integer.toString(i));
	        	Log.w("Array", QRHandler.getLevel(i));
	        	writeGrid("custom_"+Integer.toString(i), g);
	        	gridList.add(g);
            }
            else{
                Log.w("Custom Lol", "it existssss!!!");
                gridList.add(loadGrid(file));
            }
        }
        Log.w("Array", Integer.toString(gridList.size()));
        
        if(gridList.isEmpty()){
        	return null;
        }
        
        return gridList;
    }
    */
    
    
	protected ArrayList<String> loadIDs(File f){
		BufferedReader in;
		ArrayList<String> IDs = new ArrayList<String>();
		String s;
		try {
			in = new BufferedReader(new FileReader(f));
			while((s = in.readLine()) != null){
				IDs.add(s);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return IDs;
	}
	
	protected void writeIDs(File f, ArrayList<String> IDs){
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(f));
			
			for(String ID: IDs){
				out.write(ID);
				out.newLine();
			}
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<String> writeDefaultIDs(File f) {
		ArrayList<String> IDs = new ArrayList<String>();
		for(int i=1; i<=10; i++){
			IDs.add("Level " + i + "\n\t0");
		}
		
		writeIDs(f,IDs);
		
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
