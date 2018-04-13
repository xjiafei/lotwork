package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 彩票彩系表
 * @author cms group
 * @version 1.0
 * @since 1.0
 */
public class GameSeries extends BaseEntity {
	private static final long serialVersionUID = -2342565905550061511L;

	/**彩票类型；1:數字型、樂透同區型、3:基諾型、樂透同區型、4:樂透分區型、5:遊藝型*/
	private Integer lotteryTypeCode;
	/**彩票类型名称*/
	private String lotteryTypeName;
	/**彩系编码；1: 时时彩系、2: 3D系、3: 11选5系、4: 基诺系、5: 双色球系、6: 快乐彩系、7: 快乐彩系、8: 电子游艺、9: 六合系*/
	private Integer lotterySeriesCode;
	/**彩系名称*/
	private String lotterySeriesName;
	/**彩种代碼*/
	private Long lotteryid;
	/**彩种名稱*/
	private String lotteryName;
	/**销售状态；0:停售、1:可销售*/
	private Integer status;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**公司最小利润*/
	private Float miniLotteryProfit;
	/**开奖周期描述*/
	private String lotteryHelpDes;
	/**最大期数*/
	private Long maxCountIssue;
	private Long lotteryIsLock;
	private Long changeStatus;
	private String operator;
	/**下架時間*/
	private Date takeOffTime;

	public GameSeries() {
	}

	public GameSeries(Long id) {
		this.id = id;
	}

	/**
	 * 設定彩票类型。
	 * @param value 1:數字型、樂透同區型、3:基諾型、樂透同區型、4:樂透分區型、5:遊藝型
	 */
	public void setLotteryTypeCode(Integer value) {
		this.lotteryTypeCode = value;
	}

	/**
	 * 取得彩票类型。
	 * @return 1:數字型、樂透同區型、3:基諾型、樂透同區型、4:樂透分區型、5:遊藝型
	 */
	public Integer getLotteryTypeCode() {
		return this.lotteryTypeCode;
	}

	/**
	 * 設定彩票类型名称。
	 * @param value
	 */
	public void setLotteryTypeName(String value) {
		this.lotteryTypeName = value;
	}

	/**
	 * 取得彩票类型名称。
	 * @return
	 */
	public String getLotteryTypeName() {
		return this.lotteryTypeName;
	}

	/**
	 * 設定彩系编码。
	 * @param value 1: 时时彩系、2: 3D系、3: 11选5系、4: 基诺系、5: 双色球系、6: 快乐彩系、7: 快乐彩系、8: 电子游艺、9: 六合系
	 */
	public void setLotterySeriesCode(Integer value) {
		this.lotterySeriesCode = value;
	}

	/**
	 * 取得彩系编码。
	 * @return 1: 时时彩系、2: 3D系、3: 11选5系、4: 基诺系、5: 双色球系、6: 快乐彩系、7: 快乐彩系、8: 电子游艺、9: 六合系
	 */
	public Integer getLotterySeriesCode() {
		return this.lotterySeriesCode;
	}

	/**
	 * 設定彩系名称。
	 * @param value
	 */
	public void setLotterySeriesName(String value) {
		this.lotterySeriesName = value;
	}

	/**
	 * 取得彩系名称。
	 * @return
	 */
	public String getLotterySeriesName() {
		return this.lotterySeriesName;
	}

	/**
	 * 設定彩种代碼。
	 * @param value
	 */
	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}

	/**
	 * 取得彩种代碼。
	 * @return
	 */
	public Long getLotteryid() {
		return this.lotteryid;
	}

	/**
	 * 設定彩种名稱。
	 * @param value
	 */
	public void setLotteryName(String value) {
		this.lotteryName = value;
	}

	/**
	 * 取得彩种名稱。
	 * @return
	 */
	public String getLotteryName() {
		return this.lotteryName;
	}

	/**
	 * 設定销售状态。
	 * @param value 0:停售、1:可销售
	 */
	public void setStatus(Integer value) {
		this.status = value;
	}

	/**
	 * 取得销售状态。
	 * @return 0:停售、1:可销售
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 設定创建时间。
	 * @param value
	 */
	public void setCreateTime(Date value) {
		this.createTime = value;
	}

	/**
	 * 取得创建时间。
	 * @return
	 */
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 設定更新时间。
	 * @param value
	 */
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}

	/**
	 * 設定更新时间。
	 * @return
	 */
	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * 設定公司最小利润。
	 * @param value
	 */
	public void setMiniLotteryProfit(Float value) {
		this.miniLotteryProfit = value;
	}

	/**
	 * 取得公司最小利润。
	 * @return
	 */
	public Float getMiniLotteryProfit() {
		return this.miniLotteryProfit;
	}

	/**
	 * 取得开奖周期描述。
	 * @return
	 */
	public String getLotteryHelpDes() {
		return lotteryHelpDes;
	}

	/**
	 * 設定开奖周期描述。
	 * @param lotteryHelpDes
	 */
	public void setLotteryHelpDes(String lotteryHelpDes) {
		this.lotteryHelpDes = lotteryHelpDes;
	}
	
	/**
	 * 取得最大期数。
	 * @return
	 */
	public Long getMaxCountIssue() {
		return maxCountIssue;
	}

	/**
	 * 設定最大期数。
	 * @param maxCountIssue
	 */
	public void setMaxCountIssue(Long maxCountIssue) {
		this.maxCountIssue = maxCountIssue;
	}
	
	public Long getLotteryIsLock() {
		return lotteryIsLock;
	}

	public void setLotteryIsLock(Long lotteryIsLock) {
		this.lotteryIsLock = lotteryIsLock;
	}

	public Long getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(Long changeStatus) {
		this.changeStatus = changeStatus;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("LotteryTypeCode", getLotteryTypeCode())
				.append("LotteryTypeName", getLotteryTypeName()).append("LotterySeriesCode", getLotterySeriesCode())
				.append("LotterySeriesName", getLotterySeriesName()).append("Lotteryid", getLotteryid())
				.append("LotteryName", getLotteryName()).append("Status", getStatus())
				.append("CreateTime", getCreateTime()).append("UpdateTime", getUpdateTime())
				.append("MiniLotteryProfit", getMiniLotteryProfit()).append("LotteryHelpDes", getLotteryHelpDes()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getLotteryTypeCode()).append(getLotteryTypeName())
				.append(getLotterySeriesCode()).append(getLotterySeriesName()).append(getLotteryid())
				.append(getLotteryName()).append(getStatus()).append(getCreateTime()).append(getUpdateTime())
				.append(getMiniLotteryProfit()).append(getLotteryHelpDes()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameSeries == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GameSeries other = (GameSeries) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getLotteryTypeCode(), other.getLotteryTypeCode())

		.append(getLotteryTypeName(), other.getLotteryTypeName())

		.append(getLotterySeriesCode(), other.getLotterySeriesCode())

		.append(getLotterySeriesName(), other.getLotterySeriesName())

		.append(getLotteryid(), other.getLotteryid())

		.append(getLotteryName(), other.getLotteryName())

		.append(getStatus(), other.getStatus())

		.append(getCreateTime(), other.getCreateTime())

		.append(getUpdateTime(), other.getUpdateTime())

		.append(getMiniLotteryProfit(), other.getMiniLotteryProfit())
		
		.append(getLotteryHelpDes(), other.getLotteryHelpDes())

		.isEquals();
	}

	/**
	 * 取得下架時間。
	 * @return
	 */
	public Date getTakeOffTime() {
		return takeOffTime;
	}

	/**
	 * 設定下架時間。
	 * @param takeOffTime
	 */
	public void setTakeOffTime(Date takeOffTime) {
		this.takeOffTime = takeOffTime;
	}
	
}
