package com.winterframework.firefrog.active.web.dto;

import java.io.Serializable;
import java.util.List;


/** 
* @ClassName ActivityUserAwardRequest 
* @Description 用户获奖记录
* @author  hugh
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class ActivityRequest implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1017310155712873878L;
	private Long userId;
	private String userToken;
	private List<String> betTypeCodes;
	
	public List<String> getBetTypeCodes() {
		return betTypeCodes;
	}

	public void setBetTypeCodes(List<String> betTypeCodes) {
		this.betTypeCodes = betTypeCodes;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	
	

}
