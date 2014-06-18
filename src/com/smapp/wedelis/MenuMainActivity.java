package com.smapp.wedelis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuMainActivity extends Activity {
	
	 private static final long GET_DATA_INTERVAL = 5000;
     int images[] = {R.drawable.burgers,R.drawable.kdbck,R.drawable.tbck,R.drawable.mmbck};
     int index = 0;
     ImageView img;
     Handler hand = new Handler();
     TextView greetView;
     

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.menu_main);
        Toast.makeText(this, "Swipe Right to Go to Your Past Orders", Toast.LENGTH_LONG).show();
        // Reading User Details From Shared Preferences
       SharedPreferences get = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name = get.getString("NAME", "me ");
        String id = get.getString("UID", "0");
        greetView= (TextView) findViewById(R.id.greetTV);
        greetView.setText("Hello "+ name);
        String url=getString(R.string.ipadd)+getString(R.string.getCategory);
        Log.d("URL",url);
       //String url = "http://192.168.244.1/wedeli/getCat.php";
        	grabURL(url);
            img = (ImageView) findViewById(R.id.imageview);
            hand.postDelayed(run, 0);
    }

    


	@Override
    public boolean onTouchEvent(MotionEvent event) {
         return gestureDetector.onTouchEvent(event);
    }
    SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener(){

   @Override
   public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
     float sensitvity = 18;
    
    if((e1.getX() - e2.getX()) > sensitvity){
    	Intent in=new Intent(MenuMainActivity.this,History.class);
    	startActivity(in);
    }else if((e2.getX() - e1.getX()) > sensitvity){
    	Toast.makeText(getApplicationContext(),"Swipe Right", Toast.LENGTH_LONG).show();
    }
       
    return super.onFling(e1, e2, velocityX, velocityY);
   }
    };
   
    @SuppressWarnings("deprecation")
	GestureDetector gestureDetector= new GestureDetector(simpleOnGestureListener);
  
    //old code
    Runnable run = new Runnable() {
	public void run() {
		 
		                img.setBackgroundDrawable(getResources().getDrawable(images[index++]));
		                if (index == images.length)
		                    index = 0;
		                hand.postDelayed(run, GET_DATA_INTERVAL);
			}
           
           
            
        };
        
       
        private long lastBackPressTime = 0;
    	private Toast toast;
    	@Override
    	public void onBackPressed() {
    	 if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
    	    toast = Toast.makeText(this, "Press back again to close this app", 4000);
    	    toast.show();
    	    this.lastBackPressTime = System.currentTimeMillis();
    	  } else {
    	    if (toast != null) {
    	    toast.cancel();
    	    }
    	   super.finish();
    	 }
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
        	        Intent i=new Intent(MenuMainActivity.this,MainActivity.class);
        	        startActivity(i);    	        		
    	        	return true;
    	        case R.id.exit: 
    	        	super.finish();
    	        default:
    	            return super.onOptionsItemSelected(item);
    	        }
    	    }
    	  
    	  int start = 0;
    		 int limit = 9999;
    		 String response;
    	  	String build;
    	     String[] catname;
    	     String[] cat;
    		 
    		 
    				 public void grabURL(String url) {
    			  Log.v("Android Spinner JSON Data Activity", url);
    			  new GrabURL().execute(url);
    			 }
    		 private class GrabURL extends AsyncTask<String, Void, String> {
    			  private static final int REGISTRATION_TIMEOUT = 3 * 1000;
    			  private static final int WAIT_TIMEOUT = 30 * 1000;
    			  private final HttpClient httpclient = new DefaultHttpClient();
    			  final HttpParams params = httpclient.getParams();
    			   private boolean error = false;
    			  private ProgressDialog dialog =   new ProgressDialog(MenuMainActivity.this);
    			 
    			  protected void onPreExecute() {
    			   dialog.setMessage("Fetching Categories...");
    			   dialog.show();
    			  }
    			 
    			  protected String doInBackground(String... urls) {
    			 
    			   String URL = null;
    			   try {
    				   
    				   URL = urls[0];
    				   HttpConnectionParams.setConnectionTimeout(params, REGISTRATION_TIMEOUT);
    				   HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
    				   ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);
    				   HttpGet get = new HttpGet(URL);
    				   HttpClient client=new DefaultHttpClient();
    				   HttpResponse responseGet;
    				   try{
    				   responseGet = client.execute(get);
    				   HttpEntity resEntityGet = responseGet.getEntity();  
        	   	       
   	   	        		if (resEntityGet != null) {
   	   	        		InputStream instream = resEntityGet.getContent();
   	   	        		BufferedReader str = new BufferedReader(new InputStreamReader(instream));
   	   	   				String ans = new String("");
   	   	   				build = new String("");
   	   	   				while ((ans = str.readLine()) != null) {
   	   	   					build = build + ans;
   	   	   					Log.d("JSON", ans);
   	   	   					}
   	   	        		}
   	   	        		return build;
    				   }
    				   catch(Exception e)
    				   {
    					   
    					   grabURL(URL);
    				   }
    				   
    	   	        	
    			   }
    			   catch(Exception e){
    				   
    				   error = true;
    			   }
    			return "I am not responding";
    			  }
    				 
    				  protected void onCancelled() {
    				   dialog.dismiss();
    				   Toast toast = Toast.makeText(MenuMainActivity.this, 
    				     "Error connecting to Server", Toast.LENGTH_LONG);
    				   toast.setGravity(Gravity.TOP, 25, 400);
    				   toast.show();
    				 
    				  }
    				 
    				  protected void onPostExecute(String content) {
    				   dialog.dismiss();
    				   Toast toast;
    				   if (error) {
    				    toast = Toast.makeText(MenuMainActivity.this, 
    				      content, Toast.LENGTH_LONG);
    				    toast.setGravity(Gravity.TOP, 25, 400);
    				    toast.show();
    				   } else {
    				   populate(content);
    				   }
    				  }
    		 }

    				private void populate(String content2) {
    					try
    					{
    						JSONArray arr = new JSONArray(content2);
    						String arrlen = Integer.toString(arr.length());
    						Log.d("Array Length", arrlen);
    						int len=Integer.parseInt(arrlen);
    						catname=new String[len];
    						cat=new String[len];
    						for(int i=0;i<arr.length();i++)
    						{
    							JSONObject food = null;
    							food = arr.getJSONObject(i);
    							String category = food.getString("Menu_category");
    							String catid=food.getString("cat_id");
    							catname[i]=category;
    							cat[i]=catid;
    							Log.d("category",category);
    							Log.d("cat_id",catid);
    							Log.d("list",catname[i]);
    							Log.d("list",cat[i]);
    						
    						}
    					}catch(JSONException je)
    					{
    						je.printStackTrace();
    					}
    					ListView lv = (ListView)findViewById(R.id.listview);
    	 	    	    catadapter adapter = new catadapter(this, catname);
    	 	    		lv.setAdapter(adapter);
    	 	            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    	 					public void onItemClick(AdapterView<?> arg0, View arg1,
    	 							int position, long arg3) {
    	 						String keyword=cat[position].toString();
    	 						Toast.makeText(getApplicationContext(), "Category="+keyword,Toast.LENGTH_LONG).show();
    	 					}
    	 				});
    	 	      
    					
    					
    				}

    
}



   
