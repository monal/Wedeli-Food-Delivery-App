package com.smapp.wedelis;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		Thread splashThread = new Thread()
		{
			@Override
			public void run() {
				try{
					int waited =0;
					while(waited < 5000)
					{
						sleep(100);
						waited+=100;
					}
				} catch (InterruptedException e) {
				}finally{		
				finish();
				Intent i =new Intent(SplashScreen.this,MainActivity.class);
				startActivity(i);
				}
			}
			
		};
		splashThread.start();
	}
	@Override
	public void onBackPressed() {
		
		return ;
	}

}
