package com.winterframework.firefrog.game.web.dto;

import java.util.List;
import java.util.Map;

/** 
* @ClassName: GameConfigAndAwardData 
* @Description: 输出后台相关配置和最新开奖等数据
* @author Alan
* @date 2013-10-9 下午2:59:36 
*  
*/
public class GameConfigAndAwardData {

	//用户名称
	private String username;
	//是否暂停销售
	private Integer isstop;
	//当前web期号
	private String number;
	//当前期号
	private Long issueCode;
	//当前期预计开奖时间
	private String resulttime;
	//当前服务器时间
	private String nowtime;
	//当前期投注结束时间
	private String nowstoptime;
	//上期期号
	private String lastnumber;
	//上期开奖号码(exg:"7,0,2,7,5")
	private String lastballs;
	//可追号的最大期数((重庆时时彩最大360期)
	private Integer tracemaxtimes;
	//游戏期号列表
	//载入期号数(重庆时时彩载入360期)
	private List<GameNumbers> gamenumbers;
	//游戏玩法限制(String:"wuxing.zhixuan.fushi",组合)
	private List<Map<String, GameLimit>> gamelimit;
	
	//开奖号码辅助提示比如北京快乐8 需要显示和值盘面
	private String ballInfo;
	
	private Integer multiplemaxtimes;
	private Integer multipleToCnyFactor;
	//當前開獎時間
	private String lotteryday;
	
	private List<Integer> prizeBet;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getIsstop() {
		return isstop;
	}

	public void setIsstop(Integer isstop) {
		this.isstop = isstop;
	}

	public String getResulttime() {
		return resulttime;
	}

	public void setResulttime(String resulttime) {
		this.resulttime = resulttime;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public Integer getTracemaxtimes() {
		return tracemaxtimes;
	}

	public void setTracemaxtimes(Integer tracemaxtimes) {
		this.tracemaxtimes = tracemaxtimes;
	}

	public List<GameNumbers> getGamenumbers() {
		return gamenumbers;
	}

	public void setGamenumbers(List<GameNumbers> gamenumbers) {
		this.gamenumbers = gamenumbers;
	}

	public List<Map<String, GameLimit>> getGamelimit() {
		return gamelimit;
	}

	public void setGamelimit(List<Map<String, GameLimit>> gamelimit) {
		this.gamelimit = gamelimit;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public String getBallInfo() {
		return ballInfo;
	}

	public void setBallInfo(String ballInfo) {
		this.ballInfo = ballInfo;
	}

	public Integer getMultiplemaxtimes() {
		return multiplemaxtimes;
	}

	public void setMultiplemaxtimes(Integer multiplemaxtimes) {
		this.multiplemaxtimes = multiplemaxtimes;
	}

	public Integer getMultipleToCnyFactor() {
		return multipleToCnyFactor;
	}

	public void setMultipleToCnyFactor(Integer multipleToCnyFactor) {
		this.multipleToCnyFactor = multipleToCnyFactor;
	}

	public String getLotteryday() {
		return lotteryday;
	}

	public void setLotteryday(String lotteryday) {
		this.lotteryday = lotteryday;
	}

	public List<Integer> getPrizeBet() {
		return prizeBet;
	}

	public void setPrizeBet(List<Integer> prizeBet) {
		this.prizeBet = prizeBet;
	}
	
}