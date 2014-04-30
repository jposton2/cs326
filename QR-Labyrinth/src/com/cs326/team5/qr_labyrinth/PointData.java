package com.cs326.team5.qr_labyrinth;

import java.io.Serializable;

/**
 * Holds information for each Point in a Grid
 * @author Team 5
 */
public class PointData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private boolean black;
    private PointData destination = null; //teleporter destination 
	private int x;
	private int y;
	private int groupID = -1;//set to -1 as default, ungrouped, state
	private boolean teleporter = false;//default of no teleporter present
	private boolean pseudoTeleporter = false;
	
	PointData(){
		black = true;
		destination = null;
	}
	
	PointData(boolean isBlack, int x, int y){
		this.black = isBlack;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return the groupID
	 */
	public int getGroupID() {
		return groupID;
	}

	/**
	 * @param groupID the groupID to set
	 */
	public void setGroupID(int groupID) {
		this.groupID = groupID;
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

	/**
	 * @return - true if has teleporter, false if not
	 */
	boolean hasTeleporter(){
		return teleporter;
	}
	
	/**
	 * Check if teleporter exists
	 * @param hasTeleporter - value to set to
	 */
	void setTeleporter(boolean hasTeleporter){
		teleporter = hasTeleporter;
	}
	
	void setBlack(boolean b){
		this.black = b;
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
    public PointData getDestination(){
        return destination;
    }

    /**
	 * @param destination the destination to set
	 */
	public void setDestination(PointData destination) {
		this.destination = destination;
	}

	public boolean isPseudoTeleporter() {
		return pseudoTeleporter;
	}

	public void setPseudoTeleporter(boolean pseudoTeleporter) {
		this.pseudoTeleporter = pseudoTeleporter;
	}

	@Override
	public boolean equals(Object p2){
		return ((Integer) this.x).equals(((PointData) p2).x)
				&& ((Integer) this.y).equals(((PointData) p2).y);
	}
	
	@Override
	public int hashCode(){
		return ((Integer) this.x).hashCode() + 8675309*((Integer) this.y).hashCode();
	}

	/*
	 *  used for debugging
	 */
	@Override
	public String toString(){
		if(this.isPseudoTeleporter()){
			return "(" + this.x + "," + this.y + "):P";
		}
		if(this.hasTeleporter()){
			if (this.destination == null){
				return "(" + this.x + "," + this.y + ")badbadbadbadbadbadbadbadbadbadbadbadbadbad";
			}
			return "(" + this.x + "," + this.y + "):(" + destination.x + "," + destination.y + ")";
		}
		if(this.isBlack()){
			return "B";
		}
		return " ";
	}
 }
