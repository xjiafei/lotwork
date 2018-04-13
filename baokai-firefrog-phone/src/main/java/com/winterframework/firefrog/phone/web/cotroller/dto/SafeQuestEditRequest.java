package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class SafeQuestEditRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6713000755053780792L;
	private List<SafeQuestEdit> quests;

	public List<SafeQuestEdit> getQuests() {
		return quests;
	}

	public void setQuests(List<SafeQuestEdit> quests) {
		this.quests = quests;
	}
}
