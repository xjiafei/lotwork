package com.winterframework.firefrog.game.web.dto;

import java.util.List;

/** 
* @ClassName: SlipsStrucForUI 
* @Description:  
* @author Alan
* @date 2013-10-22 下午2:34:25 
*  
*/
public class SlipsStrucForUI {

	private Long orderId;
	private Long slipId;
	private String gamePlayName;
	private Integer totbets;
	private Integer multiple;
	private Long totamount;
	private String moneyMode;
	private String status;
	private Long award;
	private Long winsRadio;
	private String betDetail;
	private Integer awardMode;	//奖金模式
	private Long groupAward;	//奖金组奖金
	private Long groupAwardDown;	//奖金组小奖金
	private Long retPoint;		//返点
	private Long retAward;	//返点奖金
	private Long diamondAmount;//鑽石加獎金額
	private Long diamondWin;//鑽石中獎金額
    private Long singleWin;	//單注奖金
	/**六合彩多組賠率*/
	private List<Long> lhcMultBonus;
	public Long getSlipId() {
		return slipId;
	}
	public void setSlipId(Long slipId) {
		this.slipId = slipId;
	}
	public String getGamePlayName() {
		return gamePlayName;
	}
	public void setGamePlayName(String gamePlayName) {
		this.gamePlayName = gamePlayName;
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
	public String getMoneyMode() {
		return moneyMode;
	}
	public void setMoneyMode(String moneyMode) {
		this.moneyMode = moneyMode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getAward() {
		return award;
	}
	public void setAward(Long award) {
		this.award = award;
	}
	public Long getWinsRadio() {
		return winsRadio;
	}
	public void setWinsRadio(Long winsRadio) {
		this.winsRadio = winsRadio;
	}
	public String getBetDetail() {
		return betDetail;
	}
	public void setBetDetail(String betDetail) {
		this.betDetail = betDetail;
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
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
