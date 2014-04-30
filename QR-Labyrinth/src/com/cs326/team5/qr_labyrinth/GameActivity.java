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

public class GameActivity extends Activity {
	Grid grid = null;
	MazeView mv = null;
	Point plr = null;
	Point end;
	Point start;
	PointData returnPoint;
	PointData returnDeadEndPoint;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.maze_view);
		
		//Setup our clock so we can tell the player how long they took
		((Chronometer)findViewById(R.id.timer)).setTextSize(30);
		((Chronometer)findViewById(R.id.timer)).start();
		
		//Get the grid corresponding to the level the layer wants to play
		grid = ((QRLabyrinth)getApplicationContext()).getCurrentLevel();

		//Sanity check; if the start and end are the same there's a problem. Mostly for debug
		if(grid.getStart() == grid.getEnd())
			Log.i("GameActivity", "Start was the end all along!");

		//Put the start and end in a safe place so they don't get messed with
		end = grid.getEnd();
		start = grid.getStart();
		
		//Put the player on the start and pass off the grid to the MazeView so it can be rendered
		grid.setPlayer(grid.getStart().copy());
		mv = (MazeView) findViewById(R.id.maze);
		mv.setGrid(grid);
		mv.invalidate();
		
		//Check to see if we have the same grid object as the MazeView
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
 		
 		//Everything that happens needs grid, so just return if it doesn't exist
 		if(grid == null)
 			return;
 		
 		//Sanity check
 		if(grid.getStart().equals(grid.getEnd()))
			Log.i("GameActivity", "Start is now the end?!");
 		
 		//Grab the player's location so we can look at it easily
 		Point plr = grid.getPlayer();
 		
 		//Turn this on and the game will let you walk through walls
 		boolean debug = false;
 		
 		//Switch on the ID of the clicked button
 		//Fairly self explanatory - Up moves the player up and so on
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
 		
 		//More sanity checks. Really, we can probably just remove this
 		if(!grid.getEnd().equals(end))
 			Log.i("GameActivity", "END HAS CHANGED!");
 		if(!grid.getStart().equals(start))
 			Log.i("GameActivity", "START HAS CHANGED!");
 		
 		//Get the cell that the player has moved to
 		PointData cell = grid.getGrid()[plr.getX()][plr.getY()];
 		
 		//Did we win?
 		if(cell.getX() == grid.getEnd().getX() && cell.getY() == grid.getEnd().getY())
 		{
 			//Make sure the end and start are the same as they were when we started the level
 			grid.setEnd(end);
 			grid.setStart(start);
 			
 			//Move the player back to the level's start
 			plr = grid.getStart().copy();
 			if(plr.getX() != grid.getStart().getX() || plr.getY() != grid.getStart().getY())
 				Log.i("GameActivity", "Couldn't move player back to start...");
 			grid.setPlayer(plr);
 			
 			//Check how long it took the player to finish the level and store their score (If it's good enough)
 			long timeElapsed = SystemClock.elapsedRealtime() - ((Chronometer)findViewById(R.id.timer)).getBase();
 			int hours = (int)(timeElapsed/3600000);
 			int minutes = (int)(timeElapsed-hours*3600000)/60000;
 			int seconds = (int)(timeElapsed-hours*3600000-minutes*60000);
 			grid.setHighscore(seconds); 			
 			
 			//Reset the grid and sacrifice it to the Android gods
 			mv.setGrid(grid);
 			grid = null;
 			finish();
 			
 			startActivity(new Intent(this, SelectLevelActivity.class));
 		}
 		else if(cell.getDestination() != null)
 		{
 			//This cell is a teleporter. Let's move the player in the most jarring fashion possible
 			if(cell.getDestination().equals(cell))
 				Log.i("GameActivity", "Teleport destination was same as start...");
 			
			if(cell.getDestination().isPseudoTeleporter()){
 				if(cell.getDestination().equals(grid.deadEnd)){
 					this.returnDeadEndPoint = cell;
 				}
 				else{
 					this.returnPoint = cell;	
 				}
 			}
 			
 			if(cell.isPseudoTeleporter()){
 				if(cell.equals(grid.deadEnd)){
 					plr.setX(returnDeadEndPoint.getX());
 					plr.setY(returnDeadEndPoint.getY());
 				}
 				else{
 					plr.setX(returnPoint.getX());
 					plr.setY(returnPoint.getY());
 				}
 			}
 			else{
 	 			plr.setX(cell.getDestination().getX());
 	 			plr.setY(cell.getDestination().getY());
 			}

 			Log.i("GameActivity", "Teleporting to " + cell.getDestination().getX() + "," + cell.getDestination().getY());
 		}
 		
 		//And now that we've put everything in it's place, redraw the view!
 		mv.invalidate();
 	}
 	
 	@Override
 	protected void onResume()
 	{
 		super.onResume();
 		//Make sure everything is all nice
 		grid = ((QRLabyrinth)getApplicationContext()).getCurrentLevel();

		if(grid.getStart() == grid.getEnd())
			Log.i("GameActivity", "Start was the end all along!");

		end = grid.getEnd();
		start = grid.getStart();
		
		mv = (MazeView) findViewById(R.id.maze);
		mv.setGrid(grid);
		mv.invalidate();
 	}
 	
 	private boolean isPassable(int x, int y)
 	{
 		//Checks to see if a cell is black AND on the map
 		PointData[][] cells = grid.getGrid();
 		if(x < 0 || y < 0 || x >= cells.length || y >= cells.length)
 			return false;
 		return !cells[x][y].isBlack();
 	}

}
