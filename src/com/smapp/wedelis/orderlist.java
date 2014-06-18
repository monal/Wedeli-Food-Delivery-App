package com.smapp.wedelis;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class orderlist extends Activity{
	ListView lv;
	SQLiteDatabase db;
	MySQLiteHelper dbHelper;
	static int i;
	SharedPreferences apref;
	static String[] name,price,qty,mid;
	static String response1,uid;
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
	        setContentView(R.layout.op);
	        dbHelper = new MySQLiteHelper(this);
			db =dbHelper.getWritableDatabase();
			
	         name=new String[30];
	       price=new String[30];
	        qty=new String[30];
	         mid=new String[30];
	         apref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	         uid= apref.getString("UID", "admin");
	        lv=(ListView)findViewById(R.id.finallist);
	        Cursor cursor = db.query(dbHelper.TABLE, new String[]{dbHelper.C_NAME,dbHelper.C_PRICE,dbHelper.C_QTY,dbHelper.C_MID}, null, null, null, null, null);
			int i=0;
			if (cursor.moveToFirst())
		    {
		        do {          
		        	String name_1 = cursor.getString(cursor.getColumnIndex(dbHelper.C_NAME));
					String name_2 = cursor.getString(cursor.getColumnIndex(dbHelper.C_PRICE));
					String name_3=cursor.getString(cursor.getColumnIndex(dbHelper.C_QTY));
					String name_4=cursor.getString(cursor.getColumnIndex(dbHelper.C_MID));
					name[i]=name_1;
					price[i]=name_2;
					qty[i]=name_3;
					mid[i]=name_4;
					i++;
		        } while (cursor.moveToNext());
		    }
			
		  
	        Bundle b=new Bundle();
	        b.putStringArray("names", name);
	        b.putStringArray("prices", price);
	        b.putStringArray("qty", qty);

	       listadapter adapter = new listadapter(orderlist.this,b);
	        try
	        {
	       lv.setAdapter(adapter);
	        }
	        catch(Exception e)
	        {
	        	
	        }
	        
	        Button b1=(Button)findViewById(R.id.cancelbtn);
	        b1.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent in=new Intent(orderlist.this,json_parse.class);
					startActivity(in);
				}
			});
	        
	        Button b2=(Button)findViewById(R.id.confirmbtn);
	        b2.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					String url=getString(R.string.confirm_order_url);
				       new confirmOrder().execute(url);
				}
			});
	 }

	 
	 
	 
	 
	 
	 
	 
	 class confirmOrder extends AsyncTask<String, Void ,String>
     {
     	private final ProgressDialog dialog1 = new ProgressDialog(orderlist.this);

         @Override
         protected void onPreExecute() {
             this.dialog1.setMessage("Confirming order..");
             this.dialog1.show();

         }
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
			      for (String url : params) {
			    	  try
			            {
			            	ArrayList<NameValuePair> nameValuePairs1= new ArrayList<NameValuePair>();
							nameValuePairs1.add(new BasicNameValuePair("menu_id",mid[0]));
							nameValuePairs1.add(new BasicNameValuePair("uid",uid));
							nameValuePairs1.add(new BasicNameValuePair("quantity",qty[0]));
							HttpClient httpclient1= new DefaultHttpClient();
							HttpPost httppost1= new HttpPost(url);
							httppost1.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
							HttpResponse httpResponse1= httpclient1.execute(httppost1);
							Log.d("ent",httpResponse1.toString());
					        HttpEntity resEntityGet1= httpResponse1.getEntity();
					        if (resEntityGet1!= null) 
					        {  
					                    //do something with the response
			                    		response1= EntityUtils.toString(resEntityGet1);
					                    Log.i("GET RESPONSE",response1);
					                    Log.d("response", response1);
					                    
					        									        			}//
					        
			            }
			            
					                catch(Exception e)
					                {}
			
			    }
			      return response1; 
			}
			
			 @Override
		        protected void onPostExecute(String result)
		        {
		        	if(this.dialog1.isShowing())
		                this.dialog1.dismiss();
		        	
		        	Intent in=new Intent(orderlist.this,thankyou.class);
		            
		            startActivity(in);
		    			
		    		
		        }
		       
     }
}
