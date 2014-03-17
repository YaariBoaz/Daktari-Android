package com.daktari.android;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class MainApplication extends Application
{
	@Override
	public void onCreate() 
	{
		super.onCreate();
		Parse.initialize(this,"at13zo9lNQjWBAiCEHvyaDvJNsG1SfGJFFo4nMw1", "DJU6OyRjQ5OPNX0VwF9G7k6nn958adJaflrFhb51");
		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		PushService.subscribe(this, "all", MainActivity.class);
	}
}
