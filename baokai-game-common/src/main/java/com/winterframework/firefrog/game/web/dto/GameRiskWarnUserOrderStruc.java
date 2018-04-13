 package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameWarnUser;

/** 
* @ClassName GameRiskWarnUserOrderStruc 
* @Description 风控用户结构 
* @author  hugh
* @date 2014年4月10日 下午2:59:58 
*  
*/
public class GameRiskWarnUserOrderStruc implements Serializable  {
		
	private static final long serialVersionUID = -4168679697043098400L;
	
	//columns START
	private Long lotteryid;
	private Long issueCode;
	private Long userid;
	private String userName;
	private String userAccount;
	private Long totalWins;
	private Long winsRatio;
	private Long continuousWinsIssue;
	private Long continuousWinsTimes;
	private Long maxslipWins;
	
	private List<GameRiskWarnOrderStruc> warnOrderStrucs;
	
	public GameRiskWarnUserOrderStruc(){
		
	}
	
	public GameRiskWarnUserOrderStruc(GameWarnUser user) {
		this.lotteryid = user.getLotteryid();
		this.issueCode = user.getIssueCode();
		this.userid = user.getUserid();
		this.userAccount = user.getUserAccount();
		this.totalWins = user.getTotalWins();
		this.winsRatio = user.getWinsRatio();
		this.continuousWinsIssue = user.getContinuousWinsIssue();
		this.continuousWinsTimes = user.getContinuousWinsTimes();
		this.maxslipWins = user.getMaxslipWins();
	}
	
	//columns END
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public Long getTotalWins() {
		return totalWins;
	}
	public void setTotalWins(Long totalWins) {
		this.totalWins = totalWins;
	}
	public Long getWinsRatio() {
		return winsRatio;
	}
	public void setWinsRatio(Long winsRatio) {
		this.winsRatio = winsRatio;
	}
	public Long getContinuousWinsIssue() {
		return continuousWinsIssue;
	}
	public void setContinuousWinsIssue(Long continuousWinsIssue) {
		this.continuousWinsIssue = continuousWinsIssue;
	}
	public Long getContinuousWinsTimes() {
		return continuousWinsTimes;
	}
	public void setContinuousWinsTimes(Long continuousWinsTimes) {
		this.continuousWinsTimes = continuousWinsTimes;
	}
	public Long getMaxslipWins() {
		return maxslipWins;
	}
	public void setMaxslipWins(Long maxslipWins) {
		this.maxslipWins = maxslipWins;
	}
	public List<GameRiskWarnOrderStruc> getWarnOrderStrucs() {
		return warnOrderStrucs;
	}
	public void setWarnOrderStrucs(List<GameRiskWarnOrderStruc> warnOrderStrucs) {
		this.warnOrderStrucs = warnOrderStrucs;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


}

