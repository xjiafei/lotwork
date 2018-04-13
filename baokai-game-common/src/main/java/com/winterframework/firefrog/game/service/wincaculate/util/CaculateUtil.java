package com.winterframework.firefrog.game.service.wincaculate.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.combinatorics.Factory;
import com.winterframework.combinatorics.Generator;
import com.winterframework.combinatorics.ICombinatoricsVector;

public class CaculateUtil {
	private static final Logger log = LoggerFactory.getLogger(CaculateUtil.class);

	/**
	 * <pre>
	 * 通过规则比较两个数大小，符合及返回ture
	 * == eq  
	 * != ne 
	 * <  lt 
	 * >  gt  
	 * <= le  
	 * >= ge
	 * </pre>
	 * @param rule
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static boolean compareNum(String rule, int num1, int num2) {
		if ("eq".equalsIgnoreCase(rule)) {
			return num1 == num2 ? true : false;
		} else if ("ne".equalsIgnoreCase(rule)) {
			return num1 != num2 ? true : false;
		} else if ("lt".equalsIgnoreCase(rule)) {
			return num1 < num2 ? true : false;
		} else if ("gt".equalsIgnoreCase(rule)) {
			return num1 > num2 ? true : false;
		} else if ("le".equalsIgnoreCase(rule)) {
			return num1 <= num2 ? true : false;
		} else if ("ge".equalsIgnoreCase(rule)) {
			return num1 >= num2 ? true : false;
		}
		log.error("传入非法的规则字符" + rule + "，只能为（eq ne lt gt le ge）,忽略大小写");
		return false;
	}

	/**
	 * 统计开奖号码有几个不重复的数字
	 * @param resultCode
	 * @return
	 */
	public static int count(String resultCode) {
		char[] numbers = resultCode.toCharArray();
		Set<String> set = new HashSet<String>();
		for (char number : numbers) {
			set.add(number + "");
		}
		return set.size();
	}
	
	public static boolean isZhu3(String resultCode,String groupCode){
		int length = resultCode.length();
		String q3 = resultCode.substring(0, 2);
		String h3 = resultCode.substring(length-3, length);
		
		if(getDoubleNumbers(q3).isEmpty() && getDoubleNumbers(h3).isEmpty()){
			return false;
		}
		if(!getDoubleNumbers(h3).isEmpty() && groupCode.equals("13")){ //后3
			return true;
		}
		if(!getDoubleNumbers(q3).isEmpty() && groupCode.equals("12")){ //前3
			return true; 
		}
		
		return false;
	}

	/**
	 * 获取开奖号码中重号的最大重号次数 
	 * @param resultCode
	 * @return
	 */
	public static Integer getMaxRepeatNumber(String resultCode) {
		Map<Character, Integer> timeMap = new HashMap<Character, Integer>();
		Character cTmp;
		Integer iTmp;
		int iMax = 1;
		for (int i = 0; i < resultCode.length(); i++) {
			cTmp = resultCode.charAt(i);
			iTmp = timeMap.get(cTmp);
			//首次出现的字符，不需要比较
			if (null == iTmp) {
				timeMap.put(cTmp, 1);
				continue;
			}
			//出现次数+1，放入map
			iTmp = iTmp + 1;
			timeMap.put(cTmp, iTmp);
			//若超过最大次数，则替换最大次数
			iMax = iMax < iTmp ? iTmp : iMax;
		}
		return iMax;
	}
	
	/**
	 * 截取resultCode有效字符串
	 * @param resultCode
	 * @param resultCodeBeginIndex 开奖结果开始位  如果空约定为从0开始	
	 * @param resultCodeEndIndex 开奖结果结束位  如果空约定为最后一位
	 * @return
	 */
	public static String dealResultCode(final String resultCode, Integer resultCodeBeginIndex,
			Integer resultCodeEndIndex) {
		if(resultCodeBeginIndex == null && resultCodeEndIndex == null){			
			return resultCode;
		}
		
		if (resultCodeBeginIndex == null || resultCodeBeginIndex < 0) {
			resultCodeBeginIndex = 0;
		}
		if ( resultCodeEndIndex > resultCode.length()) {
			
			resultCodeBeginIndex = resultCode.length() - (resultCodeEndIndex - resultCodeBeginIndex);
			resultCodeEndIndex = resultCode.length();
		}

		return resultCode.substring(resultCodeBeginIndex, resultCodeEndIndex);
	}

	/**
	 * 将字符串排序
	 * @param sourceStr
	 * @return
	 */
	public static String sortStr(String sourceStr) {
		char[] chars = sourceStr.toCharArray();
		Arrays.sort(chars);
		String aa = "";
		for (char c : chars) {
			aa += c;
		}
		return aa;
	}
	
	/**
	 * 取得不重複組合。
	 * @param betNumbers 號球組
	 * @param length 幾個號球一組
	 * @return 每個號球用","分隔
	 */
	public static List<String> getCombinations(List<String> betNumbers, int length){
		String[] str=new String[betNumbers.size()];
		List<String> strs=new ArrayList<String>();
		ICombinatoricsVector<String> initialVector = Factory.createVector(betNumbers.toArray(str));
		Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector,length);
		for (ICombinatoricsVector<String> combination : gen) {
			String abc="";
			for(String st:combination.getVector()){
				abc+=(","+st);
			}
			strs.add(abc.substring(1));
		}
		return strs;
	}
	
	/**
	 * 获取开奖号码分割后的string数组
	 * @param resultCode
	 * @return
	 */
	public static String[] getResultNumbers(String resultCode) {
		char[] nums = resultCode.toCharArray();
		String[] numbers = new String[nums.length];
		for (int i = 0; i < nums.length; i++) {
			numbers[i] = nums[i] + "";
		}
		return numbers;
	}

	/**
	 * 计算某值在结果中出现几次 （1位拆分）
	 * @param resultCode
	 * @param countString
	 * @return
	 */
	public static int countStringTime(String resultCode,String countString){
		int count = 0 ;
		String[] resultCodes = getResultNumbers( resultCode);
		for (String string : resultCodes) {
			if(string.equals(countString)){
				count ++ ;
			}
		}
		return count;
	}
	
	/**
	 * 判断值是否在数组中 
	 * @param numbers
	 * @param hezhi
	 * @return
	 */
	public static boolean isExist(String[] numbers, int hezhi) {
		//特殊符号的处理 有些投注内容分割后会带有-等特殊字符
		String regex = "\\d+";
		for (String number : numbers) {
			if (number.matches(regex)) {
				if (Integer.parseInt(number) == hezhi) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取开奖号码的和值 
	 * @param resultCode
	 * @return
	 */
	public static int getHezhi(String resultCode) {
		int tmp = 0;
		String[] numbers = getResultNumbers(resultCode);
		for (String number : numbers) {
			tmp += Integer.parseInt(number);
		}
		return tmp;
	}

	/**
	 * 获取开奖号码中的重号集合
	 * @param resultCode
	 * @return
	 */
	public static List<String> getDoubleNumbers(String resultCode) {
		char[] chars = resultCode.toCharArray();
		List<String> list = new ArrayList<String>(); //过度集合
		List<String> sameList = new ArrayList<String>(); //重复出现数字的集合
		for (int i = 0; i < chars.length; i++) {
			if (!list.contains(chars[i] + "")) {
				list.add(chars[i] + "");
			} else {
				if (!sameList.contains(chars[i] + "")) {
					sameList.add(chars[i] + "");
				}

			}
		}
		return sameList;
	}

	/**
	 * 获取字符串所有的排列，字符串格式"12345"
	 * @param source
	 * @return
	 */
	public static List<String> pailie(String source) {
		if (source == null || source.equals("")) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		ICombinatoricsVector<String> initialVector = Factory.createVector(getBaseNum(source));
		Generator<String> gen = Factory.createPermutationGenerator(initialVector);
		for (ICombinatoricsVector<String> combination : gen) {
			result.add(combination.stringVal());
		}
		return result;
	}
	
	public static String[] getBaseNum(String abc) {
		return abc.split("(?!^|$)");
	}
	
	/**
	 * 获取开奖号码的跨度值
	 * @param resultCode
	 * @return
	 */
	public static int getKuadu(String resultCode) {
		Integer[] numbers = getIntegerResultNumbers(resultCode);
		Arrays.sort(numbers);
		return numbers[numbers.length - 1] - numbers[0];
	}

	/**
	 * 获取开奖号码分割后的整型数组
	 * @param resultCode
	 * @return
	 */
	public static Integer[] getIntegerResultNumbers(String resultCode) {
		char[] nums = resultCode.toCharArray();
		Integer[] numbers = new Integer[nums.length];
		for (int i = 0; i < nums.length; i++) {
			numbers[i] = Integer.parseInt(nums[i] + "");
		}
		return numbers;
	}

	/**
	 * 获取开奖号码中的单号集合
	 * @param resultCode
	 * @return
	 */
	public static List<String> getSigleNumbers(String resultCode) {
		char[] chars = resultCode.toCharArray();
		List<String> list = new ArrayList<String>(); //过度集合
		List<String> sameList = getDoubleNumbers(resultCode); //重复出现数字的集合
		for (int i = 0; i < chars.length; i++) {
			if (!sameList.contains(chars[i] + "")) {
				list.add(chars[i] + "");
			}
		}
		return list;
	}
	
	/**
	 * 获取数字在开奖号码中出现次数
	 * @param resultCode
	 * @param number
	 * @return
	 */
	public static int getNumberCount(String resultCode,int number) {
		char[] chars = resultCode.toCharArray();
		int count=0;
		for (int i = 0; i < chars.length; i++) {
			if (number==(chars[i]-'0')){
				count++;
			}
		}
		return count;
	}
	
	/**
	 * 包含对方全部，或被对方全部包含
	 * @param code
	 * @param compareCode
	 * @return
	 */
	public static boolean reflexiveContainAll(List<String> code, List<String> compareCode){
		boolean isAllHave;
		if (code.size() > compareCode.size()) {
			isAllHave = code.containsAll(compareCode);
		} else {
			isAllHave = compareCode.containsAll(code);
		}
		return isAllHave;
	}
}
