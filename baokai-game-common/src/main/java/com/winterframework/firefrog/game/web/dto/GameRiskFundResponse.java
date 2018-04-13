package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName GameRiskResponse 
* @Description 审核系统请求资金返回
* @author  hugh
* @date 2014年4月23日 下午1:43:41 
*  
*/
public class GameRiskFundResponse implements Serializable {

	private static final long serialVersionUID = 7801778799498431320L;

	private List<GameRiskFundStruc> strucList ;

	public List<GameRiskFundStruc> getStrucList() {
		return strucList;
	}

	public void setStrucList(List<GameRiskFundStruc> strucList) {
		this.strucList = strucList;
	}
	
}
