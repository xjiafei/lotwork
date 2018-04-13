package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 獎期開獎結果表
 * @author cms group
 * @version 1.0
 * @since 1.0
 */
public class GameDrawResult extends BaseEntity {

	private static final long serialVersionUID = -1967439279592011689L;

	/**彩种ID*/
	private Long lotteryid;
	/**奖期*/
	private Long issueCode;
	/**WEB显示奖期*/
	private String webIssueCode;
	/**开奖号码*/
	private String numberRecord;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**开奖时间*/
	private Date openDrawTime;
	/**型態；0:系统输入、1:人工输入*/
	private Long type;
	/**備註*/
	private String memo;
	
	/**
	 * 取得備註。
	 * @return
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 設定備註。
	 * @param memo 
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public GameDrawResult() {
	}

	public GameDrawResult(Long id) {
		this.id = id;
	}

	/**
	 * 設定彩种ID。
	 * @param value
	 */
	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}

	/**
	 * 取得彩种ID。
	 * @return
	 */
	public Long getLotteryid() {
		return this.lotteryid;
	}

	/**
	 * 設定奖期。
	 * @param value
	 */
	public void setIssueCode(Long value) {
		this.issueCode = value;
	}

	/**
	 * 取得奖期。
	 * @return
	 */
	public Long getIssueCode() {
		return this.issueCode;
	}

	/**
	 * 設定WEB显示奖期。
	 * @param value
	 */
	public void setWebIssueCode(String value) {
		this.webIssueCode = value;
	}

	/**
	 * 取得WEB显示奖期。
	 * @return
	 */
	public String getWebIssueCode() {
		return this.webIssueCode;
	}

	/**
	 * 設定开奖号码。
	 * @param value
	 */
	public void setNumberRecord(String value) {
		this.numberRecord = value;
	}

	/**
	 * 取得开奖号码。
	 * @return
	 */
	public String getNumberRecord() {
		return this.numberRecord;
	}

	/**
	 * 取得创建时间。
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 設定创建时间。
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得更新时间。
	 * @return
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 設定更新时间。
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 取得开奖时间。
	 * @return
	 */
	public Date getOpenDrawTime() {
		return openDrawTime;
	}

	/**
	 * 設定开奖时间。
	 * @param openDrawTime
	 */
	public void setOpenDrawTime(Date openDrawTime) {
		this.openDrawTime = openDrawTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getLotteryid()).append(getIssueCode())
				.append(getWebIssueCode()).append(getNumberRecord()).append(getCreateTime()).append(getUpdateTime()).append(getOpenDrawTime())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameDrawResult == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GameDrawResult other = (GameDrawResult) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getLotteryid(), other.getLotteryid())

		.append(getIssueCode(), other.getIssueCode())

		.append(getWebIssueCode(), other.getWebIssueCode())

		.append(getNumberRecord(), other.getNumberRecord())

		.append(getCreateTime(), other.getCreateTime())

		.append(getUpdateTime(), other.getUpdateTime())
		
		.append(getOpenDrawTime(), other.getOpenDrawTime())

		.isEquals();
	}

	/**
	 * 取得型態。
	 * @return 0:系统输入、1:人工输入
	 */
	public Long getType() {
		return type;
	}

	/**
	 * 設定型態。
	 * @param type 0:系统输入、1:人工输入
	 */
	public void setType(Long type) {
		this.type = type;
	}
}
