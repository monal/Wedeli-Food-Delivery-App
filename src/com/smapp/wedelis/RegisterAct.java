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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterAct extends Activity {
	private EditText name,email,phone,hostel,room,pass1,pass2; 
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	/*requestWindowFeature(Window.FEATURE_NO_TITLE);
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
	setContentView(R.layout.register);
	name = (EditText) findViewById(R.id.name);
	email =(EditText) findViewById(R.id.email_reg);
	phone = (EditText) findViewById(R.id.phone_reg);
	pass1 = (EditText) findViewById(R.id.pass1);
	pass2 = (EditText) findViewById(R.id.pass2);
	hostel = (EditText) findViewById(R.id.HostelEt);
	room =(EditText) findViewById(R.id.roomNoEt);
}
public void ButtonOnClick(View v) {
	switch (v.getId()) {
	case R.id.RegisterBut:
		registerMe();
		break;
	case R.id.ClearBut:
		if(name.getText()!=null || email.getText() !=null || phone.getText()!=null ||hostel.getText()!=null||room.getText()!=null||pass1.getText()!=null||pass2.getText()!=null){
			name.setText(null);
			email.setText(null);
			phone.setText(null);
			hostel.setText(null);
			pass1.setText(null);
			pass2.setText(null);
			room.setText(null);
		}
		break;
	
	}
}
private void registerMe() {
	String EtName = name.getText().toString();
	String EtEmail = email.getText().toString();
	String EtPhone =phone.getText().toString();
	String EtHostel =hostel.getText().toString();
	String EtRoom = room.getText().toString();
	String EtPass1=pass1.getText().toString();
	String EtPass2 = pass2.getText().toString();
	if(!(EtName.isEmpty() || EtEmail.isEmpty() || EtPhone.isEmpty() || EtHostel.isEmpty() || EtRoom.isEmpty() || EtPass1.isEmpty() || EtPass2.isEmpty()))
	{
		if(isEmailValid(EtEmail))
		{
			if(EtPass1.equals(EtPass2))
			{
				if(isNetworkAvailable())
					sendPostRequest(EtName, EtEmail,EtPass1,EtPhone,EtHostel,EtRoom);
				else 
					Toast.makeText(this,"You are not connected to internet", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(this, "Password doesn't Match", Toast.LENGTH_LONG).show();
				pass1.setText(null);
				pass2.setText(null);
			}
			
		}
		else
			Toast.makeText(this, "Enter BITS Goa Email id!!", Toast.LENGTH_LONG).show();
	} else
		Toast.makeText(this, " !! It seems you forget to fill Some Details !!", Toast.LENGTH_LONG).show();
}

private boolean isEmailValid(String etEmail) {
	if(etEmail.endsWith("@bits-goa.ac.in") && (etEmail.charAt(0)=='f' || etEmail.charAt(0)=='h') )
	{
		return true;
	}	
	return false;
}
private boolean isNetworkAvailable() {
	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	 if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }		
	return false;
}
@Override
public void onBackPressed() {
	super.onBackPressed();
	Log.d("BACK","Pressed back in register Activity");
	Intent i=new Intent(RegisterAct.this,MainActivity.class);
	startActivity(i);
}
private void sendPostRequest(final String givenName, final String givenEmail,final String givenPassword,final String givenPhone,final String givenHostel,final String givenRoom) {
		
	class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {

			String paramName = params[0];
			String paramEmail = params[1];
			String paramPass = params[2];
			String paramPhone = params[3];
			String paramHostel=params[4];
			String paramRoom = params[5];

			
			Log.d("**DOING BACKGROUND**","paramName: " + paramName + " paramEmail :" + paramEmail + "paramPhone:"+paramPhone);
			HttpClient httpClient = new DefaultHttpClient();

			
			String ip=getString(R.string.ipadd);
			String regUrl=getString(R.string.urlRegister);
			String url=ip+regUrl;
			HttpPost httpPost = new HttpPost(url);

			
			BasicNameValuePair nameBasicNameValuePair =new BasicNameValuePair("name",paramName );
			BasicNameValuePair emailBasicNameValuePair = new BasicNameValuePair("email_reg", paramEmail);
			BasicNameValuePair passBasicNameValuePair=new BasicNameValuePair("passwd", paramPass);
			BasicNameValuePair phoneBasicNameValuePAir = new BasicNameValuePair("phone_reg", paramPhone);
			BasicNameValuePair hostelBasicNameValuePair = new BasicNameValuePair("hostel", paramHostel);
			BasicNameValuePair roomBasicNameValuePair = new BasicNameValuePair("room", paramRoom);


			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			nameValuePairList.add(nameBasicNameValuePair);
			nameValuePairList.add(emailBasicNameValuePair);
			nameValuePairList.add(passBasicNameValuePair);
			nameValuePairList.add(phoneBasicNameValuePAir);
			nameValuePairList.add(hostelBasicNameValuePair);
			nameValuePairList.add(roomBasicNameValuePair);

			try {
				UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
				httpPost.setEntity(urlEncodedFormEntity);

				try {
					// HttpResponse is an interface just like HttpPost.
					//Therefore we can't initialize them
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
					}
					return stringBuilder.toString();
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Internet Connection not Available", Toast.LENGTH_LONG).show();
						while(!isNetworkAvailable());
						sendPostRequest(paramName, paramEmail, paramPass, paramPhone, paramHostel, paramRoom);						
					}
				} catch (ClientProtocolException cpe) {
					
					Log.d("CAUGHT IN CPE ","sending again");
					cpe.printStackTrace();
					sendPostRequest(paramName, paramEmail, paramPass, paramPhone, paramHostel, paramRoom);	
					
				} catch (IOException ioe) {
					
					Log.d("CAUGHT IN IOE ","sending again");
					ioe.printStackTrace();
					sendPostRequest(paramName, paramEmail, paramPass, paramPhone, paramHostel, paramRoom);	
					
				}

			} catch (UnsupportedEncodingException uee) {
				Log.d("CAUGHT IN UEE ","sedning again");
				sendPostRequest(paramName, paramEmail, paramPass, paramPhone, paramHostel, paramRoom);	
				uee.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d("RESULT",result);
			Toast.makeText(getApplicationContext(), result.substring(0,5), Toast.LENGTH_LONG).show();

			if(result.substring(0,5).equals("Hello")){
				String rest=result.substring(5);
				String uid;
				String name;
				try {
					JSONArray uJson = new JSONArray(rest);
					JSONObject userDet= null;
					userDet = uJson.getJSONObject(0);
					
					uid=userDet.getString("uid");
					name = userDet.getString("name");
					Log.d("User NAme : "+ name ,"UID:"+uid);
					SharedPreferences file = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					SharedPreferences.Editor ed= file.edit();
					ed.putString("UID", uid);
					ed.putString("NAME", name);
					ed.commit();
					
					
				} catch(JSONException e) {
					
					e.printStackTrace();
				}
				Toast.makeText(getApplicationContext(), "You are registered to wedeli Now", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(RegisterAct.this,MenuMainActivity.class);
				startActivity(i);
				
			}else{
				Toast.makeText(getApplicationContext(), "Cannot complete Registeration", Toast.LENGTH_LONG).show();
			}
		}			
	}

	SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
	sendPostReqAsyncTask.execute(givenName,givenEmail,givenPassword,givenPhone,givenHostel,givenRoom);		
}

}
