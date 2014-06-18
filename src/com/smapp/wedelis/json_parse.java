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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class json_parse extends Activity {
	static String response1;
 	static String build,build1,keyword;
    static String[] names,cat,menu,veg,id,desc,cost,size,res;
    ListView lv;
    static int len;
    Bundle b;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        b=new Bundle();
        lv=(ListView)findViewById(R.id.listview);
        String url=getString(R.string.get_categories_url);
       new getCategories().execute(url);
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(
					AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				keyword=cat[position].toString();
				b.putString("key",keyword);
				Log.d("key",keyword);
				String h=getString(R.string.get_dishes_url);
				new getDishes().execute(h);
				
				
			}
   });
    }
        class getCategories extends AsyncTask<String, Void ,String>
        {
        	private final ProgressDialog dialog = new ProgressDialog(json_parse.this);

            @Override
            protected void onPreExecute() {
                this.dialog.setMessage("Fetching menu..");
                this.dialog.show();

            }
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
			      for (String url : params) {
			    	  try
			    	  {
			    	    HttpClient client = new DefaultHttpClient();  
		                String getURL = url;
		                HttpGet get = new HttpGet(getURL);
		                HttpResponse responseGet = client.execute(get); 
		                        		                
		    	        HttpEntity resEntityGet = responseGet.getEntity();  
		    	       
		    	        if (resEntityGet != null) {
		    				InputStream instream = resEntityGet.getContent();
		    				BufferedReader str = new BufferedReader(new InputStreamReader(
		    						instream));
		    	
		    				String ans = new String("");
		    				build = new String("");
		    				while ((ans = str.readLine()) != null) {
		    					build = build + ans;
		    					Log.d("JSON", ans);
		    				}//while
		    				
		    				JSONArray arr = new JSONArray(build);
		    				String arrlen = Integer.toString(arr.length());
		    	    		Log.d("Array Length", arrlen);
		    	    		int len=Integer.parseInt(arrlen);
		    	    		names=new String[len];
		    	    		cat=new String[len];
		    	    		for(int i=0;i<arr.length();i++)
		    	    		{
		    	    			JSONObject food = arr.getJSONObject(i);
		    	    			String category = food.getString("Menu_category");
		    	    			String catid = food.getString("cat_id");
		    					
		    	    			names[i]=category;
		    	    			cat[i]=catid;
		    	    			Log.d("n",names[i]);
		    	    			Log.d("c",cat[i]);
		    	               
		    	                }//for
		    			}//if
		            } //try
		            catch(Exception e)
		            {}
			
			    }
			      return build; 
			}
			
            
        @Override
        protected void onPostExecute(String result)
        {
        	if(this.dialog.isShowing())
                this.dialog.dismiss();
        	catadapter adapter = new catadapter(json_parse.this, names);
    		lv.setAdapter(adapter);
           
    			    		

        }
         
        
        }
        
        class getDishes extends AsyncTask<String, Void ,String>
        {
        	private final ProgressDialog dialog1 = new ProgressDialog(json_parse.this);

            @Override
            protected void onPreExecute() {
                this.dialog1.setMessage("Fetching dishes..");
                this.dialog1.show();

            }
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
			      for (String url : params) {
			    	  try
			            {
			            	ArrayList<NameValuePair> nameValuePairs1= new ArrayList<NameValuePair>();
							nameValuePairs1.add(new BasicNameValuePair("catid",keyword));
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
					                    
					        				JSONArray arr1= new JSONArray(response1);
					        				String arrlen1= Integer.toString(arr1.length());
					        				Log.d("Array Length",arrlen1);
					        				len=Integer.parseInt(arrlen1);
					        				menu=new String[len];
					        				veg=new String[len];
					        				id=new String[len];
					        				desc=new String[len];
					        				cost=new String[len];
					        				size=new String[len];
					        				res=new String[len];
					        				
					        				b.putInt("length",len);
					        				for(int i=0;i<arr1.length();i++)
					        				{
					        					JSONObject food1= null;
					        					food1= arr1.getJSONObject(i);
					        					menu[i]= food1.getString("menu_name");
					         					veg[i]=food1.getString("type");
					         					id[i]=food1.getString("menu_id");
						        				desc[i]=food1.getString("menu_desc");
						        				cost[i]=food1.getString("unit_cost");
						        				size[i]=food1.getString("size");
						        				res[i]=food1.getString("res_name");
					        					   					
					        				}//for
					        				 b.putStringArray("menu",menu);
				 					         b.putStringArray("type",veg);
				 					         b.putStringArray("id",id);
				 					         b.putStringArray("desc",desc);
				 					         b.putStringArray("cost",cost);
				 					         b.putStringArray("size",size);
				 					         b.putStringArray("res",res);
				 					        
				 					        int l=menu.length;
					 					       int i;
					 					       for(i=0;i<l;i++)
					 					       {
					 					       Log.d("c",menu[i]);
					 					       }	
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
        	
        	Intent in=new Intent(json_parse.this,ExpandableListViewActivity.class);
            in.putExtras(b);
            startActivity(in);
    			
    		
        }
       
        
}
}
    	    
    	    