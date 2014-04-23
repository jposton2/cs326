package com.cs326.team5.qr_labyrinth;

import java.util.ArrayList;

public class Grid{
	
//  private ArrayList<ArrayList<PointData>> grid;
	private String name;
	private int highscore;
	private String id;
	private int xBound;
	private int yBound;
	private PointData start;
	private PointData end;
	private PointData[][] gridArray;
	int numberOfGroups;
	ArrayList<Subgrid> arrayOfSubgrids = new ArrayList<Subgrid>();
	PointData deadEnd = null;
	private Point player;
	
//  public Grid(Point start, ArrayList<ArrayList<PointData>> grid){
//      this.grid = grid;
//      this.start = start;
//  }

  	public Point getPlayer() {
		return player;
	}

	public void setPlayer(Point player) {
		this.player = player;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHighscore() {
		return highscore;
	}

	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}

  /**
   * @return start point
   */
  public PointData getStart(){
      return start;
  }
	
	/**
	 * @param start the start to set
	 */
	public void setStart(PointData start) {
		this.start = start;
	}
	
	public String getID(){
		return id;
	}

	Grid(PointData[][] grid, int xBound, int yBound, String name, int highscore){
		this.xBound = xBound;
		this.yBound = yBound;
		this.gridArray = grid;
		xBound = yBound = gridArray[0].length;
		this.name = name;
		this.highscore = highscore;
		this.id = this.toString();
	}
	
	/**
	 * @return the xBound
	 */
	public int getxBound() {
		return xBound;
	}

	/**
	 * @param xBound the xBound to set
	 */
	public void setxBound(int xBound) {
		this.xBound = xBound;
	}

	/**
	 * @return - the yBound
	 */
	public int getyBound() {
		return yBound;
	}

	/**
	 * @param - yBound the yBound to set
	 */
	public void setyBound(int yBound) {
		this.yBound = yBound;
	}

	/**
	 * @return - grid array
	 */
	PointData[][] getGrid(){
		return gridArray;
	}
	
	/**
	 * Return PointData of node above parameter node
	 * @param node - node for which the above node will be found
	 * @return - above node
	 */
	PointData getAbove(PointData node){
		if(node.getX() > 0){
			return  gridArray[node.getX() -1][node.getY()];
		}
		else{
			return null;
		}
	}
	
	/**
	 * Return PointData of node below parameter node
	 * @param node - node for which the below node will be found
	 * @return - below node
	 */
	PointData getBelow(PointData node){
		if(node.getX() < this.xBound - 1){
			return gridArray[node.getX() +1][node.getY()];
		}
		else{
			return null;
		}
	}
	
	/**
	 * Return PointData of node right parameter node
	 * @param node - node for which the right node will be found
	 * @return - right node
	 */
	PointData getRight(PointData node){
		if(node.getY() < this.yBound - 1){
			return gridArray[node.getX()][node.getY()+1];
		}
		else{
			return null;
		}
	}
	
	/**
	 * Return PointData of node left parameter node
	 * @param node - node for which the left node will be found
	 * @return - left node
	 */
	PointData getLeft(PointData node){
		if(node.getY() > 0){
			return gridArray[node.getX()][node.getY()-1];
		}
		else{
			return null;
		}
	}
	

	public ArrayList<PointData> getWhiteNeighbors(PointData node){
		ArrayList<PointData> neighbors = new ArrayList<PointData>();
		if(this.getAbove(node) != null && !this.getAbove(node).isBlack()){
			neighbors.add(this.getAbove(node));
		}
		if(this.getBelow(node) != null && !this.getBelow(node).isBlack()){
			neighbors.add(this.getBelow(node));
		}
		if(this.getLeft(node) != null && !this.getLeft(node).isBlack()){
			neighbors.add(this.getLeft(node));
		}
		if(this.getRight(node) != null && !this.getRight(node).isBlack()){
			neighbors.add(this.getRight(node));
		}
		return neighbors;
	}

	public PointData getEnd() {
		return end;
	}

	public void setEnd(PointData end) {
		this.end = end;
	}
	
	public String toString(){
		return name + "\n\t" + highscore;
	}
}
