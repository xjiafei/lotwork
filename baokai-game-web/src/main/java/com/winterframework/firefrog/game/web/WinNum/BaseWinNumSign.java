/**   
* @Title: BaseWinNumSign.java 
* @Package com.winterframework.firefrog.game.web.WinNum 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-9-2 下午4:26:22 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.WinNum;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** 
* @ClassName: BaseWinNumSign 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-9-2 下午4:26:22 
*  
*/
public class BaseWinNumSign {
	protected List<String> numberRecordList;

	public List<String> getNumberRecordList() {
		return numberRecordList;
	}

	public void setNumberRecordList(List<String> numberRecordList) {
		this.numberRecordList = numberRecordList;
	}

	/** 
	* @Title: getKd 
	* @Description: 取跨度值 
	*/
	public static int getKd(Set<Integer> numberRecordSet) {
		List<Integer> list = new ArrayList<Integer>(numberRecordSet);
		int max = 0;
		int min = 0;
		for (int i = 0; i < list.size(); i++) {
			max = Math.max(max, list.get(i));
		}

		min = max;
		for (int i = 0; i < list.size(); i++) {
			min = Math.min(min, list.get(i));
		}
		return max - min;
	}

	/** 
	* @Title: explode 
	* @Description: 将中奖号码String转换为int型的List 
	*/
	public static List<String> explode(String s) {
		List<String> list = new ArrayList<String>();
		if (s != null) {
			if (s.contains(",")) {
				String[] recordArray = s.split(",");
				for (String record : recordArray) {
					list.add(record);
				}

			} else {	
				//list.add(s);
				for (int i = 0; i < s.length(); i++) {
					list.add(String.valueOf(s.charAt(i)));
				}
			}
		}
		return list;
	}

	public static int getContainNum(String bitDetail, List<String> signNumberList) {
		int num = 0;
		for (String signNumber : signNumberList) {
			if (signNumber.equals(bitDetail)) {
				num++;
			}
		}
		return num;
	}

}
