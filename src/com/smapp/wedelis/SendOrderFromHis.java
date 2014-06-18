package com.smapp.wedelis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendOrderFromHis extends Activity{
String menu_id,menu_name, size, price,type,UID,response1,qnty;
EditText quantity;
@Override
protected void onCreate(Bundle b) {
	// TODO Auto-generated method stub
	super.onCreate(b);
	b=getIntent().getExtras();
	menu_id=b.getString("menu_id");
	menu_name=b.getString("menu_name");
	price =b.getString("Price");
	size=b.getString("size");
	type=b.getString("type");
	Log.d("MENU_ID",menu_id);
	Log.d("Menu_name",menu_name);
	Log.d("Price",price);
	setContentView(R.layout.sentorderfromhis);
	TextView MenuName ;
	MenuName = (TextView) findViewById(R.id.menuHistTV);
	MenuName.setText(menu_name+"        "+price);
	Button reorder=(Button) findViewById(R.id.reorderBut);
	 quantity= (EditText) findViewById(R.id.quantityET);
	reorder.setOnClickListener(new View.OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			String url = getString(R.string.ipadd)+"insert_order.php";
			SharedPreferences file = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			UID=file.getString("UID", "NA");
			qnty=quantity.getEditableText().toString();
			int total;
			total = Integer.parseInt(qnty) * Integer.parseInt(price);
			Toast.makeText(getApplicationContext(), "Total Payable Amount ="+total,Toast.LENGTH_LONG).show();		
			insertOrder(url);
		}
			
		
		
		
	});
	
	
	
}
public void insertOrder(String URL)
{
class insertOrder extends AsyncTask<String, Void ,String>
{
	private final ProgressDialog dialog1 = new ProgressDialog(SendOrderFromHis.this);

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
	    		  BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("uid", UID);
					BasicNameValuePair menuIdBasicNameValuePAir = new BasicNameValuePair("menu_id",menu_id);
					BasicNameValuePair quantityBasicNameValuePair = new BasicNameValuePair("quantity", qnty);
					List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
					nameValuePairList.add(usernameBasicNameValuePair);
					nameValuePairList.add(menuIdBasicNameValuePAir);
					nameValuePairList.add(quantityBasicNameValuePair);
					
					HttpClient httpclient1= new DefaultHttpClient();
					HttpPost httppost1= new HttpPost(url);
					httppost1.setEntity(new UrlEncodedFormEntity(nameValuePairList));
					if(isNetworkAvailable())
					{
					HttpResponse httpResponse1= httpclient1.execute(httppost1);
					Log.d("ent",httpResponse1.toString());
			        HttpEntity resEntityGet1= httpResponse1.getEntity();
			        if (resEntityGet1!= null) 
			        {  
			                    //do something with the response
	                    		response1= EntityUtils.toString(resEntityGet1);
			                    Log.i("GET RESPONSE",response1);
			                    Log.d("response", response1);
			                   
			                    
			       	}
					}
					else
					{
						while(!isNetworkAvailable())
	            		{
	            			this.dialog1.setMessage("Connecting to Internet...");
	                        this.dialog1.show();
	            		}
						insertOrder(url);
					}
	            }
	            
			                catch (UnsupportedEncodingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (ClientProtocolException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
	
	    }
	      return response1; 
	}
	
    
@Override
protected void onPostExecute(String result)
{
	if(this.dialog1.isShowing())
        this.dialog1.dismiss();
	if(result.equals("INSERTED"))
	{
		Intent i= new Intent(SendOrderFromHis.this,History.class);
		startActivity(i);
		finish();
	}
	else
	{
		Toast.makeText(getApplicationContext(), "Could not finish taking order", Toast.LENGTH_LONG).show();
	}

 }


}
new insertOrder().execute(URL);
}
public boolean isNetworkAvailable() {
	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	 NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	 if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }		
	return false;
}
}
