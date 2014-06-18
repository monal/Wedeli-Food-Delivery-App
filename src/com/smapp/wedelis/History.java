package com.smapp.wedelis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class History extends Activity{
	ListView lv;
	
	String[] order_id;
	String[] status;
	String[] menu_id;
	String[] menu_name={""};
	String[] res_name={" "};
	String[] type;
	String[] size={" "};
	String[] price={" "};
	static int l;
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    /*requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		    String orderUrl=getString(R.string.ipadd)+"showHistory.php";
	    	getOrder(orderUrl);
	        setContentView(R.layout.history);
            lv=(ListView)findViewById(R.id.historylist);
           	        
	     

	      //here was on progressUpdate
	        
	  }
	  public boolean isNetworkAvailable() {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			 NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			 if (networkInfo != null && networkInfo.isConnected()) {
			        return true;
			    }		
			return false;
		}
	  @Override
      public boolean onTouchEvent(MotionEvent event){
       // TODO Auto-generated method stub
         return gestureDetector.onTouchEvent(event);
      }

      
      SimpleOnGestureListener simpleOnGestureListener= new SimpleOnGestureListener(){
     @Override
     public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
       float sensitvity = 18;
       if((e1.getX() - e2.getX()) > sensitvity){
    	   //Left Swipe
    	  Toast.makeText(getApplicationContext(),"Swipe Left", Toast.LENGTH_LONG).show();      	
      }else if((e2.getX() - e1.getX()) > sensitvity){
    	  //swipe Right
      	Intent in=new Intent(History.this,json_parse.class);
      	startActivity(in);
      }
     
      return super.onFling(e1, e2, velocityX, velocityY);
     }
      };
     
      @SuppressWarnings("deprecation")
	GestureDetector gestureDetector = new GestureDetector(simpleOnGestureListener);
	  
    
  	@Override
  	public void onBackPressed() {
  		//Intent i = new Intent(History.this,json_parse.class);
  		//startActivity(i);
  		super.finish();
  	}
  	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
 	        getMenuInflater().inflate(R.menu.activity_main, menu);
 	        return true;
 	  }
 	  @Override
 	    public boolean onOptionsItemSelected(MenuItem item) {
 	        // Handle item selection
 	        switch (item.getItemId()) {
 	        case R.id.logOut:
 	        	SharedPreferences del =PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
 	        	SharedPreferences.Editor ed = del.edit();
 	        	ed.clear();
     	        ed.commit();
     	        Intent i=new Intent(History.this,MainActivity.class);
     	        startActivity(i);    	        		
 	        	return true;
 	        case R.id.exit: 
 	        	super.finish();
 	        default:
 	            return super.onOptionsItemSelected(item);
 	        }
 	    }
 	 public void getOrder(String orderUrl) {
 		class getOrder extends AsyncTask<String, Void ,String>
 		{
 			private final ProgressDialog dialog = new ProgressDialog(History.this);

     @Override
     protected void onPreExecute() {
         this.dialog.setMessage("Fetching Order History..");
         this.dialog.show();

     }
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
		      for (final String url : params) {
		    	  try
		    	  {
		    		  ArrayList<NameValuePair> nameValuePairs1= new ArrayList<NameValuePair>();
		    		  SharedPreferences readUi = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		    		  String UID = readUi.getString("UID", "-1");
		    		  Log.d("UID ORDER",UID);
						nameValuePairs1.add(new BasicNameValuePair("uid",UID));
						HttpClient httpclient1= new DefaultHttpClient();
						
						HttpPost httppost1= new HttpPost(url);
						httppost1.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
						if(isNetworkAvailable())
						{
						HttpResponse httpResponse1= httpclient1.execute(httppost1);
						Log.d("ent",httpResponse1.toString());
				        HttpEntity resEntityGet1= httpResponse1.getEntity();
				        InputStream inputStream = resEntityGet1.getContent();
						InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
						BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
						StringBuilder stringBuilder = new StringBuilder();
						String bufferedStrChunk = null;
						while((bufferedStrChunk = bufferedReader.readLine()) != null){
							stringBuilder.append(bufferedStrChunk);
						}
						return stringBuilder.toString();
						}
						else
						{
							while(!isNetworkAvailable())
							{
								this.dialog.setMessage("Connecting to Internet...");
						         this.dialog.show();
							}
							getOrder(url);
							
						}
				        	
				  	      
	            } //try
	            catch(Exception e)
	            {
	            	
	            	Log.e("ERROR FROM ORDER TASK","ERROR");
	            }
		
		    }
		      return "false"; 
		}
@Override
protected void onProgressUpdate(Void... values) {
	// TODO Auto-generated method stub
	//super.onProgressUpdate(values);
	
	
}
		
     
 @Override
 protected void onPostExecute(String result)
 {
 	try
 	{
 	JSONArray arr = new JSONArray(result);
		String arrlen = Integer.toString(arr.length());
		Log.d("Array Length", arrlen);
		int len=Integer.parseInt(arrlen);
		menu_id=new String[len];
		menu_name = new String[len];
		res_name =new String[len];
		type = new String[len];
		size=new String[len];
		price =new String[len];
		
		
		for(int i=0;i<arr.length();i++)
		{
			JSONObject food = arr.getJSONObject(i);
			menu_id[i] = food.getString("menu_id");
			menu_name[i]=food.getString("menu_name");
			res_name[i]=food.getString("res_name");
			type[i]=food.getString("type");
			size[i]=food.getString("size");
			price[i]=food.getString("unit_cost");
			Log.d("MENU ID["+i+"]",menu_id[i]);
			Log.d("MENU NAME["+i+"]",menu_name[i]);
			Log.d("RES NAME["+i+"]",res_name[i]);
			Log.d("TYPE["+i+"]",type[i]);
			Log.d("SIZE["+i+"]",size[i]);
			Log.d("PRICE["+i+"]",price[i]);
			
		}
		
 	}
		catch(JSONException e)
		{
			Log.d("I AM STUCKED IN JSON PARSING","HELP ME");
		}
 	  ArrayAdapter<String> mmadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, menu_name);
 	    lv.setAdapter(mmadapter);
 	    lv.setOnItemClickListener(new OnItemClickListener() {
 	    	  
 	    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
 	    		// TODO Auto-generated method stub
 	    		Toast.makeText(getApplicationContext(),"You Clicked on  " + menu_name[arg2], Toast.LENGTH_LONG).show();
 	    		Intent i = new Intent(History.this,SendOrderFromHis.class);
 	    		i.putExtra("menu_id", menu_id[arg2]);
 	    		i.putExtra("menu_name", menu_name[arg2]);
 	    		i.putExtra("Price", price[arg2]);
 	    		i.putExtra("size", size[arg2]);
 	    		i.putExtra("type", type[arg2]);
 	    		startActivity(i);
 	    		 	    		
 	    		
 	    	}
 	    	}); 
 		
 	if(this.dialog.isShowing())
     this.dialog.dismiss();
 	          
			    		

		}
}
 		new getOrder().execute(orderUrl);
		   
		   
 		
 	}

}
	       
	        


	 

