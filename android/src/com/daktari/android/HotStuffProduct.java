package com.daktari.android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Path.FillType;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HotStuffProduct extends Activity
{
	
	ImageView img;
	TextView tvText;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotstuffproduct);
		img = (ImageView)findViewById(R.id.img_hot);
		tvText = (TextView)findViewById(R.id.tv_hot);
		Bitmap saleImage = (Bitmap) getIntent().getExtras().get("SaleImage");
		String text = (String) getIntent().getExtras().get("SaleText");
		img.setImageBitmap(saleImage);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width-10, height/5);
		layoutParams.setMargins(0, 10, 0, 0);
		img.setLayoutParams(layoutParams);
		tvText.setText(text);
		
	}
}
