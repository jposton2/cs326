package com.cs326.team5.qr_labyrinth;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class MazeView extends View {
	
	private float charDimension = 0;
	private Drawable charImg;
	
	private Paint paint;
	private Rect rect;
	
	private Grid grid;

	public MazeView(Context context) {
		super(context);
		init(null, 0);
	}

	public MazeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public MazeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
		// Load attributes
		final TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.MazeView, defStyle, 0);

		rect = new Rect();
		paint = new Paint();
		
		charDimension = a.getDimension(R.drawable.qr_guy, charDimension);
		if(a.hasValue(R.drawable.qr_guy))
		{
			charImg = a.getDrawable(R.drawable.qr_guy);
			charImg.setCallback(this);
		}
		
		grid = null;

		a.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// TODO: consider storing these as member variables to reduce
		// allocations per draw cycle.
		int sqrWidth = getWidth() / 10;
		if(!this.isInEditMode())
		{
			sqrWidth = getWidth() / grid.getGrid().length;
			for(int i = 0; i < grid.getGrid().length; i++)
			{
				PointData[] row = grid.getGrid()[i];
				for(int j = 0; j < row.length; i++)
				{
					if(row[j].isBlack())
						paint.setColor(Color.BLACK);
					else if(row[j].hasTeleporter())
						paint.setColor(Color.BLUE);
					else
						paint.setColor(Color.BLACK);
				}
			}
			if(charImg != null)
			{
				int side = sqrWidth * grid.getPlayer().getX();
				int top = sqrWidth * grid.getPlayer().getY();
				charImg.setBounds(side, top, side+sqrWidth, top+sqrWidth);
				charImg.draw(canvas);
			}
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
			if(charImg != null)
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
