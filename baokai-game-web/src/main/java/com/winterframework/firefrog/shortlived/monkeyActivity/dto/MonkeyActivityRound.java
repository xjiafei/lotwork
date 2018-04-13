package com.winterframework.firefrog.shortlived.monkeyActivity.dto;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScore;

public class MonkeyActivityRound {

	private Date startDate;
	private Date endDate;
	private List<MonkeyActivityRoundItem> roundItems;
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

	public List<MonkeyActivityRoundItem> getRoundItems() {
		return roundItems;
	}

	public void setRoundItems(List<MonkeyActivityRoundItem> roundItems) {
		this.roundItems = roundItems;
	}

	public List<PoolKingScore> getScores() {
		return scores;
	}

	public void setScores(List<PoolKingScore> scores) {
		this.scores = scores;
	}
}
