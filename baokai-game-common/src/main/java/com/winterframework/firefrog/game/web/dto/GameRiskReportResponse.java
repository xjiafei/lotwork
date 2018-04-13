package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameReportIssue;

/** 
* @ClassName GameRiskReportResponse 
* @Description 报表 
* @author  hugh
* @date 2014年4月28日 下午3:43:11 
*  
*/
public class GameRiskReportResponse implements Serializable {

	private static final long serialVersionUID = 7801778799498431320L;

	private List<GameReportIssue> reports ;

	public List<GameReportIssue> getReports() {
		return reports;
	}

	public void setReports(List<GameReportIssue> reports) {
		this.reports = reports;
	}
	
	
}
