package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: UserAwardListByBetStruc 
* @Description: 用户投注奖金组列表基本结构 
* @author Denny 
* @date 2013-9-17 下午5:36:04 
*  
*/
public class UserAwardListByBetStruc implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1672923623005460368L;
	/** 彩系编码 */
	private Integer lotterySeriesCode;
	/** 彩种ID */
	private Long lotteryId;
	/** 奖金组ID */
	private Long awardGroupId;
	/** 奖金组名称 */
	private String awardName;
	/** UI彩种名称 */
	private String uiLotteryName;
	private Integer awardRetStatus;	//奖金返点状态
	private Boolean isMaxAward;//是否為最高獎金組
	

	public Integer getLotterySeriesCode() {
		return lotterySeriesCode;
	}

	public void setLotterySeriesCode(Integer lotterySeriesCode) {
		this.lotterySeriesCode = lotterySeriesCode;
	}

	public Long getAwardGroupId() {
		return awardGroupId;
	}

	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getUiLotteryName() {
		return uiLotteryName;
	}

	public void setUiLotteryName(String uiLotteryName) {
		this.uiLotteryName = uiLotteryName;
	}

	public Integer getAwardRetStatus() {
		return awardRetStatus;
	}

	public void setAwardRetStatus(Integer awardRetStatus) {
		this.awardRetStatus = awardRetStatus;
	}

	/**
	 * @return the isMaxAward
	 */
	public Boolean getIsMaxAward() {
		return isMaxAward;
	}

	/**
	 * @param isMaxAward the isMaxAward to set
	 */
	public void setIsMaxAward(Boolean isMaxAward) {
		this.isMaxAward = isMaxAward;
	}
	
}
