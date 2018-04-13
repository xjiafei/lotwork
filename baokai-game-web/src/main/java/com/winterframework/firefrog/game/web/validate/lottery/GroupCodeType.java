package com.winterframework.firefrog.game.web.validate.lottery;

import java.util.HashMap;
import java.util.Map;

public class GroupCodeType {
	public enum GroupCodeTypes {

		//	五星
		WX,
		//	四星
		SX,
		//	前三
		QS,
		//	后三
		HS,
		//	后二
		HE,
		//	前二
		QE,
		//	一星
		YX,
		//	任选
		ZX,
		//	趣味
		QW,
		//	三码
		SM,
		//	二码
		EM,
		//	一码
		YM;

	}

	public static Map<Object, Object> GROUP_CODE_TYPE_MAP = new HashMap<Object, Object>();

	static {

		GROUP_CODE_TYPE_MAP.put(10, GroupCodeTypes.WX);
		GROUP_CODE_TYPE_MAP.put(11, GroupCodeTypes.SX);
		GROUP_CODE_TYPE_MAP.put(12, GroupCodeTypes.QS);
		GROUP_CODE_TYPE_MAP.put(13, GroupCodeTypes.HS);
		GROUP_CODE_TYPE_MAP.put(14, GroupCodeTypes.HE);
		GROUP_CODE_TYPE_MAP.put(15, GroupCodeTypes.QE);
		GROUP_CODE_TYPE_MAP.put(16, GroupCodeTypes.YX);
		GROUP_CODE_TYPE_MAP.put(17, GroupCodeTypes.ZX);
		GROUP_CODE_TYPE_MAP.put(18, GroupCodeTypes.QW);
		GROUP_CODE_TYPE_MAP.put(19, GroupCodeTypes.SM);
		GROUP_CODE_TYPE_MAP.put(20, GroupCodeTypes.EM);
		GROUP_CODE_TYPE_MAP.put(21, GroupCodeTypes.YM);
	}
}
