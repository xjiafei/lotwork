package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 六合彩玩法說明
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月15日
 */
public class LhcGameTips implements Serializable {
	/** 玩法 */
	private String name;
	private String rule;
	private String example;
	private List<LhcGameTips> childs;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getExample() {
		return example;
	}
	public void setExample(String example) {
		this.example = example;
	}
	public List<LhcGameTips> getChilds() {
		return childs;
	}
	public void setChilds(List<LhcGameTips> childs) {
		this.childs = childs;
	}
	
	
}
