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
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;

/**
 * Wrapper around opensudoku's database.
 * <p/>
 * You have to pass application context when creating instance:
 * <code>SudokuDatabase db = new SudokuDatabase(getApplicationContext());</code>
 * <p/>
 * You have to explicitly close connection when you're done with database (see {@link #close()}).
 * <p/>
 * This class supports database transactions using {@link #beginTransaction()}, \
 * {@link #setTransactionSuccessful()} and {@link #endTransaction()}.
 * See {@link SQLiteDatabase} for details on how to use them.
 *
 * @author romario
 */
public class mDatabase {
	public static final String DATABASE_NAME = "opensudoku";


	public static final String SUDOKU_TABLE_NAME = "sudoku";
	public static final String FOLDER_TABLE_NAME = "folder";
	public static final String BOADRD_TABLE_NAME = "board";

	//private static final String TAG = "SudokuDatabase";

	private DatabaseHelper mOpenHelper;

	public mDatabase(Context context) {
		mOpenHelper = new DatabaseHelper(context);
	}

	/**
	 * Returns list of puzzle folders.

	 *
	 */
	public Cursor getSCOREList() {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(BOADRD_TABLE_NAME);

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		return qb.query(db, null, null, null, null, null, "score desc");
	}
	
	public void inputScore(String name, String t) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		mOpenHelper.insertBOARD(db, name, t);
	}

	public void close() {

		mOpenHelper.close();
	}

	public void beginTransaction() {
		mOpenHelper.getWritableDatabase().beginTransaction();
	}

	public void setTransactionSuccessful() {
		mOpenHelper.getWritableDatabase().setTransactionSuccessful();
	}

	public void endTransaction() {
		mOpenHelper.getWritableDatabase().endTransaction();
	}
}
