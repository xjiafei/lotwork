package com.winterframework.firefrog.phone.web.validate.lottery;

import java.util.HashMap;
import java.util.Map;

public class SetCodeType {
	public enum SetCodeTypes {

		//	直选
		ZHX,
		//	组选
		ZX,
		//	不定位
		BDW,
		//	趣味
		QW,
		//	定位胆
		DWD,
		//	普通任选
		PTRX,
		//	盘面
		PM,
		//	复式
		FS,
		//	单式
		DS,
		//	胆拖
		DT;
	}

	public static Map<Object, Object> SET_CODE_TYPE_MAP = new HashMap<Object, Object>();

	static {

		SET_CODE_TYPE_MAP.put(10, SetCodeTypes.ZHX);
		SET_CODE_TYPE_MAP.put(11, SetCodeTypes.ZX);
		SET_CODE_TYPE_MAP.put(12, SetCodeTypes.BDW);
		SET_CODE_TYPE_MAP.put(13, SetCodeTypes.QW);
		SET_CODE_TYPE_MAP.put(14, SetCodeTypes.DWD);
		SET_CODE_TYPE_MAP.put(15, SetCodeTypes.PTRX);
		SET_CODE_TYPE_MAP.put(16, SetCodeTypes.PM);
		SET_CODE_TYPE_MAP.put(17, SetCodeTypes.FS);
		SET_CODE_TYPE_MAP.put(18, SetCodeTypes.DS);
		SET_CODE_TYPE_MAP.put(19, SetCodeTypes.DT);
	}

}