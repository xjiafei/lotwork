/**   
* @Title: Bank.java 
* @Package com.winterframework.firefrog.fund.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Tod  
* @date 2013-6-28 下午3:21:31 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.entity;

import com.winterframework.firefrog.user.entity.BaseUser;

/** 
* @ClassName: Fund 
* @Description: 资金实体 
* @author Tod 
* @date 2013-6-28 下午3:20:58 
*  
*/
public class UserFund {

	private Long id;

	private Long bal;

	private Long disableAmt;

	private BaseUser user;

	private Long frozenAmt;

	private Long teamBal;
	public Long getId() {
		return id;
	}

	public Long getTeamBal() {
		return teamBal;
	}

	public void setTeamBal(Long teamBal) {
		this.teamBal = teamBal;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBal() {
		return bal;
	}

	public void setBal(Long bal) {
		this.bal = bal;
	}

	public Long getDisableAmt() {
		return disableAmt;
	}

	public void setDisableAmt(Long disableAmt) {
		this.disableAmt = disableAmt;
	}

	public BaseUser getUser() {
		return user;
	}

	public void setUser(BaseUser user) {
		this.user = user;
	}

	public Long getFrozenAmt() {
		return frozenAmt;
	}

	public void setFrozenAmt(Long frozenAmt) {
		this.frozenAmt = frozenAmt;
	}

}
