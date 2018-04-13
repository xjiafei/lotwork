package com.winterframework.firefrog.phone.web.bet.entity;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.web.dto.GameDrawResultStruc;
import com.winterframework.firefrog.game.web.dto.GameLimit;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardListStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameListDto;

/**
 * @ClassName: LotteryConfig
 * @Description: js动态获取彩种名称玩法群组投注方式等,以及配置的相应接口url等信息
 * @author david
 * @date 2014-3-25 下午1:49:35
 * 
 */
public class LotteryConfig {

	// 彩种
	private String gameType;

	private String gameTypeCn;

	private String defaultMethod;

	private Long lotteryId;

	private Integer userLvl;

	private Long userId;

	private String userName;
	
	private double balance;
	private List<GameDrawResultStruc> ballLists;
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	// 玩法群组投注方式列表
	private List<LotteryGameGroup> gameMethods;
	
	//遊戲紀錄
	private List<GameListDto> records;
	
	//投注限制
	private List<Map<String, GameLimit>> gameLimits;

	private List<LotteryGameUserAwardListStruc> awardGroups;
	//用戶獎金返點模式開關
	private Integer retSwitch;
	//用戶選定獎金組字段
	private String userAwardName; 
	
	public String getUserAwardName() {
		return userAwardName;
	}

	public void setUserAwardName(String userAwardName) {
		this.userAwardName = userAwardName;
	}

	public Integer getRetSwitch() {
		return retSwitch;
	}

	public void setRetSwitch(Integer retSwitch) {
		this.retSwitch = retSwitch;
	}

	public List<Map<String, GameLimit>> getGameLimits() {
		return gameLimits;
	}

	public void setGameLimits(List<Map<String, GameLimit>> gameLimits) {
		this.gameLimits = gameLimits;
	}

	public List<GameListDto> getRecords() {
		return records;
	}

	public void setRecords(List<GameListDto> records) {
		this.records = records;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getGameTypeCn() {
		return gameTypeCn;
	}

	public void setGameTypeCn(String gameTypeCn) {
		this.gameTypeCn = gameTypeCn;
	}

	public String getDefaultMethod() {
		return defaultMethod;
	}

	public void setDefaultMethod(String defaultMethod) {
		this.defaultMethod = defaultMethod;
	}

	public List<LotteryGameGroup> getGameMethods() {
		return gameMethods;
	}

	public void setGameMethods(List<LotteryGameGroup> gameMethods) {
		this.gameMethods = gameMethods;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public List<LotteryGameUserAwardListStruc> getAwardGroups() {
		return awardGroups;
	}

	public void setAwardGroups(List<LotteryGameUserAwardListStruc> awardGroups) {
		this.awardGroups = awardGroups;
	}

	public Integer getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Integer userLvl) {
		this.userLvl = userLvl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<GameDrawResultStruc> getBallLists() {
		return ballLists;
	}

	public void setBallLists(List<GameDrawResultStruc> ballLists) {
		this.ballLists = ballLists;
	}

}