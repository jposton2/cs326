package com.cs326.team5.qr_labyrinth;

import java.util.ArrayList;
import java.util.Vector;

public class GridHandler{
    private int minSize;
    private Grid grid;
    private ArrayList<ArrayList<PointData>> subgraphs;
    GridHandler(Grid g, int minSize, ArrayList<ArrayList<PointData>> subgraphs){
        this.subgraphs = subgraphs;
        this.minSize = minSize;
        grid = g;
    }
    public Grid getGrid(){
        return grid;
    }
    public Vector<Point> getFurthestPints(ArrayList<Point> subgrid){
        return null;
    }
    public void setTeleporters(ArrayList<ArrayList<Point>> subgrids){
        // TODO:Add teleporter setters 
    }
    ArrayList<ArrayList<PointData>> QRTo2DArray(){
        // TODO: Implement
        return subgraphs;
    }


}
