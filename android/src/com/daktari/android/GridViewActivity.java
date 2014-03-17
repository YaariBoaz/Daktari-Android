package com.daktari.android;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GridViewActivity extends Activity {

	GridView gridView;
	Context context;
	LocationManager   mLocationManager;
	LocationManager locManager;
	Location location;
	static final String[] MOBILE_OS = new String[] { 
		"share_SMS", "call","contact", "facebook","hot","share","gallery","map","about"};

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context=this;
		Parse.initialize(this,"at13zo9lNQjWBAiCEHvyaDvJNsG1SfGJFFo4nMw1", "DJU6OyRjQ5OPNX0VwF9G7k6nn958adJaflrFhb51");
		PushService.setDefaultPushCallback(this, MainActivity.class);
		PushService.subscribe(getBaseContext(), "all", HotStuff.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();		
		LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);   
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,500.0f, mLocationListener);
		location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(new ImageAdapter(this, MOBILE_OS));
	
		gridView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
			{  
				//share via SMS
				if(position==0)
				{
					String messageBody = "לדקטרי יש אפליקציה חדשה, שווה להוריד https://play.google.com/store/apps/details?id=com.daktari.android&hl=en";
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					intent.setType("vnd.android-dir/mms-sms");
					intent.putExtra( "sms_body", messageBody );
					startActivity(intent);					
				}
				//call
				if(position==1)
				{
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:046405005"));
					context.startActivity(intent);
				}
				//contact us
				if(position==2)
				{
					Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
							"mailto","errorshop@gmail.com", null));
					emailIntent.putExtra(Intent.EXTRA_SUBJECT, "פנייה לדקטרי");
					emailIntent.putExtra(Intent.EXTRA_TEXT, ".כתוב את תוכן הפנייה כאן");
					startActivity(Intent.createChooser(emailIntent, "Send email..."));
					
				}
				//Facebook
				if(position==3)
				{
					context.startActivity(getOpenFacebookIntent(context));
				}
				//hot 
				if(position==4)
				{
					Intent in=new Intent(GridViewActivity.this,HotStuff.class);
					startActivity(in);
				}
				//share via mail
				if(position==5)
				{
					Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
							"mailto","", null));
					emailIntent.putExtra(Intent.EXTRA_SUBJECT, "לדקטרי יש אפליקציה חדשה, שווה להוריד");
					emailIntent.putExtra(Intent.EXTRA_TEXT, " https://play.google.com/store/apps/details?id=com.daktari.android&hl=en");
					startActivity(Intent.createChooser(emailIntent, "Send email..."));

				}

				// gallery
				if(position==6)
				{
					Intent in=new Intent(GridViewActivity.this,GalleryActivity.class);
					startActivity(in);
				}

				//share via map
				if(position==7)
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
						Intent in=new Intent(GridViewActivity.this,MapActivity.class);
						startActivity(in);
					}
				}
				// info
				if(position==8)
				{
					Intent in=new Intent(GridViewActivity.this,AboutUs.class);
					startActivity(in);
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
