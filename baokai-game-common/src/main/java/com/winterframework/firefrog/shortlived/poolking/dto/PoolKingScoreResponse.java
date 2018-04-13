package com.winterframework.firefrog.shortlived.poolking.dto;

import java.util.List;

public class PoolKingScoreResponse {

	private PoolKingScore userScore;

	private List<PoolKingScore> scores;

	public PoolKingScore getUserScore() {
		return userScore;
	}

	public void setUserScore(PoolKingScore userScore) {
		this.userScore = userScore;
	}

	public List<PoolKingScore> getScores() {
		return scores;
	}

	public void setScores(List<PoolKingScore> scores) {
		this.scores = scores;
	}

}
