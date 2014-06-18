package com.smapp.wedelis;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class catadapter extends ArrayAdapter<String> 
{
	private final Activity context;
	private final String[] names;
	public catadapter(Activity context,String[] names) {
		super(context, R.layout.rowlayout, names);
		// TODO Auto-generated constructor stub
		this.context =context;
		this.names = names;
	}

	
	
	
	
	// static to save the reference to the outer class and to avoid access to
	// any members of the containing class
	static class ViewHolder {
		
		public TextView textView;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ViewHolder will have reference to the individual fields of the row
		// layout
		ViewHolder holder;
		// Recycle existing view if passed as parameter
		// This will save memory and time on Android
		// This only works if the base layout for all classes are the same
		View rowView = convertView;
		if (rowView == null) {
			//create the view as it is null
			//inflate the xml layout
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.rowlayout, null, true);
			holder = new ViewHolder();
			
			holder.textView = (TextView) rowView.findViewById(R.id.tv);
		    rowView.setTag(holder);
		} else {
			//reuse the old view and get access to it
			holder = (ViewHolder) rowView.getTag();
		}
		//attach the data into the old view
		holder.textView.setText(names[position]);
		// Atatch the icons u want from the drawables
		
		
		return rowView;
	}

}
