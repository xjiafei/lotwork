package com.winterframework.firefrog.game.entity;

import java.util.Date;
import java.util.List;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GamePackageItem {

	private Long id;
	private GamePackage gamePackage;
//	private Integer betMethodCode;
	private MoneyMode moneyMode;
	private Long totbets;
	private Long totamount;
	private Integer multiple;
	private String betDetail;
	private Date createTime;
	private Long itemAmount;
	private GameBetType gameBetType;
	private GameAwardGroupEntity gameAwardGroup;
	private List<PackageItemAssist> itemAssistList;
	private Integer mutlAward;
	private Integer mutliple;
	private String retPointChain;
	private Long retPoint;
	private Long evaluateAward; //预计奖金
//	private Integer gameGroupCode;
//	private Integer gameSetCode;
	private FileMode fileMode;
	private Integer awardMode;	//奖金模式
	private Long retAward;	//返点奖金
	private Long diamondAmount = 0l; 
    private Double odds;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRetPoint() {
		return retPoint;
	}

	public void setRetPoint(Long retPoint) {
		this.retPoint = retPoint;
	}

	public GamePackage getGamePackage() {
		return gamePackage;
	}

	public void setGamePackage(GamePackage gamePackage) {
		this.gamePackage = gamePackage;
	}

	/*public Integer getBetMethodCode() {
		return betMethodCode;
	}

	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}*/

	public MoneyMode getMoneyMode() {
		return moneyMode;
	}

	public void setMoneyMode(MoneyMode moneyMode) {
		this.moneyMode = moneyMode;
	}

	public Long getTotbets() {
		return totbets;
	}

	public void setTotbets(Long totbets) {
		this.totbets = totbets;
	}

	public Long getTotamount() {
		return totamount;
	}

	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public String getBetDetail() {
		return betDetail;
	}

	public void setBetDetail(String betDetail) {
		this.betDetail = betDetail;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(Long itemAmount) {
		this.itemAmount = itemAmount;
	}

/*	public Integer getGameGroupCode() {
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
	}*/
	
	/*@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Packageid", getGamePackage().getId())
				.append("BetMethodCode", getBetMethodCode()).append("MoneyMode", getMoneyMode())
				.append("Totbets", getTotbets()).append("Totamount", getTotamount()).append("Multiple", getMultiple())
				.append("BetDetail", getBetDetail()).append("CreateTime", getCreateTime())
				.append("ItemAmount", getItemAmount()).append("GameGroupCode", getGameGroupCode())
				.append("GameSetCode", getGameSetCode()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getGamePackage().getId()).append(getBetMethodCode())
				.append(getMoneyMode()).append(getTotbets())
				.append(getBetDetail()).append(getItemAmount()).append(getGameGroupCode())
				.append(getGameSetCode()).toHashCode();
	}*/

	/**
	* Title: equals
	* Description: 请勿轻易修改此类的equals和hash,code方法  用于投注方案item去重处理
	* @param obj
	* @return 
	* @see java.lang.Object#equals(java.lang.Object) 
	*/
	/*@Override
	public boolean equals(Object obj) {
		if (obj instanceof GamePackageItem == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GamePackageItem other = (GamePackageItem) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getGamePackage().getId(), other.getGamePackage().getId())

		.append(getBetMethodCode(), other.getBetMethodCode())

		.append(getMoneyMode(), other.getMoneyMode())

		.append(getTotbets(), other.getTotbets())

		.append(getBetDetail(), other.getBetDetail())

		.append(getItemAmount(), other.getItemAmount())

		.append(getGameGroupCode(), other.getGameGroupCode())

		.append(getGameSetCode(), other.getGameSetCode())

		.isEquals();
	}*/

	public FileMode getFileMode() {
		return fileMode;
	}

	public void setFileMode(FileMode fileMode) {
		this.fileMode = fileMode;
	}

	public GameBetType getGameBetType() {
		return gameBetType;
	}

	public void setGameBetType(GameBetType gameBetType) {
		this.gameBetType = gameBetType;
	}

	public GameAwardGroupEntity getGameAwardGroup() {
		return gameAwardGroup;
	}

	public void setGameAwardGroup(GameAwardGroupEntity gameAwardGroup) {
		this.gameAwardGroup = gameAwardGroup;
	}

	public List<PackageItemAssist> getItemAssistList() {
		return itemAssistList;
	}

	public void setItemAssistList(List<PackageItemAssist> itemAssistList) {
		this.itemAssistList = itemAssistList;
	}

	public Integer getMutlAward() {
		return mutlAward;
	}

	public void setMutlAward(Integer mutlAward) {
		this.mutlAward = mutlAward;
	}

	public Integer getMutliple() {
		return mutliple;
	}

	public void setMutliple(Integer mutliple) {
		this.mutliple = mutliple;
	}

	public String getRetPointChain() {
		return retPointChain;
	}

	public void setRetPointChain(String retPointChain) {
		this.retPointChain = retPointChain;
	}

	public Long getEvaluateAward() {
		if(null==evaluateAward){
			evaluateAward=0L;
		}
		return evaluateAward;
	}

	public void setEvaluateAward(Long evaluateAward) {
		this.evaluateAward = evaluateAward;
	}
	public Integer getAwardMode() {
		if(null == awardMode){
			awardMode=1;
		}
		return awardMode;
	}

	public void setAwardMode(Integer awardMode) {
		this.awardMode = awardMode;
	}

	public Long getRetAward() {
		if(null==retAward){
			retAward=0L;
		}
		return retAward;
	}

	public void setRetAward(Long retAward) {
		this.retAward = retAward;
	}

	public Long getDiamondAmount() {
		return diamondAmount;
	}

	public void setDiamondAmount(Long diamondAmount) {
		this.diamondAmount = diamondAmount;
	}
	public Double getOdds() {
		return odds;
	}

	public void setOdds(Double odds) {
		this.odds = odds;
	}
}
