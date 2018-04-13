package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GamePoint;

public class SlipsStrucDTO implements Serializable {

	private static final long serialVersionUID = 5274618445045491618L;

	/** 注单ID */
	private Long slipid;
	/** 玩法群 */
	private Integer gameGroupCode;
	/** 玩法组 */
	private Integer gameSetCode;
	/** 投注方式 */
	private Integer betMethodCode;
	/** 注数 */
	private Integer totbets;
	/** 倍数 */
	private Integer multiple;
	/** 注单总金额 */
	private Long totamount;
	/** 元角模式1 元 2 角 */
	private Integer moneyMode;
	private String moneyModeName;
	/** 状态1:等待开奖 2:中奖 3:撤销 4:未中奖 */
	private Integer status;
	/** 玩法 */
	private String gamePlayName;
	/**状态名 */
	private String statusName;

	/**奖金*/
	private Long award;

	private String betDetail;

	private String betDetailShow;

	private List<GamePoint> gamePoints;

	private Integer gamePointCount;
	private Integer awardMode;	//奖金模式
	private Long groupAward;	//奖金组奖金
	private Long groupAwardDown;	//奖金组小奖金
	private Long retPoint;		//返点
	private Long retAward;	//返点奖金
	private Long diamondAmount;//鑽石加獎金額
	private Long diamondWin;//鑽石中獎金額
	/**六合彩多組賠率*/
	private List<Long> lhcMultBonus;
    //六合彩-賠率
	private Long singleWin;
	public Long getSlipid() {
		return slipid;
	}

	public void setSlipid(Long slipid) {
		this.slipid = slipid;
	}

	public Integer getGameGroupCode() {
		return gameGroupCode;
	}

	public void setGameGroupCode(Integer gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	public Integer getGameSetCode() {
		return gameSetCode;
	}

	public void setGameSetCode(Integer gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	public Integer getBetMethodCode() {
		return betMethodCode;
	}

	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}

	public Integer getTotbets() {
		return totbets;
	}

	public void setTotbets(Integer totbets) {
		this.totbets = totbets;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public Long getTotamount() {
		return totamount;
	}

	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}

	public Integer getMoneyMode() {
		return moneyMode;
	}

	public void setMoneyMode(Integer moneyMode) {
		this.moneyMode = moneyMode;
	}

	public String getMoneyModeName() {
		return moneyModeName;
	}

	public void setMoneyModeName(String moneyModeName) {
		this.moneyModeName = moneyModeName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getGamePlayName() {
		return gamePlayName;
	}

	public void setGamePlayName(String gamePlayName) {
		this.gamePlayName = gamePlayName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Long getAward() {
		return award;
	}

	public void setAward(Long award) {
		this.award = award;
	}

	public String getBetDetail() {
		return betDetail;
	}

	public void setBetDetail(String betDetail) {
		this.betDetail = betDetail;
	}

	public String getBetDetailShow() {
		return betDetailShow;
	}

	public void setBetDetailShow(String betDetailShow) {
		this.betDetailShow = betDetailShow;
	}

	public List<GamePoint> getGamePoints() {
		return gamePoints;
	}

	public void setGamePoints(List<GamePoint> gamePoints) {
		this.gamePoints = gamePoints;
	}

	public Integer getGamePointCount() {
		if (gamePoints == null) {
			gamePointCount = 0;
		} else {
			gamePointCount = gamePoints.size();
		}
		return gamePointCount;
	}

	public void setGamePointCount(Integer gamePointCount) {
		this.gamePointCount = gamePointCount;
	}

	public Integer getAwardMode() {
		return awardMode;
	}

	public void setAwardMode(Integer awardMode) {
		this.awardMode = awardMode;
	}

	public Long getGroupAward() {
		return groupAward;
	}

	public void setGroupAward(Long groupAward) {
		this.groupAward = groupAward;
	}

	public Long getRetPoint() {
		return retPoint;
	}

	public void setRetPoint(Long retPoint) {
		this.retPoint = retPoint;
	}

	public Long getRetAward() {
		return retAward;
	}

	public void setRetAward(Long retAward) {
		this.retAward = retAward;
	}

	public Long getGroupAwardDown() {
		return groupAwardDown;
	}

	public void setGroupAwardDown(Long groupAwardDown) {
		this.groupAwardDown = groupAwardDown;
	}

	public Long getDiamondAmount() {
		return diamondAmount;
	}

	public void setDiamondAmount(Long diamondAmount) {
		this.diamondAmount = diamondAmount;
	}

	public Long getDiamondWin() {
		return diamondWin;
	}

	public void setDiamondWin(Long diamondWin) {
		this.diamondWin = diamondWin;
	}
	
    public Long getSingleWin() {
		return singleWin;
	}

	public void setSingleWin(Long singleWin) {
		this.singleWin = singleWin;
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
