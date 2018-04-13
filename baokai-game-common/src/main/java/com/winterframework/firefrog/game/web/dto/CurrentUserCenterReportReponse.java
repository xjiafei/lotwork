package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: UserCenterReportComplexReponse 
* @Description: 用户中心盈亏报表簡單条件响应
* @author david 
* @date 2013-9-17 下午2:54:07 
*  
*/
public class CurrentUserCenterReportReponse implements Serializable {

	private static final long serialVersionUID = 6933950873653289409L;

	private UserCenterReportStruc userCenterReportStrucs;

	public UserCenterReportStruc getUserCenterReportStrucs() {
		return userCenterReportStrucs;
	}

	public void setUserCenterReportStrucs(UserCenterReportStruc userCenterReportStrucs) {
		this.userCenterReportStrucs = userCenterReportStrucs;
	}


}
