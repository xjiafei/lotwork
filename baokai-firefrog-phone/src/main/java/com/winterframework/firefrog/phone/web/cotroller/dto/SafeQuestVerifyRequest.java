package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class SafeQuestVerifyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2494197070849752668L;
	private List<SafeQuestVerify> quests;

	public List<SafeQuestVerify> getQuests() {
		return quests;
	}

	public void setQuests(List<SafeQuestVerify> quests) {
		this.quests = quests;
	}
	
	
}
