package com.cs326.team5.qr_labyrinth;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores and maintains information for a level
 * @author Team 5
 */
public class Grid implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private int highscore;
	private String id;
	private int xBound;
	private int yBound;
	private Point start;
	private Point end;
	private PointData[][] gridArray;
	int numberOfGroups;
	ArrayList<Subgrid> arrayOfSubgrids = new ArrayList<Subgrid>();
	PointData deadEnd = null;
	private Point player;
	GridHandler handler = null;
	int level;

	Grid(PointData[][] grid, int xBound, int yBound, String name, int highscore, int level){
		this.xBound = xBound;
		this.yBound = yBound;
		this.gridArray = grid;
		xBound = yBound = gridArray[0].length;
		this.name = name;
		this.highscore = highscore;
		this.id = this.toString();
		this.handler = new GridHandler(this);
		this.level = level;

		if(level < 1){
			level = 1;
		}

		this.handler.setUpTeleporters(1 + level, level-1);
	}

	/**
	 * Resets teleporters
	 */
	public void reset()
	{
		this.handler.setUpTeleporters(1+level, level-1);
	}

	/**
	 * @return player player coordinates
	 */
	public Point getPlayer() {
		return player;
	}

	/**
	 * @param player player coordinates
	 */
	public void setPlayer(Point player) {
		this.player = player;
	}

	/**
	 * @return name name of the level
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name name of the level
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return highscore highscore for the level
	 */
	public int getHighscore() {
		return highscore;
	}

	/**
	 * @param highscore highscore for the level
	 */
	public void setHighscore(int highscore) {
		this.highscore = highscore;
		this.id = this.toString();
	}

	/**
	 * @return start point
	 */
	public Point getStart(){
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Point start) {
		this.start = start;
	}

	/**
	 * @return id ID of the level
	 */
	public String getID(){
		return id;
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

	/**
	 * Gets the white neighboring PointData's to a node
	 * @param node node whose white neighbors are returned
	 * @return neighbors list of white neighbors to node
	 */
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

	/**
	 * @return end end of the level
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * @param end end of the level to be set
	 */
	public void setEnd(Point end) {
		this.end = end;
	}

	@Override
	public String toString(){
		return name + "\n\t" + highscore;
	}
}
