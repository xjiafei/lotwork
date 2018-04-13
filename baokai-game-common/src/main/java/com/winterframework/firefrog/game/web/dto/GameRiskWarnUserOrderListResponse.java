package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName GameRiskIssueListQueryRequest 
* @Description 审核系统 审核用户订单查询返回
* @author  hugh
* @date 2014年4月9日 下午3:16:29 
*  
*/
public class GameRiskWarnUserOrderListResponse implements Serializable {

	private static final long serialVersionUID = -2744311402534011477L;
		
	
	private GameRiskIssueStruc riskIssueStruc;
	
	private QuerySeriesConfigRiskResponse riskConfigStruc;
	
	private List<GameRiskWarnUserOrderStruc> warnUserOrderStrucs;

	public List<GameRiskWarnUserOrderStruc> getWarnUserOrderStrucs() {
		return warnUserOrderStrucs;
	}

	public void setWarnUserOrderStrucs(List<GameRiskWarnUserOrderStruc> warnUserOrderStrucs) {
		this.warnUserOrderStrucs = warnUserOrderStrucs;
	}

	public GameRiskIssueStruc getRiskIssueStruc() {
		return riskIssueStruc;
	}

	public void setRiskIssueStruc(GameRiskIssueStruc riskIssueStruc) {
		this.riskIssueStruc = riskIssueStruc;
	}

	public QuerySeriesConfigRiskResponse getRiskConfigStruc() {
		return riskConfigStruc;
	}

	public void setRiskConfigStruc(QuerySeriesConfigRiskResponse riskConfigStruc) {
		this.riskConfigStruc = riskConfigStruc;
	}






}
