package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;
 
/**
 * 用户奖金列表基本结构 
 * @ClassName
 * @Description
 * @author ibm
 * 2014年11月18日
 */
public class GameUserAwardStruc implements Serializable {
  
	private static final long serialVersionUID = -43383546694005049L; 
	
	/** ID */ 
	private Long id; 
	/** 彩种ID */ 
	private Long lotteryId; 
	/** 玩法编码 */ 
	private String betTypeCode;	
	/** 系统奖金组ID */
	private Long awardGroupId;	
	/** 状态 */
	private Integer status;
	/** 返点值 */
	private Long retValue; 
	/** 用户ID */
	private Long userId;
	/** 创建时间 */	 
	private Date createTime;
	/** 修改时间 */
	private Date updateTime; 
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getBetTypeCode() {
		return betTypeCode;
	}
	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}
	public Long getAwardGroupId() {
		return awardGroupId;
	}
	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getRetValue() {
		return retValue;
	}
	public void setRetValue(Long retValue) {
		this.retValue = retValue;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	} 
	
 
}
