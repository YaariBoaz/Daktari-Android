package com.daktari.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;

public class HotStuff extends Activity {
	Adapter adapter;
	ListView lstOne;
	Context con = this;
	private ProgressDialog progress;
	private boolean isReady = false;
	ArrayList<SalesObject> sales = new ArrayList<SalesObject>();

	private class CountDownTask extends AsyncTask<String, Integer, Boolean> {
		// run on start - add please wait dialog
		// A callback method executed on UI thread on starting the task
		@Override
		protected void onPreExecute() {
			// Getting reference to the TextView tv_counter of the layout
			// activity_main
			super.onPreExecute();

			progress = new ProgressDialog(con);
			progress.setTitle("Please Wait.");
			progress.setMessage("Connecting...");
			progress.setCancelable(false);
			progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress.show();

			// Toast.makeText(getActivity(), "Please Wait",
			// Toast.LENGTH_LONG).show();
		}

		// doing the jobs that should connection to server etc...
		// A callback method executed on non UI thread, invoked after
		// onPreExecute method if exists
		@Override
		protected Boolean doInBackground(String... params) {

			long startTime = System.currentTimeMillis();

			ParseQuery<ParseObject> query = ParseQuery.getQuery("Sales");
			query.setLimit(5);
			query.addDescendingOrder("createdAt");
			query.whereNotEqualTo("text", "!!");
			List<ParseObject> objects;
			try {
				objects = query.find();
				for (int i =0; i < objects.size() ; i++) {
					Log.d("doInBackground", "doInBackground - Gilix: start:"+i);
					ParseObject obj = objects.get(i);
					final String text = obj.getString("text");
					ParseFile image = (ParseFile) obj.get("image");
					try {
						byte[] arr = image.getData();
						Log.d("doInBackground", "size of image - Gilix: start:"+arr.length);
						BitmapFactory.Options g=new BitmapFactory.Options();
						g.inSampleSize=16;
						Bitmap img;
						if(arr.length>130000)
						{
							img = BitmapFactory.decodeByteArray(arr, 0,arr.length,g);
						}
						else
						{
							img = BitmapFactory.decodeByteArray(arr, 0,arr.length);
						}

						sales.add(new SalesObject(text, img));
					} catch (com.parse.ParseException e1) {
						e1.printStackTrace();
					}
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			long stopTime = System.currentTimeMillis();
			Log.w("Elapsed time was ", + (stopTime - startTime) + " :miliseconds.");

			return true;
		}

		// call from doing background with publishProgress(values)
		// can change the dialog which setup onPreExecute
		// A callback method executed on UI thread, invoked by the
		// publishProgress()
		// from doInBackground() method
		@Override
		protected void onProgressUpdate(Integer... values) {

		}

		// now popup.hide and start handle the view with result from
		// doinbackgroud
		// occur when doinbackgroud finish
		// A callback method executed on UI thread, invoked after the completion
		// of the task
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			Log.d("", "onPostExecute - Gilix: start");

			if (result) {
				if (progress.isShowing())
					progress.dismiss();
				adapter = new Adapter(con, R.layout.checkbox_list_row, sales);
				lstOne.setAdapter(adapter);

			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this,"at13zo9lNQjWBAiCEHvyaDvJNsG1SfGJFFo4nMw1", "DJU6OyRjQ5OPNX0VwF9G7k6nn958adJaflrFhb51");
		PushService.setDefaultPushCallback(this, MainActivity.class);
		PushService.subscribe(getBaseContext(), "all", HotStuff.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();	
		setContentView(R.layout.program_listview_one);
		lstOne = (ListView) findViewById(R.id.listviewOne);
		lstOne.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				SalesObject sale = sales.get(arg2);
				Intent i = new Intent(getApplicationContext(),HotStuffProduct.class);
				i.putExtra("SaleImage", sale.image);
				i.putExtra("SaleText", sale.text);
				startActivity(i);
				
			
				
			}
		});
		con = this;
		// progress.show();

		//getData();
		CountDownTask c = new CountDownTask();
		c.execute("");

	}
}
