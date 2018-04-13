package com.winterframework.modules.utils;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 数据生成工具类.
 * 
 * @author abba
 */
public class DataUtils {
	private static Random random = new Random();

	/**
	 * 返回随机ID.
	 */
	public static long randomId() {
		return random.nextLong();
	}
	/**
	 * 返回两个数字之间的随机数
	 * @param little
	 * @param big
	 * @return
	 */
	public static int randomBetween(int little,int big){
		if(big==little) return big;
		if(big<=little) {
			int c=little;
			little=big;
			big=c;
		}
		return little+(int)(Math.random()*(big-little));		
	}
	/**
	 * 返回随机ID.
	 */
	public static boolean randomBoolean() {
		return random.nextBoolean();
	}

	/**
	 * 返回随机名称, prefix字符串+随机数字.
	 */
	public static String randomName(String prefix) {
		return prefix + random.nextInt(10000);
	}

	/**
	 * 返回随机名称, prefix字符串+随机数字.
	 */
	public static String randomName(String prefix, int length) {
		return prefix + random.nextInt(10000);
	}

	/**
	 * 从输入list中随机返回一个对象.
	 */
	public static <T> T randomOne(List<T> list) {
		return randomSome(list, 1).get(0);
	}

	/**
	 * 从输入list中随机返回随机个对象.
	 */
	public static <T> List<T> randomSome(List<T> list) {
		return randomSome(list, random.nextInt(list.size()));
	}

	/**
	 * 从输入list中随机返回count个对象.
	 */
	public static <T> List<T> randomSome(List<T> list, int count) {
		Collections.shuffle(list);
		return list.subList(0, count);
	}
	/**
	 * 计算两个经纬 度之间的距离 
	 * @param lat1 经度1
	 * @param lat2 纬度1
	 * @param lon1 经度2
	 * @param lon2 纬度2
	 * @return 返回单位是米
	 */
	public static double getDistance(double lat1, double lat2, double lon1,    double lon2) {
        double R = 6371;
        double distance = 0.0;
        double dLat = (lat2 - lat1) * Math.PI / 180;
        double dLon = (lon2 - lon1) * Math.PI / 180;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1 * Math.PI / 180)
                * Math.cos(lat2 * Math.PI / 180) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        distance = (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))) * R;
        return distance;
    }
	public static void main(String[] args) {
		System.out.println(randomBetween(10,6));
	}
}
