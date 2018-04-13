package com.winterframework.firefrog.phone.web.validate.utils;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: BetCalUtil 
* @Description: 注数计算根据选号辅助算法2得到的选项号码重复个数D，选项号码不一样的个数S，重号选项个数m，单号选项个数n工具类  
* @author david
* @date 2013-7-30 下午5:50:15 
*  
*/
public class BetCalUtil {
	public static Logger log = LoggerFactory.getLogger(BetCalUtil.class);

	/** 
	* @Title: getD 
	* @Description: 获取选项号码重复个数D
	* @param repeatStr 重号部分
	* @param oddStr 单号部分
	* @return
	*/
	public static int getD(String repeatStr, String oddStr) {
		char[] repeatChar = repeatStr.toCharArray();
		char[] oddChar = oddStr.toCharArray();
		Set<String> repeatSet = ValidateUtils.checkRepeat(repeatStr);
		Set<String> oddSet = ValidateUtils.checkRepeat(oddStr);
		repeatSet.addAll(oddSet);
		return repeatChar.length + oddChar.length - repeatSet.size();
	}

	/** 
	* @Title: getS 
	* @Description: 获取选项号码不一样的个数s  由单号看重号 重号1234 单号235  重号中和单号不一样的数字为1,4 两个
	* @param repeatStr 重号部分
	* @param oddStr 单号部分
	* @return
	*/
	public static int getS(String repeatStr, String oddStr) {
		char[] repeatChar = repeatStr.toCharArray();
		Set<String> oddSet = ValidateUtils.checkRepeat(oddStr);
		int i = 0;
		for (char c : repeatChar) {
			if (!oddSet.contains(c + "")) {
				i += 1;
			}
		}
		return i;
	}
	
	/**
	 * 
	* @Title: getS30  组选30获取选项号码不一样的个数s 
	* @Description: 针对于组选30的特殊情况 计算s是由重号看单号的不一样个数  即 重号1234 单号235  单号中和重号不一样的数字为5这1个
	* @param repeatStr
	* @param oddStr
	* @return
	 */
	public static int getS30(String repeatStr, String oddStr) {
		char[] repeatChar = oddStr.toCharArray();
		Set<String> oddSet = ValidateUtils.checkRepeat(repeatStr);
		int i = 0;
		for (char c : repeatChar) {
			if (!oddSet.contains(c + "")) {
				i += 1;
			}
		}
		
		return i;
	}

	/** 
	* @Title: getN 
	* @Description: 获取单号选项个数,123
	* @param oddStr
	* @return
	*/
	public static int getN(String oddStr) {
		char[] oddChar = oddStr.toCharArray();
		return oddChar.length;
	}

	/** 
	* @Title: getM 
	* @Description: 获取重号选项个数 ,123
	* @param repeatStr
	* @return
	*/
	public static int getM(String repeatStr) {
		char[] repeatChar = repeatStr.toCharArray();
		return repeatChar.length;
	}

}
