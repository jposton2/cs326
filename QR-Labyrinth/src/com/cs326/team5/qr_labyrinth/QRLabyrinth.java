package com.cs326.team5.qr_labyrinth;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;

public class QRLabyrinth extends Application{
	
	protected final String levelIDsFile = "levelIDs.txt";
	protected final String customIDsFile = "customIDs.txt";
	private ArrayList<String> levelIDs;
	private ArrayList<String> customIDs;
	private HashMap<Integer,Grid> levelList;
	private HashMap<Integer,Grid> customList;;
	private Grid currentLevel;
	
	public ArrayList<String> getLevelIDs() {
		return levelIDs;
	}

	public void setLevelIDs(ArrayList<String> levelIDs) {
		this.levelIDs = levelIDs;
	}

	public ArrayList<String> getCustomIDs() {
		return customIDs;
	}

	public void setCustomIDs(ArrayList<String> customIDs) {
		this.customIDs = customIDs;
	}
	
	public Grid getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(Grid currentLevel) {
		this.currentLevel = currentLevel;
	}

	public HashMap<Integer,Grid> getLevelList() {
		return levelList;
	}
	
	public void setLevelList(HashMap<Integer,Grid> levelList) {
		this.levelList = levelList;
	}
	
	public HashMap<Integer,Grid> getCustomList() {
		return customList;
	}
	
	public void setCustomList(HashMap<Integer,Grid> customList) {
		this.customList = customList;
	}
	
	

}
