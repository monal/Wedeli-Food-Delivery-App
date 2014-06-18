package com.smapp.wedelis;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

public class orderpage extends Activity{
	SQLiteDatabase db;
	MySQLiteHelper dbHelper;
	ContentValues values;
	static String a,d,s,c,r,id;
	private static final String TAG = "DialogActivity";
    private static final int DLG_EXAMPLE1 = 0;
    private static final int TEXT_ID = 0;
    EditText input;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.op);
	        savedInstanceState=getIntent().getExtras();
	        String x=savedInstanceState.getString("p");
	        String y=savedInstanceState.getString("c");
	        String name=savedInstanceState.getString("data");
	        String[] parts=name.split("\n");
	        String a=parts[0];
	        String d=parts[1];
	        String s=parts[2];
	        String c=parts[3];
	        String r=parts[4];
	        String id=parts[5];
	        dbHelper = new MySQLiteHelper(this);
			db =dbHelper.getWritableDatabase();
			values = new ContentValues();
			 input = new EditText(this);
			input.setInputType(InputType.TYPE_CLASS_NUMBER);
			 values.put(dbHelper.C_NAME, a);
 			values.put(dbHelper.C_PRICE, c);
 			values.put(dbHelper.C_QTY, "");
 			values.put(dbHelper.C_MID, id);
			showDialog(DLG_EXAMPLE1);
	 }
	 
	    /**
	     * Called to create a dialog to be shown.
	     */
	    @Override
	    protected Dialog onCreateDialog(int id) {
	 
	        switch (id) {
	            case DLG_EXAMPLE1:
	                return createExampleDialog();
	            default:
	                return null;
	        }
	    }
	 
	    /**
	     * If a dialog has already been created,
	     * this is called to reset the dialog
	     * before showing it a 2nd time. Optional.
	     */
	    @Override
	    protected void onPrepareDialog(int id, Dialog dialog) {
	 
	        switch (id) {
	            case DLG_EXAMPLE1:
	                // Clear the input box.
	                EditText text = (EditText) dialog.findViewById(TEXT_ID);
	                text.setText("");
	                break;
	        }
	    }
	 
	    /**
	     * Create and return an example alert dialog with an edit text box.
	     */
	    private Dialog createExampleDialog() {
	 
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Enter the quantity:");
	        
	 
	         // Use an EditText view to get user input.
	        
	         
	         input.setId(TEXT_ID);
	         builder.setView(input);
	 
	        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					 String value = input.getText().toString();
					 values.put(dbHelper.C_QTY, value);
		                 
		    			db.insert(dbHelper.TABLE, null, values);
		    	       
		    			Toast.makeText(getApplicationContext(),"added to basket!",Toast.LENGTH_SHORT ).show();
		    	        finish();
		                return;
				}
	 
	            
	        });
	 
	        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					 return;
				}
	 
	           
	        });
	 
	        return builder.create();
	    }



			
	        
	 
	 @Override
		protected void onDestroy() {
			super.onDestroy();
			// close databases
			db.close();
			dbHelper.close();

		}

}

