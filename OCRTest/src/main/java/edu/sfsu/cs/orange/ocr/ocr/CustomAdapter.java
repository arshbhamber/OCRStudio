package edu.sfsu.cs.orange.ocr.ocr;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import edu.sfsu.cs.orange.ocr.R;

public class CustomAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<String> key;
	private ArrayList<String> value;
	
	public CustomAdapter(Context context,ArrayList<String> key,ArrayList<String> value) {
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.key = key;
		this.value = value;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return value.size();
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
		View view = new View(context);
		LayoutInflater inflater = LayoutInflater.from(context); 
		view = inflater.inflate(R.layout.single_grid, null);
		TextView t1 = (TextView)view.findViewById(R.id.textView1);
		EditText e1 = (EditText)view.findViewById(R.id.editText1);
		t1.setText(key.get(position));

		e1.setText(value.get(position));
		
		
		return view;
	}

	
	
}
