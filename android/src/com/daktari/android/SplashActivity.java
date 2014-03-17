package com.daktari.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {

	 private static String TAG = SplashActivity.class.getName();
	 private static long SLEEP_TIME = 2;    // Sleep for some time

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
	  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,     WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar

	  setContentView(R.layout.splash);

	  // Start timer and launch main activity
	  IntentLauncher launcher = new IntentLauncher();
	  launcher.start();
	}

	 private class IntentLauncher extends Thread {
	  @Override
	  /**
	   * Sleep for some time and than start new activity.
	   */
	  public void run() {
	     try {
	        // Sleeping
	        Thread.sleep(SLEEP_TIME*1000);
	     } catch (Exception e) {
	        Log.e(TAG, e.getMessage());
	     }

	     
	     SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
			
	     
	     // Start main activity
	   
			Boolean isRegistered= pref.getBoolean("isRegistered", false);
			
			 Intent intent = new Intent(SplashActivity.this, LoginPage.class);
			if(isRegistered)
			{
				  intent = new Intent(SplashActivity.this, GridViewActivity.class);
			}
	     
	     SplashActivity.this.startActivity(intent);
	     SplashActivity.this.finish();
	  }
	}
	}