package com.winterframework.firefrog.phone.web.validate.business;

import java.util.HashMap;
import java.util.Map;

/** 
* @ClassName: HZandKDRangeMapConfig 
* @Description: 和值和跨度的范围映射
* @author david
* @date 2013-8-2 下午5:40:48 
*  
*/
public class HZandKDRangeMapConfig {
	private Map<String, String> HZandKDRangeMap = new HashMap<String, String>();

	public Map<String, String> getHZandKDRangeMap() {
		return HZandKDRangeMap;
	}

	public void setHZandKDRangeMap(Map<String, String> hZandKDRangeMap) {
		HZandKDRangeMap = hZandKDRangeMap;
	}

}
