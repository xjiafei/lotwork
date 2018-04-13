package com.winterframework.firefrog.fund.util;

import java.math.BigDecimal;

public class MowNumTool {

	public static Long fromMow(BigDecimal b){
		if(b==null){
			return 0l;
		}
		else{
			return b.multiply(BigDecimal.valueOf(10000L)).longValue();
		}
	}
	public static Long fromMow(String b){
		if(b.startsWith("N")) return 0L;
		return new BigDecimal(b).multiply(BigDecimal.valueOf(10000L)).longValue();
	}
	public static BigDecimal fromMowToBig(BigDecimal b){
		if(b==null){
			return BigDecimal.valueOf(0);
		}
		else{
			return b.multiply(BigDecimal.valueOf(10000L));
		}
	}
	public static BigDecimal fromFrifrog(BigDecimal b){
		if(b==null){
			return BigDecimal.valueOf(0);
		}
		else{
			return b.divide(BigDecimal.valueOf(10000L));
		}
	}
	public static BigDecimal fromFrifrog(Long b){
		if(b==null){
			return BigDecimal.valueOf(0);
		}
		else{
			return BigDecimal.valueOf(b).divide(BigDecimal.valueOf(10000L));
		}
	}

}
