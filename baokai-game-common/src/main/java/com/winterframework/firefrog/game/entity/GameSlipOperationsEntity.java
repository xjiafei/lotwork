package com.winterframework.firefrog.game.entity;

/** 
* @ClassName: GameOrderOperationsDetailEntity 
* @Description: 注单后台运营entity
* @author Alan
* @date 2013-10-17 下午4:30:51 
*  
*/
public class GameSlipOperationsEntity {

	/** 訂单ID */
	private Long orderId;
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
	/** 元角模式 */
	private Integer moneyMode;
	/** 状态 */
	private Integer status;
	/**奖金*/
	private Long award;
	/**中/投比*/
	private Long winsRadio;
	/**是否是文件类型*/
	private Integer fileMode;
	/** 投注内容 */
	private String betDetail;
	
	private String betTypeCode;
	private Integer awardMode;	//奖金模式
	private Long groupAward;	//奖金组奖金
	private Long groupAwardDown;	//奖金组小奖金
	private Long retPoint;		//返点
	private Long retAward;	//返点奖金
	private Long diamondAmount = 0l;//鑽石加獎金額
	private Long diamondWin = 0l;//鑽石中獎金額
	/** 中獎注數 */
	private Long winNumber ; 

private Long singleWin;	//單注奖金
	public Long getSlipid() {
		return slipid;
	}
	public void setSlipid(Long slipid) {
		this.slipid = slipid;
	}
	public Integer getGameGroupCode() {
		String[] bets =  betTypeCode.split("_");
		return Integer.parseInt(bets[0]);
	}
	public void setGameGroupCode(Integer gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}
	public Integer getGameSetCode() {
		String[] bets =  betTypeCode.split("_");
		return Integer.parseInt(bets[1]);
	}
	public void setGameSetCode(Integer gameSetCode) {
		this.gameSetCode = gameSetCode;
	}
	public Integer getBetMethodCode() {
		String[] bets =  betTypeCode.split("_");
		return Integer.parseInt(bets[1]);
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
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
	public Integer getFileMode() {
		return fileMode;
	}
	public void setFileMode(Integer fileMode) {
		this.fileMode = fileMode;
	}
	public String getBetDetail() {
		return betDetail;
	}
	public void setBetDetail(String betDetail) {
		this.betDetail = betDetail;
	}
	public String getBetTypeCode() {
		return betTypeCode;
	}
	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
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
	public Long getWinNumber() {
		return winNumber;
	}
	public void setWinNumber(Long winNumber) {
		this.winNumber = winNumber;
	}
	
}
