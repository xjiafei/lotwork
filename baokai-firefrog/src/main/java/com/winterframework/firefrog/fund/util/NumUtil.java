package com.winterframework.firefrog.fund.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumUtil {
	private static final DecimalFormat df = new DecimalFormat("0.00");
	private static final BigDecimal div=new BigDecimal(10000);
	public static BigDecimal toMow(Long dbNumber) {
		return new BigDecimal(df.format((new BigDecimal(dbNumber)).divide(div)));
	}
	public static BigDecimal toMow(BigDecimal dbNumber) {
		return new BigDecimal(df.format(dbNumber.divide(div)));
	}
	public static void main(String[] s){
		System.out.println(toMow(12345L));
		System.out.println(toMow(1223333345L));
	}

}
