package com.daktari.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Context context;
	LocationManager   mLocationManager;
	LocationManager locManager;
	Location location;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.program_listview_one);
		context=this;
		
		
		Intent intent = new Intent(context,com.daktari.android.HotStuff.class);
		context.startActivity(intent);
		finish();
//		Parse.initialize(this, "at13zo9lNQjWBAiCEHvyaDvJNsG1SfGJFFo4nMw1", "DJU6OyRjQ5OPNX0VwF9G7k6nn958adJaflrFhb51");	
//		PushService.setDefaultPushCallback(this, MainActivity.class);
//		PushService.subscribe(getBaseContext(), "all", HotStuff.class);
//		ParseInstallation.getCurrentInstallation().saveInBackground();
		ListView lstOne = (ListView) findViewById(R.id.listviewOne);
		AdapterStatic adapter = new AdapterStatic(this, R.layout.checkbox_list_row, fillLstOne());
		lstOne.setAdapter(adapter);
		LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);   
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,500.0f, mLocationListener);
		location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		lstOne.setOnItemClickListener(new OnItemClickListener()
		{
			@Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
			{  
				if(arg3==0)
				{
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:046405005"));
					context.startActivity(intent);
				}
				if(arg3==1)
				{
					Intent in=new Intent(MainActivity.this,HotStuff.class);
					startActivity(in);
				}
				if(arg3==2)
				{
					final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
					if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
						buildAlertMessageNoGps();
					}
					else
					{
						double latitude=0;
						double longitude=0;
						if(location!=null)
						{
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
						String url = "http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+32.604224+","+35.294016;
						Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
						intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
						//startActivity(intent);
						Intent in=new Intent(MainActivity.this,MapActivity.class);
						startActivity(in);
					}

				}

				if(arg3==3)
				{
					context.startActivity(getOpenFacebookIntent(context));
				}

				if(arg3==4)
				{

					Intent in=new Intent(MainActivity.this,ShareApp.class);
					startActivity(in);

					/*Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.email");
					startActivity(intent);*/
					//sendSMS("0543089208","test");
				}
				if(arg3==5)
				{

				}

			}
		});
	}

	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
				startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
				dialog.cancel();
			}
		});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	private final LocationListener mLocationListener = new LocationListener() {
		@Override
		public void onLocationChanged(final Location location) {
			//your code here
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub

		}
	};

	//---sends an SMS message to another device---
	private void sendSMS(String phoneNumber, String message)
	{        
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
				new Intent(SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
				new Intent(DELIVERED), 0);

		//---when the SMS has been sent---
		registerReceiver(new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode())
				{
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS sent", 
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Generic failure", 
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No service", 
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "Null PDU", 
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "Radio off", 
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(SENT));

		//---when the SMS has been delivered---
		registerReceiver(new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode())
				{
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS delivered", 
							Toast.LENGTH_SHORT).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "SMS not delivered", 
							Toast.LENGTH_SHORT).show();
					break;                        
				}
			}
		}, new IntentFilter(DELIVERED));        

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);        
	}

	public static Intent getOpenFacebookIntent(Context context) {

		try {
			context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
			return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100002652772574"));
		} catch (Exception e) {
			return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/100002652772574"));
		}
	}
	private Outocoupled[] fillLstOne()
	{
		Outocoupled[]  data = new Outocoupled[5];
		data[0] = new Outocoupled("חייג עכשיו", true, R.drawable.call);
		data[1] = new Outocoupled("מבצעים", true, R.drawable.hot_moti_big);
		data[2] = new Outocoupled("הצג מפה", true, R.drawable.map);
		data[3] = new Outocoupled("Facebook", true, R.drawable.social_3);
		data[4] = new Outocoupled("שתף", true, R.drawable.bubbles);
		//data[5] = new Outocoupled("יומן קניות", true, R.drawable.dog1);
		return data;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
