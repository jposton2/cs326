package com.cs326.team5.qr_labyrinth;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;

/**
 * Works with the MazeView to play a level
 * @author Team 5
 */
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

		//Put the start and end in a safe place so they don't get messed with
		end = grid.getEnd();
		start = grid.getStart();

		//Put the player on the start and pass off the grid to the MazeView so it can be rendered
		grid.setPlayer(grid.getStart().copy());
		mv = (MazeView) findViewById(R.id.maze);
		mv.setGrid(grid);
		mv.invalidate();
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
			break;

		case R.id.btnUp:
			if(isPassable(plr.getX(), plr.getY()-1) || debug == true)
			{
				plr.setY(plr.getY() - 1);
			}
			break;

		case R.id.btnLeft:
			if(isPassable(plr.getX()-1, plr.getY()) || debug == true)
			{
				plr.setX(plr.getX() - 1);
			}
			break;

		case R.id.btnRight:
			if(isPassable(plr.getX()+1, plr.getY()) || debug == true)
			{
				plr.setX(plr.getX() + 1);
			}
			break;
		}

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

			grid.setPlayer(plr);

			//Check how long it took the player to finish the level and store their score (If it's good enough)
			long timeElapsed = SystemClock.elapsedRealtime() - ((Chronometer)findViewById(R.id.timer)).getBase();
			grid.setHighscore((int)(timeElapsed / 1000)); 			

			QRLabyrinth qrl = ((QRLabyrinth)getApplicationContext());
			qrl.setCurrentLevel(grid);

			mv.setGrid(grid);
			grid = null;
			finish();

		}
		else if(cell.getDestination() != null)
		{

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
