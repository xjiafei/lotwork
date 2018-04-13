package com.winterframework.firefrog.game.web.dto;

import java.util.List;

/** 
* @ClassName: GameWarnOrderQueryResponse 
* @Description: 风险记录查询response
* @author Alan
* @date 2013-10-15 下午2:04:45 
*  
*/
public class GameWarnOrderQueryResponse {

	private List<RiskOrderStruc> riskOrderStruc;
	
	private QuerySeriesConfigRiskResponse riskConfigStruc;

	public List<RiskOrderStruc> getRiskOrderStruc() {
		return riskOrderStruc;
	}

	public void setRiskOrderStruc(List<RiskOrderStruc> riskOrderStruc) {
		this.riskOrderStruc = riskOrderStruc;
	}

	public QuerySeriesConfigRiskResponse getRiskConfigStruc() {
		return riskConfigStruc;
	}

	public void setRiskConfigStruc(QuerySeriesConfigRiskResponse riskConfigStruc) {
		this.riskConfigStruc = riskConfigStruc;
	}
}
