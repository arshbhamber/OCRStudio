package edu.sfsu.cs.orange.ocr.ocr;

import java.io.File;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import edu.sfsu.cs.orange.ocr.R;

public class DisplayData extends Activity{
	
	Database db;
	ListView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		db = new Database(this);
		
		
		listview = (ListView)findViewById(R.id.listView1);
		
		listview.setAdapter(new ListAdapter(this));
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				Dialog dialog = new Dialog(DisplayData.this);
				dialog.setContentView(R.layout.dialog);
				ImageView iv = (ImageView)dialog.findViewById(R.id.imageView1);
				TextView tv = (TextView)dialog.findViewById(R.id.textView1);
				
				tv.setMovementMethod(new ScrollingMovementMethod());
				iv.setImageURI(Uri.fromFile(new File(db.getURI().get(position))));
				tv.setText(db.getJSON().get(position));
				dialog.show();
				
				
			}
		});
		
		
	}
	
	private class ListAdapter extends BaseAdapter{

		
		
		public ListAdapter(Context context) {
			// TODO Auto-generated constructor stub
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return db.numberOfRows();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			TextView tv = new TextView(DisplayData.this);
//			android.view.ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
//			layoutParams.width = 80;
//			layoutParams.height = 80;
//			iv.setLayoutParams(layoutParams);
		
			tv.setText(db.getURI().get(position));
			
			return tv;
		}
		
		
		
		
		
	}

	
	
	



}
