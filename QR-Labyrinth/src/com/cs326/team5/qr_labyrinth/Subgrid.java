package com.cs326.team5.qr_labyrinth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class Subgrid {
		
	Grid grid;
	int groupID;
	ArrayList<PointData> subgridArray = new ArrayList<PointData>();
	
	HashMap<Integer, PointData> teleporterDestinations;
	ArrayList<PointData> teleporters;
	private PointData pseudoTeleporter = null;
	
	public Subgrid(Grid grid, int groupId){
		this.grid = grid;
		this.groupID = groupId;
	}
	
	public void addTeleporter(int x, int y){
		this.teleporters.add(grid.getGrid()[x][y]);
	}
	
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
					isEnqueued.put(pd, false);
					toVisit.add(pd);
				}
			}
		}
		
		return visited;
	}
	
	private boolean isSafe(PointData teleportAdd){
		if(teleportAdd.hasTeleporter()){
			return false;
		}
		for(PointData p : grid.getWhiteNeighbors(teleportAdd)){
			if(p.hasTeleporter()){
				return false;
			}
		}
		return true;
	}
	
	public boolean canAddTeleporter(){
		for(PointData pd : this.subgridArray){
			if(this.isSafe(pd)){
				return true;
			}
		}
		return false;
	}
	
	private boolean addTeleporter(int destinationId){
		if(this.teleporters.size() == 0 && this.subgridArray.size() > 0){
			teleporters.add(this.subgridArray.get(0));
			return true;
		}
		
		LinkedList<PointData> visitOrder = this.multiBFS(teleporters, this.subgridArray);
		while(!visitOrder.isEmpty()){
			PointData candidate = visitOrder.removeLast();
			if(this.isSafe(candidate)){
				teleporters.add(candidate);
				teleporterDestinations.put(destinationId, null);
				return true;
			}
		}
		return false;
	}
	
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
	 * @param pseudoTeleporter the pseudoTeleporter to set
	 */
	public void setPseudoTeleporter(PointData pseudoTeleporter) {
		this.pseudoTeleporter = pseudoTeleporter;
	}
	
	public boolean addPseudoTeleporter(){
		if(this.pseudoTeleporter != null){
			return true;
		}
		if(this.addTeleporter(-1)){
			this.teleporters.remove(teleporters.size() - 1);
			this.setPseudoTeleporter(teleporters.get(teleporters.size() - 1));
			return true;
		}
		else{
			return false;
		}
	}

	public void assignTeleporterDestinations(){
		Collections.shuffle(teleporters);
		int i = 0;
		for(Integer destination : teleporterDestinations.keySet()){
			teleporterDestinations.put(destination, teleporters.get(i));
			++i;
		}
	}
}