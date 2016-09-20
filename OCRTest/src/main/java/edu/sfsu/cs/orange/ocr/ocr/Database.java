package edu.sfsu.cs.orange.ocr.ocr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class Database extends SQLiteOpenHelper{

	   public static final String DATABASE_NAME = "rcbook.db";
	   public static final String RC_TABLE_NAME = "rcdetails";
	   public static final String RC_DETAILS_COLUMN_ID = "id";
	   public static final String RC_DETAILS_COLUMN_JSON = "json";
	   public static final String RC_DETAILS_COLUMN_IMAGE_URI = "image_uri";
	 
	   public Database(Context context)
	   {
//	      super(context, DATABASE_NAME , null, 1);
	      super(context, Environment.getExternalStorageDirectory()
	              + File.separator + "OCR"
	              + File.separator + DATABASE_NAME, null, 1);
	   }


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL(
			      "create table rcdetails " +
			      "(id integer primary key, json text,image_uri text)"
			      );
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS rcdetails");
	      onCreate(db);
	}
	
	public boolean insertDetails  (String json, String image_uri)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();

	      contentValues.put("json", json);
	      contentValues.put("image_uri", image_uri);
	     

	      db.insert("rcdetails", null, contentValues);
	      return true;
	   }
	public Cursor getData(int id){
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
	      return res;
	   }
	   public int numberOfRows(){
	      SQLiteDatabase db = this.getReadableDatabase();
	      int numRows = (int) DatabaseUtils.queryNumEntries(db, RC_TABLE_NAME);
	      return numRows;
	   }
	   
	   public ArrayList<String> getURI()
	   {
		   ArrayList<String> array_list = new ArrayList<String>();
	      //hp = new HashMap();
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from rcdetails", null );
	      res.moveToFirst();
	      while(res.isAfterLast() == false){
	      array_list.add(res.getString(res.getColumnIndex(RC_DETAILS_COLUMN_IMAGE_URI)));
//	      hash_map.put(res.getString(res.getColumnIndex(RC_DETAILS_COLUMN_IMAGE_URI)), res.getString(res.getColumnIndex(RC_DETAILS_COLUMN_JSON)));
	      
	      res.moveToNext();
	      }
	   return array_list;
	   }

	   public ArrayList<String> getJSON()
	   {
		   ArrayList<String> array_list = new ArrayList<String>();
	      //hp = new HashMap();
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from rcdetails", null );
	      res.moveToFirst();
	      while(res.isAfterLast() == false){
	      array_list.add(res.getString(res.getColumnIndex(RC_DETAILS_COLUMN_JSON)));
//	      hash_map.put(res.getString(res.getColumnIndex(RC_DETAILS_COLUMN_IMAGE_URI)), res.getString(res.getColumnIndex(RC_DETAILS_COLUMN_JSON)));
	      
	      res.moveToNext();
	      }
	   return array_list;
	   }
	

}
