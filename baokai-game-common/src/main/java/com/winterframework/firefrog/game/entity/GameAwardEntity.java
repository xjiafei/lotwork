package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;

/**
 * 獎金組明細。
 * @author Pogi.Lin
 */
public class GameAwardEntity implements Serializable {

	private static final long serialVersionUID = -8764411592104089122L;

	private Lottery lottery;
	/**玩法类型实例*/
	private GameBetType gameBetType;
	/**實際獎金(賠率)*/
	private BigDecimal actualBonus;
	/**理论奖金*/
	private BigDecimal theoryBonus;
	/**獎金組ID*/
	private Long awardGroupId;
	/**創建時間*/
	private Date createTime;
	/**更新時間*/
	private Date updateTime;
	/**保底獎金組最小獎金(雙色球使用)*/
	private BigDecimal actualBonusDown;
	/**三星一码不定位返点*/
	private BigDecimal threeoneRet;
	/**直选及其他返点*/
	private BigDecimal directRet;
	/**超级对子返点*/
	private BigDecimal superRet;
	/**公司最低留水*/
	private BigDecimal miniLotteryProfit;
	/**总代返点*/
	private BigDecimal topAgentPoint;
	/**公司留水*/
	private BigDecimal companyProfit;
	/**返點值*/
	private Long retValue;
	/**返点值上限*/
	private Long maxRetValue;
	/**玩法群標題*/
	private String groupCodeTitle;
	/**玩法組標題*/
	private String setCodeTitle;
	/**投注方法標題*/
	private String methodCodeTitle;
	/**玩法類型標題*/
	private String methodTypeTitle;
	/**六合彩獎金識別標題*/
	private String lhcCodeTitle;
	/**六合彩理论奖金*/
	private BigDecimal lhcTheoryBonus;
	
	/**六合彩生肖返點(From game_award_group)*/
	private BigDecimal lhcYear;
	/**六合彩兩面色波返點(From game_award_group)*/
	private BigDecimal lhcColor;
	/**六合彩平碼返點(From game_award_group)*/
	private BigDecimal lhcFlatcode;
	/**六合彩半波返點(From game_award_group)*/
	private BigDecimal lhcHalfwave;
	/**六合彩一肖返點(From game_award_group)*/
	private BigDecimal lhcOneyear;
	/**六合彩不中返點(From game_award_group)*/
	private BigDecimal lhcNotin;
	/**六合彩連肖(中)二、三連肖返點(From game_award_group)*/
	private BigDecimal lhcContinuein23;
	/**六合彩連肖(中)四連肖返點(From game_award_group)*/
	private BigDecimal lhcContinuein4;
	/**六合彩連肖(中)五連肖返點(From game_award_group)*/
	private BigDecimal lhcContinuein5;
	/**六合彩連肖(不中)二、三連肖返點(From game_award_group)*/
	private BigDecimal lhcContinuenotin23;
	/**六合彩連肖(不中)四連肖返點(From game_award_group)*/
	private BigDecimal lhcContinuenotin4;
	/**六合彩連肖(不中)五連肖返點(From game_award_group)*/
	private BigDecimal lhcContinuenotin5;
	/**六合彩連碼返點(From game_award_group)*/
	private BigDecimal lhcContinuecode;
	
	

	/**
	 * 取得返奖率 = 实际奖金[賠率](actualBonus)/理论奖金(theoryBonus)
	 * @return
	 */
	public BigDecimal getPrizeRate() {

		if (theoryBonus != null && theoryBonus.compareTo(BigDecimal.ZERO) > 0) {
			return actualBonus.divide(theoryBonus, 5, RoundingMode.HALF_UP);
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 取得返點值。
	 * @return
	 */
	public Long getRetValue() {
		return retValue;
	}

	/**
	 * 設定返點值。
	 * @param retValue
	 */
	public void setRetValue(Long retValue) {
		this.retValue = retValue;
	}

	/**
	 * 取得公司留水。<br>
	 * 公司留水=总利润(totalProfit)-总代返点(topAgentPoint) 
 	 * @return
	 */
	public BigDecimal getCompanyProfit() {
		companyProfit = getTotatProfit().subtract(getTopAgentPoint(), MathContext.DECIMAL32);

		if (companyProfit.compareTo(miniLotteryProfit) < 0) {
			return miniLotteryProfit;
		}

		return companyProfit;
	}
	
	/**
	 * 取得总代返点。
	 * @return
	 */
	public BigDecimal getTopAgentPoint() {
		if(this.getGameBetType().getGameGroupCode()>=44&&this.getGameBetType().getGameGroupCode()<=51){
			return this.getSuperRet();
		}
		return topAgentPoint;
	}
	
	/**
	 * 設定總代返點。
	 * @param topAgentPoint
	 */
	public void setTopAgentPoint(BigDecimal topAgentPoint) {
		this.topAgentPoint = topAgentPoint;
	}
	
	/**
	 * 是否為一码不定位。
	 * @param betMethodCode
	 * @return
	 */
	public Boolean isYMBDW(Integer betMethodCode) {
		return betMethodCode == 40;
	}

	/**
	 * 是否为后三 
	 * @param gameGroupCode
	 * @return
	 */
	public Boolean isHs(Integer gameGroupCode) {
		return gameGroupCode == 13;
	}

	/**
	 * 是否为中三 
	 * @param gameGroupCode
	 * @return
	 */
	public Boolean isZs(Integer gameGroupCode) {
		return gameGroupCode == 33;
	}

	/**
	 * 是否为前三
	 * @param gameGroupCode
	 * @return
	 */
	public Boolean isQs(Integer gameGroupCode) {
		return gameGroupCode == 12;
	}

	/**
	 * 取得总利润。<br>
	 * 1-返奖率=总利润 
	 * @return
	 */
	public BigDecimal getTotatProfit() {
		//1-返奖率=总利润 
		BigDecimal profit = new BigDecimal(1).subtract(getPrizeRate(), MathContext.DECIMAL32);
		//profit wei 0.25
		return profit.multiply(new BigDecimal(100));
	}

	/**獎金組明細狀態；1:進行中、2:已刪除、3:待審核、4:待發佈、5:未審核、6:未發佈*/
	private GameAwardStatus awardStatus;

	/**
	 * 獎金組明細狀態。<br>
	 * 1:進行中、2:已刪除、3:待審核、4:待發佈、5:未審核、6:未發佈
	 * @author Pogi.Lin
	 */
	public enum GameAwardStatus {
		/**1:進行中*/
		CURRENT(1), 
		/**2:已刪除*/
		DELETE(2), 
		/**3:待審核*/
		WATING_AUDITING(3), 
		/**4:待發佈*/
		WATING_PUBLISH(4), 
		/**5:未審核*/
		NotAudit(5), 
		/**6:未發佈*/
		NotPublish(6);

		private int value = 0;

		GameAwardStatus(int action) {
			value = action;
		}

		public int getValue() {
			return value;
		}
	}

	public GameAwardEntity() {}

	/**
	 * 取得實際獎金(賠率)。
	 * @return
	 */
	public BigDecimal getActualBonus() {
		return actualBonus;
	}

	/**
	 * 設定實際獎金(賠率)。
	 * @param actualBonus
	 */
	public void setActualBonus(BigDecimal actualBonus) {
		this.actualBonus = actualBonus;
	}

	/**
	 * 取得理论奖金。
	 * @return
	 */
	public BigDecimal getTheoryBonus() {
		return theoryBonus;
	}

	/**
	 * 設定理论奖金。
	 * @param theoryBonus
	 */
	public void setTheoryBonus(BigDecimal theoryBonus) {
		this.theoryBonus = theoryBonus;
	}

	/**
	 * 取得獎金組ID。
	 * @return
	 */
	public Long getAwardGroupId() {
		return awardGroupId;
	}

	/**
	 * 設定獎金組ID。
	 * @param awardGroupId
	 */
	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
	}

	/**
	 * 取得創建時間。
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 設定創建時間。
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得更新時間。
	 * @return
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 設定更新時間。
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public GameAwardStatus getAwardStatus() {
		return awardStatus;
	}

	public void setAwardStatus(GameAwardStatus awardStatus) {
		this.awardStatus = awardStatus;
	}

	/**
	 * 取得公司最低留水。
	 * @return
	 */
	public BigDecimal getMiniLotteryProfit() {
		return miniLotteryProfit;
	}

	/**
	 * 設定公司最低留水。
	 * @param miniLotteryProfit
	 */
	public void setMiniLotteryProfit(BigDecimal miniLotteryProfit) {
		this.miniLotteryProfit = miniLotteryProfit;
	}

	/**
	 * 取得三星一码不定位返点。
	 * @return
	 */
	public BigDecimal getThreeoneRet() {
		return threeoneRet;
	}

	/**
	 * 設定三星一码不定位返点。
	 * @param threeoneRet
	 */
	public void setThreeoneRet(BigDecimal threeoneRet) {
		this.threeoneRet = threeoneRet;
	}

	/**
	 * 取得直选及其他返点。
	 * @return
	 */
	public BigDecimal getDirectRet() {
		return directRet;
	}

	/**
	 * 設定直选及其他返点。
	 * @param directRet
	 */
	public void setDirectRet(BigDecimal directRet) {
		this.directRet = directRet;
	}

	/**
	 * 取得彩种类型。
	 * @return
	 */
	public Lottery getLottery() {
		return lottery;
	}

	/**
	 * 設定彩种类型。
	 * @param lottery
	 */
	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	/**
	 * 取得玩法类型实例。
	 * @return
	 */
	public GameBetType getGameBetType() {
		return gameBetType;
	}

	/**
	 * 設定玩法类型实例。
	 * @param gameBetType
	 */
	public void setGameBetType(GameBetType gameBetType) {
		this.gameBetType = gameBetType;
	}

	/**
	 * 取得保底獎金組最小獎金(雙色球使用)。
	 * @return
	 */
	public BigDecimal getActualBonusDown() {
		return actualBonusDown;
	}

	/**
	 * 設定保底獎金組最小獎金(雙色球使用)。
	 * @param actualBonusDown
	 */
	public void setActualBonusDown(BigDecimal actualBonusDown) {
		this.actualBonusDown = actualBonusDown;
	}

	/**
	 * 取得返点值上限。
	 * @return
	 */
	public Long getMaxRetValue() {
		return maxRetValue;
	}

	/**
	 * 設定返点值上限。
	 * @param maxRetValue
	 */
	public void setMaxRetValue(Long maxRetValue) {
		this.maxRetValue = maxRetValue;
	}

	/**
	 * 取得玩法群標題。
	 * @return
	 */
	public String getGroupCodeTitle() {
		return groupCodeTitle;
	}

	/**
	 * 設定玩法群標題。
	 * @param groupCodeTitle
	 */
	public void setGroupCodeTitle(String groupCodeTitle) {
		this.groupCodeTitle = groupCodeTitle;
	}

	/**
	 * 取得玩法組標題。
	 * @return
	 */
	public String getSetCodeTitle() {
		return setCodeTitle;
	}

	/**
	 * 設定玩法組標題。
	 * @param setCodeTitle
	 */
	public void setSetCodeTitle(String setCodeTitle) {
		this.setCodeTitle = setCodeTitle;
	}

	/**
	 * 取得投注方法標題。
	 * @return
	 */
	public String getMethodCodeTitle() {
		return methodCodeTitle;
	}

	/**
	 * 設定投注方法標題。
	 * @param methodCodeTitle
	 */
	public void setMethodCodeTitle(String methodCodeTitle) {
		this.methodCodeTitle = methodCodeTitle;
	}

	/**
	 * 取得玩法類型標題。
	 * @return
	 */
	public String getMethodTypeTitle() {
		return methodTypeTitle;
	}

	/**
	 * 設定玩法類型標題。
	 * @param methodTypeTitle
	 */
	public void setMethodTypeTitle(String methodTypeTitle) {
		this.methodTypeTitle = methodTypeTitle;
	}

	/**
	 * 取得超级对子返点。
	 * @return
	 */
	public BigDecimal getSuperRet() {
		return superRet;
	}

	/**
	 * 設定超级对子返点。
	 * @param superRet
	 */
	public void setSuperRet(BigDecimal superRet) {
		this.superRet = superRet;
	}

	/**
	 * 取得六合彩獎金識別標題。
	 * @return
	 */
	public String getLhcCodeTitle() {
		return lhcCodeTitle;
	}

	/**
	 * 設定六合彩獎金識別標題。
	 * @param lhcCodeTitle
	 */
	public void setLhcCodeTitle(String lhcCodeTitle) {
		this.lhcCodeTitle = lhcCodeTitle;
	}
	/**
	/**
	 * 取得六合彩理论奖金。
	 * @return
	 */
	public BigDecimal getLhcTheoryBonus() {
		return lhcTheoryBonus;
	}

	/**
	 * 設定六合彩理论奖金。
	 * @param lhcTheoryBonus
	 */
	public void setLhcTheoryBonus(BigDecimal lhcTheoryBonus) {
		this.lhcTheoryBonus = lhcTheoryBonus;
	}
	
	/**
	 * 取得六合彩生肖返點(From game_award_group)。
	 * @return
	 */
	public BigDecimal getLhcYear() {
		return lhcYear;
	}
	/**
	/**
	 * 設定六合彩生肖返點(From game_award_group)。
	 * @param lhcYear
	 */
	public void setLhcYear(BigDecimal lhcYear) {
		this.lhcYear = lhcYear;
	}

	/**
	 * 取得六合彩兩面色波返點(From game_award_group)。
	 * @return
	 */
	public BigDecimal getLhcColor() {
		return lhcColor;
	}

	/**
	 * 設定六合彩兩面色波返點(From game_award_group)。
	 * @param lhcColor
	 */
	public void setLhcColor(BigDecimal lhcColor) {
		this.lhcColor = lhcColor;
	}

	/**
	 * 取得六合彩平碼返點(From game_award_group)。
	 * @return 
	 */
	public BigDecimal getLhcFlatcode() {
		return lhcFlatcode;
	}

	/**
	 * 設定六合彩平碼返點(From game_award_group)。
	 * @param lhcFlatcode 
	 */
	public void setLhcFlatcode(BigDecimal lhcFlatcode) {
		this.lhcFlatcode = lhcFlatcode;
	}

	/**
	 * 取得六合彩半波返點(From game_award_group)。
	 * @return 
	 */
	public BigDecimal getLhcHalfwave() {
		return lhcHalfwave;
	}

	/**
	 * 設定六合彩半波返點(From game_award_group)。
	 * @param lhcHalfwave 
	 */
	public void setLhcHalfwave(BigDecimal lhcHalfwave) {
		this.lhcHalfwave = lhcHalfwave;
	}

	/**
	 * 取得六合彩一肖返點(From game_award_group)。
	 * @return 
	 */
	public BigDecimal getLhcOneyear() {
		return lhcOneyear;
	}

	/**
	 * 設定六合彩一肖返點(From game_award_group)。
	 * @param lhcOneyear 
	 */
	public void setLhcOneyear(BigDecimal lhcOneyear) {
		this.lhcOneyear = lhcOneyear;
	}

	/**
	 * 取得六合彩不中返點(From game_award_group)。
	 * @return 
	 */
	public BigDecimal getLhcNotin() {
		return lhcNotin;
	}

	/**
	 * 設定六合彩不中返點(From game_award_group)。
	 * @param lhcNotin 
	 */
	public void setLhcNotin(BigDecimal lhcNotin) {
		this.lhcNotin = lhcNotin;
	}

	/**
	 * 取得六合彩連肖(中)二、三連肖返點(From game_award_group)。
	 * @return 
	 */
	public BigDecimal getLhcContinuein23() {
		return lhcContinuein23;
	}

	/**
	 * 設定六合彩連肖(中)二、三連肖返點(From game_award_group)。
	 * @param lhcContinuein23 
	 */
	public void setLhcContinuein23(BigDecimal lhcContinuein23) {
		this.lhcContinuein23 = lhcContinuein23;
	}

	/**
	 * 取得六合彩連肖(中)四連肖返點(From game_award_group)。
	 * @return 
	 */
	public BigDecimal getLhcContinuein4() {
		return lhcContinuein4;
	}

	/**
	 * 設定六合彩連肖(中)四連肖返點(From game_award_group)。
	 * @param lhcContinuein4 
	 */
	public void setLhcContinuein4(BigDecimal lhcContinuein4) {
		this.lhcContinuein4 = lhcContinuein4;
	}

	/**
	 * 取得六合彩連肖(中)五連肖返點(From game_award_group)。
	 * @return 
	 */
	public BigDecimal getLhcContinuein5() {
		return lhcContinuein5;
	}

	/**
	 * 設定六合彩連肖(中)五連肖返點(From game_award_group)。
	 * @param lhcContinuein5 
	 */
	public void setLhcContinuein5(BigDecimal lhcContinuein5) {
		this.lhcContinuein5 = lhcContinuein5;
	}

	/**
	 * 取得六合彩連肖(不中)二、三連肖返點(From game_award_group)。
	 * @return 
	 */
	public BigDecimal getLhcContinuenotin23() {
		return lhcContinuenotin23;
	}

	/**
	 * 設定六合彩連肖(不中)二、三連肖返點(From game_award_group)。
	 * @param lhcContinuenotin23 
	 */
	public void setLhcContinuenotin23(BigDecimal lhcContinuenotin23) {
		this.lhcContinuenotin23 = lhcContinuenotin23;
	}

	/**
	 * 取得六合彩連肖(不中)四連肖返點(From game_award_group)。
	 * @return 
	 */
	public BigDecimal getLhcContinuenotin4() {
		return lhcContinuenotin4;
	}

	/**
	 * 設定六合彩連肖(不中)四連肖返點(From game_award_group)。
	 * @param lhcContinuenotin4 
	 */
	public void setLhcContinuenotin4(BigDecimal lhcContinuenotin4) {
		this.lhcContinuenotin4 = lhcContinuenotin4;
	}

	/**
	 * 取得六合彩連肖(不中)五連肖返點(From game_award_group)。
	 * @return 
	 */
	public BigDecimal getLhcContinuenotin5() {
		return lhcContinuenotin5;
	}

	/**
	 * 設定六合彩連肖(不中)五連肖返點(From game_award_group)。
	 * @param lhcContinuenotin5 
	 */
	public void setLhcContinuenotin5(BigDecimal lhcContinuenotin5) {
		this.lhcContinuenotin5 = lhcContinuenotin5;
	}

	/**
	 * 取得六合彩連碼返點(From game_award_group)。
	 * @return 
	 */
	public BigDecimal getLhcContinuecode() {
		return lhcContinuecode;
	}

	/**
	 * 設定六合彩連碼返點(From game_award_group)。
	 * @param lhcContinuecode 
	 */
	public void setLhcContinuecode(BigDecimal lhcContinuecode) {
		this.lhcContinuecode = lhcContinuecode;
	}
}
