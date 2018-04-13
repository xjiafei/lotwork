package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class TaskDetailResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8404871538374170252L;
	private String beginissue;//	奖期
	private String begintime;//	投注时间
	private Integer issuecount;//	追号期数
	private Integer finishedcount;//	完成期数
	private Float finishedmoney;//	已投注金额
	private Float totalmoney;//	总金额
	private Integer traceStop;//是否追中即停
	private Double bonus;//	中奖金额
	private Long lotteryId;
	private String taskNo;
	
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	private List<TaskDetailProject> projectList;
	private List<TaskDetailTasks> tasks;
	
	public List<TaskDetailProject> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<TaskDetailProject> projectList) {
		this.projectList = projectList;
	}
	public List<TaskDetailTasks> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskDetailTasks> tasks) {
		this.tasks = tasks;
	}
	public String getBeginissue() {
		return beginissue;
	}
	public void setBeginissue(String beginissue) {
		this.beginissue = beginissue;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
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
	public Integer getTraceStop() {
		return traceStop;
	}
	public void setTraceStop(Integer traceStop) {
		this.traceStop = traceStop;
	}
	public Double getBonus() {
		return bonus;
	}
	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	

}
