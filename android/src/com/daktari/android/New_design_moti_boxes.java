package com.daktari.android;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class New_design_moti_boxes extends Activity {

	Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_layout_boxes_main);




		LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);   
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,500.0f, mLocationListener);
		location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}

	public void call_click(View v) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:046405005"));
		this.startActivity(intent);
	}

	public void facebook_click(View v) {

		this.startActivity(getOpenFacebookIntent(this));
	}

	public void share_click(View v) {
		Intent in=new Intent(New_design_moti_boxes.this,ShareApp.class);
		startActivity(in);
	}

	public void map_click(View v) {
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
			Intent in=new Intent(New_design_moti_boxes.this,MapActivity.class);
			startActivity(in);
		}
	}

	public static Intent getOpenFacebookIntent(Context context) {

		try {
			context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
			return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100002652772574"));
		} catch (Exception e) {
			return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/100002652772574"));
		}
	}

	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Gps לא פעיל, האם תרצה להפעיל אותו?")
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


}
