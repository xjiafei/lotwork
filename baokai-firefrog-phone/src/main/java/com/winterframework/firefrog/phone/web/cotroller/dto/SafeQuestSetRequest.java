package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class SafeQuestSetRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5207868059894879697L;
	
	private List<SafeQuestSet> quests;

	public List<SafeQuestSet> getQuests() {
		return quests;
	}

	public void setQuests(List<SafeQuestSet> quests) {
		this.quests = quests;
	}

}
