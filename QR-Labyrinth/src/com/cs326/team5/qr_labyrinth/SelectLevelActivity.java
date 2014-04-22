package com.cs326.team5.qr_labyrinth;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SelectLevelActivity extends Activity {
	
	private int qrheight = 400;
	private int qrwidth = 400;
    private TextView prevClick = null;
    String s = null;
    List<String> lpList = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_level);
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
 		case R.id.play:	// if play button was clicked
 			//if(prevClick != null){
 				/*s = "what the hell\t1902364";
 				//TEMPORARY CODE
 				ListView list = (ListView) findViewById(R.id.level_list);
 				list.setAdapter(new BaseAdapter() {	//adapter for list of Locations
 					public int getCount() {
 						return 1;
 					}

 					public Object getItem(int position) {
 						return s;
 					}

 					@Override
 					public long getItemId(int position) {
 						return position;
 					}

 					@Override
 					public View getView(int position, View convertView, ViewGroup parent) {
 						LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
 						View view = inflater.inflate(R.layout.list_row, null);
 						TextView textView = (TextView) view.findViewById(R.id.textLocation);
 						try {
							textView.setText(URLDecoder.decode(s, "UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
 						return view;
 					}
 				});*/
 			lpList = new ArrayList<String>();
 			lpList.add("blarg");
 			lpList.add("does this work?");
			ListView list = (ListView) findViewById(R.id.level_list);
			list.setAdapter(new BaseAdapter() {	//adapter for list of Locations
				public int getCount() {
					return lpList.size();
				}

				public Object getItem(int position) {
					return lpList.get(position);
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
						textView.setText(URLDecoder.decode(lpList.get(position).toString(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					return view;
				}
			});
 				//END TEMP CODE
 				
 	 			//startActivity(new Intent(this, GameActivity.class));
 	 			prevClick = null;
 			//}
 			break;
 		case R.id.listRow:	// if a level was clicked
			if(prevClick != null){
				prevClick.setBackgroundColor(0x00000000);
			}
			v.setBackgroundColor(0x650000FF);
			prevClick = (TextView) v;
			
			findViewById(R.id.play).setAlpha(1);
			
			break;
		case R.id.back: // if back button was clicked
			finish();
			break;
		}
 	}

}
