package com.cs326.team5.qr_labyrinth;

import java.util.ArrayList;

import android.app.Application;

public class GridStorage extends Application{
	
	private ArrayList<Grid> levelList;
	private ArrayList<Grid> customList;;
	
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
