package com.winterframework.firefrog.user.web.controller.game;


public class GameGroup{

	private Long lotterySeriesCode;
	private String lotterySeriesName;
	private Long awardGroupId;
	private String awardName;
	private Long directRet;
	private Long threeoneRet;
	/**超级对子返点*/
	private Long superRet;
	private Long status;
	private Long directLimitRet;
	private String groupChain;
	private Long threeLimitRet;
	/**超级对子返点数*/
	private Long superLimitRet;
	private Long lotteryId;
	private Long betType;
	/**奖金组链(总代的为null)*/
	private Long sysAwardGrouId;
	/** 直选可分配返點数上限 */
	private Long maxDirectRet; 
	/** 三码可分配返點数上限 */
	private Long maxThreeOneRet;
	/**超级对子返点数上限*/
	private Long maxSuperRet;
	/**奖金组奖金*/
	private Long award;
	/**理论奖金*/
	private Long theoryAward;
	/**六合彩特肖返點*/
	private Long lhcYear;
	/**六合彩色波兩面返點*/
	private Long lhcColor;
	/**骰寶猜一個號返點*/
	private Long sbThreeoneRet;
	/**六合彩正碼-平码返點*/
	private Long lhcFlatcode;
	/**六合彩特碼-半波返點*/
	private Long lhcHalfwave;
	/**六合彩正特碼-一肖返點*/
	private Long lhcOneyear;
	/**六合彩正特碼-不中返點*/
	private Long lhcNotin;
	/**六合彩正特碼-連肖(中)二、三連肖返點*/
	private Long lhcContinuein23;
	/**六合彩正特碼-連肖(中)四連肖返點*/
	private Long lhcContinuein4;
	/**六合彩正特碼-連肖(中)五連肖返點*/
	private Long lhcContinuein5;
	/**六合彩正特碼-連肖(不中)二、三連肖返點*/
	private Long lhcContinuenotin23;
	/**六合彩正特碼-連肖(不中)四連肖返點*/
	private Long lhcContinuenotin4;
	/**六合彩正特碼-連肖(不中)五連肖返點*/
	private Long lhcContinuenotin5;
	/**六合彩連碼返點*/
	private Long lhcContinuecode;
	
	/**六合彩特肖可分配返点数*/
	private Long lhcYearLimit;
	/**六合彩色波兩面可分配返点数*/
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
	
	/**六合彩特肖可分配返点数上限*/
	private Long maxLhcYear;
	/**六合彩色波兩面可分配返点数上限*/
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
	
	public Long getMaxDirectRet() {
		return maxDirectRet;
	}

	public void setMaxDirectRet(Long maxDirectRet) {
		this.maxDirectRet = maxDirectRet;
	}

	public Long getMaxThreeOneRet() {
		return maxThreeOneRet;
	}

	public void setMaxThreeOneRet(Long maxThreeOneRet) {
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

	public Long getBetType() {
		return betType;
	}

	public void setBetType(Long betType) {
		this.betType = betType;
	}

	public Long getLotterySeriesCode() {
		return lotterySeriesCode;
	}
	
	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public void setLotterySeriesCode(Long lotterySeriesCode) {
		this.lotterySeriesCode = lotterySeriesCode;
	}
	public String getLotterySeriesName() {
		return lotterySeriesName;
	}
	public void setLotterySeriesName(String lotterySeriesName) {
		this.lotterySeriesName = lotterySeriesName;
	}
	public Long getAwardGroupId() {
		if(this.getSysAwardGrouId()!=null) return sysAwardGrouId;
		return awardGroupId;
	}
	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public Long getDirectRet() {
		return directRet;
	}
	public void setDirectRet(Long directRet) {
		this.directRet = directRet;
	}
	public Long getThreeoneRet() {
		return threeoneRet;
	}
	public void setThreeoneRet(Long threeoneRet) {
		this.threeoneRet = threeoneRet;
	}
	public Long getStatus() {
		return status;
	}
	
	public String getGroupChain() {
		return groupChain;
	}
	public void setGroupChain(String groupChain) {
		this.groupChain = groupChain;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getDirectLimitRet() {
		return directLimitRet;
	}
	public void setDirectLimitRet(Long directLimitRet) {
		this.directLimitRet = directLimitRet;
	}
	public Long getThreeLimitRet() {
		return threeLimitRet;
	}
	public void setThreeLimitRet(Long threeLimitRet) {
		this.threeLimitRet = threeLimitRet;
	}

	/**
	 * 取得奖金组奖金。
	 * @return
	 */
	public Long getAward() {
		return award;
	}

	/**
	 * 設定奖金组奖金。
	 * @param award
	 */
	public void setAward(Long award) {
		this.award = award;
	}

	/**
	 * 取得理论奖金。
	 * @return
	 */
	public Long getTheoryAward() {
		return theoryAward;
	}

	/**
	 * 設定理论奖金。
	 * @param theoryAward
	 */
	public void setTheoryAward(Long theoryAward) {
		this.theoryAward = theoryAward;
	}

	/**
	 * 取得超级对子返点。
	 * @return
	 */
	public Long getSuperRet() {
		return superRet;
	}

	/**
	 * 設定超级对子返点。
	 * @param superRet
	 */
	public void setSuperRet(Long superRet) {
		this.superRet = superRet;
	}

	/**
	 * 取得超级对子返点数。
	 * @return
	 */
	public Long getSuperLimitRet() {
		return superLimitRet;
	}

	/**
	 * 設定超级对子返点数。
	 * @param superLimitRet
	 */
	public void setSuperLimitRet(Long superLimitRet) {
		this.superLimitRet = superLimitRet;
	}

	/**
	 * 取得超级对子返点数上限。
	 * @return
	 */
	public Long getMaxSuperRet() {
		return maxSuperRet;
	}

	/**
	 * 設定超级对子返点数上限。
	 * @param maxSuperRet
	 */
	public void setMaxSuperRet(Long maxSuperRet) {
		this.maxSuperRet = maxSuperRet;
	}

	/**
	 * 取得六合彩特肖返點。
	 * @return
	 */
	public Long getLhcYear() {
		return lhcYear;
	}
	/**
	 * 設定六合彩特肖返點。
	 * @param lhcYear
	 */
	public void setLhcYear(Long lhcYear) {
		this.lhcYear = lhcYear;
	}
	/**
	 * 取得六合彩色波兩面返點。
	 * @return
	 */
	public Long getLhcColor() {
		return lhcColor;
	}
	/**
	 * 設定六合彩色波兩面返點。
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
	 * 取得六合彩正碼-平码返點。
	 * @return 
	 */
	public Long getLhcFlatcode() {
		return lhcFlatcode;
	}

	/**
	 * 設定六合彩正碼-平码返點。
	 * @param lhcFlatcode 
	 */
	public void setLhcFlatcode(Long lhcFlatcode) {
		this.lhcFlatcode = lhcFlatcode;
	}

	/**
	 * 取得六合彩特碼-半波返點。
	 * @return 
	 */
	public Long getLhcHalfwave() {
		return lhcHalfwave;
	}

	/**
	 * 設定六合彩特碼-半波返點。
	 * @param lhcHalfwave 
	 */
	public void setLhcHalfwave(Long lhcHalfwave) {
		this.lhcHalfwave = lhcHalfwave;
	}

	/**
	 * 取得六合彩正特碼-一肖返點。
	 * @return 
	 */
	public Long getLhcOneyear() {
		return lhcOneyear;
	}

	/**
	 * 設定六合彩正特碼-一肖返點。
	 * @param lhcOneyear 
	 */
	public void setLhcOneyear(Long lhcOneyear) {
		this.lhcOneyear = lhcOneyear;
	}

	/**
	 * 取得六合彩正特碼-不中返點。
	 * @return 
	 */
	public Long getLhcNotin() {
		return lhcNotin;
	}

	/**
	 * 設定六合彩正特碼-不中返點。
	 * @param lhcNotin 
	 */
	public void setLhcNotin(Long lhcNotin) {
		this.lhcNotin = lhcNotin;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)二、三連肖返點。
	 * @return 
	 */
	public Long getLhcContinuein23() {
		return lhcContinuein23;
	}

	/**
	 * 設定六合彩正特碼-連肖(中)二、三連肖返點。
	 * @param lhcContinuein23 
	 */
	public void setLhcContinuein23(Long lhcContinuein23) {
		this.lhcContinuein23 = lhcContinuein23;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)四連肖返點。
	 * @return 
	 */
	public Long getLhcContinuein4() {
		return lhcContinuein4;
	}

	/**
	 * 設定六合彩正特碼-連肖(中)四連肖返點。
	 * @param lhcContinuein4 
	 */
	public void setLhcContinuein4(Long lhcContinuein4) {
		this.lhcContinuein4 = lhcContinuein4;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)五連肖返點。
	 * @return 
	 */
	public Long getLhcContinuein5() {
		return lhcContinuein5;
	}

	/**
	 * 設定六合彩正特碼-連肖(中)五連肖返點。
	 * @param lhcContinuein5 
	 */
	public void setLhcContinuein5(Long lhcContinuein5) {
		this.lhcContinuein5 = lhcContinuein5;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)二、三連肖返點。
	 * @return 
	 */
	public Long getLhcContinuenotin23() {
		return lhcContinuenotin23;
	}

	/**
	 * 設定六合彩正特碼-連肖(不中)二、三連肖返點。
	 * @param lhcContinuenotin23 
	 */
	public void setLhcContinuenotin23(Long lhcContinuenotin23) {
		this.lhcContinuenotin23 = lhcContinuenotin23;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)四連肖返點。
	 * @return 
	 */
	public Long getLhcContinuenotin4() {
		return lhcContinuenotin4;
	}

	/**
	 * 設定六合彩正特碼-連肖(不中)四連肖返點。
	 * @param lhcContinuenotin4 
	 */
	public void setLhcContinuenotin4(Long lhcContinuenotin4) {
		this.lhcContinuenotin4 = lhcContinuenotin4;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)五連肖返點。
	 * @return 
	 */
	public Long getLhcContinuenotin5() {
		return lhcContinuenotin5;
	}

	/**
	 * 設定六合彩正特碼-連肖(不中)五連肖返點。
	 * @param lhcContinuenotin5 
	 */
	public void setLhcContinuenotin5(Long lhcContinuenotin5) {
		this.lhcContinuenotin5 = lhcContinuenotin5;
	}

	/**
	 * 取得六合彩連碼返點。
	 * @return 
	 */
	public Long getLhcContinuecode() {
		return lhcContinuecode;
	}

	/**
	 * 設定六合彩連碼返點。
	 * @param lhcContinuecode 
	 */
	public void setLhcContinuecode(Long lhcContinuecode) {
		this.lhcContinuecode = lhcContinuecode;
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
	 * 取得六合彩色波兩面可分配返点数。
	 * @return
	 */
	public Long getLhcColorLimit() {
		return lhcColorLimit;
	}

	/**
	 * 設定六合彩色波兩面可分配返点数。
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
	 * 取得六合彩色波兩面可分配返点数上限。
	 * @return
	 */
	public Long getMaxLhcColor() {
		return maxLhcColor;
	}

	/**
	 * 設定六合彩色波兩面可分配返点数上限。
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
	 * 取得六合彩正碼-平码可分配返點數上限。
	 * @return maxLhcFlatcode
	 */
	public Long getMaxLhcFlatcode() {
		return maxLhcFlatcode;
	}

	/**
	 * 設置六合彩正碼-平码可分配返點數上限。
	 * @param maxLhcFlatcode
	 */
	public void setMaxLhcFlatcode(Long maxLhcFlatcode) {
		this.maxLhcFlatcode = maxLhcFlatcode;
	}

	/**
	 * 取得六合彩特碼-半波可分配返點數上限。
	 * @return maxLhcHalfwave
	 */
	public Long getMaxLhcHalfwave() {
		return maxLhcHalfwave;
	}

	/**
	 * 設置六合彩特碼-半波可分配返點數上限。
	 * @param maxLhcHalfwave
	 */
	public void setMaxLhcHalfwave(Long maxLhcHalfwave) {
		this.maxLhcHalfwave = maxLhcHalfwave;
	}

	/**
	 * 取得六合彩正特碼-一肖可分配返點數上限。
	 * @return maxLhcOneyear
	 */
	public Long getMaxLhcOneyear() {
		return maxLhcOneyear;
	}

	/**
	 * 設置六合彩正特碼-一肖可分配返點數上限。
	 * @param maxLhcOneyear
	 */
	public void setMaxLhcOneyear(Long maxLhcOneyear) {
		this.maxLhcOneyear = maxLhcOneyear;
	}

	/**
	 * 取得六合彩正特碼-不中可分配返點數上限。
	 * @return maxLhcNotin
	 */
	public Long getMaxLhcNotin() {
		return maxLhcNotin;
	}

	/**
	 * 設置六合彩正特碼-不中可分配返點數上限。
	 * @param maxLhcNotin
	 */
	public void setMaxLhcNotin(Long maxLhcNotin) {
		this.maxLhcNotin = maxLhcNotin;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)二、三連肖可分配返點數上限。
	 * @return maxLhcContinuein23
	 */
	public Long getMaxLhcContinuein23() {
		return maxLhcContinuein23;
	}

	/**
	 * 設置六合彩正特碼-連肖(中)二、三連肖可分配返點數上限。
	 * @param maxLhcContinuein23
	 */
	public void setMaxLhcContinuein23(Long maxLhcContinuein23) {
		this.maxLhcContinuein23 = maxLhcContinuein23;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)四連肖可分配返點數上限。
	 * @return maxLhcContinuein4
	 */
	public Long getMaxLhcContinuein4() {
		return maxLhcContinuein4;
	}

	/**
	 * 設置六合彩正特碼-連肖(中)四連肖可分配返點數上限。
	 * @param maxLhcContinuein4
	 */
	public void setMaxLhcContinuein4(Long maxLhcContinuein4) {
		this.maxLhcContinuein4 = maxLhcContinuein4;
	}

	/**
	 * 取得六合彩正特碼-連肖(中)五連肖可分配返點數上限。
	 * @return maxLhcContinuein5
	 */
	public Long getMaxLhcContinuein5() {
		return maxLhcContinuein5;
	}

	/**
	 * 設置六合彩正特碼-連肖(中)五連肖可分配返點數上限。
	 * @param maxLhcContinuein5
	 */
	public void setMaxLhcContinuein5(Long maxLhcContinuein5) {
		this.maxLhcContinuein5 = maxLhcContinuein5;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)二、三連肖可分配返點數上限。
	 * @return maxLhcContinuenotin23
	 */
	public Long getMaxLhcContinuenotin23() {
		return maxLhcContinuenotin23;
	}

	/**
	 * 設置六合彩正特碼-連肖(不中)二、三連肖可分配返點數上限。
	 * @param maxLhcContinuenotin23
	 */
	public void setMaxLhcContinuenotin23(Long maxLhcContinuenotin23) {
		this.maxLhcContinuenotin23 = maxLhcContinuenotin23;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)四連肖可分配返點數上限。
	 * @return maxLhcContinuenotin4
	 */
	public Long getMaxLhcContinuenotin4() {
		return maxLhcContinuenotin4;
	}

	/**
	 * 設置六合彩正特碼-連肖(不中)四連肖可分配返點數上限。
	 * @param maxLhcContinuenotin4
	 */
	public void setMaxLhcContinuenotin4(Long maxLhcContinuenotin4) {
		this.maxLhcContinuenotin4 = maxLhcContinuenotin4;
	}

	/**
	 * 取得六合彩正特碼-連肖(不中)五連肖可分配返點數上限。
	 * @return maxLhcContinuenotin5
	 */
	public Long getMaxLhcContinuenotin5() {
		return maxLhcContinuenotin5;
	}

	/**
	 * 設置六合彩正特碼-連肖(不中)五連肖可分配返點數上限。
	 * @param maxLhcContinuenotin5
	 */
	public void setMaxLhcContinuenotin5(Long maxLhcContinuenotin5) {
		this.maxLhcContinuenotin5 = maxLhcContinuenotin5;
	}

	/**
	 * 取得六合彩連碼可分配返點數上限。
	 * @return maxLhcContinuecode
	 */
	public Long getMaxLhcContinuecode() {
		return maxLhcContinuecode;
	}

	/**
	 * 設置六合彩連碼可分配返點數上限。
	 * @param maxLhcContinuecode
	 */
	public void setMaxLhcContinuecode(Long maxLhcContinuecode) {
		this.maxLhcContinuecode = maxLhcContinuecode;
	}
}
