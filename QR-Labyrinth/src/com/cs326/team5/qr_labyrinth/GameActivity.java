package com.cs326.team5.qr_labyrinth;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends Activity {
	Grid grid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maze_view);
		Intent i = getIntent();
		grid = null;
		
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
	
    /**
 	 * Handles button clicks for the UI
 	 * @param v view that was clicked
 	 */
 	public void handleButton(View v) {
 		int id = v.getId();
 		if(grid == null)
 			return;
 		
 		Point player = grid.getPlayer();

 		switch (id) {
 		case R.id.btnDown:
 			player.setY(player.getY()-1);
 			break;
 		
 		case R.id.btnUp:
 			player.setY(player.getY()+1);
 			break;
 		
 		case R.id.btnLeft:
 			player.setX(player.getX()-1);
 			break;
 		
 		case R.id.btnRight:
 			player.setX(player.getX()+1);
 			break;
		}
 	}

}
