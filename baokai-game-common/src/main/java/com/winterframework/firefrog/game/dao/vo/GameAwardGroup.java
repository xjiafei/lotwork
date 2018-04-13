package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */
public class GameAwardGroup extends BaseEntity {

	private static final long serialVersionUID = -3616560711722238715L;

	/**彩種ID*/
	private Long lotteryid;
	/**奖金組名稱*/
	private String awardName;
	/**直选及其他返点(六合彩代表特碼-直選返點)*/
	private Long directRet;
	/**三星一码不定位返点*/
	private Long threeoneRet;
	/**状态;1:待审核 2:待发布 3:进行中 4:进行中,待审核 5:进行中，待发布*/
	private Long status;
	/**奖金组类型;1:系统奖金组 2:用户奖金组*/
	private Integer sysAwardGroup;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;

	/**彩系碼*/
	private Integer lotterySeriesCode;
	/**彩系名稱*/
	private String lotterySeriesName;
	/**彩種名稱*/
	private String lotteryName;
	/**奖金返点开关*/
	private Integer awardRetStatus;
	/**超级对子返点*/
	private Long superRet;
	/**六合彩特碼-生肖返点*/
	private Long lhcYear;
	/**六合彩特碼-兩面、色波返点*/
	private Long lhcColor;
	/**骰寶猜一個號*/
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
	
	public GameAwardGroup() {
	}

	public GameAwardGroup(Long id) {
		this.id = id;
	}

	/**
	   * 設定直选及其他返点(六合彩代表特碼-直選返點)。
	   * @param directRet
	*/
	public void setDirectRet(Long value) {
		this.directRet = value;
	}
	/**
	   * 取得直选及其他返点(六合彩代表特碼-直選返點)。
	   * @param directRet
	*/
	public Long getDirectRet() {
		return this.directRet;
	}
	/**
	   * 設定三星一码不定位返点。
	   * @param threeoneRet
	*/
	public void setThreeoneRet(Long value) {
		this.threeoneRet = value;
	}
	/**
	   * 取得三星一码不定位返点。
	   * @param threeoneRet
	*/
	public Long getThreeoneRet() {
		return this.threeoneRet;
	}
	/**
	   * 設定状态;1:待审核 2:待发布 3:进行中 4:进行中,待审核 5:进行中，待发布。
	   * @param status
	*/
	public void setStatus(Long value) {
		this.status = value;
	}
	/**
	   * 取得状态;1:待审核 2:待发布 3:进行中 4:进行中,待审核 5:进行中，待发布。
	   * @param status
	*/
	public Long getStatus() {
		return this.status;
	}
	/**
	   * 設定创建时间。
	   * @param createTime
	*/
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	/**
	   * 取得创建时间。
	   * @param createTime
	*/
	public Date getCreateTime() {
		return this.createTime;
	}
	/**
	   * 設定更新时间。
	   * @param updateTime
	*/
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	/**
	   * 取得更新时间。
	   * @param updateTime
	*/
	public Date getUpdateTime() {
		return this.updateTime;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Lottoryid", getLotteryid())
				.append("RewardName", getAwardName()).append("DirectRet", getDirectRet())
				.append("ThreeoneRet", getThreeoneRet()).append("Status", getStatus())
				.append("SysRewardGroup", getSysAwardGroup()).append("CreateTime", getCreateTime())
				.append("UpdateTime", getUpdateTime()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getLotteryid()).append(getAwardName())
				.append(getDirectRet()).append(getThreeoneRet()).append(getStatus()).append(getSysAwardGroup())
				.append(getCreateTime()).append(getUpdateTime()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameAwardGroup == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GameAwardGroup other = (GameAwardGroup) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getLotteryid(), other.getLotteryid())

		.append(getAwardName(), other.getAwardName())

		.append(getDirectRet(), other.getDirectRet())

		.append(getThreeoneRet(), other.getThreeoneRet())

		.append(getStatus(), other.getStatus())

		.append(getSysAwardGroup(), other.getSysAwardGroup())

		.append(getCreateTime(), other.getCreateTime())

		.append(getUpdateTime(), other.getUpdateTime())

		.isEquals();
	}
	/**
	   * 取得奖金組名稱。
	   * @param awardName
	*/
	public String getAwardName() {
		return awardName;
	}
	/**
	   * 設定奖金組名稱。
	   * @param awardName
	*/
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	/**
	   * 取得奖金组类型;1:系统奖金组 2:用户奖金组。
	   * @param sysAwardGroup
	*/
	public Integer getSysAwardGroup() {
		return sysAwardGroup;
	}
	/**
	   * 設定奖金组类型;1:系统奖金组 2:用户奖金组。
	   * @param sysAwardGroup
	*/
	public void setSysAwardGroup(Integer sysAwardGroup) {
		this.sysAwardGroup = sysAwardGroup;
	}
	/**
	   * 取得彩種ID。
	   * @param lotteryid
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
	   * 取得彩系名稱。
	   * @param lotterySeriesName
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
	   * 取得彩系碼。
	   * @param lotterySeriesCode
	*/
	public Integer getLotterySeriesCode() {
		return lotterySeriesCode;
	}
	/**
	   * 設定彩系碼。
	   * @param lotterySeriesCode
	*/
	public void setLotterySeriesCode(Integer lotterySeriesCode) {
		this.lotterySeriesCode = lotterySeriesCode;
	}
	/**
	   * 取得彩種名稱。
	   * @param lotteryName
	*/
	public String getLotteryName() {
		return lotteryName;
	}
	/**
	   * 設定彩種名稱。
	   * @param lotteryName
	*/
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	/**
	   * 取得超级对子返点。
	   * @param superRet
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
	   * 取得奖金返点开关。
	   * @param awardRetStatus
	*/
	public Integer getAwardRetStatus() {
		return awardRetStatus;
	}
	/**
	   * 設定奖金返点开关。
	   * @param awardRetStatus
	*/
	public void setAwardRetStatus(Integer awardRetStatus) {
		this.awardRetStatus = awardRetStatus;
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
	   * @param 
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

	/**取得六合彩正特碼-不中返点
	 * @return the lhcNotin
	 */
	public Long getLhcNotin() {
		return lhcNotin;
	}

	/**設定六合彩正特碼-不中返点
	 * @param lhcNotin the lhcNotin to set
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
