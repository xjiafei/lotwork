package com.winterframework.firefrog.game.service.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
* @ClassName: NumberTrendsUtils 
* @Description: 号码辅助工具类
* @author Richard
* @date 2013-9-4 上午10:01:46 
*
 */
public class NumberTrendsUtils {

	/**
	 * 
	* @Title: wuxinZuXuan 
	* @Description: 判断为五星组选类型， 
	* @param recordNumber
	* @return
	 */
	public static int wuxingZuXuan(String recordNumber) {

		return isZuXuan5(recordNumber) > 0 ? isZuXuan5(recordNumber)
				: isZuXuan10(recordNumber) > 0 ? isZuXuan10(recordNumber)
						: isZuXuan20(recordNumber) > 0 ? isZuXuan20(recordNumber)
								: isZuXuan30(recordNumber) > 0 ? isZuXuan30(recordNumber)
										: isZuXuan60(recordNumber) > 0 ? isZuXuan60(recordNumber)
												: isZuXuan120(recordNumber) > 0 ? isZuXuan120(recordNumber) : 0;
	}

	/**
	 * 
	* @Title: sixinZuXuan 
	* @Description: 判断4星的组选类型
	* @param recordNumber
	* @return
	 */
	public static int sixingZuXuan(String recordNumber) {

		return isZuXuan4(recordNumber) > 0 ? isZuXuan4(recordNumber)
				: isZuXuan6(recordNumber) > 0 ? isZuXuan6(recordNumber)
						: isZuXuan12(recordNumber) > 0 ? isZuXuan12(recordNumber)
								: isZuXuan24(recordNumber) > 0 ? isZuXuan24(recordNumber) : 0;
	}

	/**
	 * 
	* @Title: isZuXuan120 
	* @Description: 是否为组选120 
	* @param recordNumber
	* @return
	 */
	public static int isZuXuan120(String recordNumber) {
		//示例 23456
		Set<String> set = str2Set(recordNumber);

		if (null != set && set.size() == 5) {
			return BetMethodCode.ZUXUAN120.getBetMethodeCode();
		}

		return 0;
	}

	/**
	 * 
	* @Title: str2Set 
	* @Description:String 转换为Set
	* @param recordNumber
	* @return
	 */
	public static Set<String> str2Set(String recordNumber) {

		Set<String> set = new HashSet<String>();

		for (byte b : recordNumber.getBytes()) {
			set.add(String.valueOf(b));
		}
		return set;
	}

	/**
	 * 
	* @Title: isZuXuan60 
	* @Description: 是否为组选60
	* @param recordNumber
	* @return
	 */
	public static int isZuXuan60(String recordNumber) {
		//至少选择1个二重号码和3个单号号码组成一注，02588

		Set<String> set = str2Set(recordNumber);

		if (null != set && set.size() == 4) {
			return BetMethodCode.ZUXUAN60.getBetMethodeCode();
		}

		return 0;
	}

	/**
	 * 
	* @Title: isZuXuan30 
	* @Description: 是否为组选30
	* @param recordNumber
	* @return
	 */
	public static int isZuXuan30(String recordNumber) {
		//至少选择2个二重号码和1个单号号码组成一注， 05588

		Set<String> set = str2Set(recordNumber);

		if (null != set && set.size() == 3 && duad(recordNumber)) {
			return BetMethodCode.ZUXUAN30.getBetMethodeCode();
		}

		return 0;
	}

	/**
	 * 
	* @Title: duad 
	* @Description: 判断是否有成对的,33322, 22456 true, 33321 false
	* @param recordNumber
	* @return
	 */
	public static boolean duad(String recordNumber) {

		int[] number = str2intArray(recordNumber);

		for (int element : number) {
			if (element == 2) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	* @Title: str2intArray 
	* @Description: 转换为int数组
	* @param recordNumber
	* @return
	 */
	public static int[] str2intArray(String recordNumber) {

		int[] number = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		int j = 1;
		for (int i = 0; i < recordNumber.length(); i++) {
			for (int k = 0; k < 10; k++) {
				if (Integer.parseInt(recordNumber.substring(i, j)) == k) {
					number[k] += 1;
				}
			}

			j++;
		}

		return number;
	}

	/**
	 * 
	* @Title: kdStr2IntegerArray 
	* @Description: 转换为跨度数字 [000010000]
	* @param recordNumber
	* @return
	 */
	public static Integer[] str2IntegerArray(String recordNumber, int count) {

		Integer[] number = new Integer[count];

		for (int i = 0; i < number.length; i++) {
			number[i] = 0;
		}

		int kd = getKd(recordNumber);
		for (int i = 0; i < number.length; i++) {

			if (kd == i) {
				number[i] += 1;
			}
		}

		return number;
	}

	/**
	 * 
	* @Title: getKd 
	* @Description: 获取跨度值
	* @param recordNumber
	* @return
	 */
	public static int getKd(String recordNumber) {

		int max = 0;
		int min = 0;
		int j = 1;
		for (int i = 0; i < recordNumber.length(); i++) {
			max = Math.max(max, Integer.parseInt(recordNumber.substring(i, j)));
			j++;
		}

		min = max;
		j = 1;
		for (int i = 0; i < recordNumber.length(); i++) {
			min = Math.min(min, Integer.parseInt(recordNumber.substring(i, j)));
			j++;
		}
		return max - min;
	}

	public static Integer[] str2IntegerArray(String recordNumber) {

		Integer[] number = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		int j = 1;
		for (int i = 0; i < recordNumber.length(); i++) {
			for (int k = 0; k < 10; k++) {
				if (Integer.parseInt(recordNumber.substring(i, j)) == k) {
					number[k] += 1;
				}
			}

			j++;
		}

		return number;
	}

	public static Integer[] int2Array(int num) {

		Integer[] number = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		for (int i = 0; i < 10; i++) {
			if (num == i) {
				number[i] += 1;
			}
		}

		return number;
	}

	/**
	 * 
	* @Title: triplet 
	* @Description: 存在3个相同的，如34332 true 
	* @param recordNumber
	* @return
	 */
	public static boolean triplet(String recordNumber) {

		int[] number = str2intArray(recordNumber);

		for (int element : number) {
			if (element == 3) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	* @Title: tetrad 
	* @Description: 判断4个， 43333 true， 
	* @param recordNumber
	* @return
	 */
	public static boolean tetrad(String recordNumber) {

		int[] number = str2intArray(recordNumber);

		for (int element : number) {

			if (element == 4) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	* @Title: isZuXuan20 
	* @Description: 是否为组选20
	* @param recordNumber
	* @return
	 */
	public static int isZuXuan20(String recordNumber) {
		//至少选择1个三重号码和2个单号号码组成一注; 02888

		Set<String> set = str2Set(recordNumber);
		if (null != set && set.size() == 3 && triplet(recordNumber)) {
			return BetMethodCode.ZUXUAN20.getBetMethodeCode();
		}
		return 0;
	}

	/**
	 * 
	* @Title: isZuXuan10 
	* @Description: 是否为组选10
	* @param recordNumber
	* @return
	 */
	public static int isZuXuan10(String recordNumber) {
		//至少选择1个三重号码和1个二重号码组成一注，88822

		if (duad(recordNumber) && triplet(recordNumber)) {
			return BetMethodCode.ZUXUAN10.getBetMethodeCode();
		}
		return 0;
	}

	/**
	 * 
	* @Title: isZuXuan5 
	* @Description: 是否为组选5
	* @param recordNumber
	* @return
	 */
	public static int isZuXuan5(String recordNumber) {
		//至少选择1个四重号码和1个单号号码组成一注，41111

		Set<String> set = str2Set(recordNumber);

		if (null != set && set.size() == 2 && tetrad(recordNumber)) {
			return BetMethodCode.ZUXUAN5.getBetMethodeCode();
		}
		return 0;
	}

	/**
	 * 
	* @Title: isZuXuan24 
	* @Description: 是否为组选24, 传入的数据必须为4位。
	* @param recordNumber
	* @return
	 */
	public static int isZuXuan24(String recordNumber) {
		//至少选择4个号码投注，竞猜开奖号码的后4位， *2345

		Set<String> set = str2Set(recordNumber);

		if (null != set && set.size() == 4) {
			return BetMethodCode.ZUXUAN24.getBetMethodeCode();
		}

		return 0;
	}

	/**
	 * 
	* @Title: isZuXuan12 
	* @Description: 是否为组选12 传入的数据必须为4位。
	* @param recordNumber
	* @return
	 */
	public static int isZuXuan12(String recordNumber) {
		//至少选择1个二重号码和2个单号号码，竞猜开奖号码的后四位， *2588

		Set<String> set = str2Set(recordNumber);

		if (null != set && set.size() == 3) {
			return BetMethodCode.ZUXUAN12.getBetMethodeCode();
		}

		return 0;
	}

	/**
	 * 
	* @Title: isZuXuan6 
	* @Description: 是否为组选6, 传入的数据必须为4位。
	* @param recordNumber
	* @return
	 */
	public static int isZuXuan6(String recordNumber) {
		//至少选择2个二重号码，竞猜开奖号码的后四位， *0088

		Set<String> set = str2Set(recordNumber);

		if (null != set && set.size() == 2 && duad(recordNumber)) {
			return BetMethodCode.ZUXUAN6.getBetMethodeCode();
		}
		return 0;
	}

	/**
	 * 
	* @Title: isZuXuan4 
	* @Description: 是否为组选4 传入的数据必须为4位。
	* @param recordNumber
	* @return
	 */
	public static int isZuXuan4(String recordNumber) {
		//所选数值等于开奖号码的后3位最大与最小数字相减之差， *0222
		Set<String> set = str2Set(recordNumber);

		if (null != set && set.size() == 2 && triplet(recordNumber)) {
			return BetMethodCode.ZUXUAN4.getBetMethodeCode();
		}
		return 0;
	}

	public static void main(String[] args) {

	}

	/**
	 * 
	* @Title: iszu3 
	* @Description: 组3判断
	* @param recordNumber
	* @return
	 */
	public static int iszu3(String recordNumber) {
		// **121, 323**
		if (duad(recordNumber)) {
			return BetMethodCode.ZU3.getBetMethodeCode();
		}
		return 0;
	}

	/**
	 * 
	* @Title: iszu6 
	* @Description: 组6判断
	* @param recordeNumber
	* @return
	 */
	public static int iszu6(String recordeNumber) {

		//123**, **123
		Set<String> set = str2Set(recordeNumber);
		if (null != set && set.size() == 3) {
			return BetMethodCode.ZU6.getBetMethodeCode();
		}
		return 0;
	}

	/**
	 * 
	* @Title: isBaozi 
	* @Description: 豹子判断 
	* @param recordNumber
	* @return
	 */
	public static int isBaozi(String recordNumber) {

		//**999, 999**
		if (triplet(recordNumber)) {
			return BetMethodCode.BAOZI.getBetMethodeCode();
		}

		return 0;
	}

	/**
	 * 
	* @Title: zushan 
	* @Description: 组三、组六、豹子判断
	* @param hsReordNumber 必须是3位
	* @return
	 */
	public static int zushan(String recordNumber) {
		return isBaozi(recordNumber) > 0 ? isBaozi(recordNumber) : iszu3(recordNumber) > 0 ? iszu3(recordNumber)
				: iszu6(recordNumber) > 0 ? iszu6(recordNumber) : 0;
	}

	/**
	 * 
	* @Title: getMaxCount 
	* @Description: 获取大的个数
	* @param numberRecord
	* @return
	 */
	public static int getMaxCount(String numberRecord) {

		int count = 0;
		int j = 0;
		for (int i = 0; i < numberRecord.length(); i++) {

			if (Integer.parseInt(numberRecord.substring(i, j)) >= 5) {
				count++;
			}
			j++;
		}

		return count;
	}

	/**
	 * 
	* @Title: getMinCount 
	* @Description: 获取小的个数
	* @param numberRecord
	* @return
	 */
	public static int getMinCount(String numberRecord) {

		int count = 0;
		int j = 0;
		for (int i = 0; i < numberRecord.length(); i++) {

			if (Integer.parseInt(numberRecord.substring(i, j)) < 5) {
				count++;
			}
			j++;
		}

		return count;

	}

	/**
	 * 
	* @Title: getZXHZ 
	* @Description: 直选和值
	* @param str
	* @return
	 */
	public static int getZXHZ(String str) {

		int j = 1;
		int value = 0;
		for (int i = 0; i < str.length(); i++) {

			value += Integer.parseInt(str.substring(i, j));
			j++;

		}
		return value;
	}

	/**
	 * 
	* @Title: convert2IntArray 
	* @Description: 
	* @param chart 【1，1，4，5，。。。】
	* @param index 开始位置
	* @param count 获取数量
	* @return
	 */
	public static Integer[] convert2IntArray(String chart, int index, int count) {

		Integer[] num = new Integer[count];

		String[] array = chart.split(",");

		int j = 0;
		for (int i = index; i < (index + count); i++) {

			num[j] = Integer.parseInt(array[i]);
			j++;
		}

		return num;

	}

	/** 
	* @Title: explode 
	* @Description: 将中奖号码String转换为int型的List 
	*/
	public static List<Integer> explode(String s) {
		List<Integer> list = new ArrayList<Integer>();
		if (s.contains(",")) {
			String[] recordArray = s.split(",");
			for (String record : recordArray) {
				list.add(Integer.valueOf(record));
			}

		} else {
			for (int i = 0; i < s.length(); i++) {
				list.add(Integer.parseInt(String.valueOf(s.charAt(i))));
			}
		}
		return list;
	}

	/** 
	* @Title: getKd 
	* @Description: 取跨度值 
	*/
	public static int getKd(Set<Integer> numberRecordSet) {
		List<Integer> list = new ArrayList<Integer>(numberRecordSet);
		int max = 0;
		int min = 0;
		for (int i = 0; i < list.size(); i++) {
			max = Math.max(max, list.get(i));
		}

		min = max;
		for (int i = 0; i < list.size(); i++) {
			min = Math.min(min, list.get(i));
		}
		return max - min;
	}

	/** 
	* @Title: convertString2List 
	* @Description: 将String转换为List 
	* @param  string
	* @param    设定文件 
	* @throws 
	*/
	public static List<Integer> convertString2List(String numberString) {
		List<Integer> list = new ArrayList<Integer>();
		String[] arr = numberString.split(",");
		for (String s : arr) {
			list.add(Integer.parseInt(s));
		}
		return list;
	}

}
