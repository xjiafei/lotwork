package com.winterframework.firefrog.fund.dao.vo;

import com.winterframework.firefrog.advert.web.dto.BaseEntity;

public class BypassConfigVO extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2970144134769251339L;
	/**ID*/
	private Long id;
	/**操作種類 0:充值 1:提現*/
	private Long type;
	/**充提渠道 0:DP 1:通匯 3:匯潮 4:匯博 5:SDPAY 7:华势  8：秒付*/
	private Long agency;
	/**充值分流金額下限*/
	private Long singleLowlimit;
	/**充值分流金額上限*/
	private Long singleUplimit;
	/**充值分流每日總金額上限*/
	private Long dailyUplimit;
	/**今日充值總金額*/
	private Long dailyCharge;
	/**充值分流開關 Y:開 N:關*/
	private String isOpen;
	/**充值方式 1:快捷充值 2:網銀 3:財富通 5:銀聯 6:支付寶(個人版) 7:微信 8:支付寶(企業版)*/
	private Long chargeWaySet;
	/**系統平台 0:PC端 1:APP端*/
	private Long platform;
	/**開放用戶 0:全用戶 1:舊用戶 2:新用戶*/
	private Long openUser;
	/**後台充值分流顯示開關 Y:開 N:關*/
	private String isBypassView;
	
	
	/**
	 * 取得ID。
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 設置ID。
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 取得操作種類。
	 * @return type
	 */
	public Long getType() {
		return type;
	}

	/**
	 * 設置操作種類。
	 * @param type
	 */
	public void setType(Long type) {
		this.type = type;
	}

	/**
	 * 取得充提渠道。
	 * @return agency
	 */
	public Long getAgency() {
		return agency;
	}

	/**
	 * 設置充提渠道。
	 * @param agency
	 */
	public void setAgency(Long agency) {
		this.agency = agency;
	}

	/**
	 * 取得充值分流金額下限。
	 * @return singleLowlimit
	 */
	public Long getSingleLowlimit() {
		return singleLowlimit;
	}

	/**
	 * 設置充值分流金額下限。
	 * @param singleLowlimit
	 */
	public void setSingleLowlimit(Long singleLowlimit) {
		this.singleLowlimit = singleLowlimit;
	}

	/**
	 * 取得充值分流金額上限。
	 * @return singleUplimit
	 */
	public Long getSingleUplimit() {
		return singleUplimit;
	}

	/**
	 * 設置充值分流金額上限。
	 * @param singleUplimit
	 */
	public void setSingleUplimit(Long singleUplimit) {
		this.singleUplimit = singleUplimit;
	}

	/**
	 * 取得充值分流每日總金額上限。
	 * @return dailyUplimit
	 */
	public Long getDailyUplimit() {
		return dailyUplimit;
	}

	/**
	 * 設置充值分流每日總金額上限。
	 * @param dailyUplimit
	 */
	public void setDailyUplimit(Long dailyUplimit) {
		this.dailyUplimit = dailyUplimit;
	}

	/**
	 * 取得今日充值總金額。
	 * @return dailyCharge
	 */
	public Long getDailyCharge() {
		return dailyCharge;
	}

	/**
	 * 設置今日充值總金額。
	 * @param dailyCharge
	 */
	public void setDailyCharge(Long dailyCharge) {
		this.dailyCharge = dailyCharge;
	}

	/**
	 * 取得充值分流開關。
	 * @return isOpen
	 */
	public String getIsOpen() {
		return isOpen;
	}

	/**
	 * 設置充值分流開關。
	 * @param isOpen
	 */
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * 取得充值方式。
	 * @return chargeWaySet
	 */
	public Long getChargeWaySet() {
		return chargeWaySet;
	}

	/**
	 * 設置充值方式。
	 * @param chargeWaySet
	 */
	public void setChargeWaySet(Long chargeWaySet) {
		this.chargeWaySet = chargeWaySet;
	}

	/**
	 * 取得系統平台。
	 * @return platform
	 */
	public Long getPlatform() {
		return platform;
	}

	/**
	 * 設置系統平台。
	 * @param platform
	 */
	public void setPlatform(Long platform) {
		this.platform = platform;
	}

	/**
	 * 取得開放用戶。
	 * @return openUser
	 */
	public Long getOpenUser() {
		return openUser;
	}

	/**
	 * 設置開放用戶。
	 * @param openUser
	 */
	public void setOpenUser(Long openUser) {
		this.openUser = openUser;
	}

	/**
	 * 取得後台充值分流顯示開關。
	 * @return isBypassView
	 */
	public String getIsBypassView() {
		return isBypassView;
	}

	/**
	 * 設置後台充值分流顯示開關。
	 * @param isBypassView
	 */
	public void setIsBypassView(String isBypassView) {
		this.isBypassView = isBypassView;
	}

}
