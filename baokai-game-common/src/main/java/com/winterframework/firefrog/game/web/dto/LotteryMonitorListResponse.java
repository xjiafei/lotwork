package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: LotteryMonitorListResponse 
* @Description: 5.5.31	彩种奖期监控列表(OMI031) 响应
* @author Richard
* @date 2013-10-12 上午10:29:36 
*
 */
public class LotteryMonitorListResponse implements Serializable {

	private static final long serialVersionUID = -8232564903321034018L;

	private List<LotteryIssueStruc> List ;

	public List<LotteryIssueStruc> getList() {
		return List;
	}

	public void setList(List<LotteryIssueStruc> list) {
		List = list;
	}
	
}
