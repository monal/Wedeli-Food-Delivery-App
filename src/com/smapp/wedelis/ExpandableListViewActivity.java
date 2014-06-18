package com.smapp.wedelis;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class ExpandableListViewActivity extends ExpandableListActivity implements
OnChildClickListener {

	String[] array;
	String[] cat,id,res,desc,cost,size;
	static int l,c,c1,k;
	ArrayList<String> child;
	ArrayList<Integer> flag;
	SharedPreferences apref;
	SharedPreferences.Editor ed;
	LinearLayout layout;
	Button btn;
	
@Override
public void onCreate(Bundle b) {
super.onCreate(b);



layout = new LinearLayout(this);
b=getIntent().getExtras();
if(b!=null)
{

array=new String[l];
cat=new String[l];
id=new String[l];
desc=new String[l];
cost=new String[l];
size=new String[l];
res=new String[l];

array=b.getStringArray("menu");
cat=b.getStringArray("type");
id=b.getStringArray("id");
desc=b.getStringArray("desc");
cost=b.getStringArray("cost");
size=b.getStringArray("size");
res=b.getStringArray("res");
l=array.length;
flag=new ArrayList<Integer>();
int i,j;


for(i=0;i<l;i++)
{
	flag.add(l);
	
	
}

for(i=0;i<l;i++)
{


for(j=0;j<l;j++)
{
	int f=flag.get(j);
	if(array[j].equalsIgnoreCase(array[i]) && cat[j].equalsIgnoreCase(cat[i]) && f==l)
	{
		flag.remove(j);
		flag.add(j,i);
		
	}
		
}
   
	

}
for(i=0;i<l;i++)
{
	Log.d("F",flag.get(i)+"");
}

ExpandableListView expandbleLis = getExpandableListView();
expandbleLis.setDividerHeight(2);
expandbleLis.setGroupIndicator(null);
 
setGroupData();
setChildGroupData();

NewAdapter mNewAdapter = new NewAdapter(groupItem, childItem);
mNewAdapter
		.setInflater(
				(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
				this);
getExpandableListView().setAdapter(mNewAdapter);

getExpandableListView().setOnItemClickListener(new OnItemClickListener() {
	

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.d("g1","D");
		Toast.makeText(getBaseContext(), "das", Toast.LENGTH_LONG).show();
		
	}
});
}
}
public void setGroupData() {
	int i;
	c=0;
	for(i=0;i<l;i++)
	{
		if(flag.get(i)==i)
		{
	    groupItem.add(array[i]);
	    if(cat[i].equalsIgnoreCase("veg"))
	    {
	    	Drawable d = getResources().getDrawable(R.drawable.veg);
	    	getExpandableListView().setGroupIndicator(d);
	    }
	    else
	    {
	    	Drawable d = getResources().getDrawable(R.drawable.nonveg);
	    	getExpandableListView().setGroupIndicator(d);
	    }
	    	
	    c++;
	   
		}
	}
	
	

}

ArrayList<String> groupItem = new ArrayList<String>();
ArrayList<Object> childItem = new ArrayList<Object>();



public void setChildGroupData() {

	 int i,j;
	 
		        for(i=0;i<l;i++)
		        {
		        	 child = new ArrayList<String>();
		        	 c1=0;
		        	for(j=i;j<l;j++)
			        {
		        		
		        	     if(flag.get(j)==i)
		        	{
		        	    	 Log.d(""+j,".");
		        	    	
		        	     String s=array[j]+"\n"+desc[j]+"\n"+size[j]+"\n"+"Rs"+cost[j]+"\n"+res[j]+"\n"+id[j];
		        	     
				         child.add(s);
				         
				         c1++;
		        	}
			        }
		        	
		        	if(c1!=0)
		        	{
		             childItem.add(child);
		        	}
		        	else
		        	{
		        		Log.d("FD","fds");
		        	}
		        	
		        }
		        
			/**
	 * Add Data For Manufacture
	 */
		        
}

@SuppressLint("NewApi")
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    /** Create an option menu from res/menu/items.xml */
    getMenuInflater().inflate(R.menu.items, menu);

    /** Get the action view of the menu item whose id is search */
    View v = (View) menu.findItem(R.id.search).getActionView();
   
    /** Get the edit text from the action view */
    final EditText txtSearch = ( EditText ) v.findViewById(R.id.txt_search);
   //Button b = (Button) v.findViewById(R.id.order);
  
    /** Setting an action listener */
    txtSearch.setOnEditorActionListener(new OnEditorActionListener() {

		public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
			// TODO Auto-generated method stub
			Toast.makeText(getBaseContext(), "Search : " + txtSearch.getText(), Toast.LENGTH_SHORT).show();
            return false;
		}

    });

    return super.onCreateOptionsMenu(menu);
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
  switch (item.getItemId()) {
  case R.id.order:
    Intent i=new Intent(ExpandableListViewActivity.this,orderlist.class);
    startActivity(i);
    break;
 

  default:
    break;
  }
return true;

}
}
	
    