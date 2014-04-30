package com.cs326.team5.qr_labyrinth;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;

/**
 * Stores and maintains all shared data across different activities
 * @author Team 5
 */
public class QRLabyrinth extends Application{
	
	protected final String levelIDsFile = "levelIDs.txt";
	protected final String customIDsFile = "customIDs.txt";
	private ArrayList<String> levelIDs;
	private ArrayList<String> customIDs;
	private HashMap<Integer,Grid> levelList;
	private HashMap<Integer,Grid> customList;;
	private Grid currentLevel;
	/**
	 * @return the levelIDs
	 */
	public ArrayList<String> getLevelIDs() {
		return levelIDs;
	}
	/**
	 * @param levelIDs the levelIDs to set
	 */
	public void setLevelIDs(ArrayList<String> levelIDs) {
		this.levelIDs = levelIDs;
	}
	/**
	 * @return the customIDs
	 */
	public ArrayList<String> getCustomIDs() {
		return customIDs;
	}
	/**
	 * @param customIDs the customIDs to set
	 */
	public void setCustomIDs(ArrayList<String> customIDs) {
		this.customIDs = customIDs;
	}
	/**
	 * @return the levelList
	 */
	public HashMap<Integer, Grid> getLevelList() {
		return levelList;
	}
	/**
	 * @param levelList the levelList to set
	 */
	public void setLevelList(HashMap<Integer, Grid> levelList) {
		this.levelList = levelList;
	}
	/**
	 * @return the customList
	 */
	public HashMap<Integer, Grid> getCustomList() {
		return customList;
	}
	/**
	 * @param customList the customList to set
	 */
	public void setCustomList(HashMap<Integer, Grid> customList) {
		this.customList = customList;
	}
	/**
	 * @return the currentLevel
	 */
	public Grid getCurrentLevel() {
		return currentLevel;
	}
	/**
	 * @param currentLevel the currentLevel to set
	 */
	public void setCurrentLevel(Grid currentLevel) {
		this.currentLevel = currentLevel;
	}
}
