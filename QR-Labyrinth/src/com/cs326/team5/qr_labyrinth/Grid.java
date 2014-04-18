package com.cs326.team5.qr_labyrinth;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;

public class Grid{
        	
//    private ArrayList<ArrayList<PointData>> grid;
	private int xBound;
	private int yBound;
	private Point start;
	private PointData[][] gridArray;
	int numberOfGroups;
	ArrayList<Subgrid> arrayOfSubgrids = new ArrayList<Subgrid>();
	PointData deadEnd = null;
	
//    public Grid(Point start, ArrayList<ArrayList<PointData>> grid){
//        this.grid = grid;
//        this.start = start;
//    }

    /**
     * @return start point
     */
    public Point getStart(){
        return start;
    }
	
	Grid(Point start, PointData[][] grid, int xBound, int yBound){
		this.xBound = xBound;
		this.yBound = yBound;
		this.gridArray = grid;
		this.start = start;
		xBound = yBound = gridArray[0].length;
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
		return  gridArray[node.getX() -1][node.getY()];
	}
	
	/**
	 * Return PointData of node below parameter node
	 * @param node - node for which the below node will be found
	 * @return - below node
	 */
	PointData getBelow(PointData node){
		return gridArray[node.getX() +1][node.getY()];
	}
	
	/**
	 * Return PointData of node right parameter node
	 * @param node - node for which the right node will be found
	 * @return - right node
	 */
	PointData getRight(PointData node){
		return gridArray[node.getX()][node.getY()+1];
	}
	
	/**
	 * Return PointData of node left parameter node
	 * @param node - node for which the left node will be found
	 * @return - left node
	 */
	PointData getLeft(PointData node){
		return gridArray[node.getX()][node.getY()-1];
	}
	

	public ArrayList<PointData> getWhiteNeighbors(PointData node){
		ArrayList<PointData> neighbors = new ArrayList<PointData>();
		if(!this.getAbove(node).isBlack()){
			neighbors.add(this.getAbove(node));
		}
		if(!this.getBelow(node).isBlack()){
			neighbors.add(this.getBelow(node));
		}
		if(!this.getLeft(node).isBlack()){
			neighbors.add(this.getLeft(node));
		}
		if(!this.getRight(node).isBlack()){
			neighbors.add(this.getRight(node));
		}
		return neighbors;
	}

	@SuppressLint("UseSparseArrays")
	public void connectTeleporters() {
		HashMap<Integer, Subgrid> subgridsById = new HashMap<Integer, Subgrid>();
		for(Subgrid sg : this.arrayOfSubgrids){
			subgridsById.put(sg.groupID, sg);
		}
		
		for(Integer gid1 : subgridsById.keySet()){
			for(Integer gid2 : subgridsById.get(gid1).teleporterDestinations.keySet()){
				PointData tel1 = subgridsById.get(gid1).teleporterDestinations.get(gid2);
				PointData tel2 = subgridsById.get(gid2).teleporterDestinations.get(gid1);
				
				if(tel1 == null){
					tel1 = subgridsById.get(gid1).getPseudoTeleporter();
				}
				if(tel2 == null){
					tel2 = subgridsById.get(gid2).getPseudoTeleporter();
				}
				
				tel1.setDestination(tel2);
				tel2.setDestination(tel1);
			}
		}
	}
}
