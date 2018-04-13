package com.winterframework.firefrog.game.web.validate.utils;

import java.math.BigInteger;

/**
* @ClassName: CombinUtil 
* @Description: 投注数计算excel对应方法combin工具类 
* @author david
* @date 2013-7-30 上午11:02:15 
*  
*/
public class CombinUtil {
	/** 
	* @Title: cal 
	* @Description: 阶乘计算 
	* @param n
	* @return
	*/
	private static BigInteger cal(int n) {
		if (n == 0) {
			return BigInteger.valueOf(1);
		} if (n == 1) {
            return BigInteger.valueOf(1);
        } else {
			return cal(n - 1).multiply(BigInteger.valueOf(n));
		}
	}

	/** 
	* @Title: combin 
	* @Description: 对应投注注数计算excel-combin函数
	* @param n
	* @param m
	* @return
	*/
	public static int combin(int n, int m) {
		if (n == 0) {
			return 0;
		}
		if(n<m){
			return 0;
		}
		return cal(n).divide(cal(m)).divide(cal(n-m)).intValue();
	}

	public static void main(String[] args) {
		System.out.println("-,121,34,56,78".matches("-,[1-9]{1,},[1-9]{1,},[1-9]{1,},[1-9]{1,}"));

	}

}
