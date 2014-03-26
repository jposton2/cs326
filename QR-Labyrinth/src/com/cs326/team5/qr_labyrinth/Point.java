package com.cs326.team5.qr_labyrinth;

public class Point{
	
    private int x, y;
    
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * @return x coordinate
     */
    public int getX(){
        return x;
    }
    
    /**
     * @return y coordinate
     */
    public int getY(){
        return y;
    }
}
