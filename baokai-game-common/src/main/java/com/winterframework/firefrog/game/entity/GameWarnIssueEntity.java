package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @ClassName: GameWarnIssueEntity 
* @Description:奖期警告实体
* @author Richard
* @date 2013-10-12 下午3:03:35 
*
 */
public class GameWarnIssueEntity implements Serializable {

	private static final long serialVersionUID = 44508054888870428L;
	
	private Long id;
	private Lottery lottery;
	private Long issueCode;
	private String webIssueCode;
	private String  issueWarnId;
	private Date crateTime;
	private Date updateTime;
	private ReadFlag readFlag;
	private String warnParas;
	private GameWarnStatus status;
	private String statusRout;
	
	public enum ReadFlag{
		unread(0), read(1);
		
		private int value = 0;
		ReadFlag(int action){
			this.value= action;
		}
		
		public int getValue(){
			return value;
		}
	}
	
	public enum GameWarnStatus{
		pending(1),processed(2);
		
		private int value = 0;
		GameWarnStatus(int action){
			this.value = action;
		}
		
		public int getValue(){
			return value;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}


	public Date getCrateTime() {
		return crateTime;
	}

	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public ReadFlag getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(ReadFlag readFlag) {
		this.readFlag = readFlag;
	}

	public String getWarnParas() {
		return warnParas;
	}

	public void setWarnParas(String warnParas) {
		this.warnParas = warnParas;
	}

	public GameWarnStatus getStatus() {
		return status;
	}

	public void setStatus(GameWarnStatus status) {
		this.status = status;
	}

	public String getIssueWarnId() {
		return issueWarnId;
	}

	public void setIssueWarnId(String issueWarnId) {
		this.issueWarnId = issueWarnId;
	}

	public String getStatusRout() {
		return statusRout;
	}

	public void setStatusRout(String statusRout) {
		this.statusRout = statusRout;
	}
	

}
