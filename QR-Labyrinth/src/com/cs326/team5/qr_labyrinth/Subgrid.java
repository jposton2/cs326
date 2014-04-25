package com.cs326.team5.qr_labyrinth;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Area of whitespace in QR grid
 *
 */
public class Subgrid implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 2415170723764044236L;
	Grid grid;
	int groupID;
	ArrayList<PointData> subgridArray = new ArrayList<PointData>();
	
	//maps subgrid ids to locations of teleporters
	@SuppressLint("UseSparseArrays")
	HashMap<Integer, PointData> teleporterDestinations = new HashMap<Integer, PointData>();
	ArrayList<PointData> teleporters = new ArrayList<PointData>();
	
	//pseudo teleporter:
	//multiple teleporters can have this as a target
	//default destination is grid.deadEnd
	//will return to the node used to get there
	private PointData pseudoTeleporter = null;
	
	public Subgrid(Grid grid, int groupId){
		this.grid = grid;
		this.groupID = groupId;
	}
	
	/**
	 * Perform breadth first search 
	 * used to find teleporter that is mutally farthest away
	 * from set teleporters
	 * 
	 * @param teleporters
	 * @param nodes
	 * @return
	 */
	private LinkedList<PointData> multiBFS(Collection<PointData> teleporters, Collection<PointData> nodes){
		HashMap<PointData, Boolean> isEnqueued = new HashMap<PointData, Boolean>();
		LinkedList<PointData> toVisit = new LinkedList<PointData>();
		LinkedList<PointData> visited = new LinkedList<PointData>();
		
		for(PointData pd : nodes){
			isEnqueued.put(pd, false);
		}
		for(PointData pd : teleporters){
			isEnqueued.put(pd, true);
		}
		toVisit.addAll(teleporters);
		
		PointData visiting;
		
		while(!toVisit.isEmpty()){
			visiting = toVisit.remove();
			visited.add(visiting);
			for(PointData pd: grid.getWhiteNeighbors(visiting)){
				if(isEnqueued.get(pd) != null && !isEnqueued.get(pd)){
					isEnqueued.put(pd, true);
					toVisit.add(pd);
				}
			}
		}
		
		return visited;
	}
	
	/**
	 * Determine if it is safe to add a teleporter at specified point
	 * This means no teleporters are adjacent to it.
	 * 
	 * @param teleportAdd
	 * @return
	 */
	private boolean isSafe(PointData teleportAdd){
		if(teleportAdd.hasTeleporter() || teleportAdd.isPseudoTeleporter()){
			return false;
		}
		for(PointData p : grid.getWhiteNeighbors(teleportAdd)){
			if(p.hasTeleporter() || p.isPseudoTeleporter()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Determine if it is possible to add a teleporter to the subgrid
	 * @return
	 */
	public boolean canAddTeleporter(){
		for(PointData pd : this.subgridArray){
			if(this.isSafe(pd)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Add a single teleporter to the subgrid
	 * 
	 * @param destinationId
	 * @return
	 */
	private boolean addTeleporter(int destinationId){
		//first teleporter added
		if(this.teleporters.size() == 0 && this.subgridArray.size() > 0){
			teleporters.add(this.subgridArray.get(0));
			teleporterDestinations.put(destinationId, null);
			this.subgridArray.get(0).setTeleporter(true);
			return true;
		}

		LinkedList<PointData> visitOrder = this.multiBFS(teleporters, this.subgridArray);
		while(!visitOrder.isEmpty()){
			PointData candidate = visitOrder.removeLast();
			if(this.isSafe(candidate)){
				teleporters.add(candidate);
				teleporterDestinations.put(destinationId, null);
				candidate.setTeleporter(true);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Add a teleporter to subgrid
	 * @param destination
	 * @return
	 */
	public boolean addTeleporter(Subgrid destination){
		return this.addTeleporter(destination.groupID);
	}
	
	/**
	 * @return the pseudoTeleporter
	 */
	public PointData getPseudoTeleporter() {
		return pseudoTeleporter;
	}
	
	/**
	 * Add a pseudo teleporter if one does not exist
	 * @return
	 */
	public boolean addPseudoTeleporter(){
		if(this.pseudoTeleporter != null){
			return true;
		}
		if(this.addTeleporter(-1)){
			this.pseudoTeleporter = teleporters.get(teleporters.size() - 1);
			this.teleporters.remove(teleporters.size() - 1);
			this.teleporterDestinations.remove(-1);
			this.pseudoTeleporter.setPseudoTeleporter(true);
			this.pseudoTeleporter.setTeleporter(false);
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Assign teleporter destinations.  
	 * Do this after all teleporters have been added
	 */
	public void assignTeleporterDestinations(){
		Collections.shuffle(teleporters);
		int i = 0;
		for(Integer destination : teleporterDestinations.keySet()){
			teleporterDestinations.put(destination, teleporters.get(i));
			++i;
		}
	}
	
	@Override
	public boolean equals(Object that){
		return this.groupID == ((Subgrid) that).groupID;
	}
}