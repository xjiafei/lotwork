package com.winterframework.firefrog.user.web.dto;

import java.math.BigDecimal;

public class UserAwardStruc {
	private BigDecimal lotterySeriesCode;
	private String lotterySeriesName;
	private String awardGroupId;
	private String groupChain;
	private String awardName;
	private Long lotteryId;
	/**直選及其他返點*/
	private BigDecimal directRet;
	/**三星一碼不定位返點*/
	private BigDecimal threeoneRet;
	/**超级2000返点*/
	private BigDecimal superRet;
	private BigDecimal status;
	/**直選及其他返點上限*/
	private BigDecimal directLimitRet;
	/**三星一碼不定位返點上限*/
	private BigDecimal threeLimitRet;
	/**超级2000返点上限*/
	private BigDecimal superLimitRet;
	/**投注方式編碼*/
	private String betTypeCode;
	/**直选可分配返点数上限*/
	private Long maxDirectRet; 
	/**三星不定位可分配返点数上限 */
	private Long maxThreeOneRet;
	/**超級2000可分配返点数上限*/
	private Long maxSuperRet;
	/**六合彩特碼-特肖返点*/
	private BigDecimal lhcYear;
	/**六合彩特碼-兩面、色波返点*/
	private BigDecimal lhcColor;
	/**骰寶猜一個號返點*/
	private BigDecimal sbThreeoneRet;
	/**六合彩正碼-平码返点*/
	private BigDecimal lhcFlatcode;
	/**六合彩特碼-半波返点*/
	private BigDecimal lhcHalfwave;
	/**六合彩正特碼-一肖返点*/
	private BigDecimal lhcOneyear;
	/**六合彩正特碼-不中返点*/
	private BigDecimal lhcNotin;
	/**六合彩正特碼-連肖(中)二、三連肖返点*/
	private BigDecimal lhcContinuein23;
	/**六合彩正特碼-連肖(中)四連肖返点*/
	private BigDecimal lhcContinuein4;
	/**六合彩正特碼-連肖(中)五連肖返点*/
	private BigDecimal lhcContinuein5;
	/**六合彩正特碼-連肖(不中)二、三連肖返点*/
	private BigDecimal lhcContinuenotin23;
	/**六合彩正特碼-連肖(不中)四連肖返点*/
	private BigDecimal lhcContinuenotin4;
	/**六合彩正特碼-連肖(不中)五連肖返点*/
	private BigDecimal lhcContinuenotin5;
	/**六合彩連碼返点*/
	private BigDecimal lhcContinuecode;
	
	/**六合彩特肖可分配返点数*/
	private BigDecimal lhcYearLimit;
	/**六合彩色波兩面可分配返点数*/
	private BigDecimal lhcColorLimit;
	/**骰寶猜一個號可分配返點數*/
	private BigDecimal sbThreeoneRetLimit;
	/**六合彩正碼-平码可分配返点数*/
	private BigDecimal lhcFlatcodeLimit;
	/**六合彩特碼-半波可分配返点数*/
	private BigDecimal lhcHalfwaveLimit;
	/**六合彩正特碼-一肖可分配返点数*/
	private BigDecimal lhcOneyearLimit;
	/**六合彩正特碼-不中可分配返点数*/
	private BigDecimal lhcNotinLimit;
	/**六合彩正特碼-連肖(中)二、三連肖可分配返点数*/
	private BigDecimal lhcContinuein23Limit;
	/**六合彩正特碼-連肖(中)四連肖可分配返点数*/
	private BigDecimal lhcContinuein4Limit;
	/**六合彩正特碼-連肖(中)五連肖可分配返点数*/
	private BigDecimal lhcContinuein5Limit;
	/**六合彩正特碼-連肖(不中)二、三連肖可分配返点数*/
	private BigDecimal lhcContinuenotin23Limit;
	/**六合彩正特碼-連肖(不中)四連肖可分配返点数*/
	private BigDecimal lhcContinuenotin4Limit;
	/**六合彩正特碼-連肖(不中)五連肖可分配返点数*/
	private BigDecimal lhcContinuenotin5Limit;
	/**六合彩連碼可分配返点数*/
	private BigDecimal lhcContinuecodeLimit;
	
	/**六合彩特肖可分配返点数上限*/
	private BigDecimal maxLhcYear;
	/**六合彩色波兩面可分配返点数上限*/
	private BigDecimal maxLhcColor;
	/**骰寶猜一個號可分配返點數上限*/
	private BigDecimal maxSbThreeoneRet;
	/**六合彩正碼-平码可分配返点数上限*/
	private BigDecimal maxLhcFlatcode;
	/**六合彩特碼-半波可分配返点数上限*/
	private BigDecimal maxLhcHalfwave;
	/**六合彩正特碼-一肖可分配返点数上限*/
	private BigDecimal maxLhcOneyear;
	/**六合彩正特碼-不中可分配返点数上限*/
	private BigDecimal maxLhcNotin;
	/**六合彩正特碼-連肖(中)二、三連肖可分配返点数上限*/
	private BigDecimal maxLhcContinuein23;
	/**六合彩正特碼-連肖(中)四連肖可分配返点数上限*/
	private BigDecimal maxLhcContinuein4;
	/**六合彩正特碼-連肖(中)五連肖可分配返点数上限*/
	private BigDecimal maxLhcContinuein5;
	/**六合彩正特碼-連肖(不中)二、三連肖可分配返点数上限*/
	private BigDecimal maxLhcContinuenotin23;
	/**六合彩正特碼-連肖(不中)四連肖可分配返点数上限*/
	private BigDecimal maxLhcContinuenotin4;
	/**六合彩正特碼-連肖(不中)五連肖可分配返点数上限*/
	private BigDecimal maxLhcContinuenotin5;
	/**六合彩連碼可分配返点数上限*/
	private BigDecimal maxLhcContinuecode;
	
	/**
	 * 取得直选可分配返点数上限。
	 * @return
	 */
	public Long getMaxDirectRet() {
		return maxDirectRet;
	}
	/**
	 * 設定直选可分配返点数上限。
	 * @param maxDirectRet
	 */
	public void setMaxDirectRet(Long maxDirectRet) {
		this.maxDirectRet = maxDirectRet;
	}
	/**
	 * 取得三星不定位可分配返点数上限。
	 * @return
	 */
	public Long getMaxThreeOneRet() {
		return maxThreeOneRet;
	}
	/**
	 * 設定三星不定位可分配返点数上限。
	 * @param maxThreeOneRet
	 */
	public void setMaxThreeOneRet(Long maxThreeOneRet) {
		this.maxThreeOneRet = maxThreeOneRet;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getGroupChain() {
		return groupChain;
	}
	public void setGroupChain(String groupChain) {
		this.groupChain = groupChain;
	}
	public BigDecimal getLotterySeriesCode() {
		return lotterySeriesCode;
	}
	public void setLotterySeriesCode(BigDecimal lotterySeriesCode) {
		this.lotterySeriesCode = lotterySeriesCode;
	}
	public String getLotterySeriesName() {
		return lotterySeriesName;
	}
	public void setLotterySeriesName(String lotterySeriesName) {
		this.lotterySeriesName = lotterySeriesName;
	}
	public String getAwardGroupId() {
		return awardGroupId;
	}
	public void setAwardGroupId(String awardGroupId) {
		this.awardGroupId = awardGroupId;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	/**
	 * 取得直選及其他返點。
	 * @return
	 */
	public BigDecimal getDirectRet() {
		return directRet;
	}
	/**
	 * 設定直選及其他返點。
	 * @param directRet
	 */
	public void setDirectRet(BigDecimal directRet) {
		this.directRet = directRet;
	}
	/**
	 * 取得三星一碼不定位返點。
	 * @return
	 */
	public BigDecimal getThreeoneRet() {
		return threeoneRet;
	}
	/**
	 * 設定三星一碼不定位返點。
	 * @param threeoneRet
	 */
	public void setThreeoneRet(BigDecimal threeoneRet) {
		this.threeoneRet = threeoneRet;
	}
	public BigDecimal getStatus() {
		return status;
	}
	public void setStatus(BigDecimal status) {
		this.status = status;
	}
	/**
	 * 取得直選及其他返點上限。
	 * @return
	 */
	public BigDecimal getDirectLimitRet() {
		return directLimitRet;
	}
	/**
	 * 設定直選及其他返點上限。
	 * @param directLimitRet
	 */
	public void setDirectLimitRet(BigDecimal directLimitRet) {
		this.directLimitRet = directLimitRet;
	}
	/**
	 * 取得三星一碼不定位返點上限。
	 * @return
	 */
	public BigDecimal getThreeLimitRet() {
		return threeLimitRet;
	}
	/**
	 * 設定三星一碼不定位返點上限。
	 * @param threeLimitRet
	 */
	public void setThreeLimitRet(BigDecimal threeLimitRet) {
		this.threeLimitRet = threeLimitRet;
	}
	/**
	 * 取得投注方式編碼。
	 * @return
	 */
	public String getBetTypeCode() {
		return betTypeCode;
	}
	/**
	 * 設定投注方式編碼。
	 * @param betTypeCode
	 */
	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}
	/**
	 * 取得超级2000返点。
	 * @return
	 */
	public BigDecimal getSuperRet() {
		return superRet;
	}
	/**
	 * 設定超级2000返点。
	 * @param superRet
	 */
	public void setSuperRet(BigDecimal superRet) {
		this.superRet = superRet;
	}
	/**
	 * 取得超级2000返点上限。
	 * @return
	 */
	public BigDecimal getSuperLimitRet() {
		return superLimitRet;
	}
	/**
	 * 設定超级2000返点上限。
	 * @param superLimitRet
	 */
	public void setSuperLimitRet(BigDecimal superLimitRet) {
		this.superLimitRet = superLimitRet;
	}
	/**
	 * 取得超級2000可分配返点数上限。
	 * @return
	 */
	public Long getMaxSuperRet() {
		return maxSuperRet;
	}
	/**
	 * 設定超級2000可分配返点数上限。
	 * @param maxSuperRet
	 */
	public void setMaxSuperRet(Long maxSuperRet) {
		this.maxSuperRet = maxSuperRet;
	}
	
	/**
	 * 取得六合彩特肖返點。
	 * @return
	 */
	public BigDecimal getLhcYear() {
		return lhcYear;
	}

	/**
	 * 設定六合彩特肖返點。
	 * @param lhcYear
	 */
	public void setLhcYear(BigDecimal lhcYear) {
		this.lhcYear = lhcYear;
	}

	/**
	 * 取得六合彩色波兩面返點。
	 * @return
	 */
	public BigDecimal getLhcColor() {
		return lhcColor;
	}

	/**
	 * 設定六合彩色波兩面返點。
	 * @param lhcColor
	 */
	public void setLhcColor(BigDecimal lhcColor) {
		this.lhcColor = lhcColor;
	}
	
	/**
	 * 取得骰寶猜一個號返點。
	 * @return
	 */
	public BigDecimal getSbThreeoneRet() {
		return sbThreeoneRet;
	}
	/**
	 * 設定骰寶猜一個號返點。
	 * @param sbThreeoneRet
	 */
	public void setSbThreeoneRet(BigDecimal sbThreeoneRet) {
		this.sbThreeoneRet = sbThreeoneRet;
	}
	
	/**
	 * 取得六合彩正碼-平码返点。
	 * @return 
	 */
	public BigDecimal getLhcFlatcode() {
		return lhcFlatcode;
	}

	/**
	 * 設定六合彩正碼-平码返点。
	 * @param lhcFlatcode 
	 */
	public void setLhcFlatcode(BigDecimal lhcFlatcode) {
		this.lhcFlatcode = lhcFlatcode;
	}

	/**
	 * 取得六合彩特碼-半波返点。
	 * @return 
	 */
	public BigDecimal getLhcHalfwave() {
		return lhcHalfwave;
	}

	/**
	 * 設定六合彩特碼-半波返点。
	 * @param lhcHalfwave 
	 */
	public void setLhcHalfwave(BigDecimal lhcHalfwave) {
		this.lhcHalfwave = lhcHalfwave;
	}

	/**
	 * 取得六合彩正特碼-一肖返点。
	 * @return 
	 */
	public BigDecimal getLhcOneyear() {
		return lhcOneyear;
	}

	/**
	 * 設定六合彩正特碼-一肖返点。
	 * @param lhcOneyear 
	 */
	public void setLhcOneyear(BigDecimal lhcOneyear) {
		this.lhcOneyear = lhcOneyear;
	}

	/**
	 * 取得六合彩正特碼-不中返点。
	 * @return 
	 */
	public BigDecimal getLhcNotin() {
		return lhcNotin;
	}

	/**
	 * 設定六合彩正特碼-不中返点。
	 * @param lhcNotin 
	 */
	public void setLhcNotin(BigDecimal lhcNotin) {
		this.lhcNotin = lhcNotin;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)二、三連肖返点。
	 * @return 
	 */
	public BigDecimal getLhcContinuein23() {
		return lhcContinuein23;
	}

	/**
	 * 設定六合彩正特碼-連肖(中)二、三連肖返点。
	 * @param lhcContinuein23 
	 */
	public void setLhcContinuein23(BigDecimal lhcContinuein23) {
		this.lhcContinuein23 = lhcContinuein23;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)四連肖返点。
	 * @return 
	 */
	public BigDecimal getLhcContinuein4() {
		return lhcContinuein4;
	}

	/**
	 * 設定六合彩正特碼-連肖(中)四連肖返点。
	 * @param lhcContinuein4 
	 */
	public void setLhcContinuein4(BigDecimal lhcContinuein4) {
		this.lhcContinuein4 = lhcContinuein4;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)五連肖返点。
	 * @return 
	 */
	public BigDecimal getLhcContinuein5() {
		return lhcContinuein5;
	}

	/**
	 * 設定六合彩正特碼-連肖(中)五連肖返点。
	 * @param lhcContinuein5 
	 */
	public void setLhcContinuein5(BigDecimal lhcContinuein5) {
		this.lhcContinuein5 = lhcContinuein5;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)二、三連肖返点。
	 * @return 
	 */
	public BigDecimal getLhcContinuenotin23() {
		return lhcContinuenotin23;
	}

	/**
	 * 設定六合彩正特碼-連肖(不中)二、三連肖返点。
	 * @param lhcContinuenotin23 
	 */
	public void setLhcContinuenotin23(BigDecimal lhcContinuenotin23) {
		this.lhcContinuenotin23 = lhcContinuenotin23;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)四連肖返点。
	 * @return 
	 */
	public BigDecimal getLhcContinuenotin4() {
		return lhcContinuenotin4;
	}

	/**
	 * 設定六合彩正特碼-連肖(不中)四連肖返点。
	 * @param lhcContinuenotin4 
	 */
	public void setLhcContinuenotin4(BigDecimal lhcContinuenotin4) {
		this.lhcContinuenotin4 = lhcContinuenotin4;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)五連肖返点。
	 * @return 
	 */
	public BigDecimal getLhcContinuenotin5() {
		return lhcContinuenotin5;
	}

	/**
	 * 設定六合彩正特碼-連肖(不中)五連肖返点。
	 * @param lhcContinuenotin5 
	 */
	public void setLhcContinuenotin5(BigDecimal lhcContinuenotin5) {
		this.lhcContinuenotin5 = lhcContinuenotin5;
	}

	/**
	 * 取得六合彩連碼返点。
	 * @return 
	 */
	public BigDecimal getLhcContinuecode() {
		return lhcContinuecode;
	}

	/**
	 * 設定六合彩連碼返点。
	 * @param lhcContinuecode 
	 */
	public void setLhcContinuecode(BigDecimal lhcContinuecode) {
		this.lhcContinuecode = lhcContinuecode;
	}
	
	/**
	 * 取得六合彩特肖可分配返点数。
	 * @return
	 */
	public BigDecimal getLhcYearLimit() {
		return lhcYearLimit;
	}

	/**
	 * 設定六合彩特肖可分配返点数。
	 * @param lhcYearLimit
	 */
	public void setLhcYearLimit(BigDecimal lhcYearLimit) {
		this.lhcYearLimit = lhcYearLimit;
	}

	/**
	 * 取得六合彩色波兩面可分配返点数。
	 * @return
	 */
	public BigDecimal getLhcColorLimit() {
		return lhcColorLimit;
	}

	/**
	 * 設定六合彩色波兩面可分配返点数。
	 * @param lhcColorLimit
	 */
	public void setLhcColorLimit(BigDecimal lhcColorLimit) {
		this.lhcColorLimit = lhcColorLimit;
	}
	
	/**
	 * 取得骰寶猜一個號可分配返點數。
	 * @return
	 */
	public BigDecimal getSbThreeoneRetLimit() {
		return sbThreeoneRetLimit;
	}
	/**
	 * 設定骰寶猜一個號可分配返點數。
	 * @param sbThreeoneRetLimit
	 */
	public void setSbThreeoneRetLimit(BigDecimal sbThreeoneRetLimit) {
		this.sbThreeoneRetLimit = sbThreeoneRetLimit;
	}
	
	/**
	 * 取得六合彩正碼-平码可分配返点数。
	 * @return 
	 */
	public BigDecimal getLhcFlatcodeLimit() {
		return lhcFlatcodeLimit;
	}

	/**
	 * 設定六合彩正碼-平码可分配返点数。
	 * @param lhcFlatcodeLimit 
	 */
	public void setLhcFlatcodeLimit(BigDecimal lhcFlatcodeLimit) {
		this.lhcFlatcodeLimit = lhcFlatcodeLimit;
	}

	/**
	 * 取得六合彩特碼-半波可分配返点数。
	 * @return 
	 */
	public BigDecimal getLhcHalfwaveLimit() {
		return lhcHalfwaveLimit;
	}

	/**
	 * 設定六合彩特碼-半波可分配返点数。
	 * @param lhcHalfwaveLimit 
	 */
	public void setLhcHalfwaveLimit(BigDecimal lhcHalfwaveLimit) {
		this.lhcHalfwaveLimit = lhcHalfwaveLimit;
	}

	/**
	 * 取得六合彩正特碼-一肖可分配返点数。
	 * @return 
	 */
	public BigDecimal getLhcOneyearLimit() {
		return lhcOneyearLimit;
	}

	/**
	 * 設定六合彩正特碼-一肖可分配返点数。
	 * @param lhcOneyearLimit 
	 */
	public void setLhcOneyearLimit(BigDecimal lhcOneyearLimit) {
		this.lhcOneyearLimit = lhcOneyearLimit;
	}

	/**
	 * 取得六合彩正特碼-不中可分配返点数。
	 * @return 
	 */
	public BigDecimal getLhcNotinLimit() {
		return lhcNotinLimit;
	}

	/**
	 * 設定六合彩正特碼-不中可分配返点数。
	 * @param lhcNotinLimit 
	 */
	public void setLhcNotinLimit(BigDecimal lhcNotinLimit) {
		this.lhcNotinLimit = lhcNotinLimit;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)二、三連肖可分配返点数。
	 * @return 
	 */
	public BigDecimal getLhcContinuein23Limit() {
		return lhcContinuein23Limit;
	}

	/**
	 * 設定六合彩正特碼-連肖(中)二、三連肖可分配返点数。
	 * @param lhcContinuein23Limit 
	 */
	public void setLhcContinuein23Limit(BigDecimal lhcContinuein23Limit) {
		this.lhcContinuein23Limit = lhcContinuein23Limit;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)四連肖可分配返点数。
	 * @return 
	 */
	public BigDecimal getLhcContinuein4Limit() {
		return lhcContinuein4Limit;
	}

	/**
	 * 設定六合彩正特碼-連肖(中)四連肖可分配返点数。
	 * @param lhcContinuein4Limit 
	 */
	public void setLhcContinuein4Limit(BigDecimal lhcContinuein4Limit) {
		this.lhcContinuein4Limit = lhcContinuein4Limit;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)五連肖可分配返点数。
	 * @return 
	 */
	public BigDecimal getLhcContinuein5Limit() {
		return lhcContinuein5Limit;
	}

	/**
	 * 設定六合彩正特碼-連肖(中)五連肖可分配返点数。
	 * @param lhcContinuein5Limit 
	 */
	public void setLhcContinuein5Limit(BigDecimal lhcContinuein5Limit) {
		this.lhcContinuein5Limit = lhcContinuein5Limit;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)二、三連肖可分配返点数。
	 * @return 
	 */
	public BigDecimal getLhcContinuenotin23Limit() {
		return lhcContinuenotin23Limit;
	}

	/**
	 * 設定六合彩正特碼-連肖(不中)二、三連肖可分配返点数。
	 * @param lhcContinuenotin23Limit 
	 */
	public void setLhcContinuenotin23Limit(BigDecimal lhcContinuenotin23Limit) {
		this.lhcContinuenotin23Limit = lhcContinuenotin23Limit;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)四連肖可分配返点数。
	 * @return 
	 */
	public BigDecimal getLhcContinuenotin4Limit() {
		return lhcContinuenotin4Limit;
	}

	/**
	 * 設定六合彩正特碼-連肖(不中)四連肖可分配返点数。
	 * @param lhcContinuenotin4Limit 
	 */
	public void setLhcContinuenotin4Limit(BigDecimal lhcContinuenotin4Limit) {
		this.lhcContinuenotin4Limit = lhcContinuenotin4Limit;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)五連肖可分配返点数。
	 * @return 
	 */
	public BigDecimal getLhcContinuenotin5Limit() {
		return lhcContinuenotin5Limit;
	}

	/**
	 * 設定六合彩正特碼-連肖(不中)五連肖可分配返点数。
	 * @param lhcContinuenotin5Limit 
	 */
	public void setLhcContinuenotin5Limit(BigDecimal lhcContinuenotin5Limit) {
		this.lhcContinuenotin5Limit = lhcContinuenotin5Limit;
	}

	/**
	 * 取得六合彩連碼可分配返点数。
	 * @return 
	 */
	public BigDecimal getLhcContinuecodeLimit() {
		return lhcContinuecodeLimit;
	}

	/**
	 * 設定六合彩連碼可分配返点数。
	 * @param lhcContinuecodeLimit 
	 */
	public void setLhcContinuecodeLimit(BigDecimal lhcContinuecodeLimit) {
		this.lhcContinuecodeLimit = lhcContinuecodeLimit;
	}

	/**
	 * 取得六合彩特肖可分配返点数上限。
	 * @return
	 */
	public BigDecimal getMaxLhcYear() {
		return maxLhcYear;
	}

	/**
	 * 設定六合彩特肖可分配返点数上限。
	 * @param maxLhcYear
	 */
	public void setMaxLhcYear(BigDecimal maxLhcYear) {
		this.maxLhcYear = maxLhcYear;
	}

	/**
	 * 取得六合彩色波兩面可分配返点数上限。
	 * @return
	 */
	public BigDecimal getMaxLhcColor() {
		return maxLhcColor;
	}

	/**
	 * 設定六合彩色波兩面可分配返点数上限。
	 * @param maxLhcColor
	 */
	public void setMaxLhcColor(BigDecimal maxLhcColor) {
		this.maxLhcColor = maxLhcColor;
	}
	
	/**
	 * 取得骰寶猜一個號可分配返點數上限。
	 * @return
	 */
	public BigDecimal getMaxSbThreeoneRet() {
		return maxSbThreeoneRet;
	}
	/**
	 * 設定骰寶猜一個號可分配返點數上限。
	 * @param maxSbThreeoneRet
	 */
	public void setMaxSbThreeoneRet(BigDecimal maxSbThreeoneRet) {
		this.maxSbThreeoneRet = maxSbThreeoneRet;
	}

	/**
	 * 取得六合彩正碼-平码可分配返点数上限。
	 * @return 
	 */
	public BigDecimal getMaxLhcFlatcode() {
		return maxLhcFlatcode;
	}

	/**
	 * 設定六合彩正碼-平码可分配返点数上限。
	 * @param maxLhcFlatcode 
	 */
	public void setMaxLhcFlatcode(BigDecimal maxLhcFlatcode) {
		this.maxLhcFlatcode = maxLhcFlatcode;
	}

	/**
	 * 取得六合彩特碼-半波可分配返点数上限。
	 * @return 
	 */
	public BigDecimal getMaxLhcHalfwave() {
		return maxLhcHalfwave;
	}

	/**
	 * 設定六合彩特碼-半波可分配返点数上限。
	 * @param maxLhcHalfwave 
	 */
	public void setMaxLhcHalfwave(BigDecimal maxLhcHalfwave) {
		this.maxLhcHalfwave = maxLhcHalfwave;
	}

	/**
	 * 取得六合彩正特碼-一肖可分配返点数上限。
	 * @return 
	 */
	public BigDecimal getMaxLhcOneyear() {
		return maxLhcOneyear;
	}

	/**
	 * 設定六合彩正特碼-一肖可分配返点数上限。
	 * @param maxLhcOneyear 
	 */
	public void setMaxLhcOneyear(BigDecimal maxLhcOneyear) {
		this.maxLhcOneyear = maxLhcOneyear;
	}

	/**
	 * 取得六合彩正特碼-不中可分配返点数上限。
	 * @return 
	 */
	public BigDecimal getMaxLhcNotin() {
		return maxLhcNotin;
	}

	/**
	 * 設定六合彩正特碼-不中可分配返点数上限。
	 * @param maxLhcNotin 
	 */
	public void setMaxLhcNotin(BigDecimal maxLhcNotin) {
		this.maxLhcNotin = maxLhcNotin;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)二、三連肖可分配返点数上限。
	 * @return 
	 */
	public BigDecimal getMaxLhcContinuein23() {
		return maxLhcContinuein23;
	}

	/**
	 * 設定六合彩正特碼-連肖(中)二、三連肖可分配返点数上限。
	 * @param maxLhcContinuein23 
	 */
	public void setMaxLhcContinuein23(BigDecimal maxLhcContinuein23) {
		this.maxLhcContinuein23 = maxLhcContinuein23;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)四連肖可分配返点数上限。
	 * @return 
	 */
	public BigDecimal getMaxLhcContinuein4() {
		return maxLhcContinuein4;
	}

	/**
	 * 設定六合彩正特碼-連肖(中)四連肖可分配返点数上限。
	 * @param maxLhcContinuein4 
	 */
	public void setMaxLhcContinuein4(BigDecimal maxLhcContinuein4) {
		this.maxLhcContinuein4 = maxLhcContinuein4;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)五連肖可分配返点数上限。
	 * @return 
	 */
	public BigDecimal getMaxLhcContinuein5() {
		return maxLhcContinuein5;
	}

	/**
	 * 設定六合彩正特碼-連肖(中)五連肖可分配返点数上限。
	 * @param maxLhcContinuein5 
	 */
	public void setMaxLhcContinuein5(BigDecimal maxLhcContinuein5) {
		this.maxLhcContinuein5 = maxLhcContinuein5;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)二、三連肖可分配返点数上限。
	 * @return 
	 */
	public BigDecimal getMaxLhcContinuenotin23() {
		return maxLhcContinuenotin23;
	}

	/**
	 * 設定六合彩正特碼-連肖(不中)二、三連肖可分配返点数上限。
	 * @param maxLhcContinuenotin23 
	 */
	public void setMaxLhcContinuenotin23(BigDecimal maxLhcContinuenotin23) {
		this.maxLhcContinuenotin23 = maxLhcContinuenotin23;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)四連肖可分配返点数上限。
	 * @return 
	 */
	public BigDecimal getMaxLhcContinuenotin4() {
		return maxLhcContinuenotin4;
	}

	/**
	 * 設定六合彩正特碼-連肖(不中)四連肖可分配返点数上限。
	 * @param maxLhcContinuenotin4 
	 */
	public void setMaxLhcContinuenotin4(BigDecimal maxLhcContinuenotin4) {
		this.maxLhcContinuenotin4 = maxLhcContinuenotin4;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)五連肖可分配返点数上限。
	 * @return 
	 */
	public BigDecimal getMaxLhcContinuenotin5() {
		return maxLhcContinuenotin5;
	}

	/**
	 * 設定六合彩正特碼-連肖(不中)五連肖可分配返点数上限。
	 * @param maxLhcContinuenotin5 
	 */
	public void setMaxLhcContinuenotin5(BigDecimal maxLhcContinuenotin5) {
		this.maxLhcContinuenotin5 = maxLhcContinuenotin5;
	}

	/**
	 * 取得六合彩連碼可分配返点数上限。
	 * @return 
	 */
	public BigDecimal getMaxLhcContinuecode() {
		return maxLhcContinuecode;
	}

	/**
	 * 設定六合彩連碼可分配返点数上限。
	 * @param maxLhcContinuecode 
	 */
	public void setMaxLhcContinuecode(BigDecimal maxLhcContinuecode) {
		this.maxLhcContinuecode = maxLhcContinuecode;
	}
}
