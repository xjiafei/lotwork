package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class LotteryListStruc implements Serializable {

	private static final long serialVersionUID = 8061914838189897719L;

	private Integer lotteryTypeCode;
	private Integer lotterySeriesCode;
	private Long lotteryid;
	private String lotteryName;
	private Float miniLotteryProfit;
	private String lotteryHelpDes;
	private Integer status;
	private Long createTime;
	private Long updateTime;
	//彩种操作项状态
	//0 奖金规则，1 奖金组，2 封锁，3 变价，4 投注限制，5 玩法描述，6 销售状态，7 参数设置
	private int operationChangeStatus[];

	//彩种操作项
	//0 奖金规则，1 奖金组，2 封锁，3 变价，4 投注限制，5 玩法描述，6 销售状态，7 参数设置
	private int operationLock[];
	
	private String operator;

	public LotteryListStruc() {

	}

	public Integer getLotteryTypeCode() {
		return lotteryTypeCode;
	}

	public void setLotteryTypeCode(Integer lotteryTypeCode) {
		this.lotteryTypeCode = lotteryTypeCode;
	}

	public Integer getLotterySeriesCode() {
		return lotterySeriesCode;
	}

	public void setLotterySeriesCode(Integer lotterySeriesCode) {
		this.lotterySeriesCode = lotterySeriesCode;
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public Float getMiniLotteryProfit() {
		return miniLotteryProfit;
	}

	public void setMiniLotteryProfit(Float miniLotteryProfit) {
		this.miniLotteryProfit = miniLotteryProfit;
	}

	public String getLotteryHelpDes() {
		return lotteryHelpDes;
	}

	public void setLotteryHelpDes(String lotteryHelpDes) {
		this.lotteryHelpDes = lotteryHelpDes;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public int[] getOperationChangeStatus() {
		return operationChangeStatus;
	}

	public void setOperationChangeStatus(int[] operationChangeStatus) {
		this.operationChangeStatus = operationChangeStatus;
	}

	public int[] getOperationLock() {
		return operationLock;
	}

	public void setOperationLock(int[] operationLock) {
		this.operationLock = operationLock;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
