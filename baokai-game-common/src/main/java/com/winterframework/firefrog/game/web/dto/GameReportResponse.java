package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;
/**
 * 
* @ClassName: GameReportResponse 
* @Description:游戏报表响应
* @author Richard
* @date 2014-2-21 上午11:01:28 
*
 */
public class GameReportResponse implements Serializable {

	private static final long serialVersionUID = 9114121862818863617L;
	
	private List<GameReportStruc> reportList;

	public List<GameReportStruc> getReportList() {
		return reportList;
	}

	public void setReportList(List<GameReportStruc> reportList) {
		this.reportList = reportList;
	}
	
}
