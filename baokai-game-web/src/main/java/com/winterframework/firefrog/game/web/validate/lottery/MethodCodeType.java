package com.winterframework.firefrog.game.web.validate.lottery;

import java.util.HashMap;
import java.util.Map;

public class MethodCodeType {
	public enum MethodCodeTypes {

		//	复式
		FS,
		//	单式
		DS,
		//	胆拖
		DT,
		//	不定位
		BDW,
		//	定位胆
		DWD,
		//	任选一中一
		RX1Z1,
		//	任选二中二
		RX2Z2,
		//	任选三中三
		RX3Z3,
		//	任选四中四
		RX4Z4,
		//	任选五中五
		RX5Z5,
		//	任选六中五
		RX6Z5,
		//	任选七中五
		RX7Z5,
		//	任选八中五
		RX8Z5,
		//	任选1
		RX1,
		//	任选2
		RX2,
		//	任选3
		RX3,
		//	任选4
		RX4,
		//	任选5
		RX5,
		//	任选6
		RX6,
		//	任选7
		RX7,
		//	上下盘
		SXP,
		//	奇偶
		QO,
		//	和值大小单双
		HZDXDS,
		//	和值
		HZ,
		//	跨度
		KD,
		//	组三
		Z3,
		//	组六
		Z6,
		//	混合组选
		HHZX,
		//	和值
		//	HZ
		//	包胆
		BD,
		//	一码不定位
		YMBDW,
		//	二码不定位
		EMBDW,
		//	三码不定位
		SMBDW,
		//	组选120（杂牌）
		ZX120,
		//	组选60（对子）
		ZX60,
		//	组选30（两对）
		ZX30,
		//	组选20（三条）
		ZX20,
		//	组选10（葫芦）
		ZX10,
		//	组选5（四条）
		ZX5,
		//	组选24
		ZX24,
		//	组选12
		ZX12,
		//	组选6
		ZX6,
		//	组选4
		ZX4,
		//	一帆风顺
		YFFS,
		//	好事成双
		HSCS,
		//	三星报喜
		SXBX,
		//	四季发财
		SJFC,
		//	万位
		WW,
		//	千位
		QW,
		//	百位
		BW,
		//	十位
		SW,
		//	个位
		GW,
		//	组三单式
		ZSDS,
		//	组六单式
		ZLDS;

	}

	public static Map<Object, Object> METHOD_CODE_TYPE_MAP = new HashMap<Object, Object>();
	static {
		METHOD_CODE_TYPE_MAP.put(10, MethodCodeTypes.FS);
		METHOD_CODE_TYPE_MAP.put(11, MethodCodeTypes.DS);
		METHOD_CODE_TYPE_MAP.put(12, MethodCodeTypes.DT);
		METHOD_CODE_TYPE_MAP.put(13, MethodCodeTypes.BDW);
		METHOD_CODE_TYPE_MAP.put(14, MethodCodeTypes.DWD);
		METHOD_CODE_TYPE_MAP.put(15, MethodCodeTypes.RX1Z1);
		METHOD_CODE_TYPE_MAP.put(16, MethodCodeTypes.RX2Z2);
		METHOD_CODE_TYPE_MAP.put(17, MethodCodeTypes.RX3Z3);
		METHOD_CODE_TYPE_MAP.put(18, MethodCodeTypes.RX4Z4);
		METHOD_CODE_TYPE_MAP.put(18, MethodCodeTypes.RX5Z5);
		METHOD_CODE_TYPE_MAP.put(20, MethodCodeTypes.RX6Z5);
		METHOD_CODE_TYPE_MAP.put(21, MethodCodeTypes.RX7Z5);
		METHOD_CODE_TYPE_MAP.put(22, MethodCodeTypes.RX8Z5);
		METHOD_CODE_TYPE_MAP.put(23, MethodCodeTypes.RX1);
		METHOD_CODE_TYPE_MAP.put(24, MethodCodeTypes.RX2);
		METHOD_CODE_TYPE_MAP.put(25, MethodCodeTypes.RX3);
		METHOD_CODE_TYPE_MAP.put(26, MethodCodeTypes.RX4);
		METHOD_CODE_TYPE_MAP.put(27, MethodCodeTypes.RX5);
		METHOD_CODE_TYPE_MAP.put(28, MethodCodeTypes.RX6);
		METHOD_CODE_TYPE_MAP.put(29, MethodCodeTypes.RX7);
		METHOD_CODE_TYPE_MAP.put(30, MethodCodeTypes.SXP);
		METHOD_CODE_TYPE_MAP.put(31, MethodCodeTypes.QO);
		METHOD_CODE_TYPE_MAP.put(32, MethodCodeTypes.HZDXDS);
		METHOD_CODE_TYPE_MAP.put(33, MethodCodeTypes.HZ);
		METHOD_CODE_TYPE_MAP.put(34, MethodCodeTypes.KD);
		METHOD_CODE_TYPE_MAP.put(35, MethodCodeTypes.Z3);
		METHOD_CODE_TYPE_MAP.put(36, MethodCodeTypes.Z6);
		METHOD_CODE_TYPE_MAP.put(37, MethodCodeTypes.HHZX);
		//		METHOD_CODE_TYPE.put(38, MethodCodeTypes.);
		METHOD_CODE_TYPE_MAP.put(39, MethodCodeTypes.BD);
		METHOD_CODE_TYPE_MAP.put(40, MethodCodeTypes.YMBDW);
		METHOD_CODE_TYPE_MAP.put(41, MethodCodeTypes.EMBDW);
		METHOD_CODE_TYPE_MAP.put(42, MethodCodeTypes.SMBDW);
		METHOD_CODE_TYPE_MAP.put(43, MethodCodeTypes.ZX120);
		METHOD_CODE_TYPE_MAP.put(44, MethodCodeTypes.ZX60);
		METHOD_CODE_TYPE_MAP.put(45, MethodCodeTypes.ZX30);
		METHOD_CODE_TYPE_MAP.put(46, MethodCodeTypes.ZX20);
		METHOD_CODE_TYPE_MAP.put(47, MethodCodeTypes.ZX10);
		METHOD_CODE_TYPE_MAP.put(48, MethodCodeTypes.ZX5);
		METHOD_CODE_TYPE_MAP.put(49, MethodCodeTypes.ZX24);
		METHOD_CODE_TYPE_MAP.put(50, MethodCodeTypes.ZX12);
		METHOD_CODE_TYPE_MAP.put(51, MethodCodeTypes.ZX6);
		METHOD_CODE_TYPE_MAP.put(52, MethodCodeTypes.ZX4);
		METHOD_CODE_TYPE_MAP.put(53, MethodCodeTypes.YFFS);
		METHOD_CODE_TYPE_MAP.put(54, MethodCodeTypes.HSCS);
		METHOD_CODE_TYPE_MAP.put(55, MethodCodeTypes.SXBX);
		METHOD_CODE_TYPE_MAP.put(56, MethodCodeTypes.SJFC);
		METHOD_CODE_TYPE_MAP.put(57, MethodCodeTypes.WW);
		METHOD_CODE_TYPE_MAP.put(58, MethodCodeTypes.QW);
		METHOD_CODE_TYPE_MAP.put(59, MethodCodeTypes.BW);
		METHOD_CODE_TYPE_MAP.put(60, MethodCodeTypes.SW);
		METHOD_CODE_TYPE_MAP.put(61, MethodCodeTypes.GW);
		METHOD_CODE_TYPE_MAP.put(62, MethodCodeTypes.ZSDS);
		METHOD_CODE_TYPE_MAP.put(63, MethodCodeTypes.ZLDS);
	}

}
