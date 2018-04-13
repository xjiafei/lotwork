package com.winterframework.firefrog.game.web.dto;

import java.util.List;

/** 
* @ClassName: QueryRedEnvelopeResponse 
* @Description: 红包查询repsonse
* @author david
* @date 
*  
*/
public class QueryRedEnvelopeResponse {

	private Long  totalBetAmount;//当日投注总金额
	
	private Long  redEnvelopeAmount;//红包总金额
	
	private List<RedEnvelopeStruc> redEnvelopeStruc;

	public List<RedEnvelopeStruc> getRedEnvelopeStruc() {
		return redEnvelopeStruc;
	}

	public void setRedEnvelopeStruc(List<RedEnvelopeStruc> redEnvelopeStruc) {
		this.redEnvelopeStruc = redEnvelopeStruc;
	}

	public Long getTotalBetAmount() {
		return totalBetAmount;
	}

	public void setTotalBetAmount(Long totalBetAmount) {
		this.totalBetAmount = totalBetAmount;
	}

	public Long getRedEnvelopeAmount() {
		return redEnvelopeAmount;
	}

	public void setRedEnvelopeAmount(Long redEnvelopeAmount) {
		this.redEnvelopeAmount = redEnvelopeAmount;
	}
}
