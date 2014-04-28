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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SelectLevelActivity extends LevelSelectorActivity{
	
	private int qrheight = 400;
	private int qrwidth = 400;
    private TextView prevClick = null;
    private List<String> levelList = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_level);
        QRLabyrinth qrl = ((QRLabyrinth)getApplicationContext());
        levelList = qrl.getLevelIDs();
        if(levelList != null){
        	setupListView();
        }
    }
    
    protected void setupListView(){
    	ListView list = (ListView) findViewById(R.id.level_list);
    	
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
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.list_row, null);
				TextView textView = (TextView) view.findViewById(R.id.listRow);
				try {	//decode database text to print
					textView.setText(URLDecoder.decode(levelList.get(position), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return view;
			}
		});
    }
    
    public Grid checkFile(String n){
    	Grid g;
    	
    	File file = getBaseContext().getFileStreamPath("level_" + n);
        if(!file.exists()){
        	g = QRHandler.getGrid(QRHandler.getLevel(Integer.valueOf(n)), 400, 400, "Level " + n);
    	    //Log.w("Array", Integer.toString(i));
        	//Log.w("Array", QRHandler.getLevel(i));
        	writeGrid("level_" + n, g);
        }
        else{
            Log.w("Lol", "it existssss!!!");
            g = loadGrid(file);
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
 		case R.id.listRow:	// if a level was clicked
			if(prevClick != null){
				prevClick.setBackgroundColor(0x00000000);
			}
			v.setBackgroundColor(0x650000FF);
			prevClick = (TextView) v;
			
			String [] s = prevClick.getText().toString().split("\n");
			String [] s2 = s[0].split(" ");
			Grid g = checkFile(s2[1]);
			
			((QRLabyrinth)getApplicationContext()).setCurrentLevel(g);

			findViewById(R.id.play).setAlpha(1);
			
			break;
		case R.id.back: // if back button was clicked
			finish();
			break;
		}
 	}

}
