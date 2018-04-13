package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class RetSettingRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5207868059890009697L;
	
	/**鏈結類型；0:玩家、1:代理、2:快捷設置*/
	private Long type;
	/**鏈結設置；1:手動、2:快捷*/
	private Long setUp;
	/**鏈結有效天數；-1:永久、其他是 1,5,10,30*/
	private Long days;
	/**自行設定的說明字串*/
	private String memo;
	/**MP 查 ci db*/
	private String domain;
	/**快捷設置的統一返點值*/
	private Float setValue;
	private List<UserAwardStruc> infos;
	
	/**
	 * 取得快捷設置的統一返點值。
	 * @return
	 */
	public Float getSetValue() {
		return setValue;
	}
	/**
	 * 設定快捷設置的統一返點值。
	 * @param setValue
	 */
	public void setSetValue(Float setValue) {
		this.setValue = setValue;
	}
	/**
	 * 取得MP 查 ci db。
	 * @return
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * 設定MP 查 ci db。
	 * @param domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	/**
	 * 取得鏈結類型。
	 * @return 0:玩家、1:代理、2:快捷設置
	 */
	public Long getType() {
		return type;
	}
	/**
	 * 設定鏈結類型。
	 * @param type 0:玩家、1:代理、2:快捷設置
	 */
	public void setType(Long type) {
		this.type = type;
	}
	/**
	 * 取得鏈結設置。
	 * @return 1:手動、2:快捷
	 */
	public Long getSetUp() {
		return setUp;
	}
	/**
	 * 設定鏈結設置。
	 * @param setUp 1:手動、2:快捷
	 */
	public void setSetUp(Long setUp) {
		this.setUp = setUp;
	}
	/**
	 * 取得鏈結有效天數。
	 * @return -1:永久、其他是 1,5,10,30
	 */
	public Long getDays() {
		return days;
	}
	/**
	 * 設定鏈結有效天數。
	 * @param days -1:永久、其他是 1,5,10,30
	 */
	public void setDays(Long days) {
		this.days = days;
	}
	/**
	 * 取得自行設定的說明字串。
	 * @return
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * 設定自行設定的說明字串。
	 * @param memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public List<UserAwardStruc> getInfos() {
		return infos;
	}
	public void setInfos(List<UserAwardStruc> infos) {
		this.infos = infos;
	}
}
