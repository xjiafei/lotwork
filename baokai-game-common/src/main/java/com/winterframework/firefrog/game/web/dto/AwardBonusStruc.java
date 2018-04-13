package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
* @ClassName: AwardBonusStruc 
* @Description: 奖金组奖金基本结构
* @author Richard
* @date 2013-8-16 上午10:16:06 
*
 */
public class AwardBonusStruc implements Serializable {

	private static final long serialVersionUID = 6933950873653289409L;

	/**玩法群*/
	private Integer gameGroupCode;
	/**玩法组*/
	private Integer gameSetCode;
	/**投注方式*/
	private Integer betMethodCode;
	/**实际奖金（分）*/
	private Long actualBonus;
	private Long actualBonusDown;
	private Long actualBonusBak;
	/**理论奖金（分）*/
	private Long theoryBonus;
	/**总利润*/
	private Long totalProfit;
	/**总代返点*/
	private Long topAgentPoint;
	/**公司流水*/
	private Long companyProfit;
	//辅助投注方式
	private List<AssistBmBonusStruc> assistBMBonusList;
	/** 
	* 辅助玩法类型
	*/
	private Integer methodType;

	private Long gameBettypeStatusId;
	
	private Long retVal;
	private Long maxRetVal;	//上级用户的返点
	
	private String groupCodeTitle;
	private String setCodeTitle;
	private String methodCodeTitle;
	private String methodTypeTitle;
	private String lhcCodeTitle;
	private Long lhcTheoryBonus;
	
	public AwardBonusStruc() {

	}

	public Long getRetVal() {
		return retVal;
	}



	public void setRetVal(Long retVal) {
		this.retVal = retVal;
	}



	public Integer getGameGroupCode() {
		return gameGroupCode;
	}

	public void setGameGroupCode(Integer gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	public Integer getGameSetCode() {
		return gameSetCode;
	}

	public void setGameSetCode(Integer gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	public Integer getBetMethodCode() {
		return betMethodCode;
	}

	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}

	public Long getActualBonus() {
		return actualBonus;
	}

	public void setActualBonus(Long actualBonus) {
		this.actualBonus = actualBonus;
	}

	public Long getTheoryBonus() {
		return theoryBonus;
	}

	public void setTheoryBonus(Long theoryBonus) {
		this.theoryBonus = theoryBonus;
	}

	public Long getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(Long totalProfit) {
		this.totalProfit = totalProfit;
	}

	public Long getTopAgentPoint() {
		return topAgentPoint;
	}

	public void setTopAgentPoint(Long topAgentPoint) {
		this.topAgentPoint = topAgentPoint;
	}

	public Long getCompanyProfit() {
		return companyProfit;
	}

	public void setCompanyProfit(Long companyProfit) {
		this.companyProfit = companyProfit;
	}

	public Long getActualBonusBak() {
		return actualBonusBak;
	}

	public void setActualBonusBak(Long actualBonusBak) {
		this.actualBonusBak = actualBonusBak;
	}

	public List<AssistBmBonusStruc> getAssistBMBonusList() {
		return assistBMBonusList;
	}

	public void setAssistBMBonusList(List<AssistBmBonusStruc> assistBMBonusList) {
		this.assistBMBonusList = assistBMBonusList;
	}

	public Integer getMethodType() {
		return methodType;
	}

	public void setMethodType(Integer methodType) {
		this.methodType = methodType;
	}

	public String getMainBetTypeCode() {
		return this.getGameGroupCode() + "_" + this.getGameSetCode() + "_" + this.getBetMethodCode();
	}

	public Long getGameBettypeStatusId() {
		return gameBettypeStatusId;
	}

	public void setGameBettypeStatusId(Long gameBettypeStatusId) {
		this.gameBettypeStatusId = gameBettypeStatusId;
	}

	public Long getActualBonusDown() {
		return actualBonusDown;
	}

	public void setActualBonusDown(Long actualBonusDown) {
		this.actualBonusDown = actualBonusDown;
	}

	public Long getMaxRetVal() {
		return maxRetVal;
	}

	public void setMaxRetVal(Long maxRetVal) {
		this.maxRetVal = maxRetVal;
	}

	public String getGroupCodeTitle() {
		return groupCodeTitle;
	}

	public void setGroupCodeTitle(String groupCodeTitle) {
		this.groupCodeTitle = groupCodeTitle;
	}

	public String getSetCodeTitle() {
		return setCodeTitle;
	}

	public void setSetCodeTitle(String setCodeTitle) {
		this.setCodeTitle = setCodeTitle;
	}

	public String getMethodCodeTitle() {
		return methodCodeTitle;
	}

	public void setMethodCodeTitle(String methodCodeTitle) {
		this.methodCodeTitle = methodCodeTitle;
	}

	public String getMethodTypeTitle() {
		return methodTypeTitle;
	}

	public void setMethodTypeTitle(String methodTypeTitle) {
		this.methodTypeTitle = methodTypeTitle;
	}

	public String getLhcCodeTitle() {
		return lhcCodeTitle;
	}

	public void setLhcCodeTitle(String lhcCodeTitle) {
		this.lhcCodeTitle = lhcCodeTitle;
	}

	public Long getLhcTheoryBonus() {
		return lhcTheoryBonus;
	}

	public void setLhcTheoryBonus(Long lhcTheoryBonus) {
		this.lhcTheoryBonus = lhcTheoryBonus;
	}
}
