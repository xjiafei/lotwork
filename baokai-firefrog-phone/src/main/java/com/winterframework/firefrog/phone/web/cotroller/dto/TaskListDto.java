package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class TaskListDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7267324148969049417L;
	private String taskid;
	private Long lotteryid;//	彩种id
	private String begintime;//	追号时间
	private String beginissue;//	开始期数
	private Integer issuecount;//	追号期数
	private Integer finishedcount;//	完成期数
	private Integer status;//	追号状态
	private Float finishedmoney;//	已投注金额
	private Float totalmoney;//	总金额

	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getBeginissue() {
		return beginissue;
	}
	public void setBeginissue(String beginissue) {
		this.beginissue = beginissue;
	}
	public Integer getIssuecount() {
		return issuecount;
	}
	public void setIssuecount(Integer issuecount) {
		this.issuecount = issuecount;
	}
	public Integer getFinishedcount() {
		return finishedcount;
	}
	public void setFinishedcount(Integer finishedcount) {
		this.finishedcount = finishedcount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Float getFinishedmoney() {
		return finishedmoney;
	}
	public void setFinishedmoney(Float finishedmoney) {
		this.finishedmoney = finishedmoney;
	}
	public Float getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(Float totalmoney) {
		this.totalmoney = totalmoney;
	}

}
