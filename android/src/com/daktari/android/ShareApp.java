package com.daktari.android;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class ShareApp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.program_listview_one);
		/*PushService.subscribe(getBaseContext(), "gilix26@gmail.com", MainActivity.class);*/
		ListView lstOne = (ListView) findViewById(R.id.listviewOne);
		AdapterStatic adapter = new AdapterStatic(this, R.layout.checkbox_list_row, fillLstOne());
		lstOne.setAdapter(adapter);

		lstOne.setOnItemClickListener(new OnItemClickListener()
		{
			@Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
			{  
				if(arg3==0)
				{
						
					//sendSMS("0543089208","test");
					
					Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
				            "mailto","", null));
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "לדקטרי יש אפליקציה חדשה, שווה להוריד");
				emailIntent.putExtra(Intent.EXTRA_TEXT, " https://play.google.com/store/apps/details?id=com.daktari.android&hl=en");
				startActivity(Intent.createChooser(emailIntent, "Send email..."));
				}
				if(arg3==1)
				{
					Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
				            "mailto","", null));
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "לדקטרי יש אפליקציה חדשה, שווה להוריד");
				emailIntent.putExtra(Intent.EXTRA_TEXT, " https://play.google.com/store/apps/details?id=com.daktari.android&hl=en");
				startActivity(Intent.createChooser(emailIntent, "Send email..."));

				}

			}
		});

	}

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
	
	
	private Outocoupled[] fillLstOne()
	{
		Outocoupled[]  data = new Outocoupled[1];
		//data[0] = new Outocoupled("SMS", true, R.drawable.email_b);
		data[0] = new Outocoupled("Email", true, R.drawable.email);
		//data[2] = new Outocoupled("Facebook", true, R.drawable.dog1);

		return data;
	}


}
