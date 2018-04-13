package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class TaskDetailTasks  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6347696545077688142L;

	private String taskdetailid;//追号单详情id
	private String issue;//	追号奖期
	private Integer multiple;//	倍数
	private Float money;//	投注金额
	private String opencode;//	开奖号码
//	private String modes;//	模式
	private Integer status;//	追号状态
//	private String time;//	投注时间
	private Double bonus;//	中奖金额
	private Integer cancancel;//	是否可以终止
	private String taskDetailNo;
	private List<GameDetailProject> list;
	public List<GameDetailProject> getList() {
		return list;
	}
	public void setList(List<GameDetailProject> list) {
		this.list = list;
	}
	public String getTaskDetailNo() {
		return taskDetailNo;
	}
	public void setTaskDetailNo(String taskDetailNo) {
		this.taskDetailNo = taskDetailNo;
	}
	private Long issueCode;
	
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public String getTaskdetailid() {
		return taskdetailid;
	}
	public void setTaskdetailid(String taskdetailid) {
		this.taskdetailid = taskdetailid;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public String getOpencode() {
		return opencode;
	}
	public void setOpencode(String opencode) {
		this.opencode = opencode;
	}
//	public String getModes() {
//		return modes;
//	}
//	public void setModes(String modes) {
//		this.modes = modes;
//	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
//	public String getTime() {
//		return time;
//	}
//	public void setTime(String time) {
//		this.time = time;
//	}
	public Double getBonus() {
		return bonus;
	}
	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	public Integer getCancancel() {
		return cancancel;
	}
	public void setCancancel(Integer cancancel) {
		this.cancancel = cancancel;
	}
	

}
