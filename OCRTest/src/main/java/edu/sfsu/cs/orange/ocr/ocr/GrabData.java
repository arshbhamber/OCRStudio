package edu.sfsu.cs.orange.ocr.ocr;

import java.util.ArrayList;
import java.util.Iterator;

public class GrabData {

	ArrayList<String> key;
	ArrayList<String> value;
	Mapping mapping;
	ArrayList<String> data;
	
	public GrabData(ArrayList<String> data) {
		// TODO Auto-generated constructor stub
		
		mapping = new Mapping();
		this.data = data;
		extract(data);
		
	}
	
public void extract(ArrayList<String> data){
		
		for(int i=0;i<data.size();i++){
		String s = data.get(i);
		s = s.concat("\n");
		Iterator<MapObject> iterator = mapping.maplist.iterator();
		
		while(iterator.hasNext()){
			MapObject map = iterator.next();
			Iterator<String> it = map.mappedValues.iterator();
			while(it.hasNext()){
				String p = it.next();
				
				if(s.contains(p)&&map.flag==false){
					key.add(map.key);
					value.add(s.substring(s.indexOf(p)+p.length()+1,s.indexOf("\n",s.indexOf(p))));
					
					map.flag=true;
					
				}
//				adapter.notifyDataSetChanged();
				
			}
			
		}
			
		}
		
		
		
		
	}

public ArrayList<String> getKey(){
	
	return key;
	
}
public ArrayList<String> getValue(){
	
	return value;
	
}
}
