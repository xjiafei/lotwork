package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: UserCenterReportComplexReponse 
* @Description: 用户中心盈亏报表复杂条件响应
* @author david 
* @date 2013-9-17 下午2:54:07 
*  
*/
public class UserCenterReportComplexReponse implements Serializable {

	private static final long serialVersionUID = 6933950873653289409L;

	private List<UserCenterReportStruc> userCenterReportStrucs;

	public List<UserCenterReportStruc> getUserCenterReportStrucs() {
		return userCenterReportStrucs;
	}

	public void setUserCenterReportStrucs(List<UserCenterReportStruc> userCenterReportStrucs) {
		this.userCenterReportStrucs = userCenterReportStrucs;
	}

}
