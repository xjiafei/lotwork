package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;
/**
 * 
* @ClassName: GameRiskTransactionReportResponse 
* @Description:游戏交易报表响应
* @author hugh
* @date 2014-4-24 上午11:01:28 
*
 */
public class GameRiskTransactionReportResponse implements Serializable {

	private static final long serialVersionUID = 9114121862818863617L;
	
	private List<GameRiskTransactionReportStruc> reportList;

	public List<GameRiskTransactionReportStruc> getReportList() {
		return reportList;
	}

	public void setReportList(List<GameRiskTransactionReportStruc> reportList) {
		this.reportList = reportList;
	}
	
}
