package com.cs326.team5.qr_labyrinth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{
	
	//All Static Variables
	//Database Version
	private static final int DATABASE_VERSION = 1;
	//Database Name
	private static final String DATABASE_NAME = "highScoresManager";
	//Scores table name
	private static final String TABLE_SCORES = "scores";
	
	//Scores Table Column Names
	private static final String MAP_NAME = "map_name";
	private static final String HIGH_SCORE = "high_score"; 
	
	public DatabaseHandler(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_SCORES_TABLE = "CREATE TABLE " + TABLE_SCORES + "(" + MAP_NAME + "TEXT PRIMARY KEY" + HIGH_SCORE + "INT)";
		db.execSQL(CREATE_SCORES_TABLE);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_SCORES);
		onCreate(db);
	}
	
	//Add new high score
	public void addScore(String name, int score){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(MAP_NAME, name);
		values.put(HIGH_SCORE, score);
		
		db.insert(TABLE_SCORES, null, values);
		db.close();
	}
	
	public Object[] getScore(String name){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_SCORES, new String[]{MAP_NAME, HIGH_SCORE}, MAP_NAME + "=?", new String[]{String.valueOf(name)}, null, null, null, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		
		Object[] highScore = new Object[2];
		highScore[0] = cursor.getString(0);
		highScore[1] = cursor.getString(1);
		return highScore;
	}
	
	public int updateScore(String name, int score){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(MAP_NAME, name);
		values.put(HIGH_SCORE, score);
		
		//updating row
		return db.update(TABLE_SCORES, values, MAP_NAME + " = ?", new String[] {String.valueOf(name)});
	}
	
}
