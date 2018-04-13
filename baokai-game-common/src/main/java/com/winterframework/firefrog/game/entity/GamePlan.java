package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.user.entity.User;

/** 
* @ClassName: GamePlan 
* @Description: 
* @author Richard
* @date 2013-7-24 上午11:32:35 
*  
*/
public class GamePlan extends GamePackage implements Serializable {

	private static final long serialVersionUID = 2524316145515449403L;

	private Long id;
//	private User user;
	private Lottery lottery;
	private Long startIsuueCode;
	private Integer finishIssue;
	private Integer totalIssue;
	private StopMode stopMode;
	private List<GamePlanDetail> gamePlanDetails;

	public enum StopMode {
		//0 不停止，1 盈利停止，2 中奖即停
		NO_STOP(0),STOP_BY_BENIFIT(1),WIN_STOP(2);

		private int _value = 0;

		StopMode(int action) {
			this._value = action;
		}

		public int getValue() {
			return _value;
		}
	}

	private Long stopParms;
	private String optionParms;
//	private List<BetDetails> betDetails;
	private Date crateTime;
	private String startWebIssue;
	private GamePlanType gamePlanType;
	private Date updateTime;
	
	public enum GamePlanType {
		//追号类型 1:普通追号 2:翻倍追号 3:盈利金额追号 4:盈利率追号
		GENERAL(1), DOUBLE(2), PAY_OFF(3), EARNINGS_RATE(4);
		public int _value = 1;

		GamePlanType(int action) {
			this._value = action;
		}

		public int getValue() {
			return _value;
		}
	}

	private Long totamount;
	private Long soldAmount;
	private Long winAmount;
	private Long userIp;
	private Long serverIp;
	private Date saleTime;
	private GamePlanStatus status;
//	private GamePackage gamePackage;

	public enum GamePlanStatus {
		//状态 0:可执行 1:用户终止 2:系统终止
//		executable(0), user(1), system(2);
		EXECUTABLE(0),WAITING(1),FINISH(2),STOP(3),PAUSE(4);

		private int _value = 0;

		GamePlanStatus(int action) {
			this._value = action;
		}

		public int getValue() {
			return _value;
		}
	}

	private Date cancelTime;
	private Integer cancelIssue;
	private CancelMode cancelMode; 
	private String planCode;
//	private Integer status;
	

	public GamePlan() {

	}

/*	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}*/

	public Long getStartIsuueCode() {
		return startIsuueCode;
	}

	public void setStartIsuueCode(Long startIsuueCode) {
		this.startIsuueCode = startIsuueCode;
	}

	public Integer getFinishIssue() {
		return finishIssue;
	}

	public void setFinishIssue(Integer finishIssue) {
		this.finishIssue = finishIssue;
	}

	public Integer getTotalIssue() {
		return totalIssue;
	}

	public void setTotalIssue(Integer totalIssue) {
		this.totalIssue = totalIssue;
	}

	public Long getStopParms() {
		return stopParms;
	}

	public void setStopParms(Long stopParms) {
		this.stopParms = stopParms;
	}

	public String getOptionParms() {
		return optionParms;
	}

	public void setOptionParms(String optionParms) {
		this.optionParms = optionParms;
	}

	/*public List<BetDetails> getBetDetails() {
		return betDetails;
	}

	public void setBetDetails(List<BetDetails> betDetails) {
		this.betDetails = betDetails;
	}*/

	public Date getCrateTime() {
		return crateTime;
	}

	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
	}

	public String getStartWebIssue() {
		return startWebIssue;
	}

	public void setStartWebIssue(String startWebIssue) {
		this.startWebIssue = startWebIssue;
	}

	public Long getTotamount() {
		return totamount;
	}

	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}

	public Long getSoldAmount() {
		return soldAmount;
	}

	public void setSoldAmount(Long soldAmount) {
		this.soldAmount = soldAmount;
	}

	public Long getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(Long winAmount) {
		this.winAmount = winAmount;
	}

	public Long getUserIp() {
		return userIp;
	}

	public void setUserIp(Long userIp) {
		this.userIp = userIp;
	}

	public Long getServerIp() {
		return serverIp;
	}

	public void setServerIp(Long serverIp) {
		this.serverIp = serverIp;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StopMode getStopMode() {
		return stopMode;
	}

	public void setStopMode(StopMode stopMode) {
		this.stopMode = stopMode;
	}

	public GamePlanType getGamePlanType() {
		return gamePlanType;
	}

	public void setGamePlanType(GamePlanType gamePlanType) {
		this.gamePlanType = gamePlanType;
	}

	/*public GamePlanStatus getGamePlanStatus() {
		return gamePlanStatus;
	}

	public void setGamePlanStatus(GamePlanStatus gamePlanStatus) {
		this.gamePlanStatus = gamePlanStatus;
	}*/

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	public List<GamePlanDetail> getGamePlanDetails() {
		return gamePlanDetails;
	}

	public void setGamePlanDetails(List<GamePlanDetail> gamePlanDetails) {
		this.gamePlanDetails = gamePlanDetails;
	}

	public GamePlanStatus getStatus() {
		return status;
	}

	public void setStatus(GamePlanStatus status) {
		this.status = status;
	}

	public Integer getCancelIssue() {
		return cancelIssue;
	}

	public void setCancelIssue(Integer cancelIssue) {
		this.cancelIssue = cancelIssue;
	}

	public CancelMode getCancelMode() {
		return cancelMode;
	}

	public void setCancelMode(CancelMode cancelMode) {
		this.cancelMode = cancelMode;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/*public GamePackage getGamePackage() {
		return gamePackage;
	}

	public void setGamePackage(GamePackage gamePackage) {
		this.gamePackage = gamePackage;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
*/
}
