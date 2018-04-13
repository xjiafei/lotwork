package com.winterframework.firefrog.game.web.util;

import java.util.HashMap;
import java.util.Map;

public class GameAwardNameUtil4Web {

	/**
	 *  1)		五星	10	
		2)		四星	11	
		3)		前三	12	
		4)		后三	13	
		5)		后二	14	
		6)		前二	15	
		7)		一星	16	
	 */
	private static final String[] GAME_GROUP_NAME = { "五星", "四星", "前三", "后三", "后二", "前二", "一星" };

	/**
	 * 玩法群 
	 * @param groupCode
	 * @return
	 */
	public static String groupName(Integer groupCode) {

		return GAME_GROUP_NAME[groupCode - 10 >= 0 ? groupCode - 10 : 0];
	}

	/**
	 *  1)		直选	10
		2)		组选	11
		3)		不定位	12
		4)		趣味	13
		5)		定位胆	14
	 */
	private static final String[] SET_CODE_NAME = { "直选", "组选", "不定位", "趣味", "定位胆" };

	/**
	 * 玩法组 
	 * @param setCode
	 * @return
	 */
	public static String setCodeName(Integer setCode) {

		if (setCode == 71) {
			return "标准";
		}
		return SET_CODE_NAME[setCode - 10 >= 0 ? setCode - 10 : 0];
	}

	/**
	 * <pre>
	 * index	中文說明		assist_code
	 * 0		复式			10
	 * 1		单式			11
	 * 2		胆拖			12
	 * 3		不定位		13
	 * 4		定位胆		14
	 * 5		任选一中一		15
	 * 6		任选二中二		16
	 * 7		任选三中三		17
	 * 8		任选四中四		18
	 * 9		任选五中五		19
	 * 10		任选六中五		20
	 * 11		任选七中五		21
	 * 12		任选八中五		22
	 * 13		任选1			23
	 * 14		任选2			24
	 * 15		任选3			25
	 * 16		任选4			26
	 * 17		任选5			27
	 * 18		任选6			28
	 * 19		任选7			29
	 * 20		上下盘		30
	 * 21		奇偶			31
	 * 22		和值大小单双	32
	 * 23		和值			33
	 * 24		跨度			34
	 * 25		组三			35
	 * 26		组六			36
	 * 27		混合组选		37
	 * 28		豹子			38
	 * 29		包胆			39
	 * 30		一码不定位		40
	 * 31		二码不定位		41
	 * 32		三码不定位		42
	 * 33		组选120（杂牌）	43
	 * 34		组选60（对子）	44
	 * 35		组选30（两对）	45
	 * 36		组选20（三条）	46
	 * 37		组选10（葫芦）	47
	 * 38		组选5（四条）	48
	 * 39		组选24		49
	 * 40		组选12		50
	 * 41		组选6			51
	 * 42		组选4			52
	 * 43		一帆风顺		53
	 * 44		好事成双		54
	 * 45		三星报喜		55
	 * 46		四季发财		56
	 * 47		万位			57
	 * 48		千位			58
	 * 49		百位			59
	 * 50		十位			60
	 * 51		个位			61
	 * 52		组三			62
	 * 53		组六			63
	 * 54		主肖			64
	 * 55		副肖			65
	 * 56		中二			66
	 * 57		中三			67
	 * 58		中特			68
	 * 59		趣味			70
	 * </pre>
	 */
	private static final String[] METHOD_CODE_NAME = { "复式", "单式", "胆拖", "不定位", "定位胆", "任选一中一", "任选二中二", "任选三中三",
			"任选四中四", "任选五中五", "任选六中五", "任选七中五", "任选八中五", "任选1", "任选2", "任选3", "任选4", "任选5", "任选6", "任选7", "上下盘", "奇偶",
			"和值大小单双", "和值", "跨度", "组三", "组六", "混合组选", "豹子", "包胆", "一码不定位", "二码不定位", "三码不定位", "组选120（杂牌）", "组选60（对子）",
			"组选30（两对）", "组选20（三条）", "组选10（葫芦）", "组选5（四条）", "组选24", "组选12", "组选6", "组选4", "一帆风顺", "好事成双", "三星报喜",
			"四季发财", "万位", "千位", "百位", "十位", "个位", "组三单式", "组六单式", "主肖", "副肖", "中二", "中三", "中特", "趣味" };

	public static String methodCodeName(Integer methodCode) {
		return METHOD_CODE_NAME[methodCode - 10 >= 0 ? methodCode - 10 : 0];
	}

	private static final Map<String, Object> LOTTERY_MAP = new HashMap<String, Object>();

	static {
		LOTTERY_MAP.put("99101", "重庆时时彩");
		LOTTERY_MAP.put("99102", "江西时时彩");
		LOTTERY_MAP.put("99103", "新疆时时彩");
		LOTTERY_MAP.put("99104", "天津时时彩");
		LOTTERY_MAP.put("99105", "黑龙江时时彩");
		LOTTERY_MAP.put("99106", "宝开时时彩	");
		LOTTERY_MAP.put("99107", "上海时时乐	");
		LOTTERY_MAP.put("99108", "3D");
		LOTTERY_MAP.put("99109", "排列5");
		LOTTERY_MAP.put("99110", "排列3");
		LOTTERY_MAP.put("99301", "山东11选5	");
		LOTTERY_MAP.put("99302", "江西11选5	");
		LOTTERY_MAP.put("99307", "江苏11选5	");
		LOTTERY_MAP.put("99303", "广东11选5	");
		LOTTERY_MAP.put("99304", "重庆11选5	");
		LOTTERY_MAP.put("99305", "宝开11选5");
		LOTTERY_MAP.put("99306", "秒开11选5");
		LOTTERY_MAP.put("99201", "北京快乐8");
		LOTTERY_MAP.put("99401", "双色球");
		LOTTERY_MAP.put("99111", "宝开一分彩");
		LOTTERY_MAP.put("99112", "秒开时时彩");
		LOTTERY_MAP.put("99501", "江苏快三");
		LOTTERY_MAP.put("99502", "安徽快三");
		LOTTERY_MAP.put("99601", "江苏骰宝");
		LOTTERY_MAP.put("99701", "六合彩");		
		LOTTERY_MAP.put("99602", "高频骰宝(娱乐厅)");
		LOTTERY_MAP.put("99603", "高频骰宝(至尊厅)");
		LOTTERY_MAP.put("99113", "超级2000秒秒彩（APP版）");
		LOTTERY_MAP.put("99114", "腾讯分分彩");
	}

	public static final String lotteryName(Long lotterId) {
		return LOTTERY_MAP.get(String.valueOf(lotterId)).toString();
	}
}
