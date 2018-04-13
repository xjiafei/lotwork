package com.winterframework.firefrog.game.web.dto;

import java.util.List;

/** 
* @ClassName: GamePlanOperationsResponse 
* @Description: 追号记录运营后台repsonse
* @author Alan
* @date 2013-10-12 上午10:55:43 
*  
*/
public class GamePlanOperationsResponse {

	private List<PlansStruc> planStruc;

	public List<PlansStruc> getPlanStruc() {
		return planStruc;
	}

	public void setPlanStruc(List<PlansStruc> planStruc) {
		this.planStruc = planStruc;
	}

}
