package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户中心奖金组列表基本结构 
 * @author Denny 
 * @date 2013-9-17 下午5:36:04 
 */
public class LotteryGameUserAwardListStruc implements Serializable {

	private static final long serialVersionUID = -4434960099388030361L;
	
	/** 獎金組ID*/
	private Long gid;
	/** 彩種ID */
	private Long awardGroupId;
	/** 奖金组名称 */
	private String awardName;
	/** 投注属性 */
	private Integer betType;
	/** 直接返點*/
	private Long directRet;
	/** 不定位返點*/
	private Long threeoneRet;
	/**超级对子返点*/
	private Long superRet;
	/** 六合彩返點*/
	/**六合彩特碼-生肖返点*/
	private Long lhcYear;
	/**六合彩特碼-兩面、色波返点*/
	private Long lhcColor;
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
	/** 建立時間*/
	private Date createTime;
	/** 修改時間*/
	private Date updateTimte;
	/**奖金组返点开关*/
	private Integer awardGroupRetStatus;
	

	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTimte() {
		return updateTimte;
	}

	public void setUpdateTimte(Date updateTimte) {
		this.updateTimte = updateTimte;
	}

	public Long getAwardGroupId() {
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

	public Integer getBetType() {
		return betType;
	}

	public void setBetType(Integer betType) {
		this.betType = betType;
	}
	/**
	 * 取得奖金组返点开关。
	 * @return
	 */
	public Integer getAwardGroupRetStatus() {
		return awardGroupRetStatus;
	}
	/**
	 * 設定奖金组返点开关。
	 * @param awardGroupRetStatus
	 */
	public void setAwardGroupRetStatus(Integer awardGroupRetStatus) {
		this.awardGroupRetStatus = awardGroupRetStatus;
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
	
	
}
