package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class DailyActivityResponse implements Serializable{

	private static final long serialVersionUID = -254861760419529196L;

	private List<DailyActivityRewardStruc> strucs;

	public List<DailyActivityRewardStruc> getStrucs() {
		return strucs;
	}

	public void setStrucs(List<DailyActivityRewardStruc> strucs) {
		this.strucs = strucs;
	}
}
