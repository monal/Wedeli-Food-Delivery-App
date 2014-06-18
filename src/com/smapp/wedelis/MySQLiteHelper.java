package com.smapp.wedelis;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
		
		
		public MySQLiteHelper(Context context)
			 {
			super(context, DB_NAME,null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

		public static final String DB_NAME = "wedeli_orders.db";
		public static final int DB_VERSION = 1;
		public static final String TABLE = "orders";
		public static final String C_ID = BaseColumns._ID;
		public static final String C_NAME  = "t_name";
		public static final String C_PRICE = "t_price";
		public static final String C_QTY = "t_qty";
		public static final String C_MID = "t_mid";
		
		
		Context  context;
		
		//name is the database file name
		//database version number they are constant
		//its a best practice to have a unique identifier
		
		
		

		@Override
		public void onCreate(SQLiteDatabase db) {

			String testsql = String.format("create table %s (%s TEXT,%s TEXT,%s TEXT,%s TEXT)",TABLE,C_NAME,C_PRICE,C_QTY,C_MID);
			
			db.execSQL(testsql);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL("drop table if exists "+ TABLE);
			this.onCreate(db);
			
			
		}
		
		public Cursor getAllItems(SQLiteDatabase db)
		{
			return  db.query("orders", new String[] {"_id", "t_name"}, 
			         null, null, null, null, "name");
			   
		}

	}
