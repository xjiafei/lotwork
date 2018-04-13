package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.web.dto.GamePlanParm;

public class GameOrderResponseDTO implements Serializable {
	
	private static final long serialVersionUID = 4344908675044605193L;
	private Double totalAmount;
	private String lotteryName;
	private List<GameSlipResponseDTO> slipResonseDTOList;
	private Long gameIssueCode;
	private String webIssueCode;
	private List<GamePlanParm> gamePlanParm;
	private Integer isTrace;// 是否追号 1追号，默认0
	private Integer stopMode;
	private Integer stopParms;
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public Long getGameIssueCode() {
		return gameIssueCode;
	}
	public void setGameIssueCode(Long gameIssueCode) {
		this.gameIssueCode = gameIssueCode;
	}
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	public List<GameSlipResponseDTO> getSlipResonseDTOList() {
		return slipResonseDTOList;
	}
	public void setSlipResonseDTOList(List<GameSlipResponseDTO> slipResonseDTOList) {
		this.slipResonseDTOList = slipResonseDTOList;
	}
	public List<GamePlanParm> getGamePlanParm() {
		return gamePlanParm;
	}
	public void setGamePlanParm(List<GamePlanParm> gamePlanParm) {
		this.gamePlanParm = gamePlanParm;
	}
	public Integer getIsTrace() {
		return isTrace;
	}
	public void setIsTrace(Integer isTrace) {
		this.isTrace = isTrace;
	}
	public Integer getStopMode() {
		return stopMode;
	}
	public void setStopMode(Integer stopMode) {
		this.stopMode = stopMode;
	}
	public Integer getStopParms() {
		return stopParms;
	}
	public void setStopParms(Integer stopParms) {
		this.stopParms = stopParms;
	}

}
