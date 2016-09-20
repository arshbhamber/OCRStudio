package edu.sfsu.cs.orange.ocr.ocr;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.RectF;

public class Mapping {

	
	ArrayList<MapObject> maplist;
	HashMap<String,ArrayList<RectF>> areamap;
	ArrayList<RectF> areas;
	
	public Mapping() {
		// TODO Auto-generated constructor stub
		maplist = new ArrayList<MapObject>();
		areamap = new HashMap<String,ArrayList<RectF>>();
		add();
		addArea();
	}
	
	
	public void add(){
		
		MapObject map = new MapObject();
		map.key = "REG.DT";
		map.min_size = 8;
		map.size_limit = true;
		map.mappedValues.add ("REG.DT");
		map.mappedValues.add ("REG.OT");
		map.mappedValues.add ("REG. OT");
		map.mappedValues.add ("REG.0T");
		map.mappedValues.add ("REG . OT");
//		map.mappedValues.add ("REG.DT ");
		map.mappedValues.add ("REG. DT");
//		map.mappedValues.add ("REG. DT ");
		map.mappedValues.add ("REG . DT");
//		map.mappedValues.add ("REG . DT ");
		map.mappedValues.add ("REG.DATE");
		map.mappedValues.add ("REG .DATE");
		map.mappedValues.add ("REG . DATE");
		map.mappedValues.add ("REGDATE");
		map.mappedValues.add ("REGISTRATION DATE");
		maplist.add(map);
		map = new MapObject();
		map.key = "E.NO";
		map.min_size = 3;
		map.size_limit = true;
		map.mappedValues.add ("E NO");
		map.mappedValues.add ("ENO");
		map.mappedValues.add ("E NO");
		map.mappedValues.add ("E N0");
		map.mappedValues.add ("ENO");
		map.mappedValues.add ("ENGINE.NO");
		map.mappedValues.add ("ENGINE NO.");
		map.mappedValues.add ("ENGINENO.");
//		map.mappedValues.add ("ENGINE.NO ");
		maplist.add(map);
		map = new MapObject();
		map.key = "CHASSIS NO.";
		map.min_size = 17;
		map.size_limit = true;
		map.mappedValues.add ("CH.NO");
		map.mappedValues.add ("CH. NO");
		map.mappedValues.add ("CH. N0");
		map.mappedValues.add ("OH. NO");
		map.mappedValues.add ("CHNO");
//		map.mappedValues.add ("CHASSIS.NO ");
		map.mappedValues.add ("CHASSIS.NO");
		map.mappedValues.add ("CHASIS NO.");
		map.mappedValues.add ("CHASIS NO");
		map.mappedValues.add ("CHASIS N0");
		map.mappedValues.add ("CHASISNO.");
		map.mappedValues.add ("CHASSISNO");
//		map.mappedValues.add ("CHASSISNO ");
		maplist.add(map);
		map = new MapObject();
		map.key = "O.SNO";
		map.min_size = 2;
		map.size_limit = true;
//		map.mappedValues.add ("O SNO ");
//		map.mappedValues.add ("OSNO ");
		map.mappedValues.add ("O SNO");
		map.mappedValues.add ("OSNO");
		map.mappedValues.add ("OWNER SERIAL NO.");
		map.mappedValues.add ("O.SL.NO");
		map.mappedValues.add ("OSL.NO");
		map.mappedValues.add ("O.SLNO");
		map.mappedValues.add ("OSLNO");
//		map.mappedValues.add ("O.SL.NO ");
		maplist.add(map);
		map = new MapObject();
		map.key = "COLOUR";
		map.min_size = 4;
		map.size_limit = true;
		map.mappedValues.add ("COLOUR");
//		map.mappedValues.add ("COLOUR ");
		maplist.add(map);
		map = new MapObject();
		map.key = "MFG CD";
		map.min_size = 2;
		map.size_limit = true;
		map.mappedValues.add ("MFG CD");
		map.mappedValues.add ("MFG CO");
		map.mappedValues.add ("MFGCD");
		map.mappedValues.add ("MFGCO");
		maplist.add(map);
		map = new MapObject();
		map.key = "HP/LEASE";
		map.min_size = 0;
		map.size_limit = true;
		map.mappedValues.add ("HP1LEASE");
		map.mappedValues.add ("HPILEASE");
		map.mappedValues.add ("HP/LEASE");
		map.mappedValues.add ("HP");
		maplist.add(map);
		
		map = new MapObject();
		map.key = "MODEL";
		map.min_size = 5;
		map.mappedValues.add ("MODEL");
		map.mappedValues.add ("MANUFACTURER WITH MAKE");
//		map.mappedValues.add ("MODEL ");
		maplist.add(map);
		map = new MapObject();
		map.key = "FUEL";
		map.min_size = 6;
		map.size_limit = true;
		map.mappedValues.add ("FUEL");
//		map.mappedValues.add ("FUEL ");
		maplist.add(map);
		map = new MapObject();
		map.key = "MFG DATE";
		map.min_size = 4;
		map.size_limit = true;
		map.mappedValues.add ("MFG.DT.");
		map.mappedValues.add ("MFG.DT");
		map.mappedValues.add ("DATE OF MANUFACTURE");
		map.mappedValues.add ("DATEOF MANUFACTURE");
		map.mappedValues.add ("DATE OFMANUFACTURE");
		map.mappedValues.add ("DATEOFMANUFACTURE");
		map.mappedValues.add ("MFGDT.");
		map.mappedValues.add ("MFGDT");
		map.mappedValues.add ("MFG.DATE");
		map.mappedValues.add ("MFGDATE");
		maplist.add(map);
		map = new MapObject();
		map.key = "REGN NO";
		map.min_size = 9;
		map.size_limit = true;
//		map.mappedValues.add ("REGN . NO ");
		map.mappedValues.add ("REGN . NO");
		map.mappedValues.add ("REGN . N0");
		map.mappedValues.add ("REGN.NO");
		map.mappedValues.add ("REGN. NO");
		map.mappedValues.add ("REGN .NO");
//		map.mappedValues.add ("REG NO ");
//		map.mappedValues.add ("REGNO ");
		map.mappedValues.add ("REG NO");
		map.mappedValues.add ("REGNO");
		map.mappedValues.add ("REGNNO");
		map.mappedValues.add ("REGNN0");
		map.mappedValues.add ("REGD.NO");
		map.mappedValues.add ("REGD. NO");
		map.mappedValues.add ("REGISTRATION NO");
		map.mappedValues.add ("REG . N0");
		maplist.add(map);
		map = new MapObject();
		map.key = "NAME";
		map.min_size = 4;
		map.mappedValues.add ("NAME");
//		map.mappedValues.add ("NAME ");
//		map.mappedValues.add ("OWNERNAME ");
		map.mappedValues.add ("OWNERNAME");
		maplist.add(map);
		
		map = new MapObject();
		map.key = "REG UPTO";
		map.min_size = 3;
		map.size_limit = true;
		map.mappedValues.add ("REG.UPTO");
		map.mappedValues.add ("REGUPTO");
		
		maplist.add(map);
		map = new MapObject();
		map.key = "ADDRESS";
		map.min_size = 10;
		map.size_limit = true;
		map.mappedValues.add ("ADDRESS");
		
//		map.mappedValues.add ("ENGINE.NO ");
		maplist.add(map);
		
		
		
		
		
		
	}
	
	public void addArea(){
		
		areas = new ArrayList<RectF>();
		areas.add(new RectF(0.0f,0.0f,0.55f,0.35f));
		areas.add(new RectF(0.5f,0.0f,0.5f,0.35f));
		areas.add(new RectF(0.0f,0.7f,0.41f,0.25f));
		areas.add(new RectF(0.2f,0.0f,0.70f,0.20f));
		areas.add(new RectF(0.0f,0.3f,0.80f,0.25f));
		areas.add(new RectF(0.0f,0.55f,0.6f,0.2f));
		
		areamap.put("Maharashtra", areas);
		areamap.put("Delhi", areas);
		areas = new ArrayList<RectF>();
		areas.add(new RectF(0.0f,0.0f,1.0f,1.0f));
		areamap.put("Bihar", areas);
		areas = new ArrayList<RectF>();
		areas.add(new RectF(0.0f,0.0f,1.0f,1.0f));
		areamap.put("Bihar", areas);
		
		
		
		
		
	}
	
}
