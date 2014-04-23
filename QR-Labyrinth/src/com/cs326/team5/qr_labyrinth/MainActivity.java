package com.cs326.team5.qr_labyrinth;

import java.io.File;
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
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity {
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
    }
    
    public void onExit(){
    	Intent intent = new Intent(this, MainActivity.class);
    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	intent.putExtra("exit", "true");
    	startActivity(intent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
 			onExit();
 			break;
 		}
 	}
    
}
