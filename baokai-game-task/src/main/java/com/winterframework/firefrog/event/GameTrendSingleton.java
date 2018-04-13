package com.winterframework.firefrog.event;

import java.util.HashMap;
import java.util.Map;


 
public class GameTrendSingleton {
	
	private static Map<String,GameTrendSingleton> map=new HashMap<String,GameTrendSingleton>();
	private GameTrendSingleton() {
	}
	public static GameTrendSingleton getInstance(String key){
		if(null==map.get(key)){
			synchronized(GameTrendSingleton.class){
				if(null==map.get(key)){
					map.put(key, new GameTrendSingleton());
				}
			}
		}
		return map.get(key);
	}
	public static void removeInstance(String key){
		map.remove(key);
	}
}
