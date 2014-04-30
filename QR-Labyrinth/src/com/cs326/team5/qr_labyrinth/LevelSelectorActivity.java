package com.cs326.team5.qr_labyrinth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * Abstract class for activities in which level selection occurs
 * @author Team 5
 */
public abstract class LevelSelectorActivity extends Activity{
    
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    }
    
    protected void onResume(){
    	super.onResume();
    }
    
    /**
     * Populates a list view with level ID's
     */
    protected abstract void setupListView();

    /**
     * Checks for the n'th level file to read a Grid
     * @param n file number
     * @return g level read from file
     */
    protected abstract Grid checkFile(String n);
    
    /**
     * Writes a grid to its cooresponding file
     * @param s name of the file
     * @param g grid to be written to file
     */
    protected void writeGrid(String s, Grid g){
		FileOutputStream fos;
		ObjectOutputStream os;
		try {
			fos = openFileOutput(s, Context.MODE_PRIVATE);
			os = new ObjectOutputStream(fos);
			os.writeObject(g);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
    /**
     * Reads a grid from file
     * @param f file to be read from
     * @return g grid read from file
     */
    protected Grid loadGrid(File f){
		FileInputStream fis = null;
		ObjectInputStream is = null;
		Grid g;
		try {
			fis = new FileInputStream(f);
			is = new ObjectInputStream(fis);
			g = (Grid) is.readObject();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			try {
				is.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return null;
		}
		return g;
	}
    
    /**
 	 * Handles button clicks for the UI
 	 * @param v view that was clicked
 	 */
 	protected abstract void handleButton(View v);
}
