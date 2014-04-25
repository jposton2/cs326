package com.cs326.team5.qr_labyrinth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

public class MainActivity extends Activity implements Serializable{
	private int qrheight = 400;
	private int qrwidth = 400;
	//SharedPreferences prefs = getSharedPreferences("userValues", MODE_PRIVATE);
	// QR example here https://github.com/zxing/zxing/blob/master/androidtest/src/com/google/zxing/client/androidtest/ZXingTestActivity.java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Tests if it is the first run or not.
        /*if(prefs.getBoolean("firstRun", true)){
        	SharedPreferences.Editor editor = prefs.edit();
        	editor.putBoolean("firstRun", false);
        	editor.commit();
        	// TODO: Create level files here
        }*/
        setContentView(R.layout.activity_main);
        QRLabyrinth qrl = ((QRLabyrinth)getApplication());
        qrl.setCustomList(checkCustomFiles());
        qrl.setLevelList(checkFiles());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public ArrayList<Grid> checkCustomFiles(){
    	QRHandler h = new QRHandler();  
    	ArrayList<Grid> gridList = new ArrayList<Grid>();
        	File dir = getBaseContext().getFilesDir();
        	int currNum = 0;
        	for(File f: dir.listFiles()){
        		if(f.isFile()){
        			if(f.getName().length() > 7)
        			if(f.getName().substring(0,6).equals("custom")){
        				int temp = Integer.parseInt(f.getName().substring(6));
        				currNum = (temp > currNum ? temp : currNum);
        			}
        		}
        	}
        for(int i = 1; i <= currNum; i++){
        	File file = getBaseContext().getFileStreamPath("custom_" + Integer.toString(i));
            if(!file.exists()){
//                      Grid g = h.getGrid("lol", 400, 400);
	        	Grid g = h.getGrid(h.getLevel(i), 400, 400, "Level " + Integer.toString(i));
	        	Log.w("Array", Integer.toString(i));
	        	Log.w("Array", h.getLevel(i));
	        	g.setName("Level "+Integer.toString(i));
	        	g.setHighscore(0);
	        	writeGrid("aba"+Integer.toString(i), g);
	        	gridList.add(g);
            }
            else{
                Log.w("Lol", "it existssss!!!");
                gridList.add(loadGrid(file));
            }
        }
        Log.w("Array", Integer.toString(gridList.size()));
        
        if(gridList.isEmpty()){
        	return null;
        }
        
        return gridList;
    }
    public ArrayList<Grid> checkFiles(){
    	QRHandler h = new QRHandler();  
    	ArrayList<Grid> gridList = new ArrayList<Grid>();
        for(int i = 1; i <= 1; i++){
        	File file = getBaseContext().getFileStreamPath("aba" + Integer.toString(i));
            //if(!file.exists()){
//                      Grid g = h.getGrid("lol", 400, 400);
	        	Grid g = h.getGrid(h.getLevel(i), 400, 400, "level_"+Integer.toString(i));
        	    Log.w("Array", Integer.toString(i));
	        	Log.w("Array", h.getLevel(i));
	        //	writeGrid("aba"+Integer.toString(i), g);
	        	gridList.add(g);
            //}
            //else{
            //        Log.w("Lol", "it existssss!!!");
            //        gridList.add(loadGrid(file));
            //}

        	//writeGrid("level_"+Integer.toString(i), g);
                //}
        }
        Log.w("Array", Integer.toString(gridList.size()));
        
        if(gridList.isEmpty()){
        	return null;
        }
        
        return gridList;
    }
	public void writeGrid(String s, Grid g){
		FileOutputStream fos;
		ObjectOutputStream os;
		try {
			fos = openFileOutput(s, Context.MODE_PRIVATE);
			os = new ObjectOutputStream(fos);
			os.writeObject(g);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// TO PUT IN OTHER ACTIVITYYYYY
	public Grid loadGrid(File f){
		FileInputStream fis;
		ObjectInputStream is;
		Grid g;
		try {
			fis = new FileInputStream(f);
			is = new ObjectInputStream(fis);
			g = (Grid) is.readObject();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		Log.w("LoL", "We got this far");
		return g;
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
