package com.winterframework.firefrog.game.web.bet.entity;

import java.util.List;

import com.winterframework.firefrog.game.web.dto.GameDrawResultStruc;
import com.winterframework.firefrog.game.web.dto.LhcGameOdds;
import com.winterframework.firefrog.game.web.dto.LhcGameTips;
import com.winterframework.firefrog.game.web.dto.LhcGameZodiac;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardListStruc;

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
	private Integer awardRetStatus;	//奖金返点状态
	private Integer awardGroupRetStatus;	//奖金组返点状态
	private Integer lhcStatus; //六合彩狀態
	private Long backOutStartFee;//撤单起始金额

	private Long backOutRadio;//撤单手续费必烈
	
	private Boolean isSupport2000;//是否支持超级2000
	private Boolean isfirstimeuse2000;
	private Boolean isfirstimeusediamond2000;
	
	// 玩法群组投注方式列表
	private List<LotteryGameGroup> gameMethods;

	private List<LotteryGameUserAwardListStruc> awardGroups;

	private String dynamicConfigUrl;//动态配置接口地址

	private String uploadPath;//但是上传接口地址

	private String queryUserBalUrl;//查询用户余额接口

	private String getUserOrdersUrl;//获取用户投注页面显示订单接口地址

	private String getUserPlansUrl;//获取用户投注页面显示追号接口地址

	private String getHandingChargeUrl;//获取撤单手续费接口地址

	private String getBetAwardUrl;//查询玩法描述和默认冷热球及用户投注方式奖金接口地址

	private String getHotColdUrl;////获取冷热遗漏接口地址

	private String trendChartUrl;//获取玩法走势图接口地址

	private String getLotteryLogoPath;//获取彩种logo路径

	private String queryGameUserAwardGroupByLotteryIdUrl;//查询彩种奖金组

	private String saveProxyBetGameAwardGroupUrl;//保存代理投注奖金组

	private String sumbitUrl;//投注提交接口地址

	private String indexInit;//首页调用接口

	private Long poolBouns;//奖池金额 双色球专用
	
	private Boolean isLotteryStopSale;
	
	private String lastNumberUrl;//获取系统最新开奖号码
	
	private List<String> sourceList;
	
	private String helpLink;
	
	private Integer[] chips ={1,2,5,10,20,50,100,500,1000,5000};
	
	private Integer[] chipsSelected={10,20,50,100,500};
	
	private List<GameDrawResultStruc> ballLists;
	
	private List<LhcGameOdds> gameOdds;
	
	private List<LhcGameZodiac> gameZodiac;
	
	private List<LhcGameTips> gameTips;
	
	private String queryStraightOddsUrl;
	
	private String playerBetUrl;
	
	private String winningListUrl;
	
	private String headImg;
	private String userNickName;
	private String uploadUserInfo;
	
	/**
	 * 历史开奖接口
	 */
	private String historyLotteryAwardUrl;
	
	public String getLastNumberUrl() {
		return lastNumberUrl;
	}

	public void setLastNumberUrl(String lastNumberUrl) {
		this.lastNumberUrl = lastNumberUrl;
	}

	public Boolean getIsLotteryStopSale() {
		return isLotteryStopSale;
	}

	public void setIsLotteryStopSale(Boolean isLotteryStopSale) {
		this.isLotteryStopSale = isLotteryStopSale;
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

	public String getDynamicConfigUrl() {
		return dynamicConfigUrl;
	}

	public void setDynamicConfigUrl(String dynamicConfigUrl) {
		this.dynamicConfigUrl = dynamicConfigUrl;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getQueryUserBalUrl() {
		return queryUserBalUrl;
	}

	public void setQueryUserBalUrl(String queryUserBalUrl) {
		this.queryUserBalUrl = queryUserBalUrl;
	}

	public String getGetUserOrdersUrl() {
		return getUserOrdersUrl;
	}

	public void setGetUserOrdersUrl(String getUserOrdersUrl) {
		this.getUserOrdersUrl = getUserOrdersUrl;
	}

	public String getGetUserPlansUrl() {
		return getUserPlansUrl;
	}

	public void setGetUserPlansUrl(String getUserPlansUrl) {
		this.getUserPlansUrl = getUserPlansUrl;
	}

	public String getGetHandingChargeUrl() {
		return getHandingChargeUrl;
	}

	public void setGetHandingChargeUrl(String getHandingChargeUrl) {
		this.getHandingChargeUrl = getHandingChargeUrl;
	}

	public String getTrendChartUrl() {
		return trendChartUrl;
	}

	public void setTrendChartUrl(String trendChartUrl) {
		this.trendChartUrl = trendChartUrl;
	}

	public String getSumbitUrl() {
		return sumbitUrl;
	}

	public void setSumbitUrl(String sumbitUrl) {
		this.sumbitUrl = sumbitUrl;
	}

	public String getGetBetAwardUrl() {
		return getBetAwardUrl;
	}

	public void setGetBetAwardUrl(String getBetAwardUrl) {
		this.getBetAwardUrl = getBetAwardUrl;
	}

	public String getGetHotColdUrl() {
		return getHotColdUrl;
	}

	public void setGetHotColdUrl(String getHotColdUrl) {
		this.getHotColdUrl = getHotColdUrl;
	}

	public String getGetLotteryLogoPath() {
		return getLotteryLogoPath;
	}

	public void setGetLotteryLogoPath(String getLotteryLogoPath) {
		this.getLotteryLogoPath = getLotteryLogoPath;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getQueryGameUserAwardGroupByLotteryIdUrl() {
		return queryGameUserAwardGroupByLotteryIdUrl;
	}

	public void setQueryGameUserAwardGroupByLotteryIdUrl(String queryGameUserAwardGroupByLotteryIdUrl) {
		this.queryGameUserAwardGroupByLotteryIdUrl = queryGameUserAwardGroupByLotteryIdUrl;
	}

	public String getSaveProxyBetGameAwardGroupUrl() {
		return saveProxyBetGameAwardGroupUrl;
	}

	public void setSaveProxyBetGameAwardGroupUrl(String saveProxyBetGameAwardGroupUrl) {
		this.saveProxyBetGameAwardGroupUrl = saveProxyBetGameAwardGroupUrl;
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

	public Long getBackOutStartFee() {
		return backOutStartFee;
	}

	public void setBackOutStartFee(Long backOutStartFee) {
		this.backOutStartFee = backOutStartFee;
	}

	public Long getBackOutRadio() {
		return backOutRadio;
	}

	public void setBackOutRadio(Long backOutRadio) {
		this.backOutRadio = backOutRadio;
	}

	public String getIndexInit() {
		return indexInit;
	}

	public void setIndexInit(String indexInit) {
		this.indexInit = indexInit;
	}

	public Long getPoolBouns() {
		return poolBouns;
	}

	public void setPoolBouns(Long poolBouns) {
		this.poolBouns = poolBouns;
	}

	public List<String> getSourceList() {
		return sourceList;
	}

	public void setSourceList(List<String> sourceList) {
		this.sourceList = sourceList;
	}

	public String getHelpLink() {
		return helpLink;
	}

	public void setHelpLink(String helpLink) {
		this.helpLink = helpLink;
	}

	public Integer[] getChips() {
		return chips;
	}

	public void setChips(Integer[] chips) {
		this.chips = chips;
	}

	public Integer[] getChipsSelected() {
		return chipsSelected;
	}

	public void setChipsSelected(Integer[] chipsSelected) {
		this.chipsSelected = chipsSelected;
	}

	public List<GameDrawResultStruc> getBallLists() {
		return ballLists;
	}

	public void setBallLists(List<GameDrawResultStruc> ballLists) {
		this.ballLists = ballLists;
	}

	public Integer getAwardRetStatus() {
		return awardRetStatus;
	}

	public void setAwardRetStatus(Integer awardRetStatus) {
		this.awardRetStatus = awardRetStatus;
	}
	public Boolean getIsSupport2000() {
		return isSupport2000;
	}

	public void setIsSupport2000(Boolean isSupport2000) {
		this.isSupport2000 = isSupport2000;
	}

	public Integer getAwardGroupRetStatus() {
		return awardGroupRetStatus;
	}

	public void setAwardGroupRetStatus(Integer awardGroupRetStatus) {
		this.awardGroupRetStatus = awardGroupRetStatus;
	}

	/**
	 * @return the isfirstimeuse2000
	 */
	public Boolean getIsfirstimeuse2000() {
		return isfirstimeuse2000;
	}

	/**
	 * @param isfirstimeuse2000 the isfirstimeuse2000 to set
	 */
	public void setIsfirstimeuse2000(Boolean isfirstimeuse2000) {
		this.isfirstimeuse2000 = isfirstimeuse2000;
	}

	public List<LhcGameOdds> getGameOdds() {
		return gameOdds;
	}

	public void setGameOdds(List<LhcGameOdds> gameOdds) {
		this.gameOdds = gameOdds;
	}

	public List<LhcGameZodiac> getGameZodiac() {
		return gameZodiac;
	}

	public void setGameZodiac(List<LhcGameZodiac> gameZodiac) {
		this.gameZodiac = gameZodiac;
	}

	public String getQueryStraightOddsUrl() {
		return queryStraightOddsUrl;
	}

	public void setQueryStraightOddsUrl(String queryStraightOddsUrl) {
		this.queryStraightOddsUrl = queryStraightOddsUrl;
	}

	public List<LhcGameTips> getGameTips() {
		return gameTips;
	}

	public void setGameTips(List<LhcGameTips> gameTips) {
		this.gameTips = gameTips;
	}

	public Integer getLhcStatus() {
		return lhcStatus;
	}

	public void setLhcStatus(Integer lhcStatus) {
		this.lhcStatus = lhcStatus;
	}
	
	public Boolean getIsfirstimeusediamond2000() {
		return isfirstimeusediamond2000;
	}

	public void setIsfirstimeusediamond2000(Boolean isfirstimeusediamond2000) {
		this.isfirstimeusediamond2000 = isfirstimeusediamond2000;
	}
	
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getUploadUserInfo() {
		return uploadUserInfo;
	}

	public void setUploadUserInfo(String uploadUserInfo) {
		this.uploadUserInfo = uploadUserInfo;
	}
	
	public String getPlayerBetUrl() {
		return playerBetUrl;
	}

	public void setPlayerBetUrl(String playerBetUrl) {
		this.playerBetUrl = playerBetUrl;
	}

	public String getWinningListUrl() {
		return winningListUrl;
	}

	public void setWinningListUrl(String winningListUrl) {
		this.winningListUrl = winningListUrl;
	}
	/**
	 * 取得历史开奖接口
	 */
	public String getHistoryLotteryAwardUrl() {
		return historyLotteryAwardUrl;
	}
	/**
	 * 设定历史开奖接口
	 */
	public void setHistoryLotteryAwardUrl(String historyLotteryAwardUrl) {
		this.historyLotteryAwardUrl = historyLotteryAwardUrl;
	}
}
