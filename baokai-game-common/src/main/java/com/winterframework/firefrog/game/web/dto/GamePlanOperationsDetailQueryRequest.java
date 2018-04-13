package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GamePlanOperationsDetailQueryRequest 
* @Description: 追号后台运营详细记录request 
* @author Alan
* @date 2013-10-14 下午4:07:41 
*  
*/
public class GamePlanOperationsDetailQueryRequest implements Serializable{

	private static final long serialVersionUID = 2484883608333857701L;

	/**追号ID*/
	private Long planid;
	/**追号编号*/
	private String planCode;
	
	public Long getPlanid() {
		return planid;
	}
	public void setPlanid(Long planid) {
		this.planid = planid;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

}
