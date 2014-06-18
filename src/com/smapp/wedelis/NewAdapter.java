package com.smapp.wedelis;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
@SuppressWarnings("unchecked")
public class NewAdapter extends BaseExpandableListAdapter {

	public ArrayList<String> groupItem;
	ArrayList<String> tempChild,tempChild1;
	public ArrayList<Object> Childtem = new ArrayList<Object>();
	public LayoutInflater minflater;
	public ExpandableListViewActivity a;
	public Activity activity;
	Context context;
	
	static int pos1,pos2;
	public NewAdapter(ArrayList<String> grList, ArrayList<Object> childItem) {
		groupItem = grList;
		this.Childtem = childItem;
	}

	public void setInflater(LayoutInflater mInflater, Activity act) {
		this.minflater = mInflater;
		activity = act;
	}

	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}
	public NewAdapter(Context context) {
        this.context = context;     
   }

	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		tempChild = (ArrayList<String>) Childtem.get(groupPosition);
		//tempChild1 = (ArrayList<String>) Childtem.get(groupPosition);
		final TextView tex1,tex2;
		ImageButton b;
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.child_layout, null);
		}
		tex1 = (TextView) convertView.findViewById(R.id.textView1);
		
		b=(ImageButton)convertView.findViewById(R.id.button1);
		b.setImageResource(R.drawable.ic_action_search);
		Spannable sp=new SpannableString(tempChild.get(childPosition));
	     sp.setSpan(new ForegroundColorSpan(Color.TRANSPARENT),0,10, 0);
		tex1.setText(tempChild.get(childPosition));
		
	//	tex2.setText(tempChild1.get(childPosition));
		final CharSequence str=tex1.getText();
	    str.toString();
		b.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Log.d("gp",groupPosition+"");
				Log.d("cp",childPosition+"");
				pos1=groupPosition;
				pos2=childPosition;
						
				Intent in=new Intent(v.getContext(),orderpage.class);
				in.putExtra("p", pos1+"");
				in.putExtra("c", pos2+"");
				in.putExtra("data", str);
				v.getContext().startActivity(in);
			}
			
		});
		
		

		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return ((ArrayList<String>) Childtem.get(groupPosition)).size();
	}

	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupItem.size();
	}

	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.group_layout, null);
		}
		((CheckedTextView) convertView).setText(groupItem.get(groupPosition));
		((CheckedTextView) convertView).setChecked(isExpanded);
		convertView.setBackgroundResource(R.color.red);
		return convertView;
		
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}
	
	public int getData1()
	{
		int d=pos1;
		return d;
	}
	public int getData2()
	{
		int d=pos2;
		return d;
	}
	
		 

		}
	

