package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 用户奖金组
 * @author cms group
 * @version 1.0
 * @since 1.0
 */
public class GameUserAwardGroup extends BaseEntity {

	private static final long serialVersionUID = -2020807693398685850L;

	/**彩種ID*/
	private Long lotteryid;
	/**直選或其他返點*/
	private Long directRet;
	/**三星一碼不定位返點*/
	private Long threeoneRet; 
	/**直选可分配返点数上限*/
	private Long maxDirectRet; 
	/**三星一碼不定位可分配返点数上限*/
	private Long maxThreeOneRet;
	/**創建時間*/
	private Date createTime;
	/**更新時間*/
	private Date updateTime;
	/**用戶ID*/
	private Long userid;
	/**设置方式；1:详细设置、2:快捷设置*/
	private Integer setType;
	/**是否启用；0:未启用、1:启用*/
	private Integer status;
	/**是否可投注；0:配置属性、1:投注属性*/
	private Integer betType;
	private Long sysGroupAwardId;
	/**奖金组名称*/
	private String awardName;
	/**彩系编码*/
	private Long lotterySeriesCode;
	/**彩系名称*/
	private String lotterySeriesName;
	/**超级对子返点*/
	private Long superRet;
	/**超级对子可分配返点数上限*/
	private Long maxSuperRet;
	
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

	public GameUserAwardGroup() {
	}

	public GameUserAwardGroup(Long id) {
		this.id = id;
	}

	/**
	 * 設定直選或其他返點。
	 * @param value
	 */
	public void setDirectRet(Long value) {
		this.directRet = value;
	}

	/**
	 * 取得直選或其他返點。
	 * @return
	 */
	public Long getDirectRet() {
		return this.directRet;
	}

	/**
	 * 設定三星一碼不定位返點。
	 * @param value
	 */
	public void setThreeoneRet(Long value) {
		this.threeoneRet = value;
	}

	/**
	 * 取得三星一碼不定位返點。
	 * @return
	 */
	public Long getThreeoneRet() {
		return this.threeoneRet;
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

	/**
	 * 設定用戶ID。
	 * @param value
	 */
	public void setUserid(Long value) {
		this.userid = value;
	}

	/**
	 * 取得用戶ID。
	 * @return
	 */
	public Long getUserid() {
		return this.userid;
	}

	/**
	 * 設定设置方式。
	 * @param value 1:详细设置、2:快捷设置
	 */
	public void setSetType(Integer value) {
		this.setType = value;
	}

	/**
	 * 取得设置方式。
	 * @return 1:详细设置、2:快捷设置
	 */
	public Integer getSetType() {
		return this.setType;
	}

	/**
	 * 設定是否启用。
	 * @param value 0:未启用、1:启用
	 */
	public void setStatus(Integer value) {
		this.status = value;
	}

	/**
	 * 取得是否启用。
	 * @return 0:未启用、1:启用
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 取得是否可投注。
	 * @return 0:配置属性、1:投注属性
	 */
	public Integer getBetType() {
		return betType;
	}

	/**
	 * 設定是否可投注。
	 * @param betType 0:配置属性、1:投注属性
	 */
	public void setBetType(Integer betType) {
		this.betType = betType;
	}
	 
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
	 * 取得三星一碼不定位可分配返点数上限。
	 * @return
	 */
	public Long getMaxThreeOneRet() {
		return maxThreeOneRet;
	}

	/**
	 * 設定三星一碼不定位可分配返点数上限。
	 * @param maxThreeOneRet
	 */
	public void setMaxThreeOneRet(Long maxThreeOneRet) {
		this.maxThreeOneRet = maxThreeOneRet;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("LotteryId", getLotteryid())
				.append("DirectRet", getDirectRet()).append("ThreeoneRet", getThreeoneRet())
				.append("CreateTime", getCreateTime()).append("UpdateTime", getUpdateTime())
				.append("Userid", getUserid()).append("SetType", getSetType()).append("Status", getStatus())
				.append("sysGroupAwardId", getSysGroupAwardId()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getLotteryid()).append(getLotteryid())
				.append(getDirectRet()).append(getThreeoneRet()).append(getCreateTime()).append(getUpdateTime())
				.append(getUserid()).append(getSetType()).append(getStatus()).append(getSysGroupAwardId()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameUserAwardGroup == false)
			return false;
		if (this == obj)
			return true;
		GameUserAwardGroup other = (GameUserAwardGroup) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getLotteryid(), other.getLotteryid())

		.append(getDirectRet(), other.getDirectRet())

		.append(getThreeoneRet(), other.getThreeoneRet())

		.append(getCreateTime(), other.getCreateTime())

		.append(getUpdateTime(), other.getUpdateTime())

		.append(getUserid(), other.getUserid())

		.append(getSetType(), other.getSetType())

		.append(getStatus(), other.getStatus())

		.append(getSysGroupAwardId(), other.getSysGroupAwardId())

		.isEquals();
	}


	public Long getSysGroupAwardId() {
		return sysGroupAwardId;
	}

	public void setSysGroupAwardId(Long sysGroupAwardId) {
		this.sysGroupAwardId = sysGroupAwardId;
	}

	/**
	 * 取得奖金组名称。
	 * @return
	 */
	public String getAwardName() {
		return awardName;
	}

	/**
	 * 設定奖金组名称。
	 * @param awardName
	 */
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	/**
	 * 取得彩種ID。
	 * @return
	 */
	public Long getLotteryid() {
		return lotteryid;
	}

	/**
	 * 設定彩種ID。
	 * @param lotteryid
	 */
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	/**
	 * 取得彩系编码。
	 * @return
	 */
	public Long getLotterySeriesCode() {
		return lotterySeriesCode;
	}

	/**
	 * 設定彩系编码。
	 * @param lotterySeriesCode
	 */
	public void setLotterySeriesCode(Long lotterySeriesCode) {
		this.lotterySeriesCode = lotterySeriesCode;
	}

	/**
	 * 取得彩系名称。
	 * @return
	 */
	public String getLotterySeriesName() {
		return lotterySeriesName;
	}

	/**
	 * 設定彩系名称。
	 * @param lotterySeriesName
	 */
	public void setLotterySeriesName(String lotterySeriesName) {
		this.lotterySeriesName = lotterySeriesName;
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
	 * 取得超级对子可分配返点数上限。
	 * @return
	 */
	public Long getMaxSuperRet() {
		return maxSuperRet;
	}

	/**
	 * 設定超级对子可分配返点数上限。
	 * @param maxSuperRet
	 */
	public void setMaxSuperRet(Long maxSuperRet) {
		this.maxSuperRet = maxSuperRet;
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
}
