package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;

 
/**
 * 走势图--遗漏
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月17日
 */
public class GameTrendJbylResponse implements Serializable {
  
	private static final long serialVersionUID = -3856474376027825865L;
	private List<GameTrendJbyl> trendJbylList;
	public List<GameTrendJbyl> getTrendJbylList() {
		return trendJbylList;
	}
	public void setTrendJbylList(List<GameTrendJbyl> trendJbylList) {
		this.trendJbylList = trendJbylList;
	}
	 
	
 
}
