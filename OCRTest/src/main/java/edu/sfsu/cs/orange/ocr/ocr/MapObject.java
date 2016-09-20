package edu.sfsu.cs.orange.ocr.ocr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapObject {

	String key; 
	ArrayList<String> mappedValues;
	int min_size;
	Boolean flag = false;
	Boolean size_limit = false;
	int index=-2;
//	 ArrayList<HashMap<String,ArrayList<String>>> values;
	
	public MapObject() {
		// TODO Auto-generated constructor stub
//		hm = new HashMap<String,ArrayList<String>>();
		mappedValues = new ArrayList<String>();
//		values = new ArrayList<HashMap<String,ArrayList<String>>>();
		
	}
	
	public void add(){
		
		
		
		
		
	}
	public void clear(){
		key = "";
		mappedValues.clear();
		
		
	}
	 public static Object getKeyFromValue(HashMap hm, Object value) {
		    for (Object o : hm.keySet()) {
		      if (hm.get(o).equals(value)) {
		        return o;
		      }
		    }
		    return null;
		  }
	
}
