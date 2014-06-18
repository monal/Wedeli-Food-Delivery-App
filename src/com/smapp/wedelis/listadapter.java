package com.smapp.wedelis;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class listadapter extends ArrayAdapter<String>
{
	Activity context;
	private final String[] names;
	private final String[] prices;
	private final String[] qty;
	
	 public String[] attitude_values;
	public listadapter(Activity context,Bundle b) {
		super(context, R.layout.finallistlayout,b.getStringArray("names"));
		// TODO Auto-generated constructor stub
		this.context =context;
		this.names = b.getStringArray("names");
		this.prices=b.getStringArray("prices");
		this.qty=b.getStringArray("qty");
		
		
	}

	
	
	
	
	// static to save the reference to the outer class and to avoid access to
	// any members of the containing class
	static class ViewHolder {
		
		public TextView textView;
		public TextView price;
		public TextView qty;
		public TextView pricetotal;
		
		
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// ViewHolder will have reference to the individual fields of the row
		// layout
		final ViewHolder holder;
		
		View rowView = convertView;
		if (rowView == null) {
			//create the view as it is null
			//inflate the xml layout
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.finallistlayout, null, false);
			holder = new ViewHolder();
			Log.d("fdse2","fd");
			holder.textView = (TextView) rowView.findViewById(R.id.name);
		holder.price = (TextView) rowView.findViewById(R.id.price);
		holder.pricetotal = (TextView) rowView.findViewById(R.id.pricetotal);
			holder.qty = (TextView) rowView.findViewById(R.id.qtybox);
		    rowView.setTag(holder);
		} else {
			//reuse the old view and get access to it
			holder = (ViewHolder) rowView.getTag();
		}
		//attach the data into the old view
		holder.textView.setText(names[position]);
		holder.price.setText(prices[position]);
		
		holder.qty.setText(qty[position]);
		holder.pricetotal.setText("");
	       
		return rowView;
	}

}
