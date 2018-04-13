package com.winterframework.firefrog.game.web.bet.entity;

import java.util.List;

import com.winterframework.firefrog.game.web.dto.WinOrderResponse;

public class LastNumber {

	private String username;
	private String lastnumber;
	private String lastballs;
	private String ballInfo;

	private Integer isstop;
	private String number;
	private Long issueCode;
	private String nowtime;
	private String nowstoptime;
	private String resulttime;
	
	private List<WinOrderResponse> winlists;
	
	private  List<Integer> prizeBet;

	public String getBallInfo() {
		return ballInfo;
	}

	public void setBallInfo(String ballInfo) {
		this.ballInfo = ballInfo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLastnumber() {
		return lastnumber;
	}

	public void setLastnumber(String lastnumber) {
		this.lastnumber = lastnumber;
	}

	public String getLastballs() {
		return lastballs;
	}

	public void setLastballs(String lastballs) {
		this.lastballs = lastballs;
	}

	public Integer getIsstop() {
		return isstop;
	}

	public void setIsstop(Integer isstop) {
		this.isstop = isstop;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public String getNowtime() {
		return nowtime;
	}

	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}

	public String getNowstoptime() {
		return nowstoptime;
	}

	public void setNowstoptime(String nowstoptime) {
		this.nowstoptime = nowstoptime;
	}

	public String getResulttime() {
		return resulttime;
	}

	public void setResulttime(String resulttime) {
		this.resulttime = resulttime;
	}

	public List<WinOrderResponse> getWinlists() {
		return winlists;
	}

	public void setWinlists(List<WinOrderResponse> winlists) {
		this.winlists = winlists;
	}

	public List<Integer> getPrizeBet() {
		return prizeBet;
	}

	public void setPrizeBet(List<Integer> prizeBet) {
		this.prizeBet = prizeBet;
	}
	
	

}
