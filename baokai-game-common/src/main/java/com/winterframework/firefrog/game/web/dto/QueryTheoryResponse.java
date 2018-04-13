package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class QueryTheoryResponse implements Serializable {

	private static final long serialVersionUID = -6983398443605828516L;
	
	private Long lotteryId;
	
	/**公司最小留水*/
	private Long miniLotteryProfit;
	
	private List<AwardBonusStruc> awardList;
	
	public QueryTheoryResponse(){
		
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public List<AwardBonusStruc> getAwardList() {
		return awardList;
	}

	public void setAwardList(List<AwardBonusStruc> awardList) {
		this.awardList = awardList;
	}

	public Long getMiniLotteryProfit() {
		return miniLotteryProfit;
	}

	public void setMiniLotteryProfit(Long miniLotteryProfit) {
		this.miniLotteryProfit = miniLotteryProfit;
	}
	
	
}
