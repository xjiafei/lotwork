package com.winterframework.firefrog.game.entity;

import java.io.Serializable;

/** 
* @ClassName: UserBetAwardGroupEntity 
* @Description: 用户投注奖金组实体 
* @author Denny 
* @date 2013-9-18 下午3:48:09 
*  
*/
public class UserBetAwardGroupEntity implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5976565898706240836L;
	/**
	 * 
	 */
	private Integer lotterySeriesCode;
	private Long awardGroupid;
	private String awardName;
	private Long lotteryid;
	private Integer awardRetStatus;	//奖金返点开关
	
	private Integer sysAwardGroupId;
	
	private Boolean isMaxAward;//是否為最高獎金組
	
	public Integer getLotterySeriesCode() {
		return lotterySeriesCode;
	}
	public void setLotterySeriesCode(Integer lotterySeriesCode) {
		this.lotterySeriesCode = lotterySeriesCode;
	}
	public Long getAwardGroupid() {
		return awardGroupid;
	}
	public void setAwardGroupid(Long awardGroupid) {
		this.awardGroupid = awardGroupid;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public Integer getAwardRetStatus() {
		return awardRetStatus;
	}
	public void setAwardRetStatus(Integer awardRetStatus) {
		this.awardRetStatus = awardRetStatus;
	}
	/**
	 * @return the sysAwardGroupId
	 */
	public Integer getSysAwardGroupId() {
		return sysAwardGroupId;
	}
	/**
	 * @param sysAwardGroupId the sysAwardGroupId to set
	 */
	public void setSysAwardGroupId(Integer sysAwardGroupId) {
		this.sysAwardGroupId = sysAwardGroupId;
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
