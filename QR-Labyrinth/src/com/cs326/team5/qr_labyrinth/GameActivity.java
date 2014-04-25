package com.cs326.team5.qr_labyrinth;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GameActivity extends Activity {
	Grid grid = null;
	MazeView mv = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.maze_view);
		grid = ((QRLabyrinth)getApplicationContext()).getCurrentLevel();

		/*for(PointData[] row : grid.getGrid())
		Log.w("Lolololololl", String.valueOf(grid.getGrid()[0][0].isBlack()));
		for(PointData[] row : grid.getGrid())
		{
			for(PointData cell : row)
			{
				if(cell != null)
					Log.i("GameActivity", String.valueOf(cell.isBlack()));
				else
					Log.i("GameActivity", "NULL CELL!");
			}
		}*/
		grid.setPlayer(grid.getStart());
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
 		
 		Point plr = grid.getPlayer();

 		switch (id) {
 		case R.id.btnDown:
 			if(isPassable(plr.getX(), plr.getY()-1))
 			{
 				plr.setY(plr.getY() - 1);
 			}
 			Log.i("GameActivity", "Button pressed! (Down)");
 			break;
 		
 		case R.id.btnUp:
 			if(isPassable(plr.getX(), plr.getY()+1))
 			{
 				plr.setY(plr.getY() + 1);
 			}
 			Log.i("GameActivity", "Button pressed! (Up)");
 			break;
 		
 		case R.id.btnLeft:
 			if(isPassable(plr.getX()-1, plr.getY()))
 			{
 				plr.setX(plr.getX() - 1);
 			}
 			Log.i("GameActivity", "Button pressed! (Left)");
 			break;
 		
 		case R.id.btnRight:
 			if(isPassable(plr.getX()+1, plr.getY()))
 			{
 				plr.setX(plr.getX() + 1);
 			}
 			Log.i("GameActivity", "Button pressed! (Right)");
 			break;
		}
 		mv.invalidate();
 	}
 	
 	private boolean isPassable(int x, int y)
 	{
 		PointData[][] cells = grid.getGrid();
 		if(x < 0 || y < 0 || x > cells.length || y > cells.length)
 			return false;
 		return !cells[x][y].isBlack();
 	}

}
