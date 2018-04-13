package com.winterframework.firefrog.game.web.util;

import java.util.HashMap;
import java.util.Map;

/** 
* @ClassName: SSCBetNameUtil 
* @Description: 时时彩投注使用
* @author Denny 
* @date 2013-10-8 下午7:11:19 
*  
*/
public class SSCBetNameUtil {

	private static Map<String, Integer> GAME_GROUP_CODE_NAME_MAP = new HashMap<String, Integer>();
	private static Map<String, Integer> GAME_SET_CODE_NAME_MAP = new HashMap<String, Integer>();
	private static Map<String, Integer> BET_METHOD_CODE_NAME_MAP = new HashMap<String, Integer>();
	
	//与上面三个map的key value 掉换 成新的map
	private static Map<Integer, String> GAME_GROUP_CODE_NAME_MAP_VALUE = new HashMap<Integer, String>();
	private static Map<Integer, String> GAME_SET_CODE_NAME_MAP_VALUE = new HashMap<Integer, String>();
	private static Map<Integer, String> BET_METHOD_CODE_NAME_MAP_VALUE = new HashMap<Integer, String>();
	
	private static Map<String, Long> GAME_LOTTERY_NAME_MAP = new HashMap<String, Long>();
	
//	彩种内部编码转换
	private static Map<Long,String> GAME_LOTTERY_NAME_TO_UI_MAP = new HashMap<Long,String>();
	
	static {
		
		GAME_LOTTERY_NAME_TO_UI_MAP.put(99101L, "cqssc");
		
		GAME_LOTTERY_NAME_TO_UI_MAP.put(99201L, "bjkl8");
		
		
		//彩种
		GAME_LOTTERY_NAME_MAP.put("cqssc", 99101L);
		
		
		// 玩法群，7个
		GAME_GROUP_CODE_NAME_MAP.put("wuxing", 10);
		GAME_GROUP_CODE_NAME_MAP.put("sixing", 11);
		GAME_GROUP_CODE_NAME_MAP.put("qiansan", 12);
		GAME_GROUP_CODE_NAME_MAP.put("housan", 13);
		GAME_GROUP_CODE_NAME_MAP.put("houer", 14);
		GAME_GROUP_CODE_NAME_MAP.put("qianer", 15);
		GAME_GROUP_CODE_NAME_MAP.put("yixing", 16);
		GAME_GROUP_CODE_NAME_MAP.put("quwei",18);
		
		GAME_GROUP_CODE_NAME_MAP_VALUE.put(10,"wuxing");
		GAME_GROUP_CODE_NAME_MAP_VALUE.put(11,"sixing");
		GAME_GROUP_CODE_NAME_MAP_VALUE.put(12,"qiansan");
		GAME_GROUP_CODE_NAME_MAP_VALUE.put(13,"housan");
		GAME_GROUP_CODE_NAME_MAP_VALUE.put(14,"houer");
		GAME_GROUP_CODE_NAME_MAP_VALUE.put(15,"qianer");
		GAME_GROUP_CODE_NAME_MAP_VALUE.put(16,"yixing");
		GAME_GROUP_CODE_NAME_MAP_VALUE.put(18,"quwei");
		
		// 玩法组，5个
		GAME_SET_CODE_NAME_MAP.put("zhixuan", 10);
		GAME_SET_CODE_NAME_MAP.put("zuxuan", 11);
		GAME_SET_CODE_NAME_MAP.put("budingwei", 12);
		GAME_SET_CODE_NAME_MAP.put("quwei", 13);
		GAME_SET_CODE_NAME_MAP.put("dingweidan", 14);
		GAME_SET_CODE_NAME_MAP.put("panmian", 16);
		
		GAME_SET_CODE_NAME_MAP_VALUE.put(10,"zhixuan");
		GAME_SET_CODE_NAME_MAP_VALUE.put(11,"zuxuan");
		GAME_SET_CODE_NAME_MAP_VALUE.put(12,"budingwei");
		GAME_SET_CODE_NAME_MAP_VALUE.put(13,"quwei");
		GAME_SET_CODE_NAME_MAP_VALUE.put(14,"dingweidan");
		GAME_SET_CODE_NAME_MAP_VALUE.put(16,"panmian");
		
		// 投注方式，55个
		BET_METHOD_CODE_NAME_MAP.put("fushi", 10);
		BET_METHOD_CODE_NAME_MAP.put("danshi", 11);
		BET_METHOD_CODE_NAME_MAP.put("zuxuan120", 43);
		BET_METHOD_CODE_NAME_MAP.put("zuxuan60", 44);
		BET_METHOD_CODE_NAME_MAP.put("zuxuan30", 45);
		BET_METHOD_CODE_NAME_MAP.put("zuxuan20", 46);
		BET_METHOD_CODE_NAME_MAP.put("zuxuan10", 47);
		BET_METHOD_CODE_NAME_MAP.put("zuxuan5", 48);
		BET_METHOD_CODE_NAME_MAP.put("yimabudingwei", 40);
		BET_METHOD_CODE_NAME_MAP.put("ermabudingwei", 41);
		BET_METHOD_CODE_NAME_MAP.put("sanmabudingwei", 42);
		BET_METHOD_CODE_NAME_MAP.put("yifanfengshun", 53);
		BET_METHOD_CODE_NAME_MAP.put("haoshichengshuang", 54);
		BET_METHOD_CODE_NAME_MAP.put("sanxingbaoxi", 55);
		BET_METHOD_CODE_NAME_MAP.put("sijifacai", 56);
		BET_METHOD_CODE_NAME_MAP.put("zuxuan24", 49);
		BET_METHOD_CODE_NAME_MAP.put("zuxuan12", 50);
		BET_METHOD_CODE_NAME_MAP.put("zuxuan6", 51);
		BET_METHOD_CODE_NAME_MAP.put("zuxuan4", 52);
		BET_METHOD_CODE_NAME_MAP.put("hezhi", 33);
		BET_METHOD_CODE_NAME_MAP.put("kuadu", 34);
		BET_METHOD_CODE_NAME_MAP.put("zusan", 35);
		BET_METHOD_CODE_NAME_MAP.put("zuliu", 36);
		BET_METHOD_CODE_NAME_MAP.put("zusandanshi", 62);
		BET_METHOD_CODE_NAME_MAP.put("zuliudanshi", 63);
		BET_METHOD_CODE_NAME_MAP.put("hunhezuxuan", 37);
		//BET_METHOD_CODE_NAME_MAP.put("zuxuanhezhi", 33);
		//BET_METHOD_CODE_NAME_MAP.put("zuxuanbaodan", 39);
		BET_METHOD_CODE_NAME_MAP.put("baodan", 39);
		
		BET_METHOD_CODE_NAME_MAP_VALUE.put(10,"fushi");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(11,"danshi");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(43,"zuxuan120");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(44,"zuxuan60");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(45,"zuxuan30");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(46,"zuxuan20");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(47,"zuxuan10");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(48,"zuxuan5");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(40,"yimabudingwei");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(41,"ermabudingwei");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(42,"sanmabudingwei");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(53,"yifanfengshun");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(54,"haoshichengshuang");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(55,"sanxingbaoxi");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(56,"sijifacai");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(49,"zuxuan24");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(50,"zuxuan12");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(51,"zuxuan6");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(52,"zuxuan4");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(33,"hezhi");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(34,"kuadu");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(35,"zusan");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(36,"zuliu");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(62,"zusandanshi");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(63,"zuliudanshi");
		BET_METHOD_CODE_NAME_MAP_VALUE.put(37,"hunhezuxuan");
		//BET_METHOD_CODE_NAME_MAP.put("zuxuanhezhi", 33);
		//BET_METHOD_CODE_NAME_MAP.put("zuxuanbaodan", 39);
		BET_METHOD_CODE_NAME_MAP_VALUE.put(39,"baodan");
	}
	
	public static Integer getGameGroupCode(String gameGroupName) {
		return GAME_GROUP_CODE_NAME_MAP.get(gameGroupName);
	}
	
	public static Integer getGameSetCode(String gameSetName) {
		return GAME_SET_CODE_NAME_MAP.get(gameSetName);
	}
	
	public static Integer getBetMethodCode(String betMethodName) {
		return BET_METHOD_CODE_NAME_MAP.get(betMethodName);
	}
	
	public static Long getLotteryId(String lotteryName) {
		return GAME_LOTTERY_NAME_MAP.get(lotteryName);
	}
	
	public static String getUILotteryName(Long lotteryId) {
		return GAME_LOTTERY_NAME_TO_UI_MAP.get(lotteryId);
	}
	
	public static String getBetMethodFullNameByValue(Integer gameGroupCode,Integer gameSetCode,Integer betMethodCode){
		StringBuilder sb=new StringBuilder();
		sb.append(GAME_GROUP_CODE_NAME_MAP_VALUE.get(gameGroupCode));
		sb.append(".");
		sb.append(GAME_SET_CODE_NAME_MAP_VALUE.get(gameSetCode));
		sb.append(".");
		sb.append(BET_METHOD_CODE_NAME_MAP_VALUE.get(betMethodCode));
		return sb.toString();
	}
	
	public static void main(String[] args) {
		
		String betMethodType = "wuxing.zhixuan.fushi";
		String[] type = betMethodType.split("\\.");
		Integer gameGroupCode = SSCBetNameUtil.getGameGroupCode(type[0]);
		Integer gameSetCode = SSCBetNameUtil.getGameSetCode(type[1]);
		Integer betMethodCode = SSCBetNameUtil.getBetMethodCode(type[2]);
		
		System.out.println("" + gameGroupCode + gameSetCode + betMethodCode);
	}
	
}
