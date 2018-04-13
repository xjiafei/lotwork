package com.winterframework.firefrog.phone.web.cotroller.dto;

public class GameGroup{
	/**彩系編碼*/
	private Long lotterySeriesCode;
	/**彩系名稱*/
	private String lotterySeriesName;
	/**獎金組ID*/
	private Long awardGroupId;
	/**獎金組名稱*/
	private String awardName;
	/**奖金组状态：0，禁用；1，启用*/
	private Long status;
	private String groupChain;
	private Long lotteryId;
	/**是否可投注；0:否、1:可*/
	private Long betType;
	/**奖金组链(总代的为null)*/
	private Long sysAwardGrouId;
	/**直选及其他返点(六合彩代表特碼-直選返點)*/
	private Long directRet;
	/**三星一码不定位返点*/
	private Long threeoneRet;
	/**六合彩特碼-特肖返点*/
	private Long lhcYear;
	/**六合彩特碼-兩面、色波返点*/
	private Long lhcColor;
	/**骰寶猜一個號返點*/
	private Long sbThreeoneRet;
	/**六合彩正碼-平码返点*/
	private Long lhcFlatcode;
	/**六合彩特碼-半波返点*/
	private Long lhcHalfwave;
	/**六合彩正特碼-一肖返点*/
	private Long lhcOneyear;
	/**六合彩正特碼-不中返点*/
	private Long lhcNotin;
	/**六合彩正特碼-連肖(中)二、三連肖返点*/
	private Long lhcContinuein23;
	/**六合彩正特碼-連肖(中)四連肖返点*/
	private Long lhcContinuein4;
	/**六合彩正特碼-連肖(中)五連肖返点*/
	private Long lhcContinuein5;
	/**六合彩正特碼-連肖(不中)二、三連肖返点*/
	private Long lhcContinuenotin23;
	/**六合彩正特碼-連肖(不中)四連肖返点*/
	private Long lhcContinuenotin4;
	/**六合彩正特碼-連肖(不中)五連肖返点*/
	private Long lhcContinuenotin5;
	/**六合彩連碼返点*/
	private Long lhcContinuecode;
	
	/**直选(六合彩直選)可分配返点数*/
	private Long directLimitRet;
	/**三码可分配返点数*/
	private Long threeLimitRet;
	/**六合彩特肖可分配返点数*/
	private Long lhcYearLimit;
	/**六合彩兩面色波可分配返点数*/
	private Long lhcColorLimit;
	/**骰寶猜一個號可分配返點數*/
	private Long sbThreeoneRetLimit;
	/**六合彩正碼-平码可分配返点数*/
	private Long lhcFlatcodeLimit;
	/**六合彩特碼-半波可分配返点数*/
	private Long lhcHalfwaveLimit;
	/**六合彩正特碼-一肖可分配返点数*/
	private Long lhcOneyearLimit;
	/**六合彩正特碼-不中可分配返点数*/
	private Long lhcNotinLimit;
	/**六合彩正特碼-連肖(中)二、三連肖可分配返点数*/
	private Long lhcContinuein23Limit;
	/**六合彩正特碼-連肖(中)四連肖可分配返点数*/
	private Long lhcContinuein4Limit;
	/**六合彩正特碼-連肖(中)五連肖可分配返点数*/
	private Long lhcContinuein5Limit;
	/**六合彩正特碼-連肖(不中)二、三連肖可分配返点数*/
	private Long lhcContinuenotin23Limit;
	/**六合彩正特碼-連肖(不中)四連肖可分配返点数*/
	private Long lhcContinuenotin4Limit;
	/**六合彩正特碼-連肖(不中)五連肖可分配返点数*/
	private Long lhcContinuenotin5Limit;
	/**六合彩連碼可分配返点数*/
	private Long lhcContinuecodeLimit;
	
	/** 直选可分配返点数上限 */
	private Integer maxDirectRet;
	/** 三码可分配返点数上限 */
	private Integer maxThreeOneRet;
	/**六合彩特肖可分配返点数上限*/
	private Long maxLhcYear;
	/**六合彩兩面色波可分配返点数上限*/
	private Long maxLhcColor;
	/**骰寶猜一個號可分配返點數上限*/
	private Long maxSbThreeoneRet;
	/**六合彩正碼-平码可分配返点数上限*/
	private Long maxLhcFlatcode;
	/**六合彩特碼-半波可分配返点数上限*/
	private Long maxLhcHalfwave;
	/**六合彩正特碼-一肖可分配返点数上限*/
	private Long maxLhcOneyear;
	/**六合彩正特碼-不中可分配返点数上限*/
	private Long maxLhcNotin;
	/**六合彩正特碼-連肖(中)二、三連肖可分配返点数上限*/
	private Long maxLhcContinuein23;
	/**六合彩正特碼-連肖(中)四連肖可分配返点数上限*/
	private Long maxLhcContinuein4;
	/**六合彩正特碼-連肖(中)五連肖可分配返点数上限*/
	private Long maxLhcContinuein5;
	/**六合彩正特碼-連肖(不中)二、三連肖可分配返点数上限*/
	private Long maxLhcContinuenotin23;
	/**六合彩正特碼-連肖(不中)四連肖可分配返点数上限*/
	private Long maxLhcContinuenotin4;
	/**六合彩正特碼-連肖(不中)五連肖可分配返点数上限*/
	private Long maxLhcContinuenotin5;
	/**六合彩連碼可分配返点数上限*/
	private Long maxLhcContinuecode;
	
	/**
	 * 取得直选可分配返点数上限。
	 * @return
	 */
	public Integer getMaxDirectRet() {
		return maxDirectRet;
	}
	/**
	 * 設定直选可分配返点数上限。
	 * @param maxDirectRet
	 */
	public void setMaxDirectRet(Integer maxDirectRet) {
		this.maxDirectRet = maxDirectRet;
	}
	/**
	 * 取得三码可分配返点数上限。
	 * @return
	 */
	public Integer getMaxThreeOneRet() {
		return maxThreeOneRet;
	}
	/**
	 * 設定三码可分配返点数上限。
	 * @param maxThreeOneRet
	 */
	public void setMaxThreeOneRet(Integer maxThreeOneRet) {
		this.maxThreeOneRet = maxThreeOneRet;
	}

	/**
	 * 取得奖金组链(总代的为null)。
	 * @return
	 */
	public Long getSysAwardGrouId() {
		return sysAwardGrouId;
	}

	/**
	 * 設定奖金组链(总代的为null)。
	 * @param sysAwardGrouId
	 */
	public void setSysAwardGrouId(Long sysAwardGrouId) {
		this.sysAwardGrouId = sysAwardGrouId;
	}
	/**
	 * 取得是否可投注。
	 * @return 0:否、1:可
	 */
	public Long getBetType() {
		return betType;
	}
	/**
	 * 設定是否可投注。
	 * @param betType 0:否、1:可
	 */
	public void setBetType(Long betType) {
		this.betType = betType;
	}
	/**
	 * 取得彩系編碼。
	 * @return
	 */
	public Long getLotterySeriesCode() {
		return lotterySeriesCode;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	/**
	 * 設定彩系編碼。
	 * @param lotterySeriesCode
	 */
	public void setLotterySeriesCode(Long lotterySeriesCode) {
		this.lotterySeriesCode = lotterySeriesCode;
	}
	/**
	 * 取得彩系名稱。
	 * @return
	 */
	public String getLotterySeriesName() {
		return lotterySeriesName;
	}
	/**
	 * 設定彩系名稱。
	 * @param lotterySeriesName
	 */
	public void setLotterySeriesName(String lotterySeriesName) {
		this.lotterySeriesName = lotterySeriesName;
	}
	/**
	 * 取得獎金組ID。
	 * @return
	 */
	public Long getAwardGroupId() {
		if(this.getSysAwardGrouId()!=null) return sysAwardGrouId;
		return awardGroupId;
	}
	/**
	 * 設定獎金組ID。
	 * @param awardGroupId
	 */
	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	/**
	 * 取得奖金组状态。
	 * @return 0，禁用；1，启用
	 */
	public Long getStatus() {
		return status;
	}
	
	public String getGroupChain() {
		return groupChain;
	}
	public void setGroupChain(String groupChain) {
		this.groupChain = groupChain;
	}
	/**
	 * 設定奖金组状态。
	 * @param status 0，禁用；1，启用
	 */
	public void setStatus(Long status) {
		this.status = status;
	}
	/**
	 * 取得直选及其他返点(六合彩代表特碼-直選返點)。
	 * @return
	 */
	public Long getDirectRet() {
		return directRet;
	}
	/**
	 * 設定直选及其他返点(六合彩代表特碼-直選返點)。
	 * @param directRet
	 */
	public void setDirectRet(Long directRet) {
		this.directRet = directRet;
	}
	/**
	 * 取得三星一码不定位返点。
	 * @return
	 */
	public Long getThreeoneRet() {
		return threeoneRet;
	}
	/**
	 * 設定三星一码不定位返点。
	 * @param threeoneRet
	 */
	public void setThreeoneRet(Long threeoneRet) {
		this.threeoneRet = threeoneRet;
	}
	/**
	 * 取得六合彩特碼-特肖返点。
	 * @return 
	 */
	public Long getLhcYear() {
		return lhcYear;
	}
	/**
	 * 設定六合彩特碼-特肖返点。
	 * @param lhcYear 
	 */
	public void setLhcYear(Long lhcYear) {
		this.lhcYear = lhcYear;
	}
	/**
	 * 取得六合彩特碼-兩面、色波返点。
	 * @return 
	 */
	public Long getLhcColor() {
		return lhcColor;
	}
	/**
	 * 設定六合彩特碼-兩面、色波返点。
	 * @param lhcColor 
	 */
	public void setLhcColor(Long lhcColor) {
		this.lhcColor = lhcColor;
	}
	/**
	 * 取得骰寶猜一個號返點。
	 * @return 
	 */
	public Long getSbThreeoneRet() {
		return sbThreeoneRet;
	}
	/**
	 * 設定骰寶猜一個號返點。
	 * @param sbThreeoneRet 
	 */
	public void setSbThreeoneRet(Long sbThreeoneRet) {
		this.sbThreeoneRet = sbThreeoneRet;
	}
	/**
	 * 取得六合彩正碼-平码返点。
	 * @return 
	 */
	public Long getLhcFlatcode() {
		return lhcFlatcode;
	}
	/**
	 * 設定六合彩正碼-平码返点。
	 * @param lhcFlatcode 
	 */
	public void setLhcFlatcode(Long lhcFlatcode) {
		this.lhcFlatcode = lhcFlatcode;
	}
	/**
	 * 取得六合彩特碼-半波返点。
	 * @return 
	 */
	public Long getLhcHalfwave() {
		return lhcHalfwave;
	}
	/**
	 * 設定六合彩特碼-半波返点。
	 * @param lhcHalfwave 
	 */
	public void setLhcHalfwave(Long lhcHalfwave) {
		this.lhcHalfwave = lhcHalfwave;
	}
	/**
	 * 取得六合彩正特碼-一肖返点。
	 * @return 
	 */
	public Long getLhcOneyear() {
		return lhcOneyear;
	}
	/**
	 * 設定六合彩正特碼-一肖返点。
	 * @param lhcOneyear 
	 */
	public void setLhcOneyear(Long lhcOneyear) {
		this.lhcOneyear = lhcOneyear;
	}
	/**
	 * 取得六合彩正特碼-不中返点。
	 * @return 
	 */
	public Long getLhcNotin() {
		return lhcNotin;
	}
	/**
	 * 設定六合彩正特碼-不中返点。
	 * @param lhcNotin 
	 */
	public void setLhcNotin(Long lhcNotin) {
		this.lhcNotin = lhcNotin;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)二、三連肖返点。
	 * @return 
	 */
	public Long getLhcContinuein23() {
		return lhcContinuein23;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)二、三連肖返点。
	 * @param lhcContinuein23 
	 */
	public void setLhcContinuein23(Long lhcContinuein23) {
		this.lhcContinuein23 = lhcContinuein23;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)四連肖返点。
	 * @return 
	 */
	public Long getLhcContinuein4() {
		return lhcContinuein4;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)四連肖返点。
	 * @param lhcContinuein4 
	 */
	public void setLhcContinuein4(Long lhcContinuein4) {
		this.lhcContinuein4 = lhcContinuein4;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)五連肖返点。
	 * @return 
	 */
	public Long getLhcContinuein5() {
		return lhcContinuein5;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)五連肖返点。
	 * @param lhcContinuein5 
	 */
	public void setLhcContinuein5(Long lhcContinuein5) {
		this.lhcContinuein5 = lhcContinuein5;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)二、三連肖返点。
	 * @return 
	 */
	public Long getLhcContinuenotin23() {
		return lhcContinuenotin23;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)二、三連肖返点。
	 * @param lhcContinuenotin23 
	 */
	public void setLhcContinuenotin23(Long lhcContinuenotin23) {
		this.lhcContinuenotin23 = lhcContinuenotin23;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)四連肖返点。
	 * @return 
	 */
	public Long getLhcContinuenotin4() {
		return lhcContinuenotin4;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)四連肖返点。
	 * @param lhcContinuenotin4 
	 */
	public void setLhcContinuenotin4(Long lhcContinuenotin4) {
		this.lhcContinuenotin4 = lhcContinuenotin4;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)五連肖返点。
	 * @return 
	 */
	public Long getLhcContinuenotin5() {
		return lhcContinuenotin5;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)五連肖返点。
	 * @param lhcContinuenotin5 
	 */
	public void setLhcContinuenotin5(Long lhcContinuenotin5) {
		this.lhcContinuenotin5 = lhcContinuenotin5;
	}
	/**
	 * 取得六合彩連碼返点。
	 * @return 
	 */
	public Long getLhcContinuecode() {
		return lhcContinuecode;
	}
	/**
	 * 設定六合彩連碼返点。
	 * @param lhcContinuecode 
	 */
	public void setLhcContinuecode(Long lhcContinuecode) {
		this.lhcContinuecode = lhcContinuecode;
	}
	/**
	 * 取得直选(六合彩直選)可分配返点数。
	 * @return
	 */
	public Long getDirectLimitRet() {
		return directLimitRet;
	}
	/**
	 * 設定直选(六合彩直選)可分配返点数。
	 * @param directLimitRet
	 */
	public void setDirectLimitRet(Long directLimitRet) {
		this.directLimitRet = directLimitRet;
	}
	/**
	 * 取得三码可分配返点数。
	 * @return
	 */
	public Long getThreeLimitRet() {
		return threeLimitRet;
	}
	/**
	 * 設定三码可分配返点数。
	 * @param threeLimitRet
	 */
	public void setThreeLimitRet(Long threeLimitRet) {
		this.threeLimitRet = threeLimitRet;
	}
	/**
	 * 取得六合彩特肖可分配返点数。
	 * @return 
	 */
	public Long getLhcYearLimit() {
		return lhcYearLimit;
	}
	/**
	 * 設定六合彩特肖可分配返点数。
	 * @param lhcYearLimit 
	 */
	public void setLhcYearLimit(Long lhcYearLimit) {
		this.lhcYearLimit = lhcYearLimit;
	}
	/**
	 * 取得六合彩兩面色波可分配返点数。
	 * @return 
	 */
	public Long getLhcColorLimit() {
		return lhcColorLimit;
	}
	/**
	 * 設定六合彩兩面色波可分配返点数。
	 * @param lhcColorLimit 
	 */
	public void setLhcColorLimit(Long lhcColorLimit) {
		this.lhcColorLimit = lhcColorLimit;
	}
	/**
	 * 取得骰寶猜一個號可分配返點數。
	 * @return 
	 */
	public Long getSbThreeoneRetLimit() {
		return sbThreeoneRetLimit;
	}
	/**
	 * 設定骰寶猜一個號可分配返點數。
	 * @param sbThreeoneRetLimit 
	 */
	public void setSbThreeoneRetLimit(Long sbThreeoneRetLimit) {
		this.sbThreeoneRetLimit = sbThreeoneRetLimit;
	}
	/**
	 * 取得六合彩正碼-平码可分配返点数。
	 * @return 
	 */
	public Long getLhcFlatcodeLimit() {
		return lhcFlatcodeLimit;
	}
	/**
	 * 設定六合彩正碼-平码可分配返点数。
	 * @param lhcFlatcodeLimit 
	 */
	public void setLhcFlatcodeLimit(Long lhcFlatcodeLimit) {
		this.lhcFlatcodeLimit = lhcFlatcodeLimit;
	}
	/**
	 * 取得六合彩特碼-半波可分配返点数。
	 * @return 
	 */
	public Long getLhcHalfwaveLimit() {
		return lhcHalfwaveLimit;
	}
	/**
	 * 設定六合彩特碼-半波可分配返点数。
	 * @param lhcHalfwaveLimit 
	 */
	public void setLhcHalfwaveLimit(Long lhcHalfwaveLimit) {
		this.lhcHalfwaveLimit = lhcHalfwaveLimit;
	}
	/**
	 * 取得六合彩正特碼-一肖可分配返点数。
	 * @return 
	 */
	public Long getLhcOneyearLimit() {
		return lhcOneyearLimit;
	}
	/**
	 * 設定六合彩正特碼-一肖可分配返点数。
	 * @param lhcOneyearLimit 
	 */
	public void setLhcOneyearLimit(Long lhcOneyearLimit) {
		this.lhcOneyearLimit = lhcOneyearLimit;
	}
	/**
	 * 取得六合彩正特碼-不中可分配返点数。
	 * @return 
	 */
	public Long getLhcNotinLimit() {
		return lhcNotinLimit;
	}
	/**
	 * 設定六合彩正特碼-不中可分配返点数。
	 * @param lhcNotinLimit 
	 */
	public void setLhcNotinLimit(Long lhcNotinLimit) {
		this.lhcNotinLimit = lhcNotinLimit;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)二、三連肖可分配返点数。
	 * @return 
	 */
	public Long getLhcContinuein23Limit() {
		return lhcContinuein23Limit;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)二、三連肖可分配返点数。
	 * @param lhcContinuein23Limit 
	 */
	public void setLhcContinuein23Limit(Long lhcContinuein23Limit) {
		this.lhcContinuein23Limit = lhcContinuein23Limit;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)四連肖可分配返点数。
	 * @return 
	 */
	public Long getLhcContinuein4Limit() {
		return lhcContinuein4Limit;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)四連肖可分配返点数。
	 * @param lhcContinuein4Limit 
	 */
	public void setLhcContinuein4Limit(Long lhcContinuein4Limit) {
		this.lhcContinuein4Limit = lhcContinuein4Limit;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)五連肖可分配返点数。
	 * @return 
	 */
	public Long getLhcContinuein5Limit() {
		return lhcContinuein5Limit;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)五連肖可分配返点数。
	 * @param lhcContinuein5Limit 
	 */
	public void setLhcContinuein5Limit(Long lhcContinuein5Limit) {
		this.lhcContinuein5Limit = lhcContinuein5Limit;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)二、三連肖可分配返点数。
	 * @return 
	 */
	public Long getLhcContinuenotin23Limit() {
		return lhcContinuenotin23Limit;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)二、三連肖可分配返点数。
	 * @param lhcContinuenotin23Limit 
	 */
	public void setLhcContinuenotin23Limit(Long lhcContinuenotin23Limit) {
		this.lhcContinuenotin23Limit = lhcContinuenotin23Limit;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)四連肖可分配返点数。
	 * @return 
	 */
	public Long getLhcContinuenotin4Limit() {
		return lhcContinuenotin4Limit;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)四連肖可分配返点数。
	 * @param lhcContinuenotin4Limit 
	 */
	public void setLhcContinuenotin4Limit(Long lhcContinuenotin4Limit) {
		this.lhcContinuenotin4Limit = lhcContinuenotin4Limit;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)五連肖可分配返点数。
	 * @return 
	 */
	public Long getLhcContinuenotin5Limit() {
		return lhcContinuenotin5Limit;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)五連肖可分配返点数。
	 * @param lhcContinuenotin5Limit 
	 */
	public void setLhcContinuenotin5Limit(Long lhcContinuenotin5Limit) {
		this.lhcContinuenotin5Limit = lhcContinuenotin5Limit;
	}
	/**
	 * 取得六合彩連碼可分配返点数。
	 * @return 
	 */
	public Long getLhcContinuecodeLimit() {
		return lhcContinuecodeLimit;
	}
	/**
	 * 設定六合彩連碼可分配返点数。
	 * @param lhcContinuecodeLimit 
	 */
	public void setLhcContinuecodeLimit(Long lhcContinuecodeLimit) {
		this.lhcContinuecodeLimit = lhcContinuecodeLimit;
	}
	/**
	 * 取得六合彩特肖可分配返点数上限。
	 * @return 
	 */
	public Long getMaxLhcYear() {
		return maxLhcYear;
	}
	/**
	 * 設定六合彩特肖可分配返点数上限。
	 * @param maxLhcYear 
	 */
	public void setMaxLhcYear(Long maxLhcYear) {
		this.maxLhcYear = maxLhcYear;
	}
	/**
	 * 取得六合彩兩面色波可分配返点数上限。
	 * @return 
	 */
	public Long getMaxLhcColor() {
		return maxLhcColor;
	}
	/**
	 * 設定六合彩兩面色波可分配返点数上限。
	 * @param maxLhcColor 
	 */
	public void setMaxLhcColor(Long maxLhcColor) {
		this.maxLhcColor = maxLhcColor;
	}
	/**
	 * 取得骰寶猜一個號可分配返點數上限。
	 * @return 
	 */
	public Long getMaxSbThreeoneRet() {
		return maxSbThreeoneRet;
	}
	/**
	 * 設定骰寶猜一個號可分配返點數上限。
	 * @param maxSbThreeoneRet 
	 */
	public void setMaxSbThreeoneRet(Long maxSbThreeoneRet) {
		this.maxSbThreeoneRet = maxSbThreeoneRet;
	}
	/**
	 * 取得六合彩正碼-平码可分配返点数上限。
	 * @return 
	 */
	public Long getMaxLhcFlatcode() {
		return maxLhcFlatcode;
	}
	/**
	 * 設定六合彩正碼-平码可分配返点数上限。
	 * @param maxLhcFlatcode 
	 */
	public void setMaxLhcFlatcode(Long maxLhcFlatcode) {
		this.maxLhcFlatcode = maxLhcFlatcode;
	}
	/**
	 * 取得六合彩特碼-半波可分配返点数上限。
	 * @return 
	 */
	public Long getMaxLhcHalfwave() {
		return maxLhcHalfwave;
	}
	/**
	 * 設定六合彩特碼-半波可分配返点数上限。
	 * @param maxLhcHalfwave 
	 */
	public void setMaxLhcHalfwave(Long maxLhcHalfwave) {
		this.maxLhcHalfwave = maxLhcHalfwave;
	}
	/**
	 * 取得六合彩正特碼-一肖可分配返点数上限。
	 * @return 
	 */
	public Long getMaxLhcOneyear() {
		return maxLhcOneyear;
	}
	/**
	 * 設定六合彩正特碼-一肖可分配返点数上限。
	 * @param maxLhcOneyear 
	 */
	public void setMaxLhcOneyear(Long maxLhcOneyear) {
		this.maxLhcOneyear = maxLhcOneyear;
	}
	/**
	 * 取得六合彩正特碼-不中可分配返点数上限。
	 * @return 
	 */
	public Long getMaxLhcNotin() {
		return maxLhcNotin;
	}
	/**
	 * 設定六合彩正特碼-不中可分配返点数上限。
	 * @param maxLhcNotin 
	 */
	public void setMaxLhcNotin(Long maxLhcNotin) {
		this.maxLhcNotin = maxLhcNotin;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)二、三連肖可分配返点数上限。
	 * @return 
	 */
	public Long getMaxLhcContinuein23() {
		return maxLhcContinuein23;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)二、三連肖可分配返点数上限。
	 * @param maxLhcContinuein23 
	 */
	public void setMaxLhcContinuein23(Long maxLhcContinuein23) {
		this.maxLhcContinuein23 = maxLhcContinuein23;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)四連肖可分配返点数上限。
	 * @return 
	 */
	public Long getMaxLhcContinuein4() {
		return maxLhcContinuein4;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)四連肖可分配返点数上限。
	 * @param maxLhcContinuein4 
	 */
	public void setMaxLhcContinuein4(Long maxLhcContinuein4) {
		this.maxLhcContinuein4 = maxLhcContinuein4;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)五連肖可分配返点数上限。
	 * @return 
	 */
	public Long getMaxLhcContinuein5() {
		return maxLhcContinuein5;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)五連肖可分配返点数上限。
	 * @param maxLhcContinuein5 
	 */
	public void setMaxLhcContinuein5(Long maxLhcContinuein5) {
		this.maxLhcContinuein5 = maxLhcContinuein5;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)二、三連肖可分配返点数上限。
	 * @return 
	 */
	public Long getMaxLhcContinuenotin23() {
		return maxLhcContinuenotin23;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)二、三連肖可分配返点数上限。
	 * @param maxLhcContinuenotin23 
	 */
	public void setMaxLhcContinuenotin23(Long maxLhcContinuenotin23) {
		this.maxLhcContinuenotin23 = maxLhcContinuenotin23;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)四連肖可分配返点数上限。
	 * @return 
	 */
	public Long getMaxLhcContinuenotin4() {
		return maxLhcContinuenotin4;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)四連肖可分配返点数上限。
	 * @param maxLhcContinuenotin4 
	 */
	public void setMaxLhcContinuenotin4(Long maxLhcContinuenotin4) {
		this.maxLhcContinuenotin4 = maxLhcContinuenotin4;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)五連肖可分配返点数上限。
	 * @return 
	 */
	public Long getMaxLhcContinuenotin5() {
		return maxLhcContinuenotin5;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)五連肖可分配返点数上限。
	 * @param maxLhcContinuenotin5 
	 */
	public void setMaxLhcContinuenotin5(Long maxLhcContinuenotin5) {
		this.maxLhcContinuenotin5 = maxLhcContinuenotin5;
	}
	/**
	 * 取得六合彩連碼可分配返点数上限。
	 * @return 
	 */
	public Long getMaxLhcContinuecode() {
		return maxLhcContinuecode;
	}
	/**
	 * 設定六合彩連碼可分配返点数上限。
	 * @param maxLhcContinuecode 
	 */
	public void setMaxLhcContinuecode(Long maxLhcContinuecode) {
		this.maxLhcContinuecode = maxLhcContinuecode;
	}
}
