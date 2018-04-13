package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.entity.GameAwardGroupEntity.Status;

/**
 * 奖金组奖金查询响应 
 * @author Richard
 * @date 2013-8-16 上午10:22:10 
 */
public class GameAwardQueryResponse implements Serializable {

	private static final long serialVersionUID = -4263369045548021304L;
	
	private Long gameAwardGroupId;
	private Long lotteryId;
	private String lotteryName;
	private Long awardGroupId;
	private String awardGroupName;
	private String awardGroupNameBak;
	private Long directRet;
	private Long threeoneRet;
	private Long directRetBak;
	private Long threeoneRetBak;
	private Long directLimitRet;
	private Long threeLimitRet;
	private Long superRet;
	/**六合彩特碼-生肖返点*/
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
	private Status status;

	/**公司最低留水*/
	private Long miniLotteryProfit;
	
	private List<AwardBonusStruc> awardBonusStrucList;
	
	public GameAwardQueryResponse(){
		
	}
	
	public Long getSuperRet() {
		return superRet;
	}


	public void setSuperRet(Long superRet) {
		this.superRet = superRet;
	}


	public List<AwardBonusStruc> getAwardBonusStrucList() {
		return awardBonusStrucList;
	}

	public void setAwardBonusStrucList(List<AwardBonusStruc> awardBonusStrucList) {
		this.awardBonusStrucList = awardBonusStrucList;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}


	public Long getAwardGroupId() {
		return awardGroupId;
	}

	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
	}

	public String getAwardGroupName() {
		return awardGroupName;
	}

	public void setAwardGroupName(String awardGroupName) {
		this.awardGroupName = awardGroupName;
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

	public Long getGameAwardGroupId() {
		return gameAwardGroupId;
	}

	public void setGameAwardGroupId(Long gameAwardGroupId) {
		this.gameAwardGroupId = gameAwardGroupId;
	}

	public Long getMiniLotteryProfit() {
		return miniLotteryProfit;
	}

	public void setMiniLotteryProfit(Long miniLotteryProfit) {
		this.miniLotteryProfit = miniLotteryProfit;
	}

	public String getAwardGroupNameBak() {
		return awardGroupNameBak;
	}

	public void setAwardGroupNameBak(String awardGroupNameBak) {
		this.awardGroupNameBak = awardGroupNameBak;
	}

	public Long getDirectRetBak() {
		return directRetBak;
	}

	public void setDirectRetBak(Long directRetBak) {
		this.directRetBak = directRetBak;
	}

	public Long getThreeoneRetBak() {
		return threeoneRetBak;
	}

	public void setThreeoneRetBak(Long threeoneRetBak) {
		this.threeoneRetBak = threeoneRetBak;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	/**
	 * 取得六合彩特碼-生肖返点。
	 * @param lhcYear
	 */
	public Long getLhcYear() {
		return lhcYear;
	}
	/**
	 * 設定六合彩特碼-生肖返点。
	 * @param lhcYear
	 */
	public void setLhcYear(Long lhcYear) {
		this.lhcYear = lhcYear;
	}
	/**
	 * 取得六合彩特碼-兩面、色波返点。
	 * @param lhcColor
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
	 * @param lhcFlatcode
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
	 * @param lhcHalfwave
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
	 * @param lhcOneyear
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
	 * @param lhcNotin
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
	 * @param lhcContinuein23
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
	 * @param lhcContinuein4
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
	 * @param lhcContinuein5
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
	 * @param lhcContinuenotin23
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
	 * @param lhcContinuenotin4
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
	 * @param lhcContinuenotin5
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
	 * @param lhcContinuecode
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
