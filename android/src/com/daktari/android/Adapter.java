package com.daktari.android;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter extends ArrayAdapter<SalesObject>{

	Context context; 
    int layoutResourceId;    
    ArrayList<SalesObject> mData;
    
    public Adapter(Context context, int layoutResourceId, ArrayList<SalesObject> mData) {
        super(context, layoutResourceId, mData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.mData = mData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        OutocoupledHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new OutocoupledHolder();
            holder.check = (ImageView)row.findViewById(R.id.img_chk);
       
            holder.txtTitle = (TextView)row.findViewById(R.id.tv_check_title);
            
            row.setTag(holder);
        }
        else
        {
            holder = (OutocoupledHolder)row.getTag();
        }
        SalesObject prog = mData.get(position);
        holder.txtTitle.setText(prog.text.toString());
        holder.check.setImageBitmap(prog.image);
        return row;
    }
    
    static class OutocoupledHolder
    {
    	ImageView check;
        TextView txtTitle;
    }
}