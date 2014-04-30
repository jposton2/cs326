package com.cs326.team5.qr_labyrinth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import android.content.Context;
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
 * Level selector activity representing the custom level selection
 * @author Team 5
 */
public class CustomLevelActivity extends LevelSelectorActivity{

	private int qrheight = 400;
	private int qrwidth = 400;
	private TextView prevClick = null;
	private ArrayList<String> levelList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_custom);
		QRLabyrinth qrl = ((QRLabyrinth)getApplicationContext());
		levelList = qrl.getCustomIDs();
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
	 * Begins QR code scanning process
	 */
	public void scan(){
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		startActivityForResult(intent, 0);
	}

	/**
	 * Populates a list view with level ID's
	 */
	protected void setupListView(){
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

	/**
	 * Handles the returned information from the QR code scann
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");

				File dir = getBaseContext().getFilesDir();
				int currNum = 0;
				for(File f: dir.listFiles()){
					if(f.isFile()){
						if(f.getName().length() > 6){
							if(f.getName().substring(0,7).equals("custom_")){
								String [] s = f.getName().split("_");
								int temp = Integer.parseInt(s[1]);
								currNum = (temp > currNum ? temp : currNum);
							}
						}
					}
				}
				
				String name = "Custom " + Integer.toString(currNum + 1);
				Grid g = QRHandler.getGrid(contents, qrheight, qrwidth, name);
				writeGrid("custom_" + (currNum + 1), g);
				if(levelList == null){
					levelList = new ArrayList<String>();
				}
				levelList.add(g.getID());
				((QRLabyrinth)getApplicationContext()).setCustomIDs(levelList);
				writeIDs(((QRLabyrinth)getApplicationContext()).customIDsFile, levelList);
				setupListView();
			}
		}
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
	 * Checks for the n'th level file to read a Grid
	 * @param n file number
	 * @return g level read from file
	 */
	public Grid checkFile(String n){
		File file = getBaseContext().getFileStreamPath("custom_" + n);
		return loadGrid(file);
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

			String [] s = prevClick.getText().toString().split("\n");
			String [] s1 = s[0].split(" ");
			Grid g = checkFile(s1[1]);

			((QRLabyrinth)getApplicationContext()).setCurrentLevel(g);

			findViewById(R.id.play).setAlpha(1);
			findViewById(R.id.trash).setAlpha(1);

			break;
		case R.id.trash:
			if(prevClick != null){
				prevClick.setBackgroundColor(0x00000000);
			}
			v.setBackgroundColor(0x650000FF);
			//			prevClick = (TextView) v;

			String t = prevClick.getText().toString();
			levelList.remove(t);

			String [] s2 = t.split("\n");
			String [] s3 = s2[0].split(" ");
			File file = getBaseContext().getFileStreamPath("custom_" + s3[1]);
			file.delete();

			setupListView();
			prevClick = (TextView) v;
			break;
		case R.id.back: // if back button was clicked
			finish();
			break;
		}
	}
}
