package com.smapp.wedelis;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class thankyou extends Activity {
	SQLiteDatabase db;
	MySQLiteHelper dbHelper;
	public static final String DB_NAME = "wedeli_orders.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "orders";
	public static final String C_ID = BaseColumns._ID;
	public static final String C_NAME  = "t_name";
	public static final String C_PRICE = "t_price";
	public static final String C_QTY = "t_qty";
	public static final String C_MID = "t_mid";
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.thankyou);
	        dbHelper = new MySQLiteHelper(this);
			db =dbHelper.getWritableDatabase();
			Button b1=(Button)findViewById(R.id.returnbtn);
	        b1.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					  if(db!=null)
						{
						db.delete("orders", null, null);
						}
						  db.close();
					  Intent intent = new Intent(thankyou.this,json_parse.class);
					   startActivity(intent);
				}
	        });
			Button b2=(Button)findViewById(R.id.exitbtn);
	        b2.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub

					  if(db!=null)
						{
						db.delete("orders", null, null);
						}
						  db.close();
						  Intent intent = new Intent(Intent.ACTION_MAIN);
						  intent.addCategory(Intent.CATEGORY_HOME);
						  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						  startActivity(intent);
						 				}
			});
	      
	 
	 }
}