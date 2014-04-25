package com.cs326.team5.qr_labyrinth;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * TODO: document your custom view class.
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
		// Load attributes
		/*final TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.MazeView, defStyle, 0);*/

		rect = new Rect();
		paint = new Paint();
		charImg = res.getDrawable(R.drawable.qr_guy);
		

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// TODO: consider storing these as member variables to reduce
		// allocations per draw cycle.
		int sqrWidth = findViewById(R.id.maze).getWidth() / 4;
		int sqrHeight = findViewById(R.id.maze).getHeight() / 4;
		boolean drewPlayer = false;
		
		if(grid == null)
			return;
		
		if(grid.getPlayer() == null)
			grid.setPlayer(grid.getStart());
		Point plr = grid.getPlayer();
		
		if(plr == null)
		{
			Log.i("MazeView", "Start was null; defaulting to 10,10");
			plr = new Point(11,11);
		}
			
		if(!this.isInEditMode() && grid != null && plr != null)
		{
			PointData[][] cells = grid.getGrid();
			sqrWidth = getWidth() / 5;
			sqrHeight = getHeight() / 5;
			Log.i("MazeView", "Cells: " + cells.length + ", Height: " + getHeight() + ", Tile size: " + sqrWidth);
			
			rect.set(0, 0, getWidth(), getHeight());
			paint.setColor(Color.TRANSPARENT);
			canvas.drawRect(rect, paint);
			
			for(int i = plr.getX() - 2, x = 0; i < plr.getX() + 3; i++, x++)
			{
				for(int j = plr.getY() - 2, y = 0; j < plr.getY() + 3; j++, y++)
				{	
					
					if(i < 0 || j < 0 || i >= cells.length || j >= cells.length || cells[i][j] == null)
					{
						paint.setColor(Color.RED);
						
						rect.set(sqrWidth*x, sqrHeight*y, sqrWidth*x + sqrWidth, sqrHeight*y + sqrHeight);
						canvas.drawRect(rect, paint);
						continue;
					}
					else if(cells[i][j].isBlack())
					{
						paint.setColor(Color.BLACK);
					}
					else if(cells[i][j].hasTeleporter() || cells[i][j].isPseudoTeleporter())
					{
						paint.setColor(Color.BLUE);
					}
					else if(grid.getEnd().getX() == i && grid.getEnd().getY() == j)
					{
						paint.setColor(Color.GREEN);
					}
					else
					{
						paint.setColor(Color.WHITE);
					}
					
					
					rect.set(sqrWidth*x, sqrHeight*y, sqrWidth*x + sqrWidth, sqrHeight*y + sqrHeight);
					canvas.drawRect(rect, paint);
					if(i == plr.getX() && j == plr.getY() && !drewPlayer)
					{
						drewPlayer = true;
						Log.i("MazeView", "Player: " + plr.getX() +","+ plr.getY());
						charImg.setBounds(sqrWidth*x, sqrHeight*y, sqrWidth*x + sqrWidth, sqrHeight*y + sqrHeight);
						charImg.draw(canvas);
					}
				}
			}
			if(res != null && plr != null)
			{
				int side = sqrWidth * plr.getX();
				int top = sqrWidth * plr.getY();
				charImg.setBounds(side, top, side+sqrWidth, top+sqrWidth);
				charImg.draw(canvas);
			}
			grid.setPlayer(plr);
		}
		else
		{
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
