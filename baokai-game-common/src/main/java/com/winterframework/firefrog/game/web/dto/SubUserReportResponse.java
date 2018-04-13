package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: UserCenterReportRequest 
* @Description: 用户中心盈亏报表响应
* @author david 
* @date 2013-9-17 下午2:54:07 
*  
*/
public class SubUserReportResponse implements Serializable {

	private static final long serialVersionUID = 6933950873653289409L;

	private List<UserCenterReportStruc> userCenterReportStrucs;
	private String userChain;
	private Integer hasNextUser;
	private List<ChainUserStruc> chainUsers;

	public List<ChainUserStruc> getChainUsers() {
		return chainUsers;
	}

	public void setChainUsers(List<ChainUserStruc> chainUsers) {
		this.chainUsers = chainUsers;
	}

	public List<UserCenterReportStruc> getUserCenterReportStrucs() {
		return userCenterReportStrucs;
	}

	public void setUserCenterReportStrucs(List<UserCenterReportStruc> userCenterReportStrucs) {
		this.userCenterReportStrucs = userCenterReportStrucs;
	}

	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}

	public Integer getHasNextUser() {
		return hasNextUser;
	}

	public void setHasNextUser(Integer hasNextUser) {
		this.hasNextUser = hasNextUser;
	}
}
