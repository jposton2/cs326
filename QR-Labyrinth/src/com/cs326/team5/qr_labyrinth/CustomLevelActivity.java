package com.cs326.team5.qr_labyrinth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class CustomLevelActivity extends Activity {
	
	private int qrheight = 400;
	private int qrwidth = 400;
	private TextView prevClick = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkFiles();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom);
    }
    
    public void scan(){
    	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
    	intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
    	startActivityForResult(intent, 0);
    }
    
    public void checkFiles(){
    	QRHandler h = new QRHandler();  
    	ArrayList<Grid> gridList = new ArrayList<Grid>();
        for(int i = 1; i <= 10; i++){
                File file = getBaseContext().getFileStreamPath("level_" + Integer.toString(i));
                if(!file.exists()){
//                      Grid g = h.getGrid("lol", 400, 400);
                      Grid g = h.getGrid(h.getLevel(i), 400, 400);
                      g.setName("level_"+Integer.toString(i));
                      g.setHighscore(0);
                      writeGrid("level_"+Integer.toString(i), g);
                      gridList.add(g);
                }
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	   if (requestCode == 0) {
	      if (resultCode == RESULT_OK) {
	         String contents = intent.getStringExtra("SCAN_RESULT");
	         
	         // We can use format to check if it is a URL, or TEXT or something!
	         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	         
	         QRHandler qr = new QRHandler();
	         Grid g = qr.getGrid(contents, qrheight, qrwidth);
	         
	         
	         File file = getBaseContext().getFileStreamPath(contents);
	         if(!file.exists())
	         	writeGrid(contents, g);
	      }
	   }
	}
    
    // TO PUT IN OTHER ACTIVITY
	public void writeGrid(String s, Grid g){
		FileOutputStream fos;
		ObjectOutputStream os;
		try {
			fos = openFileOutput(s, Context.MODE_PRIVATE);
			os = new ObjectOutputStream(fos);
			os.writeObject(this);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// TO PUT IN OTHER ACTIVITYYYYY
	public Grid loadGrid(String s){
		FileInputStream fis;
		ObjectInputStream is;
		Grid g;
		try {
			fis = openFileInput(s);
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
		
		return g;
	}
	
    /**
 	 * Handles button clicks for the UI
 	 * @param v view that was clicked
 	 */
 	public void handleButton(View v) {
 		int id = v.getId();

 		switch (id) {
 		case R.id.play:	// if play button was clicked
 			if(prevClick != null){
 				//startActivity(new Intent(this, GameActivity.class));
 				prevClick = null;
 			}
 			break;
 		case R.id.scan:	// if scan button was clicked
 			scan();
 			break;
 		case R.id.listRow:	// if a level was clicked
			if(prevClick != null){
				prevClick.setBackgroundColor(0x00000000);
			}
			v.setBackgroundColor(0x650000FF);
			prevClick = (TextView) v;
			
			findViewById(R.id.play).setAlpha(1);
			findViewById(R.id.trash).setAlpha(1);
			
			break;
		case R.id.back: // if back button was clicked
			finish();
			break;
		}
 	}
}
