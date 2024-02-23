package com.board;
/* 
 * Copyright (C) 2009 Roman Masek
 * 
 * This file is part of OpenSudoku.
 * 
 * OpenSudoku is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * OpenSudoku is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with OpenSudoku.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */





import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

/**
 * This class helps open, create, and upgrade the database file.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "DatabaseHelper";

	public static final int DATABASE_VERSION = 9;

	private Context mContext;

	DatabaseHelper(Context context) {
		super(context, mDatabase.DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + mDatabase.BOADRD_TABLE_NAME + " ("
				+ BoardColumns._ID + " INTEGER PRIMARY KEY,"
				+ BoardColumns.USERNAME + " TEXT,"
				+ BoardColumns.SCORE + " TEXT,"
				+ BoardColumns.COMMIT + " TEXT"
				+ ");");
		
	}

	
	void insertBOARD(SQLiteDatabase db,  String username, String score) 
	{
		String sql = "INSERT INTO " + mDatabase.BOADRD_TABLE_NAME + "(usrname, score, rcommit) VALUES ('" + username + "', '" + score + "', '');";
		db.execSQL(sql);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ".");

	}

}
