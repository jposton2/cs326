package com.cs326.team5.qr_labyrinth;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maze_view);
		Intent i = getIntent();
		Grid grid = null;
		//Log.i("GameActivity", test);
		MazeView mv = (MazeView) findViewById(R.id.maze);
		mv.setGrid(grid);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

}
