package com.cs326.team5.qr_labyrinth;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Level selector activity representing the default level selection
 * @author Team 5
 */
public class SelectLevelActivity extends LevelSelectorActivity{
	
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
    
    @Override
    protected void onResume(){
    	super.onResume();
    	QRLabyrinth qrl = ((QRLabyrinth)getApplicationContext());
        Grid g = qrl.getCurrentLevel();
    	if(g != null){
	        String [] t = g.getID().split("\n");
	    	for(int i=0; i<levelList.size(); i++){
	    		String ID = levelList.get(i);
	    		if(ID.startsWith(t[0])){
	    			levelList.set(i,g.getID());
	            	setupListView();
	    		}
	    	}
    	}
    }
    
    /**
     * Populates a list view with level ID's
     */
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
    
    /**
     * Checks for the n'th level file to read a Grid
     * @param n file number
     * @return g level read from file
     */
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
