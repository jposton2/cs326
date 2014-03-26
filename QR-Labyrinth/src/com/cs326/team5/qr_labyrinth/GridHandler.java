package com.cs326.team5.qr_labyrinth;

import java.util.ArrayList;
import java.util.Vector;

public class GridHandler{
	
    private int minSize;
    private Grid grid;
    private ArrayList<ArrayList<PointData>> subgraphs;
    
    public GridHandler(Grid g, int minSize, ArrayList<ArrayList<PointData>> subgraphs){
        this.subgraphs = subgraphs;
        this.minSize = minSize;
        grid = g;
    }
    
    /**
     * @return grid
     */
    public Grid getGrid(){
        return grid;
    }
    
    /**
     * @param subgrid
     * @return points furthest from each other in the subgrid
     */
    public Vector<Point> getFurthestPoints(ArrayList<Point> subgrid){
        return null;
    }
    
    /**
     * @param subgrids
     */
    public void setTeleporters(ArrayList<ArrayList<Point>> subgrids){
        // TODO:Add teleporter setters 
    }
    
    /**
     * @return 2D array of PointData representing a QR code
     */
    public ArrayList<ArrayList<PointData>> QRTo2DArray(){
        // TODO: Implement
        return subgraphs;
    }


}
