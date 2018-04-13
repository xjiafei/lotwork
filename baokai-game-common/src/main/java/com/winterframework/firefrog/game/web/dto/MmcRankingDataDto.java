package com.winterframework.firefrog.game.web.dto;

import java.util.List;

public class MmcRankingDataDto {
	private MmcRankingPrizeDto prize;
	private MmcRankingMyPrizeDto myprize;
	private Long user;
	private List<MmcRankingDto> list;
	public MmcRankingPrizeDto getPrize() {
		return prize;
	}
	public void setPrize(MmcRankingPrizeDto prize) {
		this.prize = prize;
	}
	public Long getUser() {
		return user;
	}
	public void setUser(Long user) {
		this.user = user;
	}
	public List<MmcRankingDto> getList() {
		return list;
	}
	public void setList(List<MmcRankingDto> list) {
		this.list = list;
	}
	public MmcRankingMyPrizeDto getMyprize() {
		return myprize;
	}
	public void setMyprize(MmcRankingMyPrizeDto myprize) {
		this.myprize = myprize;
	}
	
	
}
