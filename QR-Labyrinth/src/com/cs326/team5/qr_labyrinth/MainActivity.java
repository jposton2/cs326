package com.cs326.team5.qr_labyrinth;




import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

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
import android.widget.TextView;


public class MainActivity extends Activity {
	private int qrheight = 400;
	private int qrwidth = 400;
	SharedPreferences prefs = getSharedPreferences("userValues", MODE_PRIVATE);
	// QR example here https://github.com/zxing/zxing/blob/master/androidtest/src/com/google/zxing/client/androidtest/ZXingTestActivity.java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tests if it is the first run or not.
        if(prefs.getBoolean("firstRun", true)){
        	SharedPreferences.Editor editor = prefs.edit();
        	editor.putBoolean("firstRun", false);
        	editor.commit();
        	// TODO: Create level files here
        }
        setContentView(R.layout.activity_main);
    }
    
    public void scan(View view){
    	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
    	intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
    	startActivityForResult(intent, 0);
    }
    

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	   if (requestCode == 0) {
    	      if (resultCode == RESULT_OK) {
    	    	 TextView test = (TextView) findViewById(R.id.textView2);
    	         String contents = intent.getStringExtra("SCAN_RESULT");
    	         
    	         // We can use format to check if it is a URL, or TEXT or something!
    	         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
    	         
    	         test.setText(contents);
    	         QRHandler qr = new QRHandler();
    	         Grid g = qr.getGrid(contents, qrheight, qrwidth);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
