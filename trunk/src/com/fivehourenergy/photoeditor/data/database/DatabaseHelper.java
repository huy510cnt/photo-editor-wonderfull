package com.fivehourenergy.photoeditor.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final 	String 	DATABASE_NAME 							= "photo_editor_wonder.db";
	
	public static final 	String 	FAVOURITE_TABLE							= "FAVOURITE";
	public static final 	String 	PHOTO_ID								= "ID";
	public static final 	String 	PHOTO_NAME								= "NAME";
	public static final 	String 	PHOTO_PATH								= "PATH";
	public static final 	String 	PHOTO_DATE_MODIFIED						= "DATE_MODIFIED";
	public static final 	String 	PHOTO_FOLDER_PATH						= "FOLDER_PATH";
	
	private static final String DATABASE_CREATE_COMIC 			= "create table "
			+ FAVOURITE_TABLE 							+ "(" 
			+ PHOTO_ID 								+ " integer primary key autoincrement, "
			+ PHOTO_NAME							+ " text, "
			+ PHOTO_PATH							+ " text, "
			+ PHOTO_DATE_MODIFIED					+ " text, "
			+ PHOTO_FOLDER_PATH 					+ " text ); " ;
	
	private static int DATABASE_VERSION 						= 1;
			

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION );
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_COMIC);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + FAVOURITE_TABLE);
		onCreate(db);
	}

}
