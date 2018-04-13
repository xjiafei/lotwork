package com.winterframework.firefrog.game.entity;

public class PackageItemAssist extends ItemAssist {

	private Long itemId;
	private static final long serialVersionUID = 6715521322858731781L;

	public PackageItemAssist(ItemAssist itemAssist) {
		this.betTypeCode = itemAssist.getBetTypeCode();
		this.createTime = itemAssist.getCreateTime();
		this.evaluatAward = itemAssist.getEvaluatAward();
		this.evaluatAwardDown = itemAssist.getEvaluatAwardDown();
		this.id = itemAssist.getId();
		this.updateTime = itemAssist.getUpdateTime();
	}

	public PackageItemAssist() {
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

}
