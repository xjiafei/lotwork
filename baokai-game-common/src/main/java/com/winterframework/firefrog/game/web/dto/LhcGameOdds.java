package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 六合彩趣味玩法賠率
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月15日
 */
public class LhcGameOdds implements Serializable {
	/** 玩法 */
	private String name;
	private List<LhcGameOddsDetail> childs;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<LhcGameOddsDetail> getChilds() {
		return childs;
	}
	public void setChilds(List<LhcGameOddsDetail> childs) {
		this.childs = childs;
	}
	
}
