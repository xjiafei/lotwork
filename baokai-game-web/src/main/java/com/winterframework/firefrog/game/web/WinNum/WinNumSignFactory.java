/**   
* @Title: WinNumSignFactory.java 
* @Package com.winterframework.firefrog.game.web.WinNum 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-9-3 下午4:21:24 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.WinNum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @ClassName: WinNumSignFactory 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-9-3 下午4:21:24 
*  
*/
public class WinNumSignFactory {

	private static Map<Long, IWinNumSign> map = new HashMap<Long, IWinNumSign>();
	static {
		//ssc
		map.put(99101l, new WinNumSignSsc());
		map.put(99102l, new WinNumSignSsc());
		map.put(99103l, new WinNumSignSsc());
		map.put(99104l, new WinNumSignSsc());
		map.put(99105l, new WinNumSignSsc());
		map.put(99106l, new WinNumSignSsc());
		//ffc mmc
		map.put(99111l, new WinNumSignSsc());
		map.put(99114l, new WinNumSignSsc());
		map.put(99112l, new WinNumSignSsc());
		//ssl
		map.put(99107l, new WinNumSignSsl());
		//3d
		map.put(99108l, new WinNumSign3D());
		//p5
		map.put(99109l, new WinNumSignP5());
		//kl8
		map.put(99201l, new WinNumSignKl8());
		//115
		map.put(99301l, new WinNumSign115());
		map.put(99302l, new WinNumSign115());
		map.put(99303l, new WinNumSign115());
		map.put(99304l, new WinNumSign115());
		map.put(99305l, new WinNumSign115());
		map.put(99306l, new WinNumSign115());
		//ssq
		map.put(99401l, new WinNumSignSsq());
		map.put(99501l, new WinNumSignK3());
		map.put(99502l, new WinNumSignK3());
	}

	public static IWinNumSign getSignNumInstance(Long lotteryId, List<String> numberRecordList) throws Exception {
		IWinNumSign winNumSign = map.get(lotteryId);
		winNumSign.setNumberRecordList(numberRecordList);
		return winNumSign;
	}
}
