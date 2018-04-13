package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 5.5.34	彩种奖期监控日志(OMI034) 响应
* @ClassName: LotteryIssueMonitorLogRequest 
* @Description:
* @author Richard
* @date 2013-10-15 上午9:17:34 
*
 */
public class LotteryIssueMonitorLogResponse implements Serializable {

	private static final long serialVersionUID = -414392698989122988L;

	private List<IssueWarnLogStruc> list;

	public List<IssueWarnLogStruc> getList() {
		return list;
	}

	public void setList(List<IssueWarnLogStruc> list) {
		this.list = list;
	}
	
}
