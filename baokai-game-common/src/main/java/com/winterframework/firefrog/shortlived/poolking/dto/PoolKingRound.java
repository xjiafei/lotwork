package com.winterframework.firefrog.shortlived.poolking.dto;

import java.util.Date;
import java.util.List;

public class PoolKingRound {

	private Date startDate;

	private Date endDate;

	private List<PoolKingRoundItem> roundItems;
	
	private List<PoolKingScore> scores;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<PoolKingRoundItem> getRoundItems() {
		return roundItems;
	}

	public void setRoundItems(List<PoolKingRoundItem> roundItems) {
		this.roundItems = roundItems;
	}

	public List<PoolKingScore> getScores() {
		return scores;
	}

	public void setScores(List<PoolKingScore> scores) {
		this.scores = scores;
	}

}
