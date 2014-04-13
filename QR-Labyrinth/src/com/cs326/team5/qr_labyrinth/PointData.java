package com.cs326.team5.qr_labyrinth;

public class PointData{
	
    private boolean black;
    private Point destination;
	private int x;
	private int y;
	private int groupID = -1;//set to -1 as default, ungrouped, state
	private boolean teleporter = false;//default of no teleporter present
	
	PointData(){
		black = true;
		destination = null;
	}
	
	PointData(boolean isBlack, int x, int y){
		this.black = isBlack;
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
	
	void setDesintation(int x, int y){
		this.destination.setX(x);
		this.destination.setY(y);
	}
	
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
