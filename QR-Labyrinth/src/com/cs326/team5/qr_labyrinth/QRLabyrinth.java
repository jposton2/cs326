package com.cs326.team5.qr_labyrinth;

import java.util.ArrayList;

import android.app.Application;

public class QRLabyrinth extends Application{
	
	private ArrayList<Grid> levelList;
	private ArrayList<Grid> customList;;
	private Grid currentLevel;
	
	public Grid getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(Grid currentLevel) {
		this.currentLevel = currentLevel;
	}

	public ArrayList<Grid> getLevelList() {
		return levelList;
	}
	
	public void setLevelList(ArrayList<Grid> levelList) {
		this.levelList = levelList;
	}
	
	public ArrayList<Grid> getCustomList() {
		return customList;
	}
	
	public void setCustomList(ArrayList<Grid> customList) {
		this.customList = customList;
	}
	
	

}
