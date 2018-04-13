/**   
* @Title: WinNumSignSsl.java 
* @Package com.winterframework.firefrog.game.web.WinNum 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-9-2 下午6:19:46 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.WinNum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @ClassName: WinNumSignSsl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-9-2 下午6:19:46 
*  
*/
public class WinNumSignSsl extends WinNumSignSsc implements IWinNumSign{
	protected List<String> getNumberRecordForSign(String groupCode) throws Exception {
		Map<String, Integer[]> GAME_GROUP_NUMBER_BITS_MAP = new HashMap<String, Integer[]>();
		GAME_GROUP_NUMBER_BITS_MAP.put("13", new Integer[] { 0, 3 });
		GAME_GROUP_NUMBER_BITS_MAP.put("14", new Integer[] { 1, 3 });
		GAME_GROUP_NUMBER_BITS_MAP.put("15", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("16", new Integer[] { 0, 3 });
		Integer[] bits = GAME_GROUP_NUMBER_BITS_MAP.get(groupCode);
		return numberRecordList.subList(bits[0], bits[1]);
	}
}
