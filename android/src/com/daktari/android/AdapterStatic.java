package com.daktari.android;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterStatic  extends ArrayAdapter<Outocoupled>{

Context context; 
int layoutResourceId;    
Outocoupled data[] = null;

public AdapterStatic (Context context, int layoutResourceId, Outocoupled[] data) {
    super(context, layoutResourceId, data);
    this.layoutResourceId = layoutResourceId;
    this.context = context;
    this.data = data;
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
    
    Outocoupled prog = data[position];
    holder.txtTitle.setText(prog.title.toString());
    
    if(prog.isChecked)
    {
    holder.check.setImageResource(prog.icon);
    }
    else
    {
    	holder.check.setImageResource(0);
    }
  //row.performClick();
    return row;
}

static class OutocoupledHolder
{
	ImageView check;
    TextView txtTitle;
}
}