package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class GameDetailResponse implements Serializable{
	
	
	private static final long serialVersionUID = -7236629571217324258L;
	private String enid;//	投注单id
	private String issue;//	奖期
	private String time;//	投注时间
	private Float total;//	投注总额
	private Float bonus;//	中奖金额
	private Integer cancancel;//	是否可以撤单
	private Integer iscancel;//	撤单状态
	private String opencode;//	开奖号码
	private Long lotteryId;
	private String gameNo;
	
	public String getGameNo() {
		return gameNo;
	}


	public void setGameNo(String gameNo) {
		this.gameNo = gameNo;
	}


	private List<GameDetailProject> list;

	public String getEnid() {
		return enid;
	}


	public void setEnid(String enid) {
		this.enid = enid;
	}


	public Long getLotteryId() {
		return lotteryId;
	}


	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}


	public String getIssue() {
		return issue;
	}


	public void setIssue(String issue) {
		this.issue = issue;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public Float getTotal() {
		return total;
	}


	public void setTotal(Float total) {
		this.total = total;
	}


	public Float getBonus() {
		return bonus;
	}


	public void setBonus(Float bonus) {
		this.bonus = bonus;
	}


	public Integer getCancancel() {
		return cancancel;
	}


	public void setCancancel(Integer cancancel) {
		this.cancancel = cancancel;
	}


	public Integer getIscancel() {
		return iscancel;
	}


	public void setIscancel(Integer iscancel) {
		this.iscancel = iscancel;
	}


	public String getOpencode() {
		return opencode;
	}


	public void setOpencode(String opencode) {
		this.opencode = opencode;
	}


	public List<GameDetailProject> getList() {
		return list;
	}


	public void setList(List<GameDetailProject> list) {
		this.list = list;
	}


	
	
}
