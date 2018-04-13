package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GamePlanQueryResponse implements Serializable {

	private static final long serialVersionUID = -2850006868975707610L;

	/** 追号 */
	private List<PlansStruc> plansStruc;

	public List<PlansStruc> getPlansStruc() {
		return plansStruc;
	}

	public void setPlansStruc(List<PlansStruc> plansStruc) {
		this.plansStruc = plansStruc;
	}
}
