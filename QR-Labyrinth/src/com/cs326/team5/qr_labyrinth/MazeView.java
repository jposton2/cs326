package com.cs326.team5.qr_labyrinth;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Renders a maze, including the walls, player, and special tiles
 */
public class MazeView extends View {
	
	private Drawable charImg;
	private Resources res;
	
	private Paint paint;
	private Rect rect;
	
	private Grid grid = null;

	public MazeView(Context context) {
		super(context);
		res = context.getResources();
		init(null, 0);
	}

	public MazeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		res = context.getResources();
		init(attrs, 0);
	}

	public MazeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		res = context.getResources();
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
		
		//Setup some items we're going to want for drawing later
		rect = new Rect();
		paint = new Paint();
		charImg = res.getDrawable(R.drawable.qr_guy);
	
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		//Get the width of the view and divide by four to get a good size
		//It won't be square, but neither is the user's device
		//We change this later anyways, but this is a nice default
		int sqrWidth = findViewById(R.id.maze).getWidth() / 4;
		int sqrHeight = findViewById(R.id.maze).getHeight() / 4;
		
		//We only want to draw the player once, so we'll use this to keep track of that
		boolean drewPlayer = false;
		
		//We need the grid to go any farther; die if we don't
		if(grid == null)
			return;
		
		//Check for the player's location; set it to start if it's null
		if(grid.getPlayer() == null)
			grid.setPlayer(grid.getStart().copy());
		Point plr = grid.getPlayer();
		
		//We need a grid and a player to continue; for the IDE's renderer we won't have an actual grid
		if(!this.isInEditMode() && grid != null && plr != null)
		{
			//Grab the cells from the grid object
			PointData[][] cells = grid.getGrid();
			
			//Recalculate the square width and height
			sqrWidth = getWidth() / 5;
			sqrHeight = getHeight() / 5;
			
			//Draw transparent over the background before drawing anything else
			rect.set(0, 0, getWidth(), getHeight());
			paint.setColor(Color.TRANSPARENT);
			canvas.drawRect(rect, paint);
			
			//Iterate through the cells, coloring them as appropriate
			//We only want to show 25 cells at a time, so we make the player's location the center cell
			for(int i = plr.getX() - 2, x = 0; i < plr.getX() + 3; i++, x++)
			{
				for(int j = plr.getY() - 2, y = 0; j < plr.getY() + 3; j++, y++)
				{	
					
					//The first one catches nulls and cells outside the grid. They're impassable, so make them black
					if(i < 0 || j < 0 || i >= cells.length || j >= cells.length || cells[i][j] == null)
					{
						paint.setColor(Color.BLACK);
						
						rect.set(sqrWidth*x, sqrHeight*y, sqrWidth*x + sqrWidth, sqrHeight*y + sqrHeight);
						canvas.drawRect(rect, paint);
						continue;
					}
					else if(cells[i][j].isBlack())	//This cell is a wall, paint it black
					{
						paint.setColor(Color.BLACK);
					}
					else if(cells[i][j].hasTeleporter() || cells[i][j].isPseudoTeleporter())
					{
						//This cell is a teleporter, paint it blue so the player can see it
						paint.setColor(Color.BLUE);
					}
					else if(grid.getEnd().getX() == i && grid.getEnd().getY() == j)
					{
						//This cell is the end of the level, paint it green so the player can see it
						paint.setColor(Color.GREEN);
					}
					else
					{
						//Everything else is passable terrain, paint it white
						paint.setColor(Color.WHITE);
					}
					
					//Set the bounds of the rectangle. This is independent of color, so we put it here to save space
					rect.set(sqrWidth*x, sqrHeight*y, sqrWidth*x + sqrWidth, sqrHeight*y + sqrHeight);

					//Draw the square on the view
					canvas.drawRect(rect, paint);
					
					//While we're here, if the player is on this cell draw the player sprite
					if(i == plr.getX() && j == plr.getY() && !drewPlayer)
					{
						drewPlayer = true;
						charImg.setBounds(sqrWidth*x, sqrHeight*y, sqrWidth*x + sqrWidth, sqrHeight*y + sqrHeight);
						charImg.draw(canvas);
					}
				}
			}
			//Set the player's location again, just to be sure
			grid.setPlayer(plr);
		}
		else
		{
			//This draws a checkerboard pattern on the view when shown in the IDE
			boolean change = false;
			for(int i = 0; i < 10; i++)
			{
				for(int j = 0; j < 10; j++)
				{
					rect.set(sqrWidth*i, sqrWidth*j, sqrWidth*i + sqrWidth, sqrWidth*j + sqrWidth);
					if(change)
					{
						change = false;
						paint.setColor(Color.BLACK);
					}
					else
					{
						change = true;
						paint.setColor(Color.WHITE);
					}
					canvas.drawRect(rect, paint);
				}
			}
			if(res != null)
			{
				int side = getWidth()/2;
				int top = getHeight()/2;
				charImg.setBounds(side, top, side+sqrWidth, top+sqrWidth);
				charImg.draw(canvas);
			}
		}

	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}


}
