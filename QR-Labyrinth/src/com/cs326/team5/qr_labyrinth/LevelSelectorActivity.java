/**
 * 
 */
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

public abstract class LevelSelectorActivity extends Activity{
    
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    }
    
    protected void onResume(){
    	super.onResume();
    }
    
    protected abstract void setupListView();

    protected abstract Grid checkFile(String n);
    
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
		return g;
	}
    
 	protected abstract void handleButton(View v);
}
