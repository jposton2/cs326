package com.cs326.team5.qr_labyrinth;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class GridHandler implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Grid grid;
    
    public GridHandler(Grid grid){
    	this.grid = grid;
    }

    /**
     * @return grid
     */
    public Grid getGrid(){
        return grid;
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
	private void groupNodes(){
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
						
						//check adjacent four nodes(not corner nodes) and add to BFS queue
						for(PointData pd : grid.getWhiteNeighbors(tempNode)){
							if(pd.getGroupID() == -1){
								queue.add(pd);
								pd.setGroupID(currentGroup);
							}
						}
					}
					//remove subgrid if only one node exists
					if(tempSubgrid.subgridArray.size() < 3){//if only one or two nodes exists in subgrid
						grid.arrayOfSubgrids.remove(tempSubgrid);
					}else{//if more than two node
						currentGroup++;//increment for next group assignment
					}
				}
			}
		}
		grid.numberOfGroups = currentGroup-1;//set number of groups existing in grid
	}

	/**
	 * Connect bad subgrids via teleporters in a tree
	 * @param badSubgrids
	 */
	private void setTreeTeleporters(ArrayList<Subgrid> badSubgrids){
		Random rand = new Random();
		for(int i = 1; i < badSubgrids.size(); ++i){
			boolean connected = false;
			int connectTo = 0;
			while(!connected){
				connectTo = rand.nextInt(i);
				connected = badSubgrids.get(connectTo).addTeleporter(badSubgrids.get(i));
			}
			badSubgrids.get(i).addTeleporter(badSubgrids.get(connectTo));
		}
	}
	

	/**
	 * Connect the good subgrids to the bad subgrids
	 * bad subgrids have pseudoteleporters that connect to good subgrids
	 * 
	 * @param goodSubgrids
	 * @param badSubgrids
	 * @param badLinks
	 */
	private void connectGoodAndBadTeleporters(ArrayList<Subgrid> goodSubgrids, ArrayList<Subgrid> badSubgrids, int badLinks) {
		@SuppressWarnings("unchecked")
		ArrayList<Subgrid> goodSubgridsCopy = (ArrayList<Subgrid>) goodSubgrids.clone();
		@SuppressWarnings("unchecked")
		ArrayList<Subgrid> badSubgridsCopy = (ArrayList<Subgrid>) badSubgrids.clone();
		
		boolean badHasLess = false;
		if(badSubgridsCopy.size() < goodSubgridsCopy.size()){
			badHasLess = true;
		}
		
		Random rand = new Random();
		boolean goodSafe = false;
		boolean badSafe = false;
		Subgrid good = null;
		Subgrid bad = null;
		for(int i = 0; i < badLinks; ++i){
			goodSafe = false;
			good = null;
			bad = null;
			while(!goodSafe && goodSubgridsCopy.size() > 0){
				good = goodSubgridsCopy.get(rand.nextInt(goodSubgridsCopy.size()));
				goodSafe = good.canAddTeleporter();
				if(!goodSafe){
					goodSubgridsCopy.remove(good);
					good = null;
				}
			}

			badSafe = false;
			while(!badSafe && badSubgridsCopy.size() > 0){
				bad = badSubgridsCopy.get(rand.nextInt(badSubgridsCopy.size()));
				badSafe = bad.canAddTeleporter();
				if(!badSafe){
					badSubgridsCopy.remove(bad);
					bad = null;
				}
			}
			
			if(good != null && bad != null){
				good.addTeleporter(bad);
				bad.addPseudoTeleporter();
				
				//Guarantee only one link between each subgrid
				if(badHasLess){
					goodSubgridsCopy.remove(good);
				}
				else{
					badSubgridsCopy.remove(bad);
				}
			}
		}
	}
	
	
    /**
     * Set good subgrids in a linear fashion
     * Also sets grid.start and grid.end
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
    	PointData start, end;
    	start = arrayOfSubgrids.get(0).subgridArray.get(1);
    	end = arrayOfSubgrids.get(arrayOfSubgrids.size() - 1).subgridArray.get(1);
    	Point a = new Point(end.getX(), end.getY());
    	Point b = new Point(start.getX(), start.getY());
    	
    	grid.setStart(b);
    	grid.setEnd(a);
    }
	
	/**
	 * Connect teleporters across subgrids
	 */
	@SuppressLint("UseSparseArrays")
	private void connectTeleporters() {
		HashMap<Integer, Subgrid> subgridsById = new HashMap<Integer, Subgrid>();
		for(Subgrid sg : grid.arrayOfSubgrids){
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
				
				if(tel1.isPseudoTeleporter()){
					tel1.setDestination(grid.deadEnd);
				}
				if(tel2.isPseudoTeleporter()){
					tel2.setDestination(grid.deadEnd);
				}
			}
		}
	}
    
	/**
	 * Set all the teleporters! [[insert hyperbole and a half image]]
	 * @param badNodes
	 * @param badLinks
	 */
	private void setTeleporters(int badNodes, int badLinks){
		ArrayList<Integer> numberList = new ArrayList<Integer>();
		for(int i=0;i<grid.arrayOfSubgrids.size();i++)numberList.add(i);
		Collections.shuffle(numberList);
		
		//separate into good and bad nodes
		badNodes = Math.min(badNodes, grid.arrayOfSubgrids.size() - 4);
		
		ArrayList<Subgrid> badSubgrids = new ArrayList<Subgrid>();
		ArrayList<Subgrid> goodSubgrids = new ArrayList<Subgrid>();
		
		Subgrid deadEnd = grid.arrayOfSubgrids.get(numberList.get(0));
		deadEnd.addPseudoTeleporter();
		grid.deadEnd = deadEnd.getPseudoTeleporter();
		
		for(int i = 1; i < badNodes; ++i){
			badSubgrids.add(grid.arrayOfSubgrids.get(numberList.get(i)));
		}
		for(int i = badNodes; i < grid.arrayOfSubgrids.size(); ++i){
			goodSubgrids.add(grid.arrayOfSubgrids.get(numberList.get(i)));
		}
		
		//set teleporters
		
		this.setLinearTeleporters(goodSubgrids);
		this.setTreeTeleporters(badSubgrids);
		this.connectGoodAndBadTeleporters(goodSubgrids, badSubgrids, badLinks);
		
		for(Subgrid sg : grid.arrayOfSubgrids){
			sg.assignTeleporterDestinations();
		}
		
		this.connectTeleporters();
	}

	/**
	 * Call this when you want to set up stuff
	 * grid.start and grid.end are also set in the process
	 * @param badNodes
	 * @param badLinks
	 */
	public void setUpTeleporters(int badNodes, int badLinks){
		this.groupNodes();
		this.setTeleporters(badNodes, badLinks);
	}
}
