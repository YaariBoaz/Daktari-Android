package com.daktari.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class MyCustomReceiver extends BroadcastReceiver {
	private static final String TAG = "MyCustomReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			String action = intent.getAction();
			if(action.equalsIgnoreCase("com.daktari.push"))
			{
				Intent intent1 = new Intent(context,com.daktari.android.HotStuff.class);
				intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent1); 
			}

		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}


}


