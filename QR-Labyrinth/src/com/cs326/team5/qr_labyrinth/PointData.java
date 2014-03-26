package com.cs326.team5.qr_labyrinth;

public class PointData{
	
    private boolean black;
    
    private Point destination;

    public PointData(boolean b){
    	this(null, b);
    }
    
    public PointData(Point p, boolean b){
        destination = p;
        black = b;
    }
    
    /**
     * @return true if represents a black point, false if white
     */
    public boolean isBlack(){
        return black;
    }
    
    /**
     * @return destination point
     */
    public Point getDestination(){
        return destination;
    }

 }
