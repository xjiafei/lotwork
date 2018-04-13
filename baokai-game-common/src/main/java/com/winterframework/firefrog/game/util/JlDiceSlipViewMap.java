package com.winterframework.firefrog.game.util;

import java.util.HashMap;
import java.util.Map;

public class JlDiceSlipViewMap {

	public static Map<String,Integer> map = new HashMap<String,Integer>();
	static{
		map.put("43_37_79-大", 0);//大小-大
		map.put("43_37_80-单", 1);//单双-单
		
		map.put("39_33_76-66*", 2);//ertonghaofuxuan
		map.put("39_33_76-55*", 3);//ertonghaofuxuan
		map.put("39_33_76-44*", 4);//ertonghaofuxuan
		
		map.put("36_30_73-666", 5);
		map.put("36_30_73-555", 6);
		map.put("36_30_73-444", 7);
		map.put("36_30_73-333", 8);
		map.put("36_30_73-222", 9);
		map.put("36_30_73-111", 10);
		
		map.put("35_29_72-111 222 333 444 555 666", 11);
		
		map.put("39_33_76-33*", 12);
		map.put("39_33_76-22*", 13);
		map.put("39_33_76-11*", 14);
		
		map.put("43_37_79-小", 15);
		map.put("43_37_80-双", 16);
		
		map.put("34_28_71-17", 17);
		map.put("34_28_71-16", 18);
		map.put("34_28_71-15", 19);
		map.put("34_28_71-14", 20);
		map.put("34_28_71-13", 21);
		map.put("34_28_71-12", 22);
		map.put("34_28_71-11", 23);
		map.put("34_28_71-10", 24);
		map.put("34_28_71-9", 25);
		map.put("34_28_71-8", 26);
		map.put("34_28_71-7", 27);
		map.put("34_28_71-6", 28);
		map.put("34_28_71-5", 29);
		map.put("34_28_71-4", 30);
		
		map.put("41_35_74-5,6", 31);
		map.put("41_35_74-4,6", 32);
		map.put("41_35_74-4,5", 33);
		map.put("41_35_74-3,6", 34);
		map.put("41_35_74-3,5", 35);
		map.put("41_35_74-3,4", 36);
		map.put("41_35_74-2,6", 37);
		map.put("41_35_74-2,5", 38);
		map.put("41_35_74-2,4", 39);
		map.put("41_35_74-2,3", 40);
		map.put("41_35_74-1,6", 41);
		map.put("41_35_74-1,5", 42);
		map.put("41_35_74-1,4", 43);
		map.put("41_35_74-1,3", 44);
		map.put("41_35_74-1,2", 45);
		
		map.put("42_36_78-6", 46);
		map.put("42_36_78-5", 47);
		map.put("42_36_78-4", 48);
		map.put("42_36_78-3", 49);
		map.put("42_36_78-2", 50);
		map.put("42_36_78-1", 51);
	}
	
	public static Integer getSlipViewId(String key){
		return map.get(key);
	}
}
