package com.winterframework.firefrog.game.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封鎖號碼物件。
 * @author Pogi.Lin
 */
public class LockPoint {
	/**注單明細的單注獎金(六合彩)、注單明細的倍數(其他彩種)*/
	private Long beishu;
	private String betTotal;
	/**实际可投倍数*/
	private Long realBeishu;
	private String betDetail;
	/**可投金額*/
	private Long amount;
	/**<收限制号码，实际可投倍数>*/
	private Map<String,Long> locks=new HashMap<String,Long>();
	/**有变价的号码*/
	private List<Points> points=new ArrayList<Points>();
	
	/**
	 * 設定注單明細的單注獎金(六合彩)、注單明細的倍數(其他彩種)。
	 * @return
	 */
	public Long getBeishu() {
		return beishu;
	}
	/**
	 * 取得注單明細的單注獎金(六合彩)、注單明細的倍數(其他彩種)。
	 * @param beishu
	 */
	public void setBeishu(Long beishu) {
		this.beishu = beishu;
	}
	/**
	 * locks 是否有資料。
	 * @return null 亦回傳 true
	 */
	public Boolean getIsLocks() {
		if(locks==null){
			return true;
		}
		return locks.size()>0;
	}
	/**
	 * points 是否有資料。
	 * @return
	 */
	public Boolean getIsChange() {
		return this.points.size()>0;
	}
	/**
	 * 取得<收限制号码，实际可投倍数>。
	 * @return
	 */
	public Map<String, Long> getLocks() {
		return locks;
	}
	/**
	 * 設定<收限制号码，实际可投倍数>。
	 * @param locks
	 */
	public void setLocks(Map<String, Long> locks) {
		this.locks = locks;
	}
	/**
	 * 取得有变价的号码。
	 * @return
	 */
	public List<Points> getPoints() {
		return points;
	}
	/**
	 * 設定有变价的号码。
	 * @param points
	 */
	public void setPoints(List<Points> points) {
		this.points = points;
	}
	/**
	 * 取得实际可投倍数。
	 * @return
	 */
	public Long getRealBeishu() {
		return realBeishu;
	}
	/**
	 * 設定实际可投倍数。
	 * @param realBeishu
	 */
	public void setRealBeishu(Long realBeishu) {
		this.realBeishu = realBeishu;
	}
	
	public String getBetTotal() {
		return betTotal;
	}
	public void setBetTotal(String betTotal) {
		this.betTotal = betTotal;
	}
	public String getBetDetail() {
		return betDetail;
	}
	public void setBetDetail(String betDetail) {
		this.betDetail = betDetail;
	}
	/**
	 * 取得 beishu = multy, locks 為空物件, realBeishu = 0 的 LockPoint 物件。
	 * @param multy 注單明細的單注獎金(六合彩)、注單明細的倍數(其他彩種)
	 * @return
	 */
	public static LockPoint getEmptyLockPoint(Long multy){
		LockPoint lp=new LockPoint();
		lp.setBeishu(multy);
		lp.setLocks(new HashMap<String, Long>());
		lp.setRealBeishu(0L);
		return lp;
	}
	/**
	 * 取得可投金額。
	 * @return
	 */
	public Long getAmount() {
		return amount;
	}
	/**
	 * 設定可投金額。
	 * @param amount
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}
}
