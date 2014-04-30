package com.cs326.team5.qr_labyrinth;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.TextView;

public class GameActivity extends Activity {
	Grid grid = null;
	MazeView mv = null;
	Point plr = null;
	Point end;
	Point start;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.maze_view);
		
		((Chronometer)findViewById(R.id.timer)).setTextSize(30);
		((Chronometer)findViewById(R.id.timer)).start();
		
		grid = ((QRLabyrinth)getApplicationContext()).getCurrentLevel();

		if(grid.getStart() == grid.getEnd())
			Log.i("GameActivity", "Start was the end all along!");

		end = grid.getEnd();
		start = grid.getStart();
		
		grid.setPlayer(grid.getStart().copy());
		mv = (MazeView) findViewById(R.id.maze);
		mv.setGrid(grid);
		mv.invalidate();
		Log.i("GameActivity", String.valueOf(grid.equals(mv.getGrid())));
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
 		
 		if(grid.getStart().equals(grid.getEnd()))
			Log.i("GameActivity", "Start is now the end?!");
 		
 		Point end = grid.getEnd();
 		
 		Point plr = grid.getPlayer();
 		boolean debug = false;
 		switch (id) {
 		case R.id.btnDown:
 			if(isPassable(plr.getX(), plr.getY()+1) || debug == true)
 			{
 				plr.setY(plr.getY() + 1);
 			}
 			Log.i("GameActivity", "Button pressed! (Down)");
 			break;
 		
 		case R.id.btnUp:
 			if(isPassable(plr.getX(), plr.getY()-1) || debug == true)
 			{
 				plr.setY(plr.getY() - 1);
 			}
 			Log.i("GameActivity", "Button pressed! (Up)");
 			break;
 		
 		case R.id.btnLeft:
 			if(isPassable(plr.getX()-1, plr.getY()) || debug == true)
 			{
 				plr.setX(plr.getX() - 1);
 			}
 			Log.i("GameActivity", "Button pressed! (Left)");
 			break;
 		
 		case R.id.btnRight:
 			if(isPassable(plr.getX()+1, plr.getY()) || debug == true)
 			{
 				plr.setX(plr.getX() + 1);
 			}
 			Log.i("GameActivity", "Button pressed! (Right)");
 			break;
		}
 		
 		if(!grid.getEnd().equals(end))
 			Log.i("GameActivity", "END HAS CHANGED!");
 		if(!grid.getStart().equals(start))
 			Log.i("GameActivity", "START HAS CHANGED!");
 		
 		PointData cell = grid.getGrid()[plr.getX()][plr.getY()];
 		if(cell.getX() == grid.getEnd().getX() && cell.getY() == grid.getEnd().getY())
 		{
 			grid.setEnd(end);
 			grid.setStart(start);
 			plr = grid.getStart().copy();
 			if(plr.getX() != grid.getStart().getX() || plr.getY() != grid.getStart().getY())
 				Log.i("GameActivity", "Couldn't move player back to start...");
 			grid.setPlayer(plr);
 			
 			long timeElapsed = SystemClock.elapsedRealtime() - ((Chronometer)findViewById(R.id.timer)).getBase();
 			int hours = (int)(timeElapsed/3600000);
 			int minutes = (int)(timeElapsed-hours*3600000)/60000;
 			int seconds = (int)(timeElapsed-hours*3600000-minutes*60000);
 			grid.setHighscore(seconds); 			
 			
 			mv.setGrid(grid);
 			//grid.reset();
 			grid = null;
 			finish();
 			
 			startActivity(new Intent(this, SelectLevelActivity.class));
 		}
 		else if(cell.getDestination() != null)
 		{
 			if(cell.getDestination().equals(cell))
 				Log.i("GameActivity", "Teleport destination was same as start...");
 			
 			plr.setX(cell.getDestination().getX());
 			plr.setY(cell.getDestination().getY());
 			Log.i("GameActivity", "Teleporting to " + cell.getDestination().getX() + "," + cell.getDestination().getY());
 		}
 		mv.invalidate();
 	}
 	
 	@Override
 	protected void onResume()
 	{
 		super.onResume();
 		grid = ((QRLabyrinth)getApplicationContext()).getCurrentLevel();

		if(grid.getStart() == grid.getEnd())
			Log.i("GameActivity", "Start was the end all along!");

		end = grid.getEnd();
		start = grid.getStart();
		
		grid.setPlayer(grid.getStart().copy());
		mv = (MazeView) findViewById(R.id.maze);
		mv.setGrid(grid);
		mv.invalidate();
 	}
 	
 	private boolean isPassable(int x, int y)
 	{
 		PointData[][] cells = grid.getGrid();
 		if(x < 0 || y < 0 || x >= cells.length || y >= cells.length)
 			return false;
 		return !cells[x][y].isBlack();
 	}

}
