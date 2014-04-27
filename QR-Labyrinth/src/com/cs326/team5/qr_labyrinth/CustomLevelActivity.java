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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CustomLevelActivity extends Activity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int qrheight = 400;
	private int qrwidth = 400;
	private TextView prevClick = null;
	private List<Grid> levelList = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom);
        QRLabyrinth qrl = ((QRLabyrinth)getApplicationContext());
        levelList = qrl.getCustomList();
        if(levelList != null){
        	setupListView();
        }
    }
    
    public void scan(){
    	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
    	intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
    	startActivityForResult(intent, 0);
    }
    
    private void setupListView(){
    	ListView list = (ListView) findViewById(R.id.custom_list);
		list.setAdapter(new BaseAdapter() {	//adapter for list of Locations
			public int getCount() {
				return levelList.size();
			}

			public Object getItem(int position) {
				return levelList.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if(levelList.get(position) == null){
					return null;
				}
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.list_row, null);
				TextView textView = (TextView) view.findViewById(R.id.listRow);
				try {	//decode database text to print
					textView.setText(URLDecoder.decode(levelList.get(position).toString(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return view;
			}
		});
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	   if (requestCode == 0) {
	      if (resultCode == RESULT_OK) {
	         String contents = intent.getStringExtra("SCAN_RESULT");
	         
	         // We can use format to check if it is a URL, or TEXT or something!
	         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	         
	         QRHandler qr = new QRHandler();
	         // TODO: Add custom names here

        	File dir = getBaseContext().getFilesDir();
        	int currNum = 0;
        	for(File f: dir.listFiles()){
        		if(f.isFile()){
        			if(f.getName().length() > 6){
        				Log.w("lols", f.getName().substring(0,6));
        			if(f.getName().substring(0,6).equals("custom")){
        				int temp = Integer.parseInt(f.getName().substring(5));
        				currNum = (temp > currNum ? temp : currNum);
        			}
        			}
        		}
        	}
        	String name = "custom_" + Integer.toString(currNum + 1);
        	Log.w("ID", contents);
	         Grid g = qr.getGrid(contents, qrheight, qrwidth, "custom_"+ Integer.toString(currNum + 1));
			((QRLabyrinth)getApplicationContext()).setCurrentLevel(g);
 			startActivity(new Intent(this, GameActivity.class));
	         
	         
	         File file = getBaseContext().getFileStreamPath(name);
	         if(!file.exists())
	         	writeGrid(name, g);
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
				prevClick.setBackgroundColor(0x00000000);
 				startActivity(new Intent(this, GameActivity.class));
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
			
			for(Grid g: ((QRLabyrinth)getApplicationContext()).getCustomList()){
				if(g.getID().equals(prevClick.getText().toString())){
					((QRLabyrinth)getApplicationContext()).setCurrentLevel(g);
				}
			}
			
			findViewById(R.id.play).setAlpha(1);
			findViewById(R.id.trash).setAlpha(1);
			
			break;
		case R.id.back: // if back button was clicked
			finish();
			break;
		}
 	}
}
