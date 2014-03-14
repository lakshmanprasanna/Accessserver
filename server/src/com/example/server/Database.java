package com.example.server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.server.Dtatabasehelper.FeedEntry;

public class Database extends SQLiteOpenHelper {
	private static final String DATABASE_USER_CREATE = "CREATE TABLE IF NOT EXISTS "
		      + FeedEntry.TABLE_NAME + "(" + FeedEntry.COLUMN_USER_ID + " INTEGER PRIMARY KEY, " + FeedEntry.COLUMN_USER_NAME + " TEXT);";
	
	private static final String DATABASE_PHOTO_CREATE = "CREATE TABLE IF NOT EXISTS "
		      + FeedEntry.TABLE1_NAME + "(" + FeedEntry.COLUMN_PHOTO_ID + " INTEGER PRIMARY KEY, " + FeedEntry.COLUMN_USER_ID + " INTEGER, " + FeedEntry.COLUMN_PHOTO_NAME + " TEXT);";
	
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "proj2.db";
    private static final String SQL_INITIAL_DELETE_ENTRIES =
    	    "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        
    }


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_USER_CREATE);
		db.execSQL(DATABASE_PHOTO_CREATE);
		System.out.println("Creating table");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL(SQL_INITIAL_DELETE_ENTRIES);
		
	}

}


