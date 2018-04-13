package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;


/** 
* @ClassName UserInfoResponse 
* @Description 用户信息
* @author  david
* @date 
*  
*/
public class UserInfoResponse implements Serializable {

	private static final long serialVersionUID = -2293300901721783083L;
	private Long userId;
	private String userName;
	private Integer vipLvl;
	private Integer awardRetStatus;	//奖金返点状态
	private Integer superPairStatus;	//超级对子状态
	private Integer lhcStatus;	//超级对子状态
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getVipLvl() {
		return vipLvl;
	}
	public void setVipLvl(Integer vipLvl) {
		this.vipLvl = vipLvl;
	}
	public Integer getAwardRetStatus() {
		return awardRetStatus;
	}
	public void setAwardRetStatus(Integer awardRetStatus) {
		this.awardRetStatus = awardRetStatus;
	}
	public Integer getSuperPairStatus() {
		return superPairStatus;
	}
	public void setSuperPairStatus(Integer superPairStatus) {
		this.superPairStatus = superPairStatus;
	}
	public Integer getLhcStatus() {
		return lhcStatus;
	}
	public void setLhcStatus(Integer lhcStatus) {
		this.lhcStatus = lhcStatus;
	}
	
}
