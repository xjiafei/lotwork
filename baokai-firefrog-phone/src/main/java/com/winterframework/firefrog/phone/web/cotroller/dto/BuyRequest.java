package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;



public class BuyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3261958856372898252L;
	private Long lotteryId;//	彩种id
	private Long issue;//	投注开始奖期
	
	private Integer traceIstrace;//是否追号
	private Integer traceStop;//	是否追中即停
	
	private String traceIssues;//	追号期数
	private String traceTimes; //倍数
	
	private Float money;//s	投注金额
	private Long saleTime;
	private Long userIp;
	private List<Projects> list;
	private Integer channelId;
	private String channelVersion;
	//封锁变价时是否是第一次提交0：不是，1：是
	private Integer isFirstSubmit;
	
	//20150421 add
	private List<GameSlipResponseDTO> slipResonseDTOList;
	
	
	public List<GameSlipResponseDTO> getSlipResonseDTOList() {
		return slipResonseDTOList;
	}
	public void setSlipResonseDTOList(List<GameSlipResponseDTO> slipResonseDTOList) {
		this.slipResonseDTOList = slipResonseDTOList;
	}
	public Integer getIsFirstSubmit() {
		return isFirstSubmit;
	}
	public void setIsFirstSubmit(Integer isFirstSubmit) {
		this.isFirstSubmit = isFirstSubmit;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelVersion() {
		return channelVersion;
	}
	public void setChannelVersion(String channelVersion) {
		this.channelVersion = channelVersion;
	}
	public String getTraceIssues() {
		return traceIssues;
	}
	public String getTraceTimes() {
		return traceTimes;
	}
	public void setTraceTimes(String traceTimes) {
		this.traceTimes = traceTimes;
	}
	public void setTraceIssues(String traceIssues) {
		this.traceIssues = traceIssues;
	}
	public Long getUserIp() {
		return userIp;
	}
	public void setUserIp(Long userIp) {
		this.userIp = userIp;
	}
	public Long getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(Long saleTime) {
		this.saleTime = saleTime;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Long getIssue() {
		return issue;
	}
	public void setIssue(Long issue) {
		this.issue = issue;
	}
	public Integer getTraceIstrace() {
		return traceIstrace;
	}
	public void setTraceIstrace(Integer traceIstrace) {
		this.traceIstrace = traceIstrace;
	}
	public Integer getTraceStop() {
		return traceStop;
	}
	public void setTraceStop(Integer traceStop) {
		this.traceStop = traceStop;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public List<Projects> getList() {
		return list;
	}
	public void setList(List<Projects> list) {
		this.list = list;
	}
	
	

}
