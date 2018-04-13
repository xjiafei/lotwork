package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;

public class GameAwardTheoryBonus implements Serializable {

	private static final long serialVersionUID = 4428226950880289796L;

	private Long lotteryId;
	private String lotteryName;
	
	private List<AwardDTO> awardList;
	
	public GameAwardTheoryBonus(){
		
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getLotteryName() {
		lotteryName = GameAwardNameUtil.lotteryName(lotteryId);
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public List<AwardDTO> getAwardList() {
		return awardList;
	}

	public void setAwardList(List<AwardDTO> awardList) {
		this.awardList = awardList;
	}
	
	
}
