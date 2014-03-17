package com.daktari.android;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class SalesObject implements Parcelable
{
String text;
Bitmap image;
public SalesObject(String text, Bitmap image) 
{
	this.text = text;
	this.image = image;
}
@Override
public int describeContents() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public void writeToParcel(Parcel dest, int flags) {

}


}
