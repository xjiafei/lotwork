package com.winterframework.firefrog.game.web.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.winterframework.firefrog.game.entity.LotteryWinLhcMap;

public class GameAwardNameUtil {

	/** 
	* @Fields nameMap : key: lotteryid_groupCode_setCode_methodCode  value:{groupName,setName,methodName} 
	*/
	private static Map<String, String[]> nameMap = new HashMap<String, String[]>();
	/** 
	* @Fields titleMap : titleMap : key: lotteryid_groupCode_setCode_methodCode  value:{groupTitle,setTitle,methodTitle} 
	*/
	private static Map<String, String[]> titleMap = new HashMap<String, String[]>();

	/** 
	* @Fields codeMap : key:lotteryid_groupName_setName_methodName value:{groupCode,setCode,methodCode}
	*/
	private static Map<String, Integer[]> codeMap = new HashMap<String, Integer[]>();

	public static Map<String, Integer[]> getCodeMap() {
		return codeMap;
	}

	public static void setCodeMap(Map<String, Integer[]> codeMap) {
		GameAwardNameUtil.codeMap = codeMap;
	}

	public static Map<String, String[]> getNameMap() {
		return nameMap;
	}

	public static void setNameMap(Map<String, String[]> nameMap) {
		GameAwardNameUtil.nameMap = nameMap;
	}

	public static Map<String, String[]> getTitleMap() {
		return titleMap;
	}

	public static void setTitleMap(Map<String, String[]> titleMap) {
		GameAwardNameUtil.titleMap = titleMap;
	}
	
	/**
	 * 返回玩法群组投注方式对应的英文名  wuxing zhixuan fushi
	 * @param lotteryId
	 * @param groupCode
	 * @param setCode
	 * @param methodCode
	 * @param num 0：群、1:组、2:投注方式
	 * @return
	 */
	public static String getName(Long lotteryId, Integer groupCode, Integer setCode, Integer methodCode, Integer num) {
		StringBuilder sb = new StringBuilder();
		sb.append(lotteryId).append("_").append(groupCode).append("_").append(setCode).append("_").append(methodCode);
		String key = sb.toString();
		String[] names = nameMap.get(key);
		return names==null?"":names[num];

	}
	
	/**
	 * 返回玩法群组投注方式对应的中文名  五星 直选 复式
	 * @param lotteryId
	 * @param groupCode
	 * @param setCode
	 * @param methodCode
	 * @param num 0：群、1:组、2:投注方式
	 * @return
	 */
	public static String getTitle(Long lotteryId, Integer groupCode, Integer setCode, Integer methodCode, Integer num) {
		StringBuilder sb = new StringBuilder();
		sb.append(lotteryId).append("_").append(groupCode).append("_").append(setCode).append("_").append(methodCode);
		String key = sb.toString();
		String[] titles = titleMap.get(key);
		return titles==null?"":titles[num];
	}
	
	/**
	 * 根据拼音返回玩法群组投注方式对应的编码 10 10  10
	 * @param lotteryId
	 * @param groupName
	 * @param setName
	 * @param methodName
	 * @param num 0：群、1:组、2:投注方式
	 * @return
	 */
	public static Integer getCode(Long lotteryId, String groupName, String setName, String methodName, Integer num) {
		StringBuilder sb = new StringBuilder();
		sb.append(lotteryId).append("_").append(groupName).append("_").append(setName).append("_").append(methodName);
		String key = sb.toString();
		Integer[] codes = codeMap.get(key);
		return codes==null?0:codes[num];
	}

	/**
	 * 获取玩法的拼音全拼 wuxing.zhixuan.fushi
	 * @param lotteryId
	 * @param groupCode
	 * @param setCode
	 * @param methodCode
	 * @return
	 */
	public static String getFullName(Long lotteryId, Integer groupCode, Integer setCode, Integer methodCode) {
		return getName(lotteryId, groupCode, setCode, methodCode, 0) + "."
				+ getName(lotteryId, groupCode, setCode, methodCode, 1) + "."
				+ getName(lotteryId, groupCode, setCode, methodCode, 2);
	}

	private static final Map<String, Object> LOTTERY_MAP = new HashMap<String, Object>();

	static {
		LOTTERY_MAP.put("99101", "重庆时时彩");
		LOTTERY_MAP.put("99102", "江西时时彩");
		LOTTERY_MAP.put("99103", "新疆时时彩");
		LOTTERY_MAP.put("99104", "天津时时彩");
		LOTTERY_MAP.put("99105", "黑龙江时时彩");
		LOTTERY_MAP.put("99106", "宝开时时彩");
		LOTTERY_MAP.put("99107", "上海时时乐");
		LOTTERY_MAP.put("99108", "3D");
		LOTTERY_MAP.put("99109", "排列5");
		LOTTERY_MAP.put("99110", "排列3");
		LOTTERY_MAP.put("99301", "山东11选5");
		LOTTERY_MAP.put("99302", "江西11选5");
		LOTTERY_MAP.put("99307", "江苏11选5");
		LOTTERY_MAP.put("99303", "广东11选5");
		LOTTERY_MAP.put("99304", "重庆11选5");
		LOTTERY_MAP.put("99305", "宝开11选5");
		LOTTERY_MAP.put("99306", "秒开11选5");
		LOTTERY_MAP.put("99307", "江苏11选5");
		LOTTERY_MAP.put("99201", "北京快乐8");
		LOTTERY_MAP.put("99401", "双色球");
		LOTTERY_MAP.put("99111", "宝开一分彩");
		LOTTERY_MAP.put("99112", "秒开时时彩"); 
		LOTTERY_MAP.put("99113", "超级2000秒秒彩（APP版）"); 
		LOTTERY_MAP.put("99501", "江苏快三");
		LOTTERY_MAP.put("99502", "安徽快三");
		LOTTERY_MAP.put("99601", "江苏骰宝");
		LOTTERY_MAP.put("99701", "六合彩"); 
		LOTTERY_MAP.put("99602", "高频骰宝(娱乐厅)");
		LOTTERY_MAP.put("99603", "高频骰宝(至尊厅)");
		LOTTERY_MAP.put("99114", "腾讯分分彩");
	}

	public static final String lotteryName(Long lotterId) {
		return LOTTERY_MAP.get(String.valueOf(lotterId)).toString();
	}

	/**
	 * 判断投注方式属于三星一码不定位
	 * @param groupCode
	 * @param setCode
	 * @param methodCode
	 * @return
	 */
	public static boolean isSXBDW(Integer groupCode, Integer setCode, Integer methodCode) {
		if ((groupCode == 12 || groupCode == 13) && setCode == 12 && methodCode == 40) {
			return true;
		}
		return false;
	}

	/**投注铺住表类型名称(有異動時 game-type.properties 的 assistBet 群要跟著改)*/
	private static Map<Integer, String> ASSISTBONUS_TYPENAME = new HashMap<Integer, String>();
	static {
		ASSISTBONUS_TYPENAME.put(1, "一等奖");
		ASSISTBONUS_TYPENAME.put(2, "二等奖");
		ASSISTBONUS_TYPENAME.put(3, "三等奖");
		ASSISTBONUS_TYPENAME.put(4, "四等奖");
		ASSISTBONUS_TYPENAME.put(5, "五等奖");
		ASSISTBONUS_TYPENAME.put(6, "六等奖");
		ASSISTBONUS_TYPENAME.put(10, "0单5双");
		ASSISTBONUS_TYPENAME.put(11, "5单0双");
		ASSISTBONUS_TYPENAME.put(12, "1单4双");
		ASSISTBONUS_TYPENAME.put(13, "4单1双");
		ASSISTBONUS_TYPENAME.put(14, "2单3双");
		ASSISTBONUS_TYPENAME.put(15, "3单2双");
		ASSISTBONUS_TYPENAME.put(16, "03,09");
		ASSISTBONUS_TYPENAME.put(17, "04,08");
		ASSISTBONUS_TYPENAME.put(18, "05,07");
		ASSISTBONUS_TYPENAME.put(19, "06");
		ASSISTBONUS_TYPENAME.put(20, "选1中1");
		ASSISTBONUS_TYPENAME.put(21, "选2中2");
		ASSISTBONUS_TYPENAME.put(22, "选3中3");
		ASSISTBONUS_TYPENAME.put(23, "选3中2");
		ASSISTBONUS_TYPENAME.put(24, "选4中4");
		ASSISTBONUS_TYPENAME.put(25, "选4中3");
		ASSISTBONUS_TYPENAME.put(26, "选4中2");
		ASSISTBONUS_TYPENAME.put(27, "选5中5");
		ASSISTBONUS_TYPENAME.put(28, "选5中4");
		ASSISTBONUS_TYPENAME.put(29, "选5中3");
		ASSISTBONUS_TYPENAME.put(30, "选6中6");
		ASSISTBONUS_TYPENAME.put(31, "选6中5");
		ASSISTBONUS_TYPENAME.put(32, "选6中4");
		ASSISTBONUS_TYPENAME.put(33, "选6中3");
		ASSISTBONUS_TYPENAME.put(34, "选7中7");
		ASSISTBONUS_TYPENAME.put(35, "选7中6");
		ASSISTBONUS_TYPENAME.put(36, "选7中5");
		ASSISTBONUS_TYPENAME.put(37, "选7中4");
		ASSISTBONUS_TYPENAME.put(38, "选7中0");
		ASSISTBONUS_TYPENAME.put(39, "上盘");
		ASSISTBONUS_TYPENAME.put(40, "下盘");
		ASSISTBONUS_TYPENAME.put(41, "中盘");
		ASSISTBONUS_TYPENAME.put(42, "奇盘");
		ASSISTBONUS_TYPENAME.put(45, "偶盘");
		ASSISTBONUS_TYPENAME.put(46, "和盘");
		ASSISTBONUS_TYPENAME.put(47, "大单");
		ASSISTBONUS_TYPENAME.put(48, "大双");
		ASSISTBONUS_TYPENAME.put(49, "小单");
		ASSISTBONUS_TYPENAME.put(50, "小双");
		ASSISTBONUS_TYPENAME.put(51, "和值3 18");
		ASSISTBONUS_TYPENAME.put(52, "和值4 17");
		ASSISTBONUS_TYPENAME.put(53, "和值5 16");
		ASSISTBONUS_TYPENAME.put(54, "和值6 15");
		ASSISTBONUS_TYPENAME.put(55, "和值7 14");
		ASSISTBONUS_TYPENAME.put(56, "和值8 13");
		ASSISTBONUS_TYPENAME.put(57, "和值9 12");
		ASSISTBONUS_TYPENAME.put(58, "和值10 11");
		ASSISTBONUS_TYPENAME.put(59, "一不同号单骰");
		ASSISTBONUS_TYPENAME.put(60, "一不同号双骰");
		ASSISTBONUS_TYPENAME.put(61, "一不同号全骰");
		ASSISTBONUS_TYPENAME.put(62, "组三");
		ASSISTBONUS_TYPENAME.put(63, "组六");
		ASSISTBONUS_TYPENAME.put(62, "组三");
		ASSISTBONUS_TYPENAME.put(63, "组六");
		ASSISTBONUS_TYPENAME.put(64, "主肖");
		ASSISTBONUS_TYPENAME.put(65, "副肖");
		ASSISTBONUS_TYPENAME.put(66, "中二");
		ASSISTBONUS_TYPENAME.put(67, "中三");
		ASSISTBONUS_TYPENAME.put(68, "中特");
		ASSISTBONUS_TYPENAME.put(70, "趣味");
	}

	/**
	 * 依據輔助玩法取得其名稱。
	 * @param type 輔助玩法
	 * @return
	 */
	public static final String getAssistBonusTypeName(Integer type) {
		return ASSISTBONUS_TYPENAME.get(type);
	}

	/**
	 * 取得六合彩獎金識別碼中文說明。
	 * @param lhcCode
	 * @return
	 */
	public static final String getLhcCodeName(String lhcCode) {
		return LotteryWinLhcMap.LHC_CODE_MAP.get(lhcCode)==null ? "" : LotteryWinLhcMap.LHC_CODE_MAP.get(lhcCode);
	}

	/**
	 * 取得 num 為六合彩色波的何種顏色。
	 * @param num 號碼
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static final String getLhcNumBallColor(String num) {
		
		String color="RED";
		Iterator iter = LotteryWinLhcMap.LHC_COLOR_MAP.keySet().iterator();
		
		while (iter.hasNext()) { 
		    String key = (String)iter.next(); 
		    String [] val = (String [] ) LotteryWinLhcMap.LHC_COLOR_MAP.get(key);
		    if(Arrays.asList(val).contains(num)){
		    	color = key;
		    }
		} 
		return color;
	}
}
