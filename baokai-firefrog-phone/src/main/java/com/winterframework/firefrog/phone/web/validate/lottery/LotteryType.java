package com.winterframework.firefrog.phone.web.validate.lottery;

import java.util.HashMap;
import java.util.Map;

public class LotteryType {
	public enum LotteryTypes {

		//重庆时时彩 99101
		SSC;
	}

	public static Map<Object, Object> LOTTERY_TYPES_MAP = new HashMap<Object, Object>();

	static {

		LOTTERY_TYPES_MAP.put(99101, LotteryTypes.SSC);
	}

}
