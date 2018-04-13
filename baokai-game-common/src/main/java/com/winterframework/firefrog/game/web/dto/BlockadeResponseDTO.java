package com.winterframework.firefrog.game.web.dto;

/**
 * 封锁号码信息（合并前）。
 * @author Pogi.Lin
 */
public class BlockadeResponseDTO {

	/**封锁的号码*/
	protected String blockadeDetail;
	/**投注倍数*/
	protected Long beishu;
	/**实际倍数*/
	protected Long realBeishu;
	/**封鎖後金額*/
	protected Long afterAmount;
	/**封鎖前金額*/
	protected Long beforeAmount;
	
	/**
	 * 取得封锁的号码。
	 * @return
	 */
	public String getBlockadeDetail() {
		return blockadeDetail;
	}
	/**
	 * 設定封锁的号码。
	 * @param blockadeDetail
	 */
	public void setBlockadeDetail(String blockadeDetail) {
		this.blockadeDetail = blockadeDetail;
	}
	/**
	 * 取得投注倍数。
	 * @return
	 */
	public Long getBeishu() {
		return beishu;
	}
	/**
	 * 設定投注倍数。
	 * @param beishu
	 */
	public void setBeishu(Long beishu) {
		this.beishu = beishu;
	}
	/**
	 * 取得实际倍数。
	 * @return
	 */
	public Long getRealBeishu() {
		return realBeishu;
	}
	/**
	 * 設定实际倍数。
	 * @param realBeishu
	 */
	public void setRealBeishu(Long realBeishu) {
		this.realBeishu = realBeishu;
	}
	/**
	 * 取得封鎖後金額。
	 * @return
	 */
	public Long getAfterAmount() {
		return afterAmount;
	}
	/**
	 * 設定封鎖後金額。
	 * @param afterAmount
	 */
	public void setAfterAmount(Long afterAmount) {
		this.afterAmount = afterAmount;
	}
	/**
	 * 取得封鎖前金額。
	 * @return
	 */
	public Long getBeforeAmount() {
		return beforeAmount;
	}
	/**
	 * 設定封鎖前金額。
	 * @param beforeAmount
	 */
	public void setBeforeAmount(Long beforeAmount) {
		this.beforeAmount = beforeAmount;
	}
	
}
