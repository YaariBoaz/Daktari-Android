package com.daktari.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	private final String[] mobileValues;

	public ImageAdapter(Context context, String[] mobileValues) {
		this.context = context;
		this.mobileValues = mobileValues;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {

			gridView = new View(context);

			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.mobile, null);

			// set value into textview

			// set image based on selected text
			ImageView imageView = (ImageView) gridView
					.findViewById(R.id.grid_item_image);

			String mobile = mobileValues[position];

			if (mobile.equals("share_SMS")) {
				imageView.setImageResource(R.drawable.sms_new);
			}
			else if (mobile.equals("call")) 
			{
				imageView.setImageResource(R.drawable.call_new);
			}
			else if (mobile.equals("contact"))
			{
				imageView.setImageResource(R.drawable.sendmail_new);
			}
			else if (mobile.equals("facebook"))
			{
				imageView.setImageResource(R.drawable.facebook_new);
			}
			else if (mobile.equals("hot"))
			{
				imageView.setImageResource(R.drawable.hot_new);
			}
			else if (mobile.equals("share"))
			{
				imageView.setImageResource(R.drawable.share_new);
			}
			else if (mobile.equals("gallery")) 
			{
				imageView.setImageResource(R.drawable.gallery_new);
			}
			else if (mobile.equals("map")) 
			{
				imageView.setImageResource(R.drawable.map_new);
			}
			else if (mobile.equals("about"))
			{
				imageView.setImageResource(R.drawable.about_new01);
			} 
		}
		else
		{
		   gridView = (View) convertView;
		}
			return gridView;
		}

		@Override
		public int getCount() {
			return mobileValues.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

	}
