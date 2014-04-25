package com.cs326.team5.qr_labyrinth;

public class Point{
	
    private int x=-1, y=-1;//mandatory -1 to check if variable is unmodified
    
    Point(){
    	//do nothing for when x and y will be later set
    }
    
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	public Point copy() {
		return new Point(this.x, this.y);
	}

	public boolean equals(Object that){
		Point thatPoint = (Point) that;
		return this.x == thatPoint.x && this.y == thatPoint.y;
	}
}
