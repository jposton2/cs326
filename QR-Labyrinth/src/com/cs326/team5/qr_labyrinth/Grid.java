package com.cs326.team5.qr_labyrinth;

import java.util.ArrayList;

public class Grid{
	
    private ArrayList<ArrayList<PointData>> grid;
    private Point start;
    
    public Grid(Point start, ArrayList<ArrayList<PointData>> grid){
        this.grid = grid;
        this.start = start;
    }
    
    /**
     * @return grid
     */
    public ArrayList<ArrayList<PointData>> getGrid(){
        return grid;
    }

    /**
     * @return start point
     */
    public Point getStart(){
        return start;
    }
}
