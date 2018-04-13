package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;
import java.util.List;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 獎金組明細
 * @author cms group
 * @version 1.0
 * @since 1.0
 */
public class GameAward extends BaseEntity {

	private static final long serialVersionUID = -4568248657527391023L;
	
	/**彩種ID*/
	private Long lotteryid;
	/**投注方式編碼*/
	private String betTypeCode;
	/**實際獎金(賠率)*/
	private Long actualBonus;
	/**保底獎金組最小獎金(雙色球使用)*/
	private Long actualBonusDown;
	/**獎金組ID*/
	private Long awardGroupId;
	/**創建時間*/
	private Date createTime;
	/**更新時間*/
	private Date updateTime;
	/**狀態；1：進行中、2：已刪除、3:待審核、4:待發佈、5:未審核、6:未發佈*/
	private Integer status;
	/**返點值*/
	private Long retValue;
	/**返点值上限*/
	private Long maxRetValue;
	/**六合彩奖金识别*/
	private String lhcCode;
	/**六合彩理论奖金*/
	private Long lhcTheoryBonus;
	/**六合彩多組賠率*/
	private List<Long> lhcMultBonus;

	public GameAward() {
	}

	public GameAward(Long id) {
		this.id = id;
	}

	/**
	 * 設定彩種ID。
	 * @param value
	 */
	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}

	/**
	 * 取得彩種ID。
	 * @return
	 */
	public Long getLotteryid() {
		return this.lotteryid;
	}

	/**
	 * 取得玩法群編碼。
	 * @return
	 */
	public Integer findGameGroupCode() {
		if (betTypeCode != null) {
			return Integer.parseInt(betTypeCode.split("_")[0]);
		}
		return null;
	}

	/**
	 * 取得玩法組編碼。
	 * @return
	 */
	public Integer findGameSetCode() {
		if (betTypeCode != null) {
			return Integer.parseInt(betTypeCode.split("_")[1]);
		}
		return null;
	}

	/**
	 * 取得投注方式編碼。
	 * @return
	 */
	public Integer findBetMethodCode() {
		if (betTypeCode != null) {
			return Integer.parseInt(betTypeCode.split("_")[2]);
		}
		return null;
	}

	/**
	 * 設定實際獎金(賠率)。
	 * @param value
	 */
	public void setActualBonus(Long value) {
		this.actualBonus = value;
	}
	
	/**
	 * 取得實際獎金(賠率)。
	 * @return
	 */
	public Long getActualBonus() {
		return this.actualBonus;
	}

	/**
	 * 設定獎金組ID。
	 * @param value
	 */
	public void setAwardGroupId(Long value) {
		this.awardGroupId = value;
	}

	/**
	 * 取得獎金組ID。
	 * @return
	 */
	public Long getAwardGroupId() {
		return this.awardGroupId;
	}

	/**
	 * 設定創建時間。
	 * @param value
	 */
	public void setCreateTime(Date value) {
		this.createTime = value;
	}

	/**
	 * 取得創建時間。
	 * @return
	 */
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 設定更新時間。
	 * @param value
	 */
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}

	/**
	 * 取得更新時間。
	 * @return
	 */
	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * 取得狀態。
	 * @return 1：進行中、2：已刪除、3:待審核、4:待發佈、5:未審核、6:未發佈
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 設定狀態。
	 * @param status 1：進行中、2：已刪除、3:待審核、4:待發佈、5:未審核、6:未發佈
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	 * 取得投注方式編碼(bet_method_code)。
	 * @return
	 */
	public Integer findMethodType() {
		if (betTypeCode != null) {
			if(betTypeCode.split("_").length>3)
			return Integer.parseInt(betTypeCode.split("_")[3]);
		}
		return null;
	}

	/**
	 * 取得保底獎金組最小獎金(雙色球使用)。
	 * @return
	 */
	public Long getActualBonusDown() {
		return actualBonusDown;
	}

	/**
	 * 設定保底獎金組最小獎金(雙色球使用)。
	 * @param actualBonusDown
	 */
	public void setActualBonusDown(Long actualBonusDown) {
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
	 * 取得六合彩奖金识别。
	 * @return
	 */
    public String getLhcCode() {
		return lhcCode;
	}

    /**
     * 設定六合彩奖金识别。
     * @param lhcCode
     */
	public void setLhcCode(String lhcCode) {
		this.lhcCode = lhcCode;
	}

	/**
	 * 取得六合彩理论奖金。
	 * @return
	 */
	public Long getLhcTheoryBonus() {
		return lhcTheoryBonus;
	}

	/**
	 * 設定六合彩理论奖金。
	 * @param lhcTheoryBonus
	 */
	public void setLhcTheoryBonus(Long lhcTheoryBonus) {
		this.lhcTheoryBonus = lhcTheoryBonus;
	}
	/**
	 * 取得六合彩多組奖金。
	 * @return
	 */
	public List<Long> getLhcMultBonus() {
		return lhcMultBonus;
	}
	/**
	 * 設定六合彩多組奖金。
	 * @param lhcMultBonus
	 */
	public void setLhcMultBonus(List<Long> lhcMultBonus) {
		this.lhcMultBonus = lhcMultBonus;
	}
}
