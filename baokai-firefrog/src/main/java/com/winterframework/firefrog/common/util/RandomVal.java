package com.winterframework.firefrog.common.util;
import java.util.Random;


public class RandomVal {

	public static int randInt(int max, int min){
		return (int)(min+Math.random()*(max-min+1));
	}
	
	public static long randlong(long max, long min){
		return (long)(min+Math.random()*(max-min+1));
	}

}
