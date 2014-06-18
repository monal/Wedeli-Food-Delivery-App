package com.smapp.wedelis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	Button Login;
	EditText email_id;
	EditText passwd;
	TextView register;
	String name;
	String uid;
	Boolean isRem=false;
	CheckBox remChe;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		
        SharedPreferences autLog=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       if(autLog.getBoolean("SAVE", false))
       {
    	   isRem=true;
    	   if(isNetworkAvailable())
    	   {
    		   sendPostRequest(autLog.getString("EMAIL", null), autLog.getString("PASS", null));
    	   }
    	   else
    	   {
    		   Toast.makeText(getApplicationContext(), "Cannot reach Wedeli Server", Toast.LENGTH_LONG).show();
    	   }
    	   
       }
       setContentView(R.layout.login);
       email_id = (EditText) findViewById(R.id.email_log);
       passwd=(EditText) findViewById(R.id.passwd);
       Login=(Button) findViewById(R.id.LoginBut);
       register = (TextView) findViewById(R.id.NotRegTV);
       Login.setOnClickListener(this);
       register.setOnClickListener(this);
       remChe = (CheckBox) findViewById(R.id.rememberChe);
        remChe.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				
				// TODO Auto-generated method stub
				isRem=arg1;
			}
			
		
				
			
		});
        
               
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }
    public void sendPostRequest(final String givenUsername,  final String givenPassword) {

		class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{

			@Override
			protected String doInBackground(String... params) {

					String paramUsername = params[0];
					String paramPassword = params[1];
					Log.d("**DOING BACKGROUND**","paramUsername: " + paramUsername + " paramPassword :" + paramPassword);
					HttpClient httpClient = new DefaultHttpClient();
					String ip=getString(R.string.ipadd);
					String loginUrl=getString(R.string.urlLogin);
					String url=ip+loginUrl;
					HttpPost httpPost = new HttpPost(url);
					BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("email_reg", paramUsername);
					BasicNameValuePair passwordBasicNameValuePAir = new BasicNameValuePair("passwd", paramPassword);
					List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
					nameValuePairList.add(usernameBasicNameValuePair);
					nameValuePairList.add(passwordBasicNameValuePAir);
					try {
					 
					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
					httpPost.setEntity(urlEncodedFormEntity);

					try {
							if(isNetworkAvailable())
							{
								HttpResponse httpResponse = httpClient.execute(httpPost);
								InputStream inputStream = httpResponse.getEntity().getContent();
								InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
								BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
								StringBuilder stringBuilder = new StringBuilder();
								String bufferedStrChunk = null;
								while((bufferedStrChunk = bufferedReader.readLine()) != null){
								stringBuilder.append(bufferedStrChunk);
								return stringBuilder.toString();
								}
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Internet Connection Not Available",Toast.LENGTH_LONG ).show();
								while(isNetworkAvailable()==false);
								sendPostRequest(paramUsername, paramPassword);
							}
					} catch (ClientProtocolException cpe) {
							sendPostRequest(paramUsername, paramPassword);
							//System.out.println("First Exception caz of HttpResponese :" + cpe);
							cpe.printStackTrace();
					} catch (IOException ioe) {
							sendPostRequest(paramUsername, paramPassword);
							//System.out.println("Second Exception caz of HttpResponse :" + ioe);
							ioe.printStackTrace();
					}

				} catch (UnsupportedEncodingException uee) {
						System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
						uee.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if(result.substring(0,3).equals("Log")){
						String dataJson = result.substring(3);
						
						Log.d("JSON",dataJson);
						try {
							JSONArray uJson = new JSONArray(dataJson);
							JSONObject userDet= null;
							userDet = uJson.getJSONObject(0);
							uid=userDet.getString("uid");
							name = userDet.getString("name");
							
							Log.d("User NAme : "+ name ,"UID:"+uid);
							Toast.makeText(getApplicationContext(), "Hello "+name, Toast.LENGTH_SHORT).show();
							try{
							
						
								SharedPreferences file = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
								SharedPreferences.Editor ed= file.edit();
								ed.putString("UID", uid);
								ed.putString("NAME", name);
								ed.putString("EMAIL",givenUsername);
								ed.putString("PASS", givenPassword);
								ed.putBoolean("SAVE", isRem);
								ed.commit();
							}
							catch(Exception e){}
							
							
						} catch(JSONException e) {
							e.printStackTrace();
						}
						
						Intent i=new Intent(MainActivity.this,json_parse.class);
						startActivity(i);
					}else{
						Toast.makeText(getApplicationContext(), "Sorry Dude!! Not a valid Username or Password", Toast.LENGTH_LONG).show();
				}
			}			
		}

		SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
		sendPostReqAsyncTask.execute(givenUsername, givenPassword);		
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
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case R.id.menu_settings:
				Toast.makeText(this, "SETTINGS is Selected. Customize it Later",Toast.LENGTH_SHORT).show();
			return true;
		default:
		return super.onOptionsItemSelected(item);}
		}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.LoginBut)
		{
			String givenUsername=email_id.getText().toString();
			String givenPassword=passwd.getText().toString();
			//Log.d("USERNAME",givenUsername+" "+givenPassword);
			if(isNetworkAvailable())
			{
				sendPostRequest(givenUsername, givenPassword);
			
			}
			else
				Toast.makeText(this, "You are not connected to internet!!", Toast.LENGTH_LONG).show();
		}
		else if(v.getId()==R.id.NotRegTV)
		{
			Intent i= new Intent(MainActivity.this,RegisterAct.class);
			Log.d("REGISTER","You clicked Register Going to New Activity");
			startActivity(i);
		}
	}
	

}
