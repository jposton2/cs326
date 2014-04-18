package com.cs326.team5.qr_labyrinth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
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
	 * 
	 * Took out teleporter setting, should all be done using
	 * setTeleporters()
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
						tempSubgrid = new Subgrid(grid, currentGroup);//create new subgrid for newly found group
						grid.arrayOfSubgrids.add(tempSubgrid);//add newly found subgrid to grid subgrid array
						//tempSubgrid.setTeleporterOne(grid.getGrid()[i][j].getX(), grid.getGrid()[i][j].getY());//set the first subgrid teleporter to first found in group(upper left most node)
						//grid.getGrid()[i][j].setTeleporter(true);//give first found node a teleporter
						
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
							//grid.getGrid()[tempSubgrid.getTeleporterOne().getX()][tempSubgrid.getTeleporterOne().getY()].setTeleporter(false);
							//grid.getGrid()[tempSubgrid.getTeleporterOne().getX()][tempSubgrid.getTeleporterOne().getY()].setGroupID(-1);
						}else{//if more than one node
							currentGroup++;//increment for next group assignment
							//tempNode.setTeleporter(true);//set teleporter to last BFS visited node
							//tempSubgrid.setTeleporterTwo(tempNode.getX(), tempNode.getY());//give teleporter
						}
					}
				}
			}
			grid.numberOfGroups = currentGroup-1;//set number of groups existing in grid
		}

	private void setTreeTeleporters(ArrayList<Subgrid> arrayOfSubgraphs){
		Random rand = new Random();
		for(int i = 1; i < arrayOfSubgraphs.size(); ++i){
			boolean connected = false;
			int connectTo = 0;
			while(!connected){
				connectTo = rand.nextInt(i);
				connected = arrayOfSubgraphs.get(connectTo).addTeleporter(arrayOfSubgraphs.get(i));
			}
			arrayOfSubgraphs.get(i).addTeleporter(arrayOfSubgraphs.get(connectTo));
		}
	}
	

	private void connectGoodAndBadTeleporters(ArrayList<Subgrid> goodSubgraphs, ArrayList<Subgrid> badSubgraphs, int badLinks) {
		@SuppressWarnings("unchecked")
		ArrayList<Subgrid> goodSubgraphsCopy = (ArrayList<Subgrid>) goodSubgraphs.clone();
		@SuppressWarnings("unchecked")
		ArrayList<Subgrid> badSubgraphsCopy = (ArrayList<Subgrid>) badSubgraphs.clone();
		
		Random rand = new Random();
		boolean goodSafe = false;
		boolean badSafe = false;
		Subgrid good = null;
		Subgrid bad = null;
		for(int i = 0; i < badLinks; ++i){
			goodSafe = false;
			while(!goodSafe && goodSubgraphsCopy.size() > 0){
				good = goodSubgraphsCopy.get(rand.nextInt(goodSubgraphsCopy.size()));
				goodSafe = good.canAddTeleporter();
				if(!goodSafe){
					goodSubgraphsCopy.remove(good);
					good = null;
				}
			}

			badSafe = false;
			while(!badSafe && badSubgraphsCopy.size() > 0){
				bad = badSubgraphsCopy.get(rand.nextInt(badSubgraphsCopy.size()));
				badSafe = bad.canAddTeleporter();
				if(!badSafe){
					badSubgraphsCopy.remove(bad);
					bad = null;
				}
			}
			
			if(good != null && bad != null){
				good.addTeleporter(bad);
				bad.addTeleporter(good);
			}
		}
	}
	
	
    /**
     * @param subgrids
     */
    private void setLinearTeleporters(ArrayList<Subgrid> arrayOfSubgrids){
    	Subgrid s1;
    	Subgrid s2;
    	for(int i = 0; i < arrayOfSubgrids.size() - 1; ++i){
    		s1 = arrayOfSubgrids.get(i);
    		s2 = arrayOfSubgrids.get(i+1);
    		
    		s1.addTeleporter(s2);
    		s2.addTeleporter(s1);
    	}
    }
	
	void setTeleporters(int badNodes, int badLinks){
		ArrayList<Integer> numberList = new ArrayList<Integer>();
		for(int i=1;i<grid.arrayOfSubgrids.size()-1;i++)numberList.add(i);
		Collections.shuffle(numberList);
		
		//separate into good and bad nodes
		badNodes = Math.min(badNodes, grid.arrayOfSubgrids.size() - 4);
		
		ArrayList<Subgrid> badSubgraphs = new ArrayList<Subgrid>();
		ArrayList<Subgrid> goodSubgraphs = new ArrayList<Subgrid>();
		
		Subgrid deadEnd = grid.arrayOfSubgrids.get(numberList.get(0));
		deadEnd.addPseudoTeleporter();
		grid.deadEnd = deadEnd.getPseudoTeleporter();
		
		for(int i = 1; i < badNodes; ++i){
			badSubgraphs.add(grid.arrayOfSubgrids.get(numberList.get(i)));
		}
		for(int i = badNodes; i < grid.arrayOfSubgrids.size(); ++i){
			goodSubgraphs.add(grid.arrayOfSubgrids.get(numberList.get(i)));
		}
		
		//set teleporters
		this.setLinearTeleporters(goodSubgraphs);
		this.setTreeTeleporters(badSubgraphs);
		this.connectGoodAndBadTeleporters(goodSubgraphs, badSubgraphs, badLinks);
		
		for(Subgrid sg : grid.arrayOfSubgrids){
			sg.assignTeleporterDestinations();
		}
		grid.connectTeleporters();
	}
	
	
}
