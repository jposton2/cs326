package com.cs326.team5.qr_labyrinth;

public class PointData{
    private boolean black;
    Point destination;
    PointData(Point p, boolean b){
        destination = p;
        black = b;
    }
    public boolean isBlack(){
        return black;
    }
    public Point getDestination(){
        return destination;
    }

 }
