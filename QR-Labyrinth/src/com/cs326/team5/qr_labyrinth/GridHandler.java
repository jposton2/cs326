package com.cs326.team5.qr_labyrinth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;


public class GridHandler{
	
    private int minSize;
    private Grid grid;
    private ArrayList<ArrayList<PointData>> subgraphs;
    
    GridHandler(Grid grid){
    	this.grid = grid;
    }
    
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
    public Vector<PointData> getFurthestPoints(ArrayList<PointData> subgrid){
        return null;
    }
    
    /**
     * @param subgrids
     */
    public void setTeleporters(ArrayList<ArrayList<PointData>> subgrids){
        // TODO:Add teleporter setters 
    }
    
    /**
     * @return 2D array of PointData representing a QR code
     */
    public ArrayList<ArrayList<PointData>> QRTo2DArray(){
        // TODO: Implement
        return subgraphs;
    }

	/**
	 * Groups PointData nodes into groups based
	 * on which white(non-black) nodes are touching.
	 * Also, sets two teleporter locations based on
	 * farthest 2 points as determined by a BFS.
	 */
	void groupNodes(){
			Queue<PointData> queue = new LinkedList<PointData>();//BFS queue
			int currentGroup = 0;//current group counter
			PointData tempNode = null;
			Subgrid tempSubgrid;
			
			//loop through all nodes
			for(int j = 0;j<grid.getxBound();j++){//horizontal loop
				for(int i=0;i<grid.getyBound();i++){//vertical loop
					if(grid.getGrid()[i][j].isBlack() == false && grid.getGrid()[i][j].getGroupID() == -1){//if is white and not yet in group
						grid.getGrid()[i][j].setGroupID(currentGroup);//set group id to current
						queue.add(grid.getGrid()[i][j]);//add to BFS queue
						tempSubgrid = new Subgrid();//create new subgrid for newly found group
						grid.arrayOfSubgrids.add(tempSubgrid);//add newly found subgrid to grid subgrid array
						tempSubgrid.setTeleporterOne(grid.getGrid()[i][j].getX(), grid.getGrid()[i][j].getY());//set the first subgrid teleporter to first found in group(upper left most node)
						grid.getGrid()[i][j].setTeleporter(true);//give first found node a teleporter
						
						//Perform BFS on adjacent nodes, looking for all white nodes in subgrid
						while(queue.peek() != null){//while BFS queue is not empty
							tempNode = queue.remove();//remove current node from BFS queue
							tempSubgrid.subgridArray.add(tempNode);//add current node to subgid array
							tempNode.setGroupID(currentGroup);//set group id
							//check adjacent four nodes(not corner nodes) and add to BFS queue
							if((i-1)>-1 && grid.getAbove(tempNode).isBlack() == false && grid.getAbove(tempNode).getGroupID() == -1){//above
								queue.add(grid.getAbove(tempNode));
							}
							if((i+1)<grid.getyBound() && grid.getBelow(tempNode).isBlack() == false && grid.getBelow(tempNode).getGroupID() == -1){//below
								queue.add(grid.getBelow(tempNode));
							}
							if((i+1)<grid.getxBound() && grid.getRight(tempNode).isBlack() == false && grid.getRight(tempNode).getGroupID() == -1){//right
								queue.add(grid.getRight(tempNode));
							}
							if((j-1)>-1 && grid.getLeft(tempNode).isBlack() == false && grid.getLeft(tempNode).getGroupID() == -1){//left
								queue.add(grid.getLeft(tempNode));
							}
						}
						//remove subgrid if only one node exists
						if(tempSubgrid.subgridArray.size() == 1){//if only one node exists in subgrid
							grid.arrayOfSubgrids.remove(tempSubgrid);
							grid.getGrid()[tempSubgrid.getTeleporterOne().getX()][tempSubgrid.getTeleporterOne().getY()].setTeleporter(false);
							grid.getGrid()[tempSubgrid.getTeleporterOne().getX()][tempSubgrid.getTeleporterOne().getY()].setGroupID(-1);
						}else{//if more than one node
							currentGroup++;//increment for next group assignment
							tempNode.setTeleporter(true);//set teleporter to last BFS visited node
							tempSubgrid.setTeleporterTwo(tempNode.getX(), tempNode.getY());//give teleporter
						}
					}
				}
			}
			grid.numberOfGroups = currentGroup-1;//set number of groups existing in grid
		}
	
	/**
	 * Randomly sets linear path through subgrid teleporters
	 */	
	void setTeleporters(){
		ArrayList<Integer> numberList = new ArrayList<Integer>();
		for(int i=1;i<grid.arrayOfSubgrids.size()-1;i++)numberList.add(i);
		Collections.shuffle(numberList);
		
		grid.arrayOfSubgrids.get(0).setIncomingSubgridIndex(-10);//first
		grid.arrayOfSubgrids.get(grid.arrayOfSubgrids.size()-1).setOutgoingGridIndex(-20);//finish node
		
		//Loop through randomized number list(doesn't include 0th or nth subgrid)
		for(int i=0;i<numberList.size();i++){
			if(i==0){//first shuffled item
				grid.arrayOfSubgrids.get(0).setOutgoingGridIndex(numberList.get(0));//point 0 outgoing to random outgoing
				grid.arrayOfSubgrids.get(numberList.get(0)).setIncomingSubgridIndex(0);//point random outgoing to 0
			}
			if(i+1==numberList.size()){//last shuffled item
				grid.arrayOfSubgrids.get(grid.arrayOfSubgrids.size()-1).setIncomingSubgridIndex(numberList.get(i));//point finish to second to last
				grid.arrayOfSubgrids.get(numberList.get(i)).setOutgoingGridIndex(grid.arrayOfSubgrids.size()-1);//point second to last to finish
				break;
			}
			
			grid.arrayOfSubgrids.get(numberList.get(i)).setOutgoingGridIndex(numberList.get(i+1));//point current subgrid to next
			grid.arrayOfSubgrids.get(numberList.get(i+1)).setIncomingSubgridIndex(numberList.get(i));//point next subgrid to current
			
		}
	}
	
	/**
	 * -Adds third teleporter to subgrids with more than 3 nodes
	 * -Pairs up subgrids with third teleporter
	 */
	void addThirdTeleporter(){
		int i = 0;
		ArrayList<PointData> array = new ArrayList<PointData>();//temporarily stores point datas that will be third teleporter
		
		for (Subgrid subgrid : grid.arrayOfSubgrids) {//loop through subgrids
			if(subgrid.subgridArray.size() > 3 && subgrid.getDummyNodeIndex() == -1){//if more than 3 nodes exist in subgrid
				//loop through nodes, look for node with 3 black adjacencies
				for (PointData pointData : subgrid.subgridArray) {
					i=0;//adjacent black counter
					if(pointData.hasTeleporter())continue;
					
					//check for adjacent blacks
					if(grid.getAbove(pointData).isBlack())i++;
					if(grid.getBelow(pointData).isBlack())i++;
					if(grid.getRight(pointData).isBlack())i++;
					if(grid.getLeft(pointData).isBlack())i++;
					
					if(i==3){//if three adjacent sides are black
						subgrid.setTeleporterThree(pointData.getX(), pointData.getY());
						grid.getGrid()[pointData.getX()][pointData.getY()].setTeleporter(true);;
						array.add(pointData);
						break;
					}
				}
			}
		}
		
		//pair up and connect third teleporters
		for(i=0;i<array.size();i+=2){
			if(i+1 != array.size()){
				grid.arrayOfSubgrids.get(array.get(i).getGroupID()).setDummyNodeIndex(array.get(i+1).getGroupID());
				grid.arrayOfSubgrids.get(array.get(i+1).getGroupID()).setDummyNodeIndex(array.get(i).getGroupID());
			}
		}
		
	}

}
